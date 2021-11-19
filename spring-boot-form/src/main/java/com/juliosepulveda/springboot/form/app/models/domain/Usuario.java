package com.juliosepulveda.springboot.form.app.models.domain;

import java.util.Date;
import java.util.List;

//import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//import org.springframework.format.annotation.DateTimeFormat;

import com.juliosepulveda.springboot.form.app.validation.IdentificadorRegex;
import com.juliosepulveda.springboot.form.app.validation.Requerido;

public class Usuario {

	/*
	 * Mismos nombres que en el HTML
	 */
	
	private String identificador;
	
	/*
	 * Patron para validar una expresión regular. En este caso quiere que se muestre un identificador como el siguiente: 11.111.111-A
	 * Se comenta el patrón porque vamos a crear una clase para validar los datos
	 */
	//@Pattern(regexp = "[0-9]{2}[.][0-9]{3}[.][0-9]{3}[-][A-Z]{1}")
	@IdentificadorRegex
	private String identificador2;
	
	/*
	 * Anotaciones para el control de errores de cada uno de los campos
	 * Si entre parentesis de las anotaciones incluimos el campo message podemos poner un texto de error personalizado
	 * Sin embargo si tenemos en el fichero messages.properties la anotación NotEmpty (por ejemplo), los errores de esa anotación
	 * mostrarán principalmente el texto del fichero
	 * Se comenta la validación porque vamos a crear una clase validadora
	 */
	//@NotEmpty (message = "El nombre no puede estar vacío")
	private String nombre;
	
	//@NotEmpty
	@Requerido
	private String apellido;
	
	/*
	 * NotBlank valida tanto que no esté vacío como que no esté solo con espacios en blanco
	 */
	@NotBlank
	@Size(min = 3, max = 8)
	private String username;
	
	@NotEmpty
	private String password;
	
	/*
	 * Como en el fichero messages.properties no tenemos establecido un mensaje para Requerido.usuario.email, saldrá el mensaje por defecto de 
	 * la anotación Requerido 
	 */
	@Requerido
	@Email (message = "Email con formato incorrecto")
	private String email;
	
	//Esta anotación vale para objetos no String. Para por ejemplo int no nos valdría ya que es un dato primario
	@NotNull
	@Min(5)
	@Max(5000)
	private Integer cuenta;
	
	/*
	 * Con la anotación @DateTimeFormat poniendole el atributo pattern indicamos el formato de fecha que queremos que tenga el campo una vez
	 * se envía el formulario
	 * Si el campo en el HTML lo tenemos como un date, tenemos que controlarlo como yyyy-MM-dd que es como lo envía HTML5
	 * Con @Future o @Past controlamos que la fecha sea a futuro o a pasado
	 */
	@NotNull
	//@DateTimeFormat(pattern= "yyyy-MM-dd")
	@Future
	private Date fechaNacimiento;
	
	@NotEmpty
	private String pais;
	
	@NotEmpty
	private String paisMap;
	
	//Con esta anotación @Valid indicamos uqe se evaluen las anotaciones puestas dentro de la clase Pais
	//@Valid
	//Validamos el objeto completo ya que lo enviamos por completo al seleccionarlo
	@NotNull
	private Pais paisClas;
	
	//EL NotEmpty también sirve para calcular el length de una lista
//	@NotEmpty
//	private List<String> roles;
	
	//Lista roles pero desde un objeto Role
	@NotEmpty
	private List<Role> roles;
	
	@NotEmpty
	private String genero;
	
	private Boolean habilitar;
	
	private String valorSecreto;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getIdentificador2() {
		return identificador2;
	}

	public void setIdentificador2(String identificador2) {
		this.identificador2 = identificador2;
	}

	public Integer getCuenta() {
		return cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPaisMap() {
		return paisMap;
	}

	public void setPaisMap(String paisMap) {
		this.paisMap = paisMap;
	}

	public Pais getPaisClas() {
		return paisClas;
	}

	public void setPaisClas(Pais paisClas) {
		this.paisClas = paisClas;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Boolean getHabilitar() {
		return habilitar;
	}

	public void setHabilitar(Boolean habilitar) {
		this.habilitar = habilitar;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getValorSecreto() {
		return valorSecreto;
	}

	public void setValorSecreto(String valorSecreto) {
		this.valorSecreto = valorSecreto;
	}

//	public List<String> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(List<String> roles) {
//		this.roles = roles;
//	}

	
}
