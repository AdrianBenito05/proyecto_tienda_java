package com.adrian.clienteservidor.RESTcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.clienteservidor.model.Producto;
import com.adrian.clienteservidor.servicios.ServicioProducto;


@RestController
@RequestMapping("productosREST/")
public class ProductosREST {

	@Autowired
	private  ServicioProducto servicioProducto;
	//REST son servicios que tu programas
	//esta ruta sera invocada desde javascript para recibir un json con el listado de libros
	@RequestMapping("obtener")
	public List<Map<String, Object>> obtener(){

		return servicioProducto.obtenerProductosParaFormarJSON();
	}
	
}
