package com.adrian.clienteservidor.serviciosJPAImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;   
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import com.adrian.clienteservidor.constantesSQL.ConstantesSQL;
import com.adrian.clienteservidor.model.Categoria;
import com.adrian.clienteservidor.model.Producto;
import com.adrian.clienteservidor.servicios.ServicioCarrito;
import com.adrian.clienteservidor.servicios.ServicioProducto;
import com.adrian.clienteservidor.servicios.ServicioValoraciones;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class ServicioProductosImpl implements ServicioProducto {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ServicioValoraciones servicioValoraciones;
	
	@Override
	public void registrarProducto(Producto p) {
		try {
			p.setImagenPortada(p.getImagen().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Tenemos que asociar la categoria a nivel de base de datos
		//porque idCategoria tiene puesto @Transient
		Categoria categoria = entityManager.find(Categoria.class, p.getIdCategoria());
		p.setCategoria(categoria);
		entityManager.persist(p);
		
	}

	@Override
	public List<Producto> obtenerProductos() {
		List<Producto> productos = entityManager.createQuery("select p from Producto p  order by p.id desc").getResultList();
		return productos;
	}

	@Override
	public void borrarProducto(long id) {
		// antes de borrar el producto debemos eliminar todas las referencias
		// al mismo en el carrito y pedidos
		entityManager.createNativeQuery("delete from CARRITO where PRODUCTO_ID = :id").setParameter("id", id).executeUpdate();
		entityManager.createNativeQuery("delete from PRODUCTOS_PEDIDO where PRODUCTO_ID = :id").setParameter("id", id).executeUpdate();

		entityManager.createNativeQuery("delete from tabla_productos where id = :id").setParameter("id", id).executeUpdate();

	}

	@Override
	public void actualizarProducto(Producto productoFormEditar) {
		//Lo siu¡guiente es un tanto bestia, porque hay campos sensible como por ejemplo el de byte[]
		//entityManager.merge(p);
		Producto productoBaseDatos = entityManager.find(Producto.class, productoFormEditar.getId());
		productoBaseDatos.setNombre(productoFormEditar.getNombre());
		productoBaseDatos.setFabricante(productoFormEditar.getFabricante());
		productoBaseDatos.setDescripcion(productoFormEditar.getDescripcion());
		productoBaseDatos.setMaterial(productoFormEditar.getMaterial());
		productoBaseDatos.setMedidas(productoFormEditar.getMedidas());
		productoBaseDatos.setPrecio(productoFormEditar.getPrecio());
		if( productoFormEditar.getImagen().getSize() > 0) {
			try {
				productoBaseDatos.setImagenPortada(productoFormEditar.getImagen().getBytes());
			} catch (IOException e) {
				System.out.println("No se pudo procesar el archivo subido");
				e.printStackTrace();
			}
		}
		productoBaseDatos.setCategoria(entityManager.find(Categoria.class, productoFormEditar.getIdCategoria()));
		entityManager.merge(productoBaseDatos);
		
	}

	@Override
	public Producto obtenerProductoPorId(long id) {
		return entityManager.find(Producto.class, id);

	}

	@Override
	public List<Map<String, Object>> obtenerProductosParaFormarJSON() {
	    Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_PRODUCTOS_PARA_JSON, Tuple.class);
	    List<Tuple> tuples = query.getResultList();

	    List<Map<String, Object>> resultado = new ArrayList<>();
	    for (Tuple tuple : tuples) {
	        Map<String, Object> fila = new HashMap<>();
	        for (TupleElement<?> element : tuple.getElements()) {
	            fila.put(element.getAlias(), tuple.get(element));
	        }

	        // OJO: aquí sacamos el ID del producto y calculamos la media
	        Object idObj = fila.get("ID"); // el alias que uses en tu SQL para el id
	        if (idObj != null) {
	            Long idProducto = ((Number) idObj).longValue();
	            Double media = servicioValoraciones.mediaValoracionesProducto(idProducto);
	            fila.put("mediaValoraciones", media); // añadimos al Map
	        }

	        resultado.add(fila);
	    }

	    return resultado;
	}




}
