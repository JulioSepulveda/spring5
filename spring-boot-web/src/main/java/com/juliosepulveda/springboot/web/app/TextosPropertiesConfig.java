package com.juliosepulveda.springboot.web.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Clase para poder acceder al fichero que hemos creado textos.properties
 * En el @PropertySources puedes inyectar tantos ficheros como se quiera, separados por comas
 * @author jsepulveda
 *
 */
@Configuration
@PropertySources({
	@PropertySource("classpath:textos.properties")
})
public class TextosPropertiesConfig {

}
