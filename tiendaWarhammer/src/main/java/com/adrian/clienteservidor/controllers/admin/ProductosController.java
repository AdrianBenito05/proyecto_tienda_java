package com.adrian.clienteservidor.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adrian.clienteservidor.model.Producto;
import com.adrian.clienteservidor.servicios.ServicioCategorias;
import com.adrian.clienteservidor.servicios.ServicioProducto;
import com.adrian.clienteservidor.servicios.ServicioValoraciones;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/")
public class ProductosController {
	
	@Autowired
	private  ServicioProducto servicioProducto;
	
	@Autowired
	private  ServicioCategorias servicioCategorias;
	
	@Autowired ServicioValoraciones servicioValoraciones;
	
	@RequestMapping("editarProducto")
	public String editarProducto(@RequestParam("id") Long id, Model model) {
		Producto producto = servicioProducto.obtenerProductoPorId(id);
		producto.setIdCategoria(producto.getCategoria().getId());
		model.addAttribute("productoEditar", producto);
		model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
		return "admin/productos_editar";
	}
	
	@RequestMapping("guardarCambiosProducto")
	public String guardarCambiosProducto(@ModelAttribute("productoEditar")@Valid Producto productoEditar,BindingResult resultadoValidaciones ,Model model) {
		if(resultadoValidaciones.hasErrors()) {
			model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
			return "admin/productos_editar";
		}
		servicioProducto.actualizarProducto(productoEditar);
		return obtenerProductos(model);
	}
	
	@RequestMapping("obtenerProductos")
	public String obtenerProductos(Model model) {
		model.addAttribute("array_productos", servicioProducto.obtenerProductos());
	    List<Producto> productos = servicioProducto.obtenerProductos();

		for (Producto p : productos) {
		    Double media = servicioValoraciones.mediaValoracionesProducto(p.getId());
	        p.setMediaValoraciones(media);
		}
		model.addAttribute("productos", productos);
		return "admin/productos";
	}
	@RequestMapping("borrarProducto")
	public String borrarProducto( @RequestParam("id") Long id, Model model) {
		servicioProducto.borrarProducto(id);
		return obtenerProductos(model);
	}
	@RequestMapping("registrarProducto")
	public String registrarProducto(Model model) {
		Producto p = new Producto();
		p.setPrecio(1);
		model.addAttribute("nuevoProducto", p);
		//vamos a meter las categorias en model para que le lleguen a la vista
		model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
		return "admin/productos_registro";
	}
	@RequestMapping("guardarProducto")
	public String guardarProducto(@ModelAttribute("nuevoProducto") @Valid Producto nuevoProducto, BindingResult resultadoValidaciones, Model model) {
		if(resultadoValidaciones.hasErrors()) {
			model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
			return "admin/productos_registro";
		}
		
		servicioProducto.registrarProducto(nuevoProducto);
		return obtenerProductos(model);
	}


}
