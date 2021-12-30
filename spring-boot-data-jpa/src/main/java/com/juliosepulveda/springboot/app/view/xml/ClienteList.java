package com.juliosepulveda.springboot.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.juliosepulveda.springboot.app.models.entity.Cliente;

/*
 * Clase que contiene lo que se va a exportar a XML. En este caso el listado de clientes
 */
@XmlRootElement(name = "clientes")
public class ClienteList {
	
	/*
	 * Con la anotación XmlElement indicamos el objeto que va a crear una nueva grupo en el xml
	 */
	@XmlElement(name = "cliente")
	public List<Cliente> clientes;

	/*
	 * Esta clase necesita un constructor vacío
	 */
	public ClienteList() {
	}

	public ClienteList(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}
}
