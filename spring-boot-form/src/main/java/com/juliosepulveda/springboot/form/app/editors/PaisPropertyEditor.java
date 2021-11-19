package com.juliosepulveda.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juliosepulveda.springboot.form.app.services.PaisService;

/*
 * Lo anotamos como componente para meterlo dentro de la arquitectura de Spring
 */
@Component
public class PaisPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private PaisService service;

	@Override
	public void setAsText(String idString) throws IllegalArgumentException {

		// El if y el try catch son para controlar si viene vacío (no se selecciona
		// ningún país) De esta forma obtenemos un error más controlado
		try {
			Integer id = Integer.parseInt(idString);
			this.setValue(service.obtenerPorId(id));
		} catch (NumberFormatException e) {
			setValue(null);
		}
	}

}
