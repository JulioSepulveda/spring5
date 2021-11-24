package com.juliosepulveda.springboot.error.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.juliosepulveda.springboot.error.app.models.domain.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	private List<Usuario> lista;
	
	public UsuarioServiceImpl() {
		
		this.lista = new ArrayList<>();
		
		this.lista.add(new Usuario(1, "Julio", "Sepúlveda"));
		this.lista.add(new Usuario(2, "Rocío", "García"));
		this.lista.add(new Usuario(3, "África", "Sepúlveda"));
		this.lista.add(new Usuario(4, "Kike", "Gacía"));
		this.lista.add(new Usuario(5, "Fran", "Muñoz"));
		this.lista.add(new Usuario(6, "Victoria", "Sanchez"));
		this.lista.add(new Usuario(7, "Raul", "Calvo"));
	}
	
	
	@Override
	public List<Usuario> listar() {
		
		return this.lista;
	}

	@Override
	public Usuario obtenerPorId(Integer id) {
		Usuario resultado = null;
		
		for(Usuario u: this.lista) {
			if (u.getId().equals(id)) {
				resultado = u;
				break;
			}
		}
		return resultado;
	}


	/*
	 * Método para manejar el error del id o si viene nulo con Java8
	 */
	@Override
	public Optional<Usuario> obtenerPorIdoptional(Integer id) {
		
		Usuario usuario = this.obtenerPorId(id);
		
		return Optional.ofNullable(usuario);
	}
	
	

}
