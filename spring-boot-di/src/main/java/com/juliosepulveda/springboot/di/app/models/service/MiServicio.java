package com.juliosepulveda.springboot.di.app.models.service;

import org.springframework.stereotype.Component;

//Al anotar la clase servicio como un component le estamos indicando que es un bean de Spring. Crea una sola instancia de nuestra clase en el proyecto y se puede
//inyectar en otras clases

/**
 * @Component es la anotación genérica. En vez de este se pueden poner @Controller (para las clases controller), @Service (para las clases service) o @Repository (para las clases DAO)
 * @author jsepulveda
 *
 */
//@Component("miServicioSimple")
public class MiServicio implements IServicio {
	
	/*
	 * Ponemos el override para que el método herede de la interfaz y así pueda ser llamado en el controlador cuando se llame a la interfaz
	 */
	@Override
	public String operacion() {
		return "Ejecutando algún proceso importante...";
	}

}
