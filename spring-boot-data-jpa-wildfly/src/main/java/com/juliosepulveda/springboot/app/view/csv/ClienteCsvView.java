package com.juliosepulveda.springboot.app.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.juliosepulveda.springboot.app.models.entity.Cliente;

/*
 * En la anotación component le ponemos el nombre de la vista que va a llamar desde el controlador. 
 * Se tiene que llamar igual que el return del método del controlador que lo llama
 * Se usa AbstractView ya que no hay ninguna clase especifica para csv en springboot
 * El .csv se puede añadir cuando tenemos más de una vista con el mismo nombre (por ejempo si quieres exportar a csv y pdf). Con esa extensión la arquitectura
 * sabe a que clase dirigirse. Para poder usar las extensiones en el application.properties tenemos que especificarlo con el atributo 
 * spring.mvc.contentnegotiation.media-types.csv
 */
@Component("listar.csv")
public class ClienteCsvView extends AbstractView {

	
	public ClienteCsvView() {
		setContentType("text/csv");
	}

	@Override
	protected boolean generatesDownloadContent() {
		// TODO Auto-generated method stub
		return super.generatesDownloadContent();
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Para asignar el nombre del archivo
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		response.setContentType(getContentType());
		
		//Obtenemos el listado de clientes
		@SuppressWarnings("unchecked")
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		//Seleccionamos los datos a escribir en el bean
		String[] header = {"id", "nombre", "apellido", "email", "createAt"};
		beanWriter.writeHeader(header);
		
		for(Cliente cliente: clientes) {
			beanWriter.write(cliente, header);
		}
		beanWriter.close();
	}
}
