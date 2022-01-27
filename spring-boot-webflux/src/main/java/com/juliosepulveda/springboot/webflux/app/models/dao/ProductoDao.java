package com.juliosepulveda.springboot.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.juliosepulveda.springboot.webflux.app.models.documents.Producto;

/*
 * entre <> se indica la clase entity y el tipo de dato del ID
 */
public interface ProductoDao extends ReactiveMongoRepository<Producto, String> {

}
