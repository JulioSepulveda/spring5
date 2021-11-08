package com.juliosepulveda.springboot.di.app.models.service;

/*
 * Creamos la interfad para tener un elemento más generico con los nombres de los métodos de nuestras clases del paquete service.
 * Esto nos ayudará a no tener que modificar tanto el código en el caso de tener que crear otra clase con mismos metodos etc...
 */
public interface IServicio {

	public String operacion();
}
