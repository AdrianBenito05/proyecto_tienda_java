package com.adrian.clienteservidor.RESTcontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.adrian.clienteservidor.model.Valoracion;
import com.adrian.clienteservidor.servicios.ServicioPedidos;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pedidosREST/")
public class PedidosREST {
	
	@Autowired
	private ServicioPedidos servicioPedidos;
	
	
	@RequestMapping("paso2")
	public String paso2(String tarjeta, String numero, String titular, HttpServletRequest request) {
		int idUsuario = (int) request.getSession().getAttribute("usuario_id");
		servicioPedidos.procesarPaso2(tarjeta, numero, titular, idUsuario);
		String respuesta = "ok";
		return respuesta;
	}
	@RequestMapping("paso3")
	public ResumenPedido paso3(String faccion, String valoraciones, HttpServletRequest request) throws Exception {
		
		System.out.println("faccion: " + faccion);
		System.out.println("valoraciones: " + valoraciones);
		
	    int idUsuario = (int) request.getSession().getAttribute("usuario_id");

	    ObjectMapper mapper = new ObjectMapper();
	    List<Map<String, Object>> listaValoracionesInput = mapper.readValue(
	        valoraciones, new TypeReference<List<Map<String, Object>>>() {}
	    );

	    // Convertir los valores a tipos correctos
	    List<Map<String, Object>> listaValoraciones = new ArrayList<>();
	    for (Map<String, Object> v : listaValoracionesInput) {
	        Map<String, Object> val = new HashMap<>();
	        val.put("idProducto", Integer.parseInt(v.get("idProducto").toString()));
	        val.put("puntuacion", Double.parseDouble(v.get("puntuacion").toString()));
	        listaValoraciones.add(val);
	    }

	    ResumenPedido resumen = servicioPedidos.procesarPaso3(faccion, idUsuario, listaValoraciones);
	    return resumen;
	}



	
	
	@RequestMapping("paso1")
	public String paso1(String nombre, String provincia, String direccion, String portal, String piso, String telefono ,HttpServletRequest request) {
		String respuesta = "";
		int idUsuario = (int) request.getSession().getAttribute("usuario_id");
		servicioPedidos.procesarPaso1(nombre, direccion, provincia, idUsuario, portal, piso, telefono);
		respuesta = "ok";
		return respuesta;
	}
	@RequestMapping("paso4")
	public String paso4(HttpServletRequest request) {
		int idUsuario = (int) request.getSession().getAttribute("usuario_id");
		servicioPedidos.confirmarPedido( idUsuario);
		 return "ok";
		 
	}

	
	
}
