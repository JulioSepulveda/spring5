package com.juliosepulveda.springboot.web.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Clase con métodos de ejemplo para recoger valores del la URL. 
 * @author jsepulveda
 *
 */
@Controller
@RequestMapping("/params")
public class ParamsController {

	//Con el @RequestParam("texto") recibimos el valor de texto indicado en la url (Ejemplo "http://localhost:8080/params/string?texto=Hola que tal")
	//En @RequestParam con el atributo required indicamos si es obligatori o no. Con defaultValue indicamos el valor por defecto
	@GetMapping("/string")
	public String param(@RequestParam(name = "texto", required = false, defaultValue = "Valor por defecto") String valorTexto, Model model)
	{
		model.addAttribute("resultado", "El texto enviado es: ".concat(valorTexto));
		
		//Al devolver params/ver, estamos indicando que dentro de la carpeta templates va a haber una carpeta params y dentro el HTML
		return "params/ver";
	}
		
	//En este método podemos ver que desde el html se envía un parámetro
	@GetMapping("/index")
	public String index()
	{
		return "params/index"; 	
	}
	
	//Recibir dos valores de la URL
	@GetMapping("/mix-params")
	public String param(@RequestParam String saludo, @RequestParam Integer numero, Model model)
	{
		model.addAttribute("resultado", "El saludo es: ' " + saludo + "' y el número es: '" + numero + "'");
		
		//Al devolver params/ver, estamos indicando que dentro de la carpeta templates va a haber una carpeta params y dentro el HTML
		return "params/ver";
	}
	
	//otra formade recoger los valores
	@GetMapping("/mix-params-request")
	public String param(HttpServletRequest request, Model model)
	{
		String saludo = request.getParameter("saludo");
		Integer numero = null;
		
		try 
		{
			numero = Integer.parseInt(request.getParameter("numero"));
		} 
		catch (NumberFormatException e) 
		{
			numero = 0;
		}
		
		model.addAttribute("resultado", "El saludo es: ' " + saludo + "' y el número es: '" + numero + "'");
		
		//Al devolver params/ver, estamos indicando que dentro de la carpeta templates va a haber una carpeta params y dentro el HTML
		return "params/ver";
	}
}
