package com.juliosepulveda.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.juliosepulveda.springboot.app.models.entity.Cliente;

/*
 * Comentamos toda la clase ya que vamos a crear la interfaz extendiendo de CrudRepository y esta clase no sería necesaria
 */

/*
 * Indica que es un bean de acceso a dato dentro de la arquitectura de Spring
 */
@Repository
//public class ClienteDaoImpl implements IClienteDao {
public class ClienteDaoImpl{

//	//Se encarga de realizar todas las acciones de BBDD contra la clase entity(en este caso Cliente)
//	@PersistenceContext
//	private EntityManager em;
//	
//	/*
//	 * Con transactional indicamos que es una query de solo lectura
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Cliente> findAll() {
//		
//		return em.createQuery("from Cliente").getResultList();
//	}
//
//	@Override
//	public Cliente findOne(Long id) {
//		
//		return em.find(Cliente.class, id);
//	}
//	
//	/*
//	 * Realiza un insert del nuevo cliente
//	 */
//	@Override
//	public void save(Cliente cliente) {
//		
//		//Con este if controlamos si el cliente ya existe, lo que quiere decir que se está modificando un cliente 
//		if(cliente.getId() != null && cliente.getId() > 0) {
//			em.merge(cliente);
//		}
//		else {
//			em.persist(cliente);
//		}		
//	}
//
//	@Override
//	public void delete(Long id) {
//		em.remove(findOne(id));
//	}

}
