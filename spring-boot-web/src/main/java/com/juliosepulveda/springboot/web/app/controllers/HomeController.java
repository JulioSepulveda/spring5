package com.juliosepulveda.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value = "/")
	public String home() 
	{
		//Con redirect envisamos a otra página dentro de nuesta app o incluso a otra web (por ejemplo google). La diferencia con cargar  
		//una vista es que al redirigir se reinicia el proceso y se pierden todos los request etc...
		//return "redirect:/app/index";
		//return "redirect:https://www.google.com";
		
		//La otra forma de hacer la redirección es con forward. La diferencia es que este no reinicia el request. Si vemos no cambia la url cuando cambia a otra página
		//El forward solo se puede usar para páginas propias de nuestra app
		return "forward:/app/index";
	}
	
}
