package com.adrian.clienteservidor.serviciosJPAImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.clienteservidor.Constantes.Estados;
import com.adrian.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.adrian.clienteservidor.constantesSQL.ConstantesSQL;
import com.adrian.clienteservidor.model.Carrito;
import com.adrian.clienteservidor.model.Pedido;
import com.adrian.clienteservidor.model.Producto;
import com.adrian.clienteservidor.model.ProductosPedido;
import com.adrian.clienteservidor.model.Usuario;
import com.adrian.clienteservidor.model.Valoracion;
import com.adrian.clienteservidor.servicios.ServicioCarrito;
import com.adrian.clienteservidor.servicios.ServicioPedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class ServicioPedidoImpl implements ServicioPedidos {
	
	@Autowired
	private ServicioCarrito servicioCarrito;
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<Pedido> obtenerPedidos() {	
		return entityManager.createQuery("select p from Pedido p order by p.id desc", Pedido.class).getResultList();
	}
	@Override
	public Pedido obtenerPedidoPorId(int idPedido) {
		return (Pedido) entityManager.find(Pedido.class, idPedido);
	}
	@Override
	public void actualizarPedido(int idPedido, String estado) {
		Pedido p = entityManager.find(Pedido.class, idPedido);
		p.setEstado(estado);
		entityManager.merge(p);
		
	}
	
	
	
	@Override
	public void procesarPaso1(String nombre, String direccion, String provincia, int idUsuario, String portal, String piso, String telefono) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		p.setNombreCompleto(nombre);
		p.setDireccion(direccion);
		p.setProvincia(provincia);
		p.setPortal(portal);
		p.setPiso(piso);
		p.setTelefono(telefono);
		entityManager.persist(p);
		
	}
	// este metodo devuelve el pedido incompleto actual del usuario, si no existe, lo creamos
	private Pedido obtenerPedidoIncompletoActual(int idUsuario) {
		Usuario usuario = entityManager.find(Usuario.class, idUsuario);
		Object pedidoIncompleto = null;
		List<Pedido> pedidos = 
				entityManager.createQuery("select p from Pedido p where p.estado = :estado and p.usuario.id = :usuario_id")
				.setParameter("estado", Estados.INCOMPLETO.name()).setParameter("usuario_id", idUsuario).getResultList();
		if(pedidos.size() == 1) {
			pedidoIncompleto = pedidos.get(0);
		}
		Pedido pedido = null;
		if(pedidoIncompleto != null) {
			pedido = (Pedido) pedidoIncompleto;
		}else {
			pedido = new Pedido();
			pedido.setEstado(Estados.INCOMPLETO.name());
			pedido.setUsuario(usuario);
		}
		return pedido;
	}
	@Override
	public void procesarPaso2(String tarjeta, String numero, String titular, int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		p.setTipoTarjeta(tarjeta);
		p.setNumeroTarjeta(numero);
		p.setTitularTarjeta(titular);
		entityManager.merge(p);
		// preparamos el ResumenPedido a devolver
		
	}
	private ResumenPedido obtenerResumenDelPedido(int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		ResumenPedido resumen = new ResumenPedido();
		resumen.setNombreCompleto(p.getNombreCompleto());
		resumen.setDireccion(p.getDireccion());
		resumen.setProvincia(p.getProvincia());
		resumen.setPortal(p.getPortal());
		resumen.setPiso(p.getPiso());
		resumen.setTelefono(p.getTelefono());
		resumen.setTipoTarjeta(p.getTipoTarjeta());
		resumen.setTitularTarjeta(p.getTitularTarjeta());
		// lo siguiente ira cifrado menos los 4 ultimos numeros
		resumen.setNumeroTarjeta(p.getNumeroTarjeta());
		resumen.setProductos(servicioCarrito.obtenerProductosCarrito(idUsuario));
		resumen.setFaccion(p.getFaccion());
		return resumen;
		
		
	}


	
	@Override
	public void confirmarPedido(int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		// obtener los productos en el carrito para meterlos en productoPedido
		Usuario u = entityManager.find(Usuario.class, idUsuario);
		List<Carrito> c = entityManager.createQuery("select c from Carrito c where c.usuario.id = :usuario_id")
				.setParameter("usuario_id", idUsuario).getResultList();
		for( Carrito productoCarrito : c ) {
			ProductosPedido pp = new ProductosPedido();
			pp.setCantidad(productoCarrito.getCantidad());
			pp.setProducto(productoCarrito.getProducto());
			pp.setPedido(p);
			entityManager.persist(pp);
		}
		p.setEstado(Estados.TERMINADO.name());
		entityManager.merge(p);
		// eliminar los productos del carrito
		entityManager.createNativeQuery(ConstantesSQL.SQL_VACIAR_CARRITO).setParameter("usuario_id", idUsuario).executeUpdate();
	}

	@Override
	public ResumenPedido procesarPaso3(String faccion, int idUsuario, List<Map<String, Object>> listaValoracionesInput) {
	    Pedido p = obtenerPedidoIncompletoActual(idUsuario);
	    p.setFaccion(faccion);

	    for (Map<String, Object> vInput : listaValoracionesInput) {
	        Long idProducto = ((Number)vInput.get("idProducto")).longValue();
	        Double puntuacion = ((Number)vInput.get("puntuacion")).doubleValue();

	        Producto producto = entityManager.find(Producto.class, idProducto);
	        Valoracion v = new Valoracion();
	        v.setPedido(p);
	        v.setProducto(producto);
	        v.setPuntuacion(puntuacion);

	        entityManager.persist(v);
	    }

	    entityManager.merge(p);
	    return obtenerResumenDelPedido(idUsuario);
	}




	
	

	
	
}
