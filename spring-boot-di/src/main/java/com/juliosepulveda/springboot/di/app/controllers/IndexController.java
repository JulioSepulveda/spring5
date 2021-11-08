package com.juliosepulveda.springboot.di.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.juliosepulveda.springboot.di.app.models.service.IServicio;

@Controller
public class IndexController {
	
	//Con autowired inyectamos el objeto declarado en el atributo. Sustituye la igualacion a new MiServicio(). Para poder tener esta anotación
	//La clase a la que se hace referencia tiene que estar anotada como component o service etc...
	//El autowired se lo podemos poner al objeto, al setter del objeto o al contructor de la clase siempre y cuando este establezca el valor del objeto, como se puede ver más abajo
	//En el caso del constructor no es necesario indicarlo, pero es mejor indicarlo para que quede más claro
	/*
	 * En vez de crear el objeto directamente como la clase, lo hacemos con la interfaz
	 */
	@Autowired
	/*
	 * Si utilizamos en el controlador la anotación @Qualifier seguido del nombre del servicio, estaríamos indicando cual de los servicios iguales queremos que se ejecute.
	 * Si no indicaramos nada usaria el servicio que tiene la anotación @Primary
	 */
	@Qualifier("miServicioSimple")
	private IServicio servicio;
	
//	@Autowired
//	public IndexController(IServicio servicio) {
//		this.servicio = servicio;
//	}

	@GetMapping({"/", "", "/index"})
	public String index(Model model){
		
		model.addAttribute("objeto", servicio.operacion());
		
		return "index";
	}	

}
