package
com.juliosepulveda.springboot.web.app.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juliosepulveda.springboot.web.app.models.Usuario;

//Toda clase marcada con @Component (como podemos ver en la clase Controller si pinchamos en ella)
//significa que va apertenecer a Spring
@Controller
//Si añadimos el decorador RequestMapping a la clase del controlador, le estamos metiendo una ruta previa a todos los métodos 
//de dicho controlador. En este caso para acceder a las rutas de cada metodo previamente tenemos que tener en la URL http://localhost:8080/app
@RequestMapping("/app")
public class IndexController {
	
	//Con la anotación @Value inyectamos el valor de las variables definidas en el fichero textos.properties
	@Value("${texto.indexcontroller.index.titulo}")
	private String textoIndex;
	
	@Value("${texto.indexcontroller.perfil.titulo}")
	private String textoPerfil;
	
	@Value("${texto.indexcontroller.listar.titulo}")
	private String textoListar;
	
	/**
	 * En el controlador cada metodo va a manejar una pagina HTTP. Por ello se le pone el decorador RequestMapping para indicar
	 * la ruta HTTP que maneja dicho método. En este caso sería la ruta http://localhost:8080/index.
	 * Por defecto son metodos GET y no es necesario indicarlo. Tambien se podría poner en vez de RequestMapping como GetMapping, PostMapping, etc 
	 * Se pueden redirigir a varias rutas cada método usando las llaves
	 * @return
	 */
	@GetMapping(value = {"index", "/", "", "home"})
	public String index(Model model) {

//		Para poder pasar campos al html tenemos que recibir un Model o ModelMap o Map o ModelAndView en el método y a este 
//		añadirle atributos como	se ve a continuación:		
		model.addAttribute("titulo", textoIndex);
		
		return "index";
	}
	
	
	@RequestMapping("/perfil")
	public String perfil(Model model)
	{
		Usuario usuario = new Usuario();
		
		usuario.setNombre("Julio");
		usuario.setApellido("Sepúlveda");
		usuario.setEmail("julio240688@gmail.com");
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", textoPerfil.concat(usuario.getNombre()));
		
		return "perfil";
	}
	
	
	@RequestMapping("/listar")
	public String listar(Model model)
	{
		
		model.addAttribute("titulo", textoListar);
		
		return "listar";
	}
	
	/**
	 * Con este método y el decorador @ModelAttribute hacemos que el objeto usuarios esté disponible para todos los métodos del controlador	
	 * @return
	 */
	@ModelAttribute("usuarios")
	public List<Usuario> enviarUsuarios()
	{
//		List<Usuario> usuarios = new ArrayList<>();
//		
//		usuarios.add(new Usuario("Julio", "Sepúlveda", "julio@correo.com"));
//		usuarios.add(new Usuario("Rocío", "García", "rocio@correo.com"));
//		usuarios.add(new Usuario("África", "Sepúlveda", "africa@correo.com"));
		
		//Otra forma de hacer lo anterior
		List<Usuario> usuarios = Arrays.asList
				(
						new Usuario("Julio", "Sepúlveda", "julio@correo.com"),
						new Usuario("Rocío", "García", "rocio@correo.com"),
						new Usuario("África", "Sepúlveda", "africa@correo.com"),
						new Usuario("Leo", "Sepúlveda", "leo@correo.com")
				); 
		
		return usuarios;
	}
	
}
