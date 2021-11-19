package com.juliosepulveda.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.juliosepulveda.springboot.form.app.editors.NombreMayusculaEditor;
import com.juliosepulveda.springboot.form.app.editors.PaisPropertyEditor;
import com.juliosepulveda.springboot.form.app.editors.RolesEditor;
import com.juliosepulveda.springboot.form.app.models.domain.Pais;
import com.juliosepulveda.springboot.form.app.models.domain.Role;
import com.juliosepulveda.springboot.form.app.models.domain.Usuario;
import com.juliosepulveda.springboot.form.app.services.PaisService;
import com.juliosepulveda.springboot.form.app.services.RoleService;
import com.juliosepulveda.springboot.form.app.validation.UsuarioValidador;

/*
 * Con la anotación @SessionAttributes indicamos el objeto que sea persistente durante la sesión. Por ejemplo si ponemos el usuario como atributo de la 
 * sesión, el valor de identificador se podrá utilizar en las diferentes páginas aunque no se use en el formulario.
 */
@Controller
@SessionAttributes("usuario")
public class FormController {
	
	@Autowired
	private UsuarioValidador validador;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private PaisPropertyEditor paisEditor;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RolesEditor roleEditor;
	
	/*
	 * Con la siguiente función lo que hacemos es registrar el validador en el WebDataBinder y de esta forma no es necesario llamar a este validador
	 * desde los métodos del controlador. Automáticamente ejecuta las validaciones
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		/*
		 * Se tiene que usar addValidators en vez de setValidator ya que con el set solamente se validaría lo de la clase UsuarioValidador
		 * y no las anotaciones de la clase Usuario
		 */
		//binder.setValidator(validador);
		binder.addValidators(validador);
		
		//Control de la fehca con initBinder
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Con esta enttencia le indicamos que es estricto para evitar ambigüedad con la fecha
		dateFormat.setLenient(false);
		/*
		 * Se puede usar la primera sentencia, la cual aplicaría a todos los campos de tipo fecha. La segunda sentencia solo aplicaría para el campo
		 * fecha de nacimiento
		 * El true o false del final de la sentencia es para decir si acepta nulos o no
		 */
		//binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, true));
		
		//Con esta customización hacemos que todos los campos de tipo String modifique el texto poniendolos en mayúsculas
		binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
		
		//Con esta customización conseguimos poder enviar el objeto país completo cuando se seleccione un pais en el combo paisClas
		binder.registerCustomEditor(Pais.class, "paisClas", paisEditor);
		
		//Con esta customización conseguimos poder enviar el objeto roles completo cuando se seleccionen los roless
		binder.registerCustomEditor(Role.class, "roles", roleEditor);
	}
	
	/*
	 * Rellena los posibles valores del radio Button
	 */
	@ModelAttribute("genero")
	public List<String> genero() {
		return Arrays.asList("Hombre", "Mujer");
	}
	
	/*
	 * Metodo para rellenar la lista de paises
	 * Con el modelAttribute enviamos esta lista al html
	 */
	@ModelAttribute("paises")
	public List<String> paises() {
		return Arrays.asList("España", "Mexico", "Chile", "Argentina", "Peru", "Colombia", "Venezuela");
	}
	
	/*
	 * Método para rellenar el combo box de paises pero esta vez con un Map
	 */
	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap() {
		Map<String, String> paises = new HashMap<String, String>();
		
		paises.put("ES", "España");
		paises.put("MX", "México");
		paises.put("CL", "Chile");
		paises.put("AR", "Argentina");
		paises.put("PE", "Perú");
		paises.put("CO", "Colombia");
		paises.put("VE", "Venezuela");
		
		return paises;
	}
	
	/*
	 * Método para rellenar un combo desde una clase creada por nosotros
	 * Rellenamos la lista desde nuestra interfaz PaisService
	 */
	@ModelAttribute("listaPaises")
	public List<Pais> paisesClas() {
		return paisService.listar();
	}
	
	@ModelAttribute("listaRolesString")
	public List<String> listaRolesString() {
		List<String> roles = new ArrayList<>();
		
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		roles.add("ROLE_MODERATOR");
		
		return roles;
	}
	
	@ModelAttribute("listaRoles")
	public List<Role> listaRoles() {
		return this.roleService.listar();
	}
	
	/*
	 * Método para rellenar el checkbox de roles pero esta vez con un Map
	 */
	@ModelAttribute("listaRolesMap")
	public Map<String, String> rolesMap() {
		Map<String, String> roles = new HashMap<String, String>();
		
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_USER", "Usuario");
		roles.put("ROLE_MODERATOR", "Moderador");
		
		return roles;
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		
		Usuario usuario = new Usuario();
		
		/*
		 * Incluimos los campos identificador, nombre y apellido por defecto
		 */
		usuario.setIdentificador("12.456.789-J");
		usuario.setIdentificador2("12.456.789-J");
		usuario.setNombre("Julio");
		usuario.setApellido("Sepúlveda");
		usuario.setUsername("JulioSep");
		usuario.setPassword("1234");
		usuario.setEmail("julio@email.com");
		usuario.setCuenta(1234);
		usuario.setHabilitar(true);
		usuario.setValorSecreto("Algún valor secreto...");
		//Para poder seleccionar un pais por defecto, dentro de la clase Pais tenemos que implementar el método toString
		usuario.setPaisClas(new Pais(3, "CL", "Chile"));
		//Para poder seleccionar un rol por defecto, se tiene que implementar el método equal en la clase Role
		usuario.setRoles(Arrays.asList(new Role (2, "Usuario", "ROLE_USER")));
		
		
		model.addAttribute("titulo", "Formulario Usuarios");
		/*
		 * Enviamos un usuario vacío para que no nos falle las asignaciones a los campos del valor de usuario la primera vez que se ejecuta el formulario.
		 * Esto es debido a que accedemos a los valores de usuario cuando nos devuelve algún error de validación.
		 */
		model.addAttribute("usuario", usuario);
		
		return "form";
	}
	
	/*
	 * Recoge los datos rellenos del formulario. Con @RequestParam recogemos los campos del html.
	 * RequestPAram se puede recoger usando el mismo nombre que en el HTML -->  @RequestParam String username
	 * o especificando el nombre del HTML si queremos poner otro aqui @RequestParam(name="username") String nombre
	 */
