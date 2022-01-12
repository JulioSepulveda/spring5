package com.juliosepulveda.springboot.app.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * CLase que necesitamos crear para que en la clase JWTAAuthorizationFilter podamos realizar la instancia SimpleGrantedAuthority. Básicamente el problema que tenemos es que la 
 * clase SimpleGrantedAuthority no tiene un constructor vacío y por eso nos falla la instancia a esa clase. Con esta le añadimos el constructor vacío
 */
public abstract class SimpleGrantedAuthorityMixin {

	@JsonCreator
	public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) {}

	
}
