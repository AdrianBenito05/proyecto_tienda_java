package com.adrian.clienteservidor.serviciosJPAImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adrian.clienteservidor.model.Categoria;
import com.adrian.clienteservidor.servicios.ServicioCategorias;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class ServicioCategoriasImpl implements ServicioCategorias {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public List<Categoria> obtenerCategorias() {
		
		return entityManager.createQuery("select c from Categoria c").getResultList();
	}

}
