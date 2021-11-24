package com.juliosepulveda.springboot.error.app.errors;

public class UsuarioNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioNoEncontradoException(String id) {
		super("Usuario con Id ".concat(id).concat(" no exite en el sistema"));
	}
	
	

}
