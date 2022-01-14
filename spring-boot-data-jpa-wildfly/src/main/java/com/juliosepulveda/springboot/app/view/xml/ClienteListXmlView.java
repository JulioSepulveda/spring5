package com.juliosepulveda.springboot.app.view.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.juliosepulveda.springboot.app.models.entity.Cliente;

/*
 * El nombre de la clase le ponemos listar.xml para especificar que esta es la vista para exportar a xml, igual que en el csv indicamos el .csv
 */
@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView {

	/*
	 * Le ponemos el @Autowired para que coja el Jaxb2Marshaller que creamos en la clase MvcConfig
	 */
	@Autowired
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//Vaciamos el model, guardandonos antes los clientes
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		
		model.remove("titulo");
		model.remove("page");		
		model.remove("cliente");
		
		//Volvemos a rellenar el model con la lista de clientes pero instanciado de la clase ClienteList (la cual es la que se usa para convertir el objeto en xml)
		model.put("clienteList", new ClienteList(clientes.getContent()));		
		
		super.renderMergedOutputModel(model, request, response);
	}

	
}
