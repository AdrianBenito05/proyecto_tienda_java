package com.adrian.clienteservidor.setup;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//si no ponemos el @Table(name ="nombre_tabla") el nombre de la tabla sera el mismo nombre que la clase, empezando por minuscula
@Entity
public class TablaSetUp {
	
	private boolean completado;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	public TablaSetUp() {
		// TODO Auto-generated constructor stub
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
