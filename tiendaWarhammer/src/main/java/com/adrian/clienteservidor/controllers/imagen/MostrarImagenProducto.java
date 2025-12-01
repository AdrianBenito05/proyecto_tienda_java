package com.adrian.clienteservidor.controllers.imagen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adrian.clienteservidor.servicios.ServicioProducto;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MostrarImagenProducto {

	@Autowired
	private ServicioProducto servicioProducto;
	
	// excepcionalmente este metodo no va a devolver un String indicando una vista
	// sino que programaremos manualmente que es lo que va devolver al usuario
	@RequestMapping("mostrar_imagen")
	public void mostrarImagen(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
		byte[] info = servicioProducto.obtenerProductoPorId(id).getImagenPortada(); 
		if(info == null) {
			return;
		}
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(info);
		response.getOutputStream().close();
		
	}
	@RequestMapping("mostrar_imagen_usuario")
	public void mostrarImagen_usuario(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
	
		
	}
	
}
