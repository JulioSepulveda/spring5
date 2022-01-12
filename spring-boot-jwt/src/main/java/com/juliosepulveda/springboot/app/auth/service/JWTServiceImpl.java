package com.juliosepulveda.springboot.app.auth.service;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juliosepulveda.springboot.app.auth.SimpleGrantedAuthorityMixin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/*
 * Servicio con la implementación de JWT para tenerlo todo más centralizado
 */
@Component
public class JWTServiceImpl implements JWTService {

	public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	public static final long EXPIRATION_DATE = 3600000L;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	
	@Override
	public String create(Authentication auth) throws IOException {
		
		//Recuperamos el username. Se puede utilizar cualquiera de las dos siguientes formas
		//String username = authResult.getName();
		String username = ((User) auth.getPrincipal()).getUsername();
		
		/*
		 * Rol del usuario
		 */
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		
		Claims claims = Jwts.claims();
		claims.put("authorities",  new ObjectMapper().writeValueAsString(roles)); //El ObjectMapper convierte un objeto a json
				
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.signWith(SECRET_KEY)
				.setIssuedAt(new Date()) //Fecha creación
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)) //Fecha en la que expira el token y se tendrían que volver a logar. Hay que pasarlo como long por eso la L final. En este caso es una hora
				.compact();
		
		return token;
	}

	@Override
	public boolean validate(String token) {
		
		try {
			getClaims(token);
			
			return true;
		}	
		catch (JwtException | IllegalArgumentException e) {
			return false;
		}
		
	}

	@Override
	public Claims getClaims(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(resolve(token))
				.getBody();
	}

	@Override
	public String getUsername(String token) {
		
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		
		Object roles = getClaims(token).get("authorities");
		
		//Usamos el atributo mixIn para unir la clase SimpleGrantedAuthority con la nuestra que hemos creado SimpleGrantedAuthorityMixin y así tener un constructor vacíos
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
				.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
				.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
		
		return authorities;
	}

	@Override
	public String resolve(String token) {
		
		if(token != null && token.startsWith(TOKEN_PREFIX)){
			return token.replace(TOKEN_PREFIX, "");
		}
		return null;
	}
}
