package com.juliosepulveda.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.juliosepulveda.springboot.app.models.entity.Cliente;

/*
 * Extendemos nuestra interfad de CrudRepository para acceder a los métodos predefinidos de esta y no tener que desarrollarlos nosotros
 * No se anota con nada ya que al extender de CrudRepository hereda de esta y esta ya está anotada
 */
/*
 * Comentamos el CRUD Repositori para usar paginación y este ya extiende de CRUD REpository
 */
//public interface IClienteDao extends CrudRepository<Cliente, Long> {
	
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {
	/*
	 * vamos a crear nuestra interfaz de CRUD REPOSITORY por eso comentamos todos los métodos de la interfaz
	 */
	
	
//	public List<Cliente> findAll();
//	
//	public void save(Cliente cliente);
//	
//	public Cliente findOne(Long id);
//	
//	public void delete(Long id);
//	
	

}