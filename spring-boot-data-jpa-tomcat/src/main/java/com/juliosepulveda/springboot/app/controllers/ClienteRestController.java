package com.juliosepulveda.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juliosepulveda.springboot.app.models.service.IClienteService;
import com.juliosepulveda.springboot.app.view.xml.ClienteList;

/*
 * Controlador que al indicarle que es un @RestController todos los métodos de este controllador devolveran los datos en xml o json
 * Sirve para montar apis. 
 * No es necesario anotar cada método con @ResponseBody
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;	
	
	@GetMapping(value="/listar-rest")
	public ClienteList listar() {
		
		return new ClienteList(clienteService.findAll());
	}
}
