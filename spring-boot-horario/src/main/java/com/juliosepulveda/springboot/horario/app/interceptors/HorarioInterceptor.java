package com.juliosepulveda.springboot.horario.app.interceptors;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("horario")
public class HorarioInterceptor implements HandlerInterceptor {

	/*
	 * Con value recuperamos los valores del fichero application.properties
	 */
	@Value("${config.horario.apertura}")
	private Integer apertura;
	
	@Value("${config.horario.cierre}")
	private Integer cierre;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//Recogemos la hora del día
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);

		//Si la hora está dentro de la apertura y el cierre se devuelve true y se deja entrar, sino no
		if (hora >= apertura && hora < cierre) {
			
			StringBuilder mensaje = new StringBuilder("Bienvenido al horario de atención a clientes, ");
			mensaje.append("atendemos desde las ");
			mensaje.append(apertura);
			mensaje.append(" hrs. hasta las ");
			mensaje.append(cierre);
			mensaje.append(" hrs. Gracias por su visita.");
			
			request.setAttribute("mensaje", mensaje);

			return true;
		}
		
		response.sendRedirect(request.getContextPath().concat("/cerrado"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		//Recogemos el mensaje escripto en el preHandle para mostrarlo en la vista
		String mensaje = (String) request.getAttribute("mensaje").toString();
		
		if (modelAndView != null && handler instanceof HandlerMethod) {
			modelAndView.addObject("horario", mensaje);
		}
		
		
	}

	
}
