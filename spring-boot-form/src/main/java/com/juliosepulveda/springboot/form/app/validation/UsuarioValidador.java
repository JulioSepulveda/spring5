package com.juliosepulveda.springboot.form.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.juliosepulveda.springboot.form.app.models.domain.Usuario;

@Component
public class UsuarioValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		/*
		 * Se tiene que devolver la clase que se quiere validar de la siguiente forma
		 */
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
//		Usuario usuario = (Usuario)target;
		
		/*
		 * Primero validamos que el nombre no está vacío
		 * A la función le pasamos el atributo errors, nombre del campo (tal cual está en la clase) y el mensaje de error que queramos del fichero messages.properties
		 */
		//ValidationUtils.rejectIfEmpty(errors, "nombre", "NotEmpty.usuario.nombre");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "NotEmpty.usuario.nombre");
		
//		/*
//		 * Validación del formato del campo identificador2
		//Lo comentamos ya que lo vamos a validar por anotaciones propias
//		 */
//		if (!usuario.getIdentificador2().matches("[0-9]{2}[.][0-9]{3}[.][0-9]{3}[-][A-Z]{1}")) {
//			errors.rejectValue("identificador2", "pattern.usuario.identificador2");
//		}
	}

}
