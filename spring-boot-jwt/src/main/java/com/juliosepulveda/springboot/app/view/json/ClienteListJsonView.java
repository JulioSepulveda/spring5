package com.juliosepulveda.springboot.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.juliosepulveda.springboot.app.models.entity.Cliente;

/*
 * Clase para exportar la lista de clientes a JSON
 */
@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView {

	@SuppressWarnings("unchecked")
	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		//Eliminamos todo lo que no sea la lista de clientes del model 
		model.remove("titulo");
		model.remove("page");
		
		//Quitamos la lista de clientes del model, para volverlo a meter pero como una instancia de la clase entity y no de Page<>
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		model.remove("clientes");
		model.put("clientes", clientes.getContent());
		
		//Enviamos el model con la lista de clientes al padre
		return super.filterModel(model);
	}

	
}
