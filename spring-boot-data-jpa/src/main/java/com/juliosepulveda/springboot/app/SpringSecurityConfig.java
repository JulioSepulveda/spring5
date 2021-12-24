package com.juliosepulveda.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.juliosepulveda.springboot.app.auth.handler.LoginSuccessHandler;

/*
 *Clase de configuración para el login
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * Se han comentado todas las rutas de ver, uploads, form, eliminar y factura para poner la seguridad con anotaciones en el controlador
		 */
		
		//Asignamos las rutas que van a ser publicas y las que van a ser privadas
		http.authorizeRequests()
			.antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll() //Páginas publicas
//			.antMatchers("/ver/**").hasAnyRole("USER")	//Páginas privadas que dependiendo del rol que tenga el usuario podrá acceder o no
//			.antMatchers("/uploads/**").hasAnyRole("USER") //Páginas privadas que dependiendo del rol que tenga el usuario podrá acceder o no
//			.antMatchers("/form/**").hasAnyRole("ADMIN") //Páginas privadas que dependiendo del rol que tenga el usuario podrá acceder o no
//			.antMatchers("/eliminar/**").hasAnyRole("ADMIN") //Páginas privadas que dependiendo del rol que tenga el usuario podrá acceder o no
//			.antMatchers("/factura/**").hasAnyRole("ADMIN") //Páginas privadas que dependiendo del rol que tenga el usuario podrá acceder o no
			.anyRequest().authenticated()
			.and() //Con estos and permitimos que si se pulsa en una página pública un botón que nos envía a una privada, nos envíe a la página de login
				.formLogin()
				.successHandler(successHandler) //Clase que envia un mensaje al usuario que se ha logado correctamente
				.loginPage("/login") //Con el método loginPage especificamos la ruta de nuestra página de login personalizada en vez de la de por defecto
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/error_403"); //Excepción cuando se intenta acceder a un recurso que no se tiene permisos
	}

	/*
	 * Bean para poder usar el encriptador de contraseñas
	 */
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * Método para crear usuarios y asignarles su password y roles
	 */
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		PasswordEncoder encoder = passwordEncoder();
		UserBuilder users = User.builder().passwordEncoder(encoder::encode); //Simplificación de User.builder().passwordEncoder(password -> encoder.encode(password));
		
		builder.inMemoryAuthentication()
			.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
			.withUser(users.username("julio").password("12345").roles( "USER"));
	}
}
