package com.juliosepulveda.springboot.form.app.interceptors;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * Los interceptores son un apoyo para nuestro proyecto con el cualse gestionan peticiones http que no tengan nada que ver con la lógica de negocio
 * Por ejemplo tiempo que tarda en cargar uja  página, usuarios que la han visitado, loggin, etc
 */
@Component("tiempoTranscurridoInterceptor")
public class TiempoTranscurridoInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(TiempoTranscurridoInterceptor.class);
	
	/*
	 * El preHandle se ejecuta antes del método
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		/*
		 * Con este if controlamos que en los métodos post del controlador para form no aplique el interceptor
		 */
		if (request.getMethod().equalsIgnoreCase("post")) {
			return true;
		}
		
		if(handler instanceof HandlerMethod) {
			HandlerMethod metodo = (HandlerMethod) handler;
			logger.info("Es un método del controlador: " + metodo.getMethod().getName());
		}
		logger.info("TiempoTranscurridoInterceptor:_preHandle() entrando ...");
		
		long tiempoInicio = System.currentTimeMillis();
		
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		Random random = new Random();
		Integer demora = random.nextInt(500);
		Thread.sleep(demora);
		
		/*
		 * Si el interceptor devuelve false no deja cargar la página del controlador. Lo normal cuando devolvemos false es redirigir a la página que queramos.
		 * Ejemplo de devolver false es si el usuario no está logado o solo se puede acceder en unas determinadas horas etc
		 * 
		 * response.sendRedirect(request.getContextPath().concat("/login"));
		 * return false;
		 */
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		/*
		 * Con este if controlamos que en los métodos post del controlador para form no aplique el interceptor
		 */
		if (!request.getMethod().equalsIgnoreCase("post")) {
			
			long tiempoFin = System.currentTimeMillis();
			long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
			long tiempoTranscurrido = tiempoFin - tiempoInicio;
			
			//Este control hay que ponerlo ya que en el log si no daría error por los recursos estaticos
			if(handler instanceof HandlerMethod && modelAndView != null) {
				modelAndView.addObject("tiempoTranscurrido", tiempoTranscurrido);
			}
			
			logger.info("Tiempo Transcurrido: " + tiempoTranscurrido + "milisegundos");
			logger.info("TiempoTranscurridoInterceptor:_postHandle() saliendo ...");
		}
	}

	
}
