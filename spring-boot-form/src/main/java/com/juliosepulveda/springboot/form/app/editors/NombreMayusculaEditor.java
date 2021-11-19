package com.juliosepulveda.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

/*
 * Clase que usaos en el initBinder para modificar todos los tipo String poniendolo en mayúsculas
 */
public class NombreMayusculaEditor extends PropertyEditorSupport{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text.toUpperCase().trim());
	}

	
}
