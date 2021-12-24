package com.juliosepulveda.springboot.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Clase de configuración para indicar donde se guardan los archivos de las imagenes de cada cliente
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

//	private final Logger log = LoggerFactory.getLogger(getClass());
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
	
	/*
	 * Con este método controlamos los errores 403 para redirigirlos a nuestra vista de error_403
	 * Este error es el que da cuando un usuario no tiene permisos para acceder a una url
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	/*
	 * Bean para poder usar el encriptador de contraseñas
	 */
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
