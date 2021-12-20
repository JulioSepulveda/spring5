package com.juliosepulveda.springboot.app.controllers;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

@Controller
public class ClienteController {

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
	 * Método para recuperar las imagenes del archivo
	 * En el vaue se indica que recupere el nombredel archivo sin el .jpg etc
	 */
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
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		/*
		 * En vez de realizar varias consultas a la BBDD, realizamos la consulta pero solo con una query
		 */
		//Cliente cliente = clienteService.findOne(id);
		Cliente cliente = clienteService.fetchByIdWithFacturas(id);
		
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		
		model.put("titulo", "Detalle cliente " + cliente.getNombre());
		model.put("cliente", cliente);
		
		
		return "ver";
	}
	
	/*
	 * Ussamos el @RequestParam para incluir la página. Le ponemos el valor por defecto a 0
	 */
	@RequestMapping(value="listar", method = RequestMethod.GET)
	public String Listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 4);
		
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		//Paginador
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", "Listado de clientes");

		//Comentamos este método para usar el pageable
		//model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		
		return "listar";
	}

	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model) {
		
		Cliente cliente = new Cliente();
		
		model.put("titulo", "Formulario de Cliente");
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
	@RequestMapping(value="form", method = RequestMethod.POST)
	public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
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
			flash.addFlashAttribute("info", "Has subido correctamente: '" + uniqueFileName + "'");
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
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Cliente cliente = null;
		
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
			}
		}
		else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser 0!");
			return "redirect:/listar";
		}
		
		model.put("titulo", "Editar Cliente");
		model.put("cliente", cliente);
		
		return "form";
	}
	
	/*
	 * Método para eliminar un cliente a a través del id. Este campo tiene que estar anotado con @PathVariable
	 */
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			
			Cliente cliente = clienteService.findOne(id);
			
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
			
			if (uploadFileService.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con exito!");
			}
			
		}
		
		return "redirect:/listar";
	}
}
