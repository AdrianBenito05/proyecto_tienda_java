package com.adrian.clienteservidor.servicios;

import java.util.List;
import java.util.Map;

import com.adrian.clienteservidor.model.Producto; 


public interface ServicioProducto {

	 void registrarProducto(Producto p);
	 
	 
	 List<Producto> obtenerProductos();
	 
	 void borrarProducto (long id);
	 
	 void actualizarProducto (Producto p);


	 Producto obtenerProductoPorId(long id);
	 
	 // para la parte publica, servicios REST
	 
	 List< Map<String, Object> >obtenerProductosParaFormarJSON ();
	
}
