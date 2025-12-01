package com.adrian.clienteservidor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Valoracion {
	private Double puntuacion;
	
	@ManyToOne
	@JoinColumn(name = "producto_id")
	private Producto producto;
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	public Double getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(Double puntuacion) {
		this.puntuacion = puntuacion;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
