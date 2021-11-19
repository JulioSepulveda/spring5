package com.juliosepulveda.springboot.form.app.models.domain;

//import javax.validation.constraints.NotEmpty;

public class Pais {

	private Integer id;

	//@NotEmpty
	private String codigo;
	private String nombre;

	public Pais(Integer id, String codigo, String nombre) {

		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public Pais() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/*
	 * Se tiene que implementar el método toString para poder seleccionar en el controlador un valor por defecto.
	 * Esto es debido a que el html hace una representación toString del id que es el valor que se selecciona en el comboBox
	 */
	
	@Override
	public String toString() {
		return this.id.toString();
	}
	
	

}
