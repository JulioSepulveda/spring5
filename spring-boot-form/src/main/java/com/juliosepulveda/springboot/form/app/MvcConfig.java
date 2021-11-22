package com.juliosepulveda.springboot.form.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Con esta clase registramos el interceptor en nuestro proyecto
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer{

	@Autowired
	@Qualifier("tiempoTranscurridoInterceptor")
	private HandlerInterceptor tiempoTranscurridoInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		/*
		 * Con el addPatterns lo que hacemos es que solo aplique para esa ruta. Los asteriscos son para que todo lo que vaya despues de /form tambien aplique
		 * En este caso en Resultado nos saldría un null
		 */	
		registry.addInterceptor(tiempoTranscurridoInterceptor).addPathPatterns("/form/**");
	}
}
