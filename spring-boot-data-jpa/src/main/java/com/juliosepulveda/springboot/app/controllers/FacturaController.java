package com.juliosepulveda.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juliosepulveda.springboot.app.models.entity.Cliente;
import com.juliosepulveda.springboot.app.models.entity.Factura;
import com.juliosepulveda.springboot.app.models.entity.ItemFactura;
import com.juliosepulveda.springboot.app.models.entity.Producto;
import com.juliosepulveda.springboot.app.models.service.IClienteService;


/*
 * En este caso el Secured se lo indicamos a la clase ya que todos los métodos de la clase necesitan tener este role para acceder a ellos
*/
@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		
		/*
		 * En vez de usar el findFacturaById utilizamos el método fetch que realiza la consulta contra todas las tablas a la vez
		 */
		//Factura factura = clienteService.findFacturaById(id);
		Factura factura = clienteService.fetchByIdWithClienteWithItemFacturaWithProducto(id);
		
		if(factura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la Base de Datos!");
			return "redirect:/listar";
		}
		
		model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));
		model.addAttribute("factura", factura);
		
		return "factura/ver";
	}
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(clienteId);
		
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la Base de Datos");
			return "redirect:/listar";
		}
		
		Factura factura = new Factura();
		factura.setCliente(cliente);
		
		model.put("titulo", "Crear Factura");
		model.put("factura", factura);
		
		return "factura/form";
	}
	
	/*
	 * Para sugerir productos a medida que se va escribiendo. Utiliza una petición ajax
	 */
	@GetMapping(value="/cargar-productos/{term}", produces = {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return clienteService.findByNombre(term);
	}
	
	/*
	 * Método para registrar las facturas en la tabla itemFactura
	 */
	@PostMapping("/form")
	public String guardar(@Valid Factura factura, 
						  BindingResult result,
						  Model model,
						  @RequestParam(name = "item_id[]", required = false) Long[] itemId, 
						  @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
						  RedirectAttributes flash,
						  SessionStatus status) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Factura");
			return "factura/form";
		}
		
		if(itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Factura");
			model.addAttribute("error", "Error: La factura NO puede no tener líneas");
			return "factura/form";
		}
		
		for(int i = 0; i< itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);
			
			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}
		
		/*
		 * Por último guardamos la factura en la BBDD
		 */
		clienteService.saveFactura(factura);
		
		/*
		 * Completamos el @SessionAttributes de la factura
		 */
		status.setComplete();
		
		flash.addFlashAttribute("success", "Factura creada con éxito!");
		
		return "redirect:/ver/" + factura.getCliente().getId();
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		
		Factura factura = clienteService.findFacturaById(id);
		
		if(factura != null) {
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success", "Factura eliminada con éxito!");
			return "redirect:/ver/" + factura.getCliente().getId();
		}
		
		flash.addFlashAttribute("error", "La factura no existe en la Base de datos, no se pudo eliminar!");
		
		return "redirect:/listar";
	}
	
}
