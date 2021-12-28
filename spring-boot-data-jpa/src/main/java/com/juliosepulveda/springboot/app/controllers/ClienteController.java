package com.juliosepulveda.springboot.app.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juliosepulveda.springboot.app.models.entity.Cliente;
import com.juliosepulveda.springboot.app.models.service.IClienteService;
import com.juliosepulveda.springboot.app.models.service.IUploadFileService;
import com.juliosepulveda.springboot.app.util.paginator.PageRender;

/*
 * La anotación EnableGlobalMethodSecurity es necesaria indicarla con el atributo securedEnabled a true para poder usar la anotación Secured en cada método para indicar
 * el role que permite el acceso a ese recurso
 * El PreAuthorize lo tenemos que activar con prePostEnabled. Es lo mismo que el Secured nada más que necesita utilizar el método hasRole() de la arquitectura.
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Controller
public class ClienteController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/*
	 * EL qualifier se utilizaría si tuvieramos más de una implementación de esta interfaz, para indicar que implementación queremos utilizar
	 * Para poderlo utilizar, tenemos que indicar en la clase Impl el nombre en la anotacion Persistence y después ese mismo aqui.
	 */
	@Autowired
	private IClienteService clienteService;	
	
	/*
	 * Interfad para el manejo de los archivos de imagenes 
	 */
	@Autowired
	private IUploadFileService uploadFileService;
	
	/*
	 * Atributo para saber a que fichero de mensajes dirigirse en función del idioma
	 */
	@Autowired
	private MessageSource messageSource;
	
	/*
	 * Método para recuperar las imagenes del archivo
	 * En el vaue se indica que recupere el nombredel archivo sin el .jpg etc
	 */
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource>verFoto(@PathVariable String filename) {
		
		Resource recurso = null;
		
		try {
			recurso = uploadFileService.load(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	/*
	 * Método para listar los datos del cliente y ver los detalles del mismo
	 */
	//@PreAuthorize("hasRole('ROLE_USER')")
	//Para usar el PreAuthorize usando más de un role
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {
		
		/*
		 * En vez de realizar varias consultas a la BBDD, realizamos la consulta pero solo con una query
		 */
		//Cliente cliente = clienteService.findOne(id);
		Cliente cliente = clienteService.fetchByIdWithFacturas(id);
		
		if(cliente == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
			return "redirect:/listar";
		}
		
		model.put("titulo", messageSource.getMessage("text.cliente.detalle.titulo", null, locale).concat(": ").concat(cliente.getNombre()));
		model.put("cliente", cliente);
		
		
		return "ver";
	}
	
	/*
	 * Ussamos el @RequestParam para incluir la página. Le ponemos el valor por defecto a 0
	 */
	@RequestMapping(value={"listar", "/"}, method = RequestMethod.GET)
	public String Listar(@RequestParam(name="page", defaultValue="0") int page, Model model, HttpServletRequest request, Locale locale) {
//	public String Listar(@RequestParam(name="page", defaultValue="0") int page, Model model, Authentication authentication, HttpServletRequest request) {
		
		/*
		 * Se comenta ya que vamos a probar con un metodo estatico. Para poder usar este tendríamos que utilizar la declaración del método comentada
		 */
//		if(authentication != null) {
//			logger.info("Hola usuario autenticado, tu username es: " + authentication.getName());
//		}
		
		//Otra forma de obtener el Authentication sin recibirlo directamente en el método
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(); Usuario autenticado, tu username es: " + auth.getName());
		}
		
		//Indica si tienes acceso o no a algún recurso SE TIENE MAS CONTROL
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Hola " + auth.getName() + " tienes acceso!");
		}
		else {
			logger.info("Hola " + auth.getName() + " NO tienes acceso!");
		}
		
		//Nos permirte validar el role es lo mismo que lo anterior pero usando la clase SecurityContextHolderAwareRequestWrapper en vez de la creada por nosotros
		//
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
		
		if(securityContext.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola " + auth.getName() + " tienes acceso!");
		}
		else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola " + auth.getName() + " NO tienes acceso!");
		}
		
		//Forma usando el HttpServerRequest, es lo mismo que lo anterior
		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando HttpServerRequest: Hola " + auth.getName() + " tienes acceso!");
		}
		else {
			logger.info("Forma usando HttpServerRequest: Hola " + auth.getName() + " NO tienes acceso!");
		}
		
		Pageable pageRequest = PageRequest.of(page, 4);
		
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		//Paginador
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));

		//Comentamos este método para usar el pageable
		//model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		
		return "listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model, Locale locale) {
		
		Cliente cliente = new Cliente();
		
		model.put("titulo", messageSource.getMessage("text.cliente.form.titulo.crear", null, locale));
		model.put("cliente", cliente);
		
		return "form";
	}
	
	/*
	 * El objeto recibido lo tenemos que anotar con @Valid para que pueda realizar las validaciones puestas en la clase Ciente
	 * La anotación @ModelAttribute solamente es necesaria si el objeto con el que se pasan los datos a la vista se llama diferente de la clase (en 
	 * este caso no sería necesario aunque si se especifica no pasa nada)
	 * El objeto BindingResult lo utilizamos para controlar si hay errores en el formulario. Es muy importante que esté puesto despés del 
	 * objeto al que hace referncia (en este caso de Cliente)
	 * Con el atributo RedirectAttributes enviamos un mensaje por pantalla del estado de la acción
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="form", method = RequestMethod.POST)
	public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, Locale locale) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.cliente.form.titulo", null, locale));
			return "form";
		}
		
		if (!foto.isEmpty()) {
			
			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null && cliente.getFoto().length() > 0) {
			
				uploadFileService.delete(cliente.getFoto());
			}
			
			String uniqueFileName= null;
			try {
				uniqueFileName = uploadFileService.copy(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/*
			 * Mostramos un mensaje de subido correctamente
			 */
			flash.addFlashAttribute("info", messageSource.getMessage("text.cliente.flash.foto.subir.success", null, locale) + "'" + uniqueFileName + "'");
			/*
			 * Guardamos el nombre del archivo en la BBDD
			 */
			cliente.setFoto(uniqueFileName);
			
		}
		
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";
		
		clienteService.save(cliente);
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:listar";
	}
	
	/*
	 * Método para editar un cliente a a través del id. Este campo tiene que estar anotado con @PathVariable
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {
		
		Cliente cliente = null;
		
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
			}
		}
		else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.id.error", null, locale));
			return "redirect:/listar";
		}
		
		model.put("titulo", messageSource.getMessage("text.cliente.form.titulo.editar", null, locale));
		model.put("cliente", cliente);
		
		return "form";
	}
	
	/*
	 * Método para eliminar un cliente a a través del id. Este campo tiene que estar anotado con @PathVariable
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash, Locale locale) {
		if (id > 0) {
			
			Cliente cliente = clienteService.findOne(id);
			
			clienteService.delete(id);
			flash.addFlashAttribute("success", messageSource.getMessage("text.cliente.flash.eliminar.success", null, locale));
			
			if (uploadFileService.delete(cliente.getFoto())) {
				String mensajeFotoEliminar = String.format(messageSource.getMessage("text.cliente.flash.foto.eliminar.success", null, locale), cliente.getFoto());
				flash.addFlashAttribute("info", mensajeFotoEliminar);
			}
			
		}
		
		return "redirect:/listar";
	}
	
	/*
	 * Devuelve el rol del usuario
	 */
	private boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if (context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		//Cualquier tipo de objeto que implemente esta interfaz
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				logger.info("Hola usuario " + auth.getName() + " tu rol es: " + authority.getAuthority());
				return true;
			}
		}
		return false;
	}
}
