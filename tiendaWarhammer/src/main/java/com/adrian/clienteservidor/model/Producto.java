package com.adrian.clienteservidor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "tabla_productos")
public class Producto {
	@OneToMany(mappedBy = "producto")
	private List<Carrito> carritos = 
		new ArrayList<Carrito>();
	@Transient
	private Double mediaValoraciones;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
	private List<Valoracion> valoraciones = new ArrayList<>();
	@Size(min = 3, max = 40, message = "Nombre debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "Debes insertar un nombre para el producto")
	@Pattern(
		    regexp = "^(?!\\s+$)([A-Za-z0-9áéíóúÁÉÍÓÚñÑ]+(?: [A-Za-z0-9áéíóúÁÉÍÓÚñÑ]+)*)$",
		    message = "Solo letras, números y un solo espacio entre palabras"
		)
	private String nombre;
	@Size(min = 3, max = 40, message = "Medidas debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "Debes insertar unas medidas para el producto")
	@Pattern(
		    regexp = "^(?!\\s+$)([A-Za-z0-9áéíóúÁÉÍÓÚñÑ]+(?: [A-Za-z0-9áéíóúÁÉÍÓÚñÑ]+)*)$",
		    message = "Solo letras, números y un solo espacio entre palabras"
		)
	private String medidas;
	@Size(min = 3, max = 40, message = "Fabricante debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "Debes insertar un fabricante para el producto")
	@Pattern(
		    regexp = "^(?!\\s+$)([A-Za-z0-9áéíóúÁÉÍÓÚñÑ]+(?: [A-Za-z0-9áéíóúÁÉÍÓÚñÑ]+)*)$",
		    message = "Solo letras, números y un solo espacio entre palabras"
		)
	private String fabricante;
	private String descripcion;
	//campo para la imagen del producto
	@Lob
	@Column(name = "imagen_portada", columnDefinition = "LONGBLOB")
	private byte[] imagenPortada;

	@Size(min = 3, max = 40, message = "Material debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "Debes insertar un material para el producto")
	@Pattern(
		    regexp = "^(?!\\s+$)([A-Za-z0-9áéíóúÁÉÍÓÚñÑ]+(?: [A-Za-z0-9áéíóúÁÉÍÓÚñÑ]+)*)$",
		    message = "Solo letras, números y un solo espacio entre palabras"
		)
	private String material;
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	@Transient
	private MultipartFile imagen;
	
	@Transient
	private long idCategoria;
	
	
	@NotNull(message = "Debes insertar un precio")
	@Min(value = 1, message = "El precio mínimo es de 1 euro")
	@Max(value = 999, message = "El precio máximo es de 999 euros")
	private double precio;
	public Producto() {
	}
	

	public Producto(String nombre, String medidas, String fabricante, String descripcion, String material,
			double precio) {
		super();
		this.nombre = nombre;
		this.medidas = medidas;
		this.fabricante = fabricante;
		this.descripcion = descripcion;
		this.material = material;
		this.precio = precio;
	}


	public Producto(long id, String nombre, String medidas, String fabricante, String descripcion, String material,
			double precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.medidas = medidas;
		this.fabricante = fabricante;
		this.descripcion = descripcion;
		this.material = material;
		this.precio = precio;
	}
	



	public Double getMediaValoraciones() {
		return mediaValoraciones;
	}


	public void setMediaValoraciones(Double mediaValoraciones) {
		this.mediaValoraciones = mediaValoraciones;
	}


	public List<Valoracion> getValoraciones() {
		return valoraciones;
	}


	public void setValoraciones(List<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}


	public long getIdCategoria() {
		return idCategoria;
	}


	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public List<Carrito> getCarritos() {
		return carritos;
	}


	public void setCarritos(List<Carrito> carritos) {
		this.carritos = carritos;
	}


	public byte[] getImagenPortada() {
		return imagenPortada;
	}


	public void setImagenPortada(byte[] imagenPortada) {
		this.imagenPortada = imagenPortada;
	}


	public MultipartFile getImagen() {
		return imagen;
	}


	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMedidas() {
		return medidas;
	}
	public void setMedidas(String medidas) {
		this.medidas = medidas;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	@Override
	public String toString() {
		return "Maqueta [nombre=" + nombre + ", medidas=" + medidas + ", fabricante=" + fabricante + ", descripcion="
				+ descripcion + ", material=" + material + ", precio=" + precio + "]";
	}
	

}
