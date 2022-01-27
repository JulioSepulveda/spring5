package com.juliosepulveda.springboot.webflux.app.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.juliosepulveda.springboot.webflux.app.models.dao.ProductoDao;
import com.juliosepulveda.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoDao dao;

	@GetMapping({"/listar", "/"})
	public String listar(Model model) {
	
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		});
		
		productos.subscribe(prod -> log.info(prod.getNombre()));
		
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		
		return "listar";
	}
	
	/*
	 * Control de los productos que se muestran con Data Driver
	 */
	@GetMapping("/listar-datadriver")
	public String listarDataDriver(Model model) {
	
		/*
		 * Con delayElements podemos ponerle un delay a la subscripción para que vaya mostrando los elementos poco a poco
		 * Por ejemplo, en este caso mostrará uno nuevo cada segundo en el log. La vista tardará entonces en cargarse ya que hasta que no termina el subscribe del
		 * controlador no se ejecuta el siguiente, el de la clase principal del programa
		 */
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).delayElements(Duration.ofSeconds(1));
		
		productos.subscribe(prod -> log.info(prod.getNombre()));
		
		//Con el método de ReactiveDataDriverContextVariable mostramos los productos en la página web de uno en uno
		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 1));
		model.addAttribute("titulo", "Listado de productos");
		
		return "listar";
	}
	
	/*
	 * Control de los productos que se muestran con Chuck -- en este ejemplo se muestran todos
	 * Para controlar el número de bytes que se van mostrando cada vez podemos utilizar la propiedad spring.thymeleaf.reactive.max-chunk-size en el fichero application.properties
	 */
	@GetMapping({"/listar-full"})
	public String listarFull(Model model) {
	
		//Con repeat hacemos que se repitan nuestros datos x veces
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).repeat(5000);
		
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		
		return "listar";
	}
	
	/*
	 * Control de los productos que se muestran con Chuck -- en este ejemplo se muestran todos
	 * Para controlar el número de bytes que se van mostrando cada vez podemos utilizar la propiedad spring.thymeleaf.reactive.max-chunk-size en el fichero application.properties
	 * Si ademas de la anterior propiedad indicamos en el application.properties la propiedad spring.thymeleaf.reactive.chunked-mode-view-names, en esta propiedad podemos indicar a
	 * que vistas afectan estas configuraciones
	 */
	@GetMapping({"/listar-chunked"})
	public String listarChunked(Model model) {
	
		//Con repeat hacemos que se repitan nuestros datos x veces
		Flux<Producto> productos = dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).repeat(5000);
		
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		
		return "listar-chunked";
	}
}
