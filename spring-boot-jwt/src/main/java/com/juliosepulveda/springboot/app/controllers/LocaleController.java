package com.juliosepulveda.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Con este controlador lo que realizamos es poder obtener la última URL que tenemos antes de cambiar el idioma cuando se pulsan alguno de los botones de idioma.
 * De esta forma si por ejemplo en la lista estamos en la página 3 y cambiamos de idioma, no perdemos el cursor sobre esa página al cambiarlo
 */
@Controller
public class LocaleController {

	@GetMapping("/locale")
	public String locale(HttpServletRequest request) {
		
		//Esto nos devuelve el ultimo link que ha tenido la url
		String ultimaUrl = request.getHeader("referer");
		
		return "redirect:" + ultimaUrl;
	}
}
