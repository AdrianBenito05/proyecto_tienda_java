package com.adrian.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
	
	//estado indicara si el pedido esta completado o no
	private String estado;
	
	
	
	//paso1:
	private String nombreCompleto;
	private String direccion;
	private String provincia;
	private String portal;
	private String piso;
	private String telefono;
	
	//paso2:
	private String TipoTarjeta;
	private String NumeroTarjeta;
	private String TitularTarjeta;
	
	//paso3
	private String faccion;
	
	
	// lo siguiente simplemente pide los productos pedido cuando se pida un pedido
	//asociacion inversa de @ManyToOne maoeadi en ProductoPedido
	@OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
	private List<ProductosPedido> productosPedido =
	new ArrayList<ProductosPedido>();
	
	@OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
	private List<Valoracion> valoraciones = new ArrayList<>();
	@ManyToOne(targetEntity = Usuario.class, optional = false)
	private Usuario usuario;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;



	







	public List<ProductosPedido> getProductosPedido() {
		return productosPedido;
	}



	public void setProductosPedido(List<ProductosPedido> productosPedido) {
		this.productosPedido = productosPedido;
	}



	public String getFaccion() {
		return faccion;
	}



	public void setFaccion(String faccion) {
		this.faccion = faccion;
	}



	public String getPortal() {
		return portal;
	}



	public void setPortal(String portal) {
		this.portal = portal;
	}



	public String getPiso() {
		return piso;
	}



	public void setPiso(String piso) {
		this.piso = piso;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public String getTipoTarjeta() {
		return TipoTarjeta;
	}



	public void setTipoTarjeta(String tipoTarjeta) {
		TipoTarjeta = tipoTarjeta;
	}



	public String getNumeroTarjeta() {
		return NumeroTarjeta;
	}



	public void setNumeroTarjeta(String numeroTarjeta) {
		NumeroTarjeta = numeroTarjeta;
	}



	public String getTitularTarjeta() {
		return TitularTarjeta;
	}



	public void setTitularTarjeta(String titularTarjeta) {
		TitularTarjeta = titularTarjeta;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public String getNombreCompleto() {
		return nombreCompleto;
	}



	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public String getProvincia() {
		return provincia;
	}



	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
	
	
}
