package com.juliosepulveda.springboot.app.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.juliosepulveda.springboot.app.auth.service.JWTService;
import com.juliosepulveda.springboot.app.auth.service.JWTServiceImpl;

/*
 * Clase para validar el token si ya lo recibimos directamente. Esto sirve si ya se ha logado y vuelve a acceder a nuestra web y el token está aún activo para que 
 * no tenga que volver a logarse
 */
public class JWTAuthorizationFiltrer extends BasicAuthenticationFilter {
	
	private JWTService jwtService;
	
	public JWTAuthorizationFiltrer(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		String header = request.getHeader(JWTServiceImpl.HEADER_STRING);
		
		if (!requiresAuthorization(header)) {
			chain.doFilter(request, response);
			return;
		}
		
		/*
		 * Para validar el token
		 */
		UsernamePasswordAuthenticationToken authentication = null;
		if (jwtService.validate(header)) {
			
			authentication= new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header));
		}
		
		//Autentica al usuario dentro del request
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
	
	/*
	 * Método para ver si requiere la autorización
	 */
	protected boolean requiresAuthorization(String header) {
		
		if (header == null || !header.startsWith(JWTServiceImpl.TOKEN_PREFIX)) {
			return false;
		}
		return true;
	}

}
