package com.juliosepulveda.springboot.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.juliosepulveda.springboot.app.models.entity.Factura;
import com.juliosepulveda.springboot.app.models.entity.ItemFactura;

/*
 * En la anotación component le ponemos .xlsx ya que no podemos nombrarlo igual que la clase de exportar a pdf
 */
@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Otra forma de recuperar los mensajes multiidioma, la utilizo en las columnas de la tabla Factura
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		
		//Recuperamos los datos de la factura del model, que es donde a guardado los datos el controlador
		Factura factura = (Factura) model.get("factura");
		
		//Para darle un nombre a la Excel
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
		//Para darle un nombre a la hoja
		Sheet sheet = workbook.createSheet("Factura Spring");
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		
		//Una forma de meter los datos en la Excel
		cell.setCellValue(mensajes.getMessage("text.factura.ver.datos.cliente"));
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		//Otra forma más directa
		sheet.createRow(4).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.factura"));
		sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());
		
		//Estilos de la tabla
		CellStyle tHeaderStyle = workbook.createCellStyle();
		tHeaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		tHeaderStyle.setBorderTop(BorderStyle.MEDIUM);
		tHeaderStyle.setBorderRight(BorderStyle.MEDIUM);
		tHeaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		tHeaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		tHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tBodyStyle = workbook.createCellStyle();
		tBodyStyle.setBorderBottom(BorderStyle.THIN);
		tBodyStyle.setBorderTop(BorderStyle.THIN);
		tBodyStyle.setBorderRight(BorderStyle.THIN);
		tBodyStyle.setBorderLeft(BorderStyle.THIN);
		
		//Detalle de la factura.Es una tabla
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue(mensajes.getMessage("text.factura.form.item.nombre"));
		header.createCell(1).setCellValue(mensajes.getMessage("text.factura.form.item.precio"));
		header.createCell(2).setCellValue(mensajes.getMessage("text.factura.form.item.cantidad"));
		header.createCell(3).setCellValue(mensajes.getMessage("text.factura.form.item.total"));
		
		//Asignamos los estilos a la cabecera de la tabla
		header.getCell(0).setCellStyle(tHeaderStyle);
		header.getCell(1).setCellStyle(tHeaderStyle);
		header.getCell(2).setCellStyle(tHeaderStyle);
		header.getCell(3).setCellStyle(tHeaderStyle);
		
		int rownum = 10;
		for(ItemFactura item : factura.getItems()) {
			Row fila = sheet.createRow(rownum++);
			
			cell = fila.createCell(0);
			cell.setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(tBodyStyle);
						
			cell = fila.createCell(1);
			cell.setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(tBodyStyle);
			
			cell = fila.createCell(2);
			cell.setCellValue(item.getCantidad());
			cell.setCellStyle(tBodyStyle);
			
			cell = fila.createCell(3);
			cell.setCellValue(item.calcularImporte());
			cell.setCellStyle(tBodyStyle);
		}
		
		Row filaTotal = sheet.createRow(rownum);
		cell = filaTotal.createCell(2);
		cell.setCellValue(mensajes.getMessage("text.factura.form.total") + ": ");
		cell.setCellStyle(tBodyStyle);
		
		cell = filaTotal.createCell(3);
		cell.setCellValue(factura.getTotal());
		cell.setCellStyle(tBodyStyle);
	}

}
