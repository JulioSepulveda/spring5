package com.juliosepulveda.springboot.app;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

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
	
	/*
	 * Establecemos el locale resolver para saber el idioma por defecto que se debe usar
	 * Este método establece donde se va a guardar el locale, en este caso en la Session
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		
		localeResolver.setDefaultLocale(new Locale("es", "ES"));
		
		return localeResolver;
	}
	
	/*
	 * Método para realizar el cambio de lenguaje de la aplicación
	 * Modificará el lenguaje cada vez que se envie el parámetro lang por URL
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		
		localeInterceptor.setParamName("lang");
		
		return localeInterceptor;
	}

	/*
	 * Método para registrar el interceptor
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	
}
