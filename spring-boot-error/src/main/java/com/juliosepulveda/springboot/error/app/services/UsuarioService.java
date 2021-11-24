package com.juliosepulveda.springboot.error.app.services;

import java.util.List;
import java.util.Optional;

import com.juliosepulveda.springboot.error.app.models.domain.Usuario;

public interface UsuarioService {

	public List<Usuario> listar();
	public Usuario obtenerPorId(Integer id);
	
	//Excpecifico de Java8
	public Optional<Usuario> obtenerPorIdoptional(Integer id);
}
