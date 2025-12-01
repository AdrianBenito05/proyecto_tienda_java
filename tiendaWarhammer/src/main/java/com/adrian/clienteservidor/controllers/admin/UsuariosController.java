package com.adrian.clienteservidor.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adrian.clienteservidor.servicios.ServicioUsuarios;

@Controller
@RequestMapping("admin/")
public class UsuariosController {
	
	@Autowired
	private ServicioUsuarios servicioUsuarios;
	
	
	@RequestMapping("obtenerUsuarios")
	public String obtenerUsuarios(Model model) {
		model.addAttribute("usuarios", servicioUsuarios.obtenerUsuarios());
		return "admin/usuarios";
	}
	@RequestMapping("borrarUsuario")
	public String borrarProducto( @RequestParam("id") int id, Model model) {
		servicioUsuarios.borrarUsuario(id);
		return obtenerUsuarios(model);
	}
	
	

}
