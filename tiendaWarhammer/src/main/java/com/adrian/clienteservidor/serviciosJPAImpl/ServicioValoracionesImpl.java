package com.adrian.clienteservidor.serviciosJPAImpl;

import org.springframework.stereotype.Service;

import com.adrian.clienteservidor.servicios.ServicioValoraciones;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioValoracionesImpl implements ServicioValoraciones {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Double mediaValoracionesProducto(long idProducto) {
	    Double media = entityManager.createQuery(
	            "SELECT AVG(v.puntuacion) FROM Valoracion v WHERE v.producto.id = :idProducto", Double.class)
	            .setParameter("idProducto", idProducto)
	            .getSingleResult();
	    if(media == null) {
	    	media = 0.0;
	    }

		if (media != null) {
		    media = Math.round(media * 10.0) / 10.0; 
		}
	    return media;
	}


}
