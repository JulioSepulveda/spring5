package com.juliosepulveda.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.juliosepulveda.springboot.form.app.models.domain.Pais;

/*
 * Clase que se implementa de la interfaz país que sirve como un servicio para rellenar el combo Pais y devolver el Pais seleccionado en el formulario
 */
@Service
public class PaisserviceImpl implements PaisService {

	private List<Pais> lista;
	
	public PaisserviceImpl() {
		this.lista = Arrays.asList(
				new Pais(1, "ES", "España"), 
				new Pais(2, "MX", "Mexico"), 
				new Pais(3, "CL", "Chile"), 
				new Pais(4, "AR", "Argentina"), 
				new Pais(5, "PE", "Peru"), 
				new Pais(6, "CO", "Colombia"), 
				new Pais(7, "VE", "Venezuela"));
	}

	@Override
	public List<Pais> listar() {
		return lista;
	}

	@Override
	public Pais obtenerPorId(Integer id) {
		Pais resultado = null;
		
		for(Pais pais: this.lista) {
			if (id == pais.getId()) {
				resultado = pais;
				break;
			}
		}
		
		return resultado;
	}

}
