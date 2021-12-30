package com.juliosepulveda.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	/*
	 * Con el GeneratedValue indicamos que es autoincremental
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String descripcion;
	
	private String observacion;
	
	/*
	 * Indicamos que en la tabla solo se va a guardar la fecha (sin la hora)
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;
	
	/*
	 * Con esta anotación indicamos que muchas facturas pueden pertenecer a un mismo cliente
	 * El fecth es para que podamos indicar si es carga perezosa o no.
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	private Cliente cliente;

	/*
	 * La anotación @JoinColumn crea una clave foranea en la tabla facturas_items para poder relacionarlo con la tabla facturas
	 * La anotación cascade es para que realice los inserts, deletes, updates en cascada en todas las tablas. Factura, facturas_items
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="factura_id")
	private List<ItemFactura> items;
	
	
	public Factura() {
		this.items = new ArrayList<ItemFactura>();
	}
	
	/*
	 * Como la fecha se asigna de forma automática no es necesario que esté en el formulario. Se asigna con la función anotada por @PrePersist
	 */
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/*
	 * Esta anotación se usa para que al exportar a XML no se poduzca un bucle infinito al estar relacionada la tabla factura con la de clientes de forma bidireccional.
	 * Al ponerle esta anotación larelación siempre va hacia delante, de cliente a factura y nunca al reves
	 */
	@XmlTransient
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	public void addItemFactura(ItemFactura item) {
		this.items.add(item);
	}
	
	public Double getTotal() {
		Double total = 0.0;
		
		int size = items.size();
		
		for(int i=0; i<size; i++) {
			total += items.get(i).calcularImporte();
		}
		
		return total;
	}

	private static final long serialVersionUID = 1L;
}
