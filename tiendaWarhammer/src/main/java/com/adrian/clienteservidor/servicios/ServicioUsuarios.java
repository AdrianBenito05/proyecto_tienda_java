package com.adrian.clienteservidor.servicios;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.adrian.clienteservidor.model.Usuario; 


// en java empresarial hay muchos conceptos confusos
// en este caso se llama servicio al interfaz 
// que va a definir las operaciones posibles con usuarios
// casi igual que cuando hemos usado el paquete repository
// o el paquete daos 



public interface ServicioUsuarios {

	void registrarUsuario(Usuario usuario);
	
	List<Usuario> obtenerUsuarios();
	
	void borrarUsuario(int id);
	
	Usuario obtenerUsuarioPorId(int id);
	
	Usuario obtenerUsuarioPoremail(String email);

	void actualizarUsuario(Usuario usuarioEditar);

	Usuario obtenerUsuarioPorEmailYpass(String email, String pass);
	
}
