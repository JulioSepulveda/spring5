package com.juliosepulveda.springboot.app.models.service;

import java.util.List;

import com.juliosepulveda.springboot.app.models.entity.Cliente;

public interface IClienteService {

	/*
	 * Tenemos los mismos métodos que en la interfaz IClienteDao
	 */
	
	public List<Cliente> findAll();
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
}
