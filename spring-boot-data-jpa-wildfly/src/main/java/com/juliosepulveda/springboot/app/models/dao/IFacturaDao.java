package com.juliosepulveda.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.juliosepulveda.springboot.app.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
	
	/*
	 * Método para realizar una query contra todas las tablas a la vez a la hora de mostrar toda la información del detalle de un cliente, con sus facturas y las líneas de sus facturas.
	 */
	@Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id = ?1")
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);

}
