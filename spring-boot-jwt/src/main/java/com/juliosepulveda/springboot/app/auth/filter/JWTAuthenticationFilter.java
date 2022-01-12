package com.juliosepulveda.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juliosepulveda.springboot.app.auth.service.JWTService;
import com.juliosepulveda.springboot.app.auth.service.JWTServiceImpl;
import com.juliosepulveda.springboot.app.models.entity.Usuario;

/*
 * Autenticación desde request Parameter con JWT
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	private JWTService jwtService;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		
		/*
		 * La autenticación por defecto se realiza en la ruta /login. Si quisieramos indicar otra diferente tendríamos que añadir lasiguiente sentencia
		 * setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
		 */
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = obtainUsername(request);		
		String password = obtainPassword(request);

		if(username != null && password != null) {
			/*
			 * El logger lo utilizamos desde una clase abstracta de la clase padre, por eso no se instancia en esta clase
			 */
			logger.info("Username desde request parameter (form-data): " + username);
			logger.info("Password desde request parameter (form-data): " + password);
		}
		/*
		 * El else es por si los datos los recibimos directamente del raw (en bruto, se puede ver en postman) como un json
		 */
		else {
			Usuario user = null;
			try {
				user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class); //convierte un json en el objeto que indiquemos como segundo parametro.
				
				username = user.getUsername();
				password = user.getPassword();
				
				logger.info("Username desde request InputStream (raw): " + username);
				logger.info("Password desde request InputStream (raw): " + password);	
			} 
			catch (StreamReadException e) {
				e.printStackTrace();
			} 
			catch (DatabindException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		UsernamePasswordAuthenticationToken autToken = new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(autToken);
	}

	/*
	 * Gestión del JWT cuando se ha logado correctamente
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = jwtService.create(authResult);
		
		response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);
		
		Map<String, Object> body = new HashedMap<String, Object>();
		body.put("token", token);
		body.put("user", (User) authResult.getPrincipal());
		body.put("mensaje", String.format("Hola %s has iniciado sesión con éxito!", authResult.getName()));
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
	}

	/*
	 * Gestión del JWT cuando se ha logado incorrectamente
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
	
		Map<String, Object> body = new HashedMap<String, Object>();
		
		body.put("mensaje", "Error en la autenticación: username o password incoprrecto!");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
	}
}
