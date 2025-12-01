package com.adrian.clienteservidor.servicios;

import java.util.List;
import java.util.Map;

import com.adrian.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.adrian.clienteservidor.model.Pedido;
import com.adrian.clienteservidor.model.Valoracion;

public interface ServicioPedidos {
	
	
	//gestion en administracion
	
	List<Pedido> obtenerPedidos();
	
	Pedido obtenerPedidoPorId(int idPedido);
	
	void actualizarPedido(int idPedido, String estado);
	
	// operaciones ajax
	
	void procesarPaso1(String nombre, String direccion, String provincia, int idUsuario, String portal, String piso, String telefono);

	void procesarPaso2(String tarjeta, String numero, String titular, int idUsuario);

	void confirmarPedido(int idUsuario);
	


	ResumenPedido procesarPaso3(String faccion, int idUsuario, List<Map<String, Object>> listaValoracionesInput);
	

}
