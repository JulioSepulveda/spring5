package com.juliosepulveda.springboot.reactor.app;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.juliosepulveda.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/*
		 * Ejemplo de subscripción a un stream
		 */
//		Flux<String> nombres = Flux.just("Julio", "Rocío", "África", "Luna", "Leo")
//				.doOnNext(elemento -> System.out.println(elemento));
		
		//Una forma más simple de hacer lo anterior
//		.doOnNext(System.out::println);
		
		
		Flux<Usuario> nombres = Flux.just("Julio Sepúlveda", "Rocío García", "África Sepúlveda", "Luna Sepúlveda", "Leo Sepúlveda")
				.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
				.filter(usuario -> usuario.getNombre().equals("ÁFRICA"))
				.doOnNext(usuario -> {
					if(usuario == null) {
						throw new RuntimeException("Nombre no puede ser vacío");
					}
					System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellido()));
				})
				.map(usuario -> {
					String nombre = usuario.getNombre().toLowerCase();
					usuario.setNombre(nombre);
					return usuario;
					});
		
//		nombres.subscribe();
		
		/*
		 * El primer parámetro del subscrivbe es el dato en si, el segundo es un posible error que haya ocurrido, el tercero es si queremos que realice algo al terminar la ejecución de observable
		 * Solo es obligatorio el primero
		 */
		nombres.subscribe(e -> log.info(e.getNombre()), 
						  error -> log.error(error.getMessage()),
						  new Runnable() {
							
							@Override
							public void run() {
								log.info("Ha finalizado la ejecución del observable con éxito!");
								
							}
						});
		
		List<String> usuarios = new ArrayList<>();
		usuarios.add("Julio Sepúlveda");
		usuarios.add("Rocío García");
		usuarios.add("África Sepúlveda");
		usuarios.add("Luna Sepúlveda");
		usuarios.add("Leo Sepúlveda");
		
		Flux<String> nombresIterable = Flux.fromIterable(usuarios);
		
		nombresIterable.subscribe(log::info);
		
		
	}

}
