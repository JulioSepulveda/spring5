package com.juliosepulveda.springboot.app.view.pdf;

import java.util.Locale;
import java.util.Map;
import java.awt.Color;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.juliosepulveda.springboot.app.models.entity.Factura;
import com.juliosepulveda.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/*
 * En la anotación component le tenemos que poner el mismo nombre que se devuelve en el método del controlador que va a generar el pdf.
 * La arquitectura internamente va a saber si tiene que mostrar el html (la vista de web) o el pdf, por el parámetro PDF que le vamos a pasar en la URL
 */
@Component("factura/ver")
public class FacturaPdfView  extends AbstractPdfView{

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
    private LocaleResolver localeResolver;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Recuperamos los datos de la factura del model, que es donde a guardado los datos el controlador
		Factura factura = (Factura) model.get("factura");
		
		Locale locale = localeResolver.resolveLocale(request);
		
		//Otra forma de recuperar los mensajes multiidioma, la utilizo en las columnas de la tabla Factura
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		
		PdfPCell cell = null;
		
		//Creamos las tablas que va a contener el PDF
		PdfPTable tablaCliente = new PdfPTable(1);
		tablaCliente.setSpacingAfter(20);
		
		//Para dar formato a el titulo de la tabla
		
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		
		tablaCliente.addCell(cell);
		tablaCliente.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tablaCliente.addCell(factura.getCliente().getEmail());
		
		PdfPTable tablaFactura = new PdfPTable(1);
		tablaFactura.setSpacingAfter(20);
		
		//Para dar formato a el titulo de la tabla
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		tablaFactura.addCell(cell);
		tablaFactura.addCell(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
		tablaFactura.addCell(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		tablaFactura.addCell(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());
		
		//Añadimos las tablas al document
		document.add(tablaCliente);
		document.add(tablaFactura);
		
		//Tabla con el detalle
		PdfPTable tablaDetalle = new PdfPTable(4);
		//Relación de medidas relativas entre las columnas
		tablaDetalle.setWidths(new float[] {3.5f, 1, 1, 1});
		tablaDetalle.addCell(mensajes.getMessage("text.factura.form.item.nombre"));
		tablaDetalle.addCell(mensajes.getMessage("text.factura.form.item.precio"));
		tablaDetalle.addCell(mensajes.getMessage("text.factura.form.item.cantidad"));
		tablaDetalle.addCell(mensajes.getMessage("text.factura.form.item.total"));
		
		for(ItemFactura item: factura.getItems()) {
			tablaDetalle.addCell(item.getProducto().getNombre());
			tablaDetalle.addCell(item.getProducto().getPrecio().toString());
			
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
			tablaDetalle.addCell(cell);
			
			tablaDetalle.addCell(item.calcularImporte().toString());
		}
		
		cell = new PdfPCell(new Phrase(mensajes.getMessage("text.factura.form.total") + ": "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
		tablaDetalle.addCell(cell);
		tablaDetalle.addCell(factura.getTotal().toString());
		
		document.add(tablaDetalle);	
	}

}
