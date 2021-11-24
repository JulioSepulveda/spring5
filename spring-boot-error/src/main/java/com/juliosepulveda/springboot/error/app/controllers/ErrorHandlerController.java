package com.juliosepulveda.springboot.error.app.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.juliosepulveda.springboot.error.app.errors.UsuarioNoEncontradoException;

/*
 * Con esta clase anotada con @ControllerAdvice lo que hace es manejar los errores para saber a que vista personalizada debe llamar
 */
@ControllerAdvice
public class ErrorHandlerController {
	
	/*
	 * Se tiene que anotar cada método con la clase que da el error. Si se ejecuta el proyecto antes de tener este control lo podremos ver en la traza
	 */
	@ExceptionHandler(ArithmeticException.class)
	public String arimeticaError(ArithmeticException ex, Model model) {
		
		//Se tienen que añadir todos los atributos que utiliza el HTML
		model.addAttribute("error", "Error de aritmética");
		model.addAttribute("menssage", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		
		//Vista que se va a mostrar con este error. Es el nombre del fichero HTML
		return "error/aritmetica";
	}

	@ExceptionHandler(NumberFormatException.class)
	public String formatoNumeroError(NumberFormatException ex, Model model) {
		
		//Se tienen que añadir todos los atributos que utiliza el HTML
		model.addAttribute("error", "Formato número incorrecto!");
		model.addAttribute("menssage", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		
		//Vista que se va a mostrar con este error. Es el nombre del fichero HTML
		return "error/numero-formato";
	}
	
	@ExceptionHandler(UsuarioNoEncontradoException.class)
	public String formatoNumeroError(UsuarioNoEncontradoException ex, Model model) {
		
		//Se tienen que añadir todos los atributos que utiliza el HTML
		model.addAttribute("error", "Error: Usuario no encontrado!");
		model.addAttribute("menssage", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		
		//Vista que se va a mostrar con este error. Es el nombre del fichero HTML
		return "error/generica";
	}
}
