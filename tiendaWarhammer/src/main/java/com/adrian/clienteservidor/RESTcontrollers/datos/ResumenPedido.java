package com.adrian.clienteservidor.RESTcontrollers.datos;

import java.util.List;
import java.util.Map;

public class ResumenPedido {
	// productos que hay en el carrito
	private List<Map<String, Object>> productos;
	
	//datos paso1
	private String nombreCompleto;
	private String direccion;
	private String provincia;
	private String portal;
	private String piso;
	private String telefono;
	
	
	//datos paso2
	private String titularTarjeta;
	private String numeroTarjeta;
	private String tipoTarjeta;
	
	//datos paso3
	private String faccion;
	
	
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
	public List<Map<String, Object>> getProductos() {
		return productos;
	}
	public void setProductos(List<Map<String, Object>> productos) {
		this.productos = productos;
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
	public String getTitularTarjeta() {
		return titularTarjeta;
	}
	public void setTitularTarjeta(String titularTarjeta) {
		this.titularTarjeta = titularTarjeta;
	}
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public String getTipoTarjeta() {
		return tipoTarjeta;
	}
	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}
	
	
}
