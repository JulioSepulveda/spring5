package com.juliosepulveda.springboot.di.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.juliosepulveda.springboot.di.app.models.domain.ItemFactura;
import com.juliosepulveda.springboot.di.app.models.domain.Producto;
import com.juliosepulveda.springboot.di.app.models.service.IServicio;
import com.juliosepulveda.springboot.di.app.models.service.MiServicio;
import com.juliosepulveda.springboot.di.app.models.service.MiServicioComplejo;

/*
 * Esta clase sustituye las anotaciones que realizamos en los servicios de @Component y @Primary o @Qualifier. Tiene las mismas caracteristicas que si lo anotas en los servicios
 */
@Configuration
public class AppConfig {

	@Bean("miServicioSimple")
	public IServicio registrarMiServicio() {
		return new MiServicio();
	}
	
	@Bean("miServicioComplejo")
	//@Primary
	public IServicio registrarMiServicioComlejo() {
		return new MiServicioComplejo();
	}
	
	/*
	 * La lista de ItemFactura de la clase Factura la tenemos que anotar en este fichero ya que al ser una lista no se puede realizar directamente en la clase Factura
	 */
	@Bean("itemsFactura")
	public List<ItemFactura> registrarItems() {
		Producto producto1 = new Producto("Camara Sony", 100);
		Producto producto2 = new Producto("Bicicleta Bianchi", 200);
		
		ItemFactura item1 = new ItemFactura(producto1, 2);
		ItemFactura item2 = new ItemFactura(producto2, 4);
		
		return Arrays.asList(item1, item2);
	}
	
	/*
	 * La lista de ItemFactura de la clase Factura la tenemos que anotar en este fichero ya que al ser una lista no se puede realizar directamente en la clase Factura
	 */
	@Bean("itemsFacturaOficina")
	@Primary
	public List<ItemFactura> registrarItemsOficina() {
		Producto producto1 = new Producto("Monitor LG LCD 24", 250);
		Producto producto2 = new Producto("Notebook Asus", 500);
		Producto producto3 = new Producto("Impresora HP Multifuncional", 80);
		Producto producto4 = new Producto("Escritorio de Oficina", 300);
		
		ItemFactura item1 = new ItemFactura(producto1, 2);
		ItemFactura item2 = new ItemFactura(producto2, 1);
		ItemFactura item3 = new ItemFactura(producto3, 1);
		ItemFactura item4 = new ItemFactura(producto4, 1);
		
		return Arrays.asList(item1, item2, item3, item4);
	}
}
