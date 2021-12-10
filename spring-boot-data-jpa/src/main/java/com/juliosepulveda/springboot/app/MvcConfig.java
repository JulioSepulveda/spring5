package com.juliosepulveda.springboot.app;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Clase de configuración para indicar donde se guardan los archivos de las imagenes de cada cliente
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	private final Logger log = LoggerFactory.getLogger(getClass());
/*
 * Comentamos la clasepara cargar las imagenes programáticamente desde la respuesta HTTP	
 */
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		
//		/*
//		 * Para indicar la ruta absoluta externa en la raiz del proyecto.
//		 */
//		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
//		
//		log.info(resourcePath);
//		
//		WebMvcConfigurer.super.addResourceHandlers(registry);
//		
////		registry.addResourceHandler("/uploads/**").addResourceLocations("file:/C:/Temp/uploads/");
//		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
//	}
	
	

}
