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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/*
 * Entity para indicar que es la persistencia de una tabla de BBDD
 * Table es para indicar el nombre de la tabla (no es obligatorio)
 */
@Entity
@Table(name="clientes")
public class Cliente  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Con Id indicamos que es la clave de la tabla
	 * Con GeneratedValue indicamos la estrategia de incremento de este campo
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email
	private String email;
	
	/*
	 * Solo se indica el column en este campo ya que en los otros el nombre de la columna de la BBDD es igual y no es necesario indicarlo
	 * Con Temporal indicamos que tipo de fecha queremos guardar, timestamp, date o time
	 */
	@NotNull
	@Column(name="CREATE_AT")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date createAt;
	
	/*
	 * Con esta anotación indicamos que un cliente puede tener muchas facturas
	 * Con el cascade indicamos que todas las acciones de persistencia se ejecuten en cascada. Es decir si eliminas un cliente se eliminan todas sus facturas de la tabla
	 */
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Factura> facturas;
	
	private String foto;
	
//	//Método para obtener la fecha justo antes de hacer el insert en BBDD
//	@PrePersist
//	public void prePersist() {
//		createAt = new Date();
//	}
	
	
	/*
	 * Creamos el constructor para inicializar la lista de facturas
	 */
	public Cliente() {
		facturas = new ArrayList<Factura>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	
	/*
	 * Método para añadir una nueva factura a la lista 
	 */
	public void addFactura(Factura factura) {
		facturas.add(factura);
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 *Método generado desde el source en el que puedes especificar los campos que quieres que se impriman 
	 */
	@Override
	public String toString() {
		return nombre + " " + apellido;
	}
	
	
	
}
