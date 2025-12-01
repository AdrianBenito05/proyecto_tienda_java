package com.adrian.clienteservidor.RESTcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.clienteservidor.servicios.ServicioCarrito;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("carritoREST/")
public class CarritoREST {
	@Autowired 
	private ServicioCarrito servicioCarrito;
	
	@RequestMapping("eliminar")
	public String eliminar(@RequestParam("id") Long id, HttpServletRequest request) {
		int idUsuario = (int) request.getSession().getAttribute("usuario_id");
		servicioCarrito.quitarProductoCarrito(idUsuario, id);
		return "El producto ha sido eliminado del carrito";
	}
	
	
	@RequestMapping("agregarProducto")
	public String agregarProducto(Integer id, Integer cantidad, HttpServletRequest request) {
		int idUsuario = (int) request.getSession().getAttribute("usuario_id");
		System.out.println("Ya tenemos todo lo necesario para agregar un producto al carrito");
		System.out.println("idUsuario: " + idUsuario);
		System.out.println("idProducto: " + id);
		System.out.println("cantidad: " + cantidad);
		servicioCarrito.agregarProducto(idUsuario, id, cantidad);
		return "Se ha añadido al carrito";


	}
	@RequestMapping("obtener") 
	public List<Map<String, Object>> obtener(HttpServletRequest request) {
		int idUsuario = (int) request.getSession().getAttribute("usuario_id");
		return servicioCarrito.obtenerProductosCarrito(idUsuario);
	}

}
