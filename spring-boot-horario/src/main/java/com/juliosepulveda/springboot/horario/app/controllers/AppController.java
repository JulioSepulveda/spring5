package com.juliosepulveda.springboot.horario.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	/*
	 * Con value recuperamos los valores del fichero application.properties
	 */
	@Value("${config.horario.apertura}")
	private Integer apertura;
	
	@Value("${config.horario.cierre}")
	private Integer cierre;
	
	//Página para cuando está dentro del horario. El interceptor devuelve true
	@GetMapping({"/", "/index"})
	public String index(Model model) {
		
		model.addAttribute("titulo", "Bienvenido al horario de atención a clientes");
		return "index";
	}
	
	//Página para cuando está fuera del horario. El interceptor devuelve false
	@GetMapping({"/cerrado"})
	public String cerrado(Model model) {
		
		StringBuilder mensaje = new StringBuilder("Cerrado, por favor visitenos desde las ");
		mensaje.append(apertura);
		mensaje.append(" hrs. hasta las ");
		mensaje.append(cierre);
		mensaje.append(" hrs. Gracias.");
		
		
		model.addAttribute("titulo", "Fuera del horario de atención al cliente");
		model.addAttribute("mensaje", mensaje);
		
		
		return "cerrado";
	}

}
