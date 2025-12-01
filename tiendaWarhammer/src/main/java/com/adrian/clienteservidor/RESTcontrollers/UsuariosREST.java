package com.adrian.clienteservidor.RESTcontrollers;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.clienteservidor.model.Usuario;
import com.adrian.clienteservidor.servicios.ServicioUsuarios;

import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("usuariosREST/")
public class UsuariosREST {

	@Autowired
	private ServicioUsuarios servicioUsuarios;
	
	
	@RequestMapping("login")
	public String login(String email, String pass, HttpServletRequest request){
		Usuario usuario = servicioUsuarios.obtenerUsuarioPorEmailYpass(email, pass);
		String respuesta = "";
		if( usuario != null ) {
			request.getSession().setAttribute("usuario_id", usuario.getId());
			respuesta = "ok," + usuario.getNombre();
		}else {
			respuesta = "error,email o pass incorrectos";
		}
		return respuesta;
	}
	
	@RequestMapping("registrar")
	public String registrar(String nombre, String email, String pass, String cp, String localidad){
		System.out.println("voy a registrar: " + nombre + " " + email + " " + pass + " " + Integer.parseInt(cp) + " " + localidad);
		if(servicioUsuarios.obtenerUsuarioPoremail(email)!= null) {
			return "Email ya registrado";
		}
		
		servicioUsuarios.registrarUsuario(
			    new Usuario(nombre, pass, email, Integer.parseInt(cp), localidad)
			);

		return "registro ok, ya puedes identificarte";
	}
	@RequestMapping("logout")
	public ResponseEntity<String> logout  (HttpServletRequest request) {
		request.getSession().invalidate();
		return ResponseEntity.ok("Logout Correcto");
	}
	
	
}
