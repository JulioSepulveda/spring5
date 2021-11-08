package com.juliosepulveda.springboot.di.app.models.domain;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/*
 * Con el @RequestScope cada factura va a durar lo que dura cada petición HTTP. Es decir cada vez que se hace un refresh de la página factura, se destruye la anterior
 * y se crea una factura nueva.
 * En este caso, al ser singleton el cliente (eso es por defecto) se va anidando un José en el nombre por cada refresh. Si se pusiera la misma anotación @RequestScope 
 * en la clase cliente, esto no pasaría
 */
/*
 * El @SessionScope es similar al @RequestScope solo que este se mantiene hasta que se cierra el navegador o se cierra una sesion o da un timeOup. Este sirva por ejemplo para
 * web con crrito de la compra. Se mantienen los objetos en el carrito hasta que se cierra sesión o se da un timeout. En esta anotación no se ejecuta el @PreDestroy 
 */
/*
 * @ApplicationScope es practicamente lo mismo que no poner nada. Es decir sería del tipo singleton.
 */
@Component
@RequestScope
public class Factura {

	//Inyectamos el valor de descripcion desde el ficjero application.properties
	@Value("${factura.descripcion}")
	private String descripcion;
	
	//Inyectamos el cliente de la clase Cliente con el @Autowired
	@Autowired
	private Cliente cliente;
	
	//Este atributo se tiene que anotar en la clase AppConfig ya que al ser una lista no se puede realizar aquí. El autowired se redirige al fichero AppConfig 
	@Autowired
	private List<ItemFactura> items;
	
	/*
	 * Esta anotacion es propia de Java que está integrada en Spring. Se ejecuta justo despues de crear el objeto y de la inyección de las dependencias
	 */
	@PostConstruct
	public void inicializar() {
		cliente.setNombre(cliente.getNombre().concat(" ").concat("José"));
		descripcion = descripcion.concat(" del cliente: ".concat(cliente.getNombre()));
	}
	
	/*
	 * Se ejecuta justo antes de destruir la ejecución. Por ejemplo si paramos el servidor
	 */
	@PreDestroy
	public void destruir() {
		System.out.println("Factura destruida: ".concat(descripcion));
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

}
