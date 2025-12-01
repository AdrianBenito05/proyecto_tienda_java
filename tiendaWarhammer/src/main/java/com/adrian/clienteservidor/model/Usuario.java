package com.adrian.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {
	@Size(min = 3, max = 40, message = "Nombre debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "Debes insertar un nombre para el producto")
	@Pattern(
		    regexp = "^(?!\\s+$)([A-Za-z0-9áéíóúÁÉÍÓÚñÑ]+(?: [A-Za-z0-9áéíóúÁÉÍÓÚñÑ]+)*)$",
		    message = "Solo letras, números y un solo espacio entre palabras"
		)
	private String nombre;
	@Size(min = 3, max = 30, message = "Nombre debe tener entre 3 y 40 caracteres")

	private String pass;
	@Column(unique = true)
	private String email;
	private int cp;
	private String localidad;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL,
	           orphanRemoval = true)
	private List<Carrito> productosCarrito = 
					new ArrayList<Carrito>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	@Lob
	@Column(name = "imagen_usuario", columnDefinition = "LONGBLOB")
	private byte[] imagenUsuario;
	@Transient
	private MultipartFile imagen;
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	

	public Usuario(String nombre, String pass, String email, int cp, String localidad) {
		super();
		this.nombre = nombre;
		this.pass = pass;
		this.email = email;
		this.cp = cp;
		this.localidad = localidad;
	}




	public List<Carrito> getProductosCarrito() {
		return productosCarrito;
	}


	public void setProductosCarrito(List<Carrito> productosCarrito) {
		this.productosCarrito = productosCarrito;
	}


	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
