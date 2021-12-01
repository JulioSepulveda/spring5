package com.juliosepulveda.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juliosepulveda.springboot.app.models.entity.Cliente;
import com.juliosepulveda.springboot.app.models.service.IClienteService;

@Controller
public class ClienteController {

	/*
	 * EL qualifier se utilizaría si tuvieramos más de una implementación de esta interfaz, para indicar que implementación queremos utilizar
	 * Para poderlo utilizar, tenemos que indicar en la clase Impl el nombre en la anotacion Persistence y después ese mismo aqui.
	 */
	@Autowired
	private IClienteService clienteService;
	
	@RequestMapping(value="listar", method = RequestMethod.GET)
	public String Listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteService.findAll());
		
		return "listar";
	}

	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model) {
		
		Cliente cliente = new Cliente();
		
		model.put("titulo", "Formulario de Cliente");
		model.put("cliente", cliente);
		
		return "form";
	}
	
	/*
	 * El objeto recibido lo tenemos que anotar con @Valid para que pueda realizar las validaciones puestas en la clase Ciente
	 * La anotación @ModelAttribute solamente es necesaria si el objeto con el que se pasan los datos a la vista se llama diferente de la clase (en 
	 * este caso no sería necesario aunque si se especifica no pasa nada)
	 * El objeto BindingResult lo utilizamos para controlar si hay errores en el formulario. Es muy importante que esté puesto despés del 
	 * objeto al que hace referncia (en este caso de Cliente)
	 */
	@RequestMapping(value="form", method = RequestMethod.POST)
	public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		
		clienteService.save(cliente);
		
		return "redirect:listar";
	}
	
	/*
	 * Método para editar un cliente a a través del id. Este campo tiene que estar anotado con @PathVariable
	 */
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		
		Cliente cliente = null;
		
		if (id > 0) {
			cliente = clienteService.findOne(id);
		}
		else {
			return "redirect:/listar";
		}
		
		model.put("titulo", "Editar Cliente");
		model.put("cliente", cliente);
		
		return "form";
	}
	
	/*
	 * Método para eliminar un cliente a a través del id. Este campo tiene que estar anotado con @PathVariable
	 */
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		if (id > 0) {
			clienteService.delete(id);
		}
		
		return "redirect:/listar";
	}
}
