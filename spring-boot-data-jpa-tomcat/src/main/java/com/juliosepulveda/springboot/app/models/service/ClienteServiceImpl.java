package com.juliosepulveda.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juliosepulveda.springboot.app.models.dao.IClienteDao;
import com.juliosepulveda.springboot.app.models.dao.IFacturaDao;
import com.juliosepulveda.springboot.app.models.dao.IProductoDao;
import com.juliosepulveda.springboot.app.models.entity.Cliente;
import com.juliosepulveda.springboot.app.models.entity.Factura;
import com.juliosepulveda.springboot.app.models.entity.Producto;

/*
 * Es una buena práctica crear la clase service para implementar uno o más dao.
 */

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IProductoDao productoDao;

	@Autowired
	private IFacturaDao facturaDao;
	
	/*
	 * Comentamos todos los métodos ya que ahora la interfaz IClienteDao extiende de CrudRepository y vamos a definir los métodos más abajo
	 */
	
//	@Override
//	@Transactional(readOnly = true)
//	public List<Cliente> findAll() {
//		
//		return clienteDao.findAll();
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public Cliente findOne(Long id) {
//		
//		return clienteDao.findOne(id);
//	}
//	
//	@Override
//	@Transactional
//	public void save(Cliente cliente) {
//		clienteDao.save(cliente);
//		
//	}
//
//	@Override
//	@Transactional
//	public void delete(Long id) {
//		clienteDao.delete(id);
//		
//	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		
		return clienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente fetchByIdWithFacturas(Long id) {
		
		return clienteDao.fetchByIdWithFacturas(id);
	}
	
	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		clienteDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {

		return productoDao.findByNombre(term);
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		
		facturaDao.save(factura);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		/*
		 * El orElse se pone por si no devolviera nada el findById que devuelva un null 
		 */
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	public void deleteFactura(Long id) {
		facturaDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id) {
		
		return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
	}
}