//	@PostMapping("/form")
//	public String procesar(Model model, @RequestParam String username, @RequestParam String password, @RequestParam String email) {
//		
//		Usuario usuario = new Usuario();
//		usuario.setUsername(username);
//		usuario.setPassword(password);
//		usuario.setEmail(email);
//		
//		//Incluimos los campos del HTML en el model para poder pasarselos a la vista
//		model.addAttribute("titulo", "Resultado formulario");
////		model.addAttribute("username", username);
////		model.addAttribute("password", password);
////		model.addAttribute("email", email);
//		
//		//Al tener la clase usuario, no es necesario pasar a la vista campo a campo sino que se pasa directamente el objeto usuario
//		model.addAttribute("usuario", usuario);
//		
//		return "resultado";
//	}
	
	/*
	 * Este metodo sustituye al anterior ya que es más eficiente. No es necesario recuperar todos los parámetros uno a uno ya que los tenemos
	 * en la clase usuario. Es muy importante que en esta clase usuario se tengan los atributos con el mismo nombre que en el HTML
	 * 
	 * Incluimos la anotación @Valid para utilizar las validaciones por defecto que tiene Java para los campos de la clase Usuario
	 * Esto se une junto con lasanotaciones incluidas en la clase usuario para cada uno de los campos
	 * Después el objeto BindingResult contiene los errores que devuelvan estas validaciones (en lallamada del método tiene que ir después del
	 * elemento que controla, en este caso de usuario) Con este objeto podemos gestionar cada uno de los errores que han dado los campos
	 */
	@PostMapping("/form")
	public String procesar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status) {

//		if (result.hasErrors()) {
//			Map<String, String> errores = new HashMap<>();
//			
//			result.getFieldErrors().forEach(err -> {
//				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
//			});
//			
//			model.addAttribute("error", errores);
//			
//			return "form";
//		}
		
		/*
		 * Para llamar a la clase validadora de campos
		 * se comenta ya que lo vamos a llamar automáticamente dede el método initBinder
		 */
		//validador.validate(usuario, result);
		
		/*
		 * Otra forma de hacer lo anterior de manera más automática
		 */
		if (result.hasErrors()) {	
			model.addAttribute("titulo", "Resultado formulario");
			return "form";
		}
		
		
		//model.addAttribute("usuario", usuario);
		/*
		 * Con status limpiamos los datos persistentes declarados en la anotación @SessionAttributes
		 */
		//status.setComplete();
		
		//Lo redirigimos para evitar que cada vez que se refresque la página se reenvie el formulario
		return "redirect:/ver";
	}
	
	@GetMapping("/ver")
	public String ver(@SessionAttribute(name="usuario", required = false) Usuario usuario, Model model, SessionStatus status) {
		
		if (usuario == null) {
			return "redirect:/form";
		}
		
		model.addAttribute("titulo", "Resultado formulario");
		
		status.setComplete();
		return "resultado";
	}
	
	
}
