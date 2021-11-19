package com.juliosepulveda.springboot.form.app.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * Esto se monta creando una Anotación desde new
 * Para lincarlo con la clase usamos @Constraint nombre de la clase
 * 
 */
@Constraint(validatedBy = RequeridoValidador.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface Requerido {
	/*
	 * Los tres atributos se pueden copiar de cualquier anotación por defecto, NotBlank, NotEmpty etc...
	 */
	String message() default "El campo es requerido - usando anotaciones";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
