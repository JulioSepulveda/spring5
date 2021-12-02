package com.juliosepulveda.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.juliosepulveda.springboot.app.models.entity.Cliente;

public interface IClienteService {

	/*
	 * Tenemos los mismos métodos que en la interfaz IClienteDao
	 */
	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
}
