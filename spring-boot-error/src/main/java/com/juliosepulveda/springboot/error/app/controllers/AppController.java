package com.juliosepulveda.springboot.error.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.juliosepulveda.springboot.error.app.errors.UsuarioNoEncontradoException;
import com.juliosepulveda.springboot.error.app.models.domain.Usuario;
import com.juliosepulveda.springboot.error.app.services.UsuarioService;

@Controller
public class AppController {

	@Autowired
	private UsuarioService usuarioService;
	
	@SuppressWarnings("unused")
	@GetMapping("/index")
	public String index() {
		//Integer i = 100/0;
		Integer valor = Integer.parseInt("10x");
		
		return "index";
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Integer id, Model model) {
		
//		Usuario usuario = usuarioService.obtenerPorId(id);
		
		//Si no exite el usuario lanzamos nuestra excepción personalizada
//		if (usuario == null) {
//			throw new UsuarioNoEncontradoException(id.toString());
//		}
	
		//Se comenta todo lo anterior ya que con Java8 podemos utilizar el Optional y una función lambda que es más elegante.
		Usuario usuario = usuarioService.obtenerPorIdoptional(id).orElseThrow(() -> new UsuarioNoEncontradoException(id.toString())); 
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Detalle usuario: ".concat(usuario.getNombre()));
		
		return "ver";
	}
}