package com.adrian.clienteservidor.controllers.loginAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adrian.clienteservidor.controllers.admin.ProductosController;

import jakarta.servlet.http.HttpServletRequest;

//Controlador que se encarga de identificar a un admin
@Controller
public class LoginAdminController {
	
	@Autowired
	private ProductosController productosController;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("loginAdmin")
	public String loginAdmin() {
		return "admin/loginAdmin";
	}
	
	@RequestMapping("logoutAdmin")
	public String logoutAdmin(HttpServletRequest request) {
		
		String idiomaActual = 
				messageSource.getMessage("idioma", null, LocaleContextHolder.getLocale()).trim();

		request.getSession().invalidate();

		
		System.out.println("idiomaActual: " + idiomaActual);
		
		return "tienda_"+idiomaActual;

	}

}
