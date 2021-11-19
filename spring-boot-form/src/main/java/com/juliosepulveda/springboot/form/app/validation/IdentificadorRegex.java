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
@Constraint(validatedBy = IdentificadorRegexValidador.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface IdentificadorRegex {
	/*
	 * Mensaje por defecto a no ser que se envíe uno desde la anotación o que se establezca uno en messages.properties con el nombre por ejemplo
	 * IdentificadorRegex.usuario.identificador2
	 * 
	 * Los tres atributos se pueden copiar de cualquier anotación por defecto, NotBlank, NotEmpty etc...
	 */
	String message() default "Identificador invalido";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
