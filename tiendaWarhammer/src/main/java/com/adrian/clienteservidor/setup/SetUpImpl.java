package com.adrian.clienteservidor.setup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrian.clienteservidor.model.Carrito;
import com.adrian.clienteservidor.model.Categoria;
import com.adrian.clienteservidor.model.Producto;
import com.adrian.clienteservidor.model.Usuario;
import com.adrian.clienteservidor.servicios.ServicioPedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class SetUpImpl implements SetUp {
	
	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@PersistenceContext
	private EntityManager entityManager;
	

	@Override
	public void prepararRegistros() {
		TablaSetUp registroSetUp = null;
		// si no hay un registro en la tabla set up, preparamos los registros iniciales
		try {
			registroSetUp = (TablaSetUp) entityManager.createQuery("select r from TablaSetUp r").getSingleResult();
		} catch (Exception e) {
			System.out.println("No se encontro ningun egistro en la tabla setup, " + "comenzamos a realizar los registros iniciales");
		}
		if(registroSetUp!=null && registroSetUp.isCompletado()) {
			return;
		}
		//preparar categorias iniciales
		Categoria cSpaceMarine = new Categoria("Space Marine", "Figura de space marine");
		entityManager.persist(cSpaceMarine);
		Categoria cChaos = new Categoria("Chaos", "Figura de chaos");
		entityManager.persist(cChaos);
		Categoria cOtros = new Categoria("Otros", "Figura de otra faccion");
		entityManager.persist(cOtros);
		//preparamos los registros iniciales:
		Producto producto1 = new Producto("orko", "23x12x32", "Bandai", "Orko soldado", "Resina", 12.4 );
		producto1.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/productos/orko.jpg"));
		producto1.setCategoria(cOtros);
		entityManager.persist(producto1);
		Producto producto2 = new Producto("Space Marine", "25x15x35", "Games Workshop", "Soldado de élite", "Plástico", 15.8);
		producto2.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/productos/space.jpg"));
		producto2.setCategoria(cSpaceMarine);
		entityManager.persist(producto2);

		Producto producto3 = new Producto("Eldar Farseer", "20x10x30", "Citadel", "Vidente psíquico Eldar", "Resina", 13.2);
		producto3.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/productos/Eldar.jpg"));
		producto3.setCategoria(cOtros);
		entityManager.persist(producto3);

		Producto producto4 = new Producto("Ork Nob", "28x18x40", "Forge World", "Líder de escuadra Ork", "Metal", 17.5);
		producto4.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/productos/orknob.jpg"));
		producto4.setCategoria(cOtros);
		entityManager.persist(producto4);

		Producto producto5 = new Producto("Necron Warrior", "22x12x33", "Games Workshop", "Soldado robótico Necron", "Plástico", 14.0);
		producto5.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/productos/necron.jpg"));
		producto5.setCategoria(cOtros);
		entityManager.persist(producto5);

		Producto producto6 = new Producto("Chaos Terminator", "30x20x45", "Citadel", "Guerrero del Caos blindado", "Resina", 19.3);
		producto6.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/productos/terminator.jpg"));
		producto6.setCategoria(cChaos);
		entityManager.persist(producto6);

		Usuario usuario1 = new Usuario ("Adrian", "567", "adrian@gmail.com", 28032, "Madrid");
		entityManager.persist(usuario1);
		Usuario usuario2 = new Usuario("Lucía", "891", "lucia.fernandez@gmail.com", 18001, "Barcelona");
		entityManager.persist(usuario2);

		Usuario usuario3 = new Usuario("Carlos", "234", "carlos.martinez@hotmail.com", 41001, "Sevilla");
		entityManager.persist(usuario3);

		Usuario usuario4 = new Usuario("Marta", "765", "marta.gomez@yahoo.es", 46001, "Valencia");
		entityManager.persist(usuario4);

		Usuario usuario5 = new Usuario("Javier", "345", "javier.rodriguez@outlook.com", 50001, "Zaragoza");
		entityManager.persist(usuario5);
		
		
		// vamos a meter unos productos en el carrito de varios usuarios:
		Carrito registroCarrito = new Carrito();
		registroCarrito.setUsuario(usuario1);
		registroCarrito.setProducto(producto1);
		registroCarrito.setCantidad(3);
		entityManager.persist(registroCarrito);
		
		
		
		//registrar un pedido para usuario1
		servicioPedidos.procesarPaso1("Adrian", "Nelson 21", "Madrid" ,usuario1.getId(),"9","4d", "662132415");
		servicioPedidos.procesarPaso2("1", "54656743", "Adrian", usuario1.getId());
		servicioPedidos.confirmarPedido(usuario1.getId());
		
		Carrito registroCarrito2 = new Carrito();
		registroCarrito2.setUsuario(usuario3);
		registroCarrito2.setProducto(producto5);
		registroCarrito2.setCantidad(2);
		entityManager.persist(registroCarrito2);
		
		
		// Una vez preparados los registros iniciales, marcamos el setup completado de la siguiente forma:
		TablaSetUp registro = new TablaSetUp();
		registro.setCompletado(true);
		entityManager.persist(registro);

	}// end prepararRegistros
	
	// metodo que nos va a permitir obtener un byte[] de cada archivo de imagenes_base
	private byte[] obtenerInfoImagen(String ruta_origen) {
		byte[] info = null;
		try {
			URL url = new URL(ruta_origen);
			info = IOUtils.toByteArray(url);
		} catch (IOException e) {
			System.out.println("No se pudo procesar: " + ruta_origen);
			e.printStackTrace();
		}	
		return info;
	}

}
