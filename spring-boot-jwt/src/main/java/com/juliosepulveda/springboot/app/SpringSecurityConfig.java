package com.juliosepulveda.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.juliosepulveda.springboot.app.auth.filter.JWTAuthenticationFilter;
import com.juliosepulveda.springboot.app.auth.filter.JWTAuthorizationFiltrer;
import com.juliosepulveda.springboot.app.auth.handler.LoginSuccessHandler;
import com.juliosepulveda.springboot.app.auth.service.JWTService;
import com.juliosepulveda.springboot.app.models.service.JpaUserDetailsService;

/*
 *Clase de configuración para el login
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private JWTService jwtService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * A diferencia del proyecto data-jpa, se añade .csrf.disable para que poder utilizar JSON WEB TOKEN
		 * Se comenta desde el formulario login hasta el manejo de errores
		 */
		
		//Asignamos las rutas que van a ser publicas y las que van a ser privadas
		http.authorizeRequests()
			.antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar**", "/locale", "/api/clientes/listar**").permitAll() //Páginas publicas
			.anyRequest().authenticated()
//			.and() //Con estos and permitimos que si se pulsa en una página pública un botón que nos envía a una privada, nos envíe a la página de login
//				.formLogin()
//				.successHandler(successHandler) //Clase que envia un mensaje al usuario que se ha logado correctamente
//				.loginPage("/login") //Con el método loginPage especificamos la ruta de nuestra página de login personalizada en vez de la de por defecto
//				.permitAll()
//			.and()
//			.logout().permitAll()
//			.and()
//			.exceptionHandling().accessDeniedPage("/error_403") //Excepción cuando se intenta acceder a un recurso que no se tiene permisos
			.and()
			.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService)) //Añadimos nuestro filtro de JWT
			.addFilter(new JWTAuthorizationFiltrer(authenticationManager(), jwtService))
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/*
	 * Método para crear usuarios y asignarles su password y roles
	 */
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

		/*
		 * Autenticación con JPA
		 */
		builder.userDetailsService(userDetailsService);
	}		
}
