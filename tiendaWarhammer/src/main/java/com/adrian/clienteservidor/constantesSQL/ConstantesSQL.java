package com.adrian.clienteservidor.constantesSQL;

public class ConstantesSQL {
	
	public static final String SQL_OBTENER_PRODUCTOS_PARA_JSON = 
			"select p.id, p.nombre, p.descripcion, p.precio, p.medidas, p.fabricante, p.material, c.nombre AS nombre_categoria from tabla_productos as p, categoria as c where p.categoria_id = c.id order by p.id desc";	
	public static final String SQL_OBTENER_PRODUCTOS_CARRITO =
			"SELECT "
			+ "C.USUARIO_ID, TP.NOMBRE, TP.ID AS PRODUCTO_ID, TP.PRECIO, C.CANTIDAD "
			+ "FROM CARRITO AS C, TABLA_PRODUCTOS AS TP "
			+ "WHERE C.USUARIO_ID = :usuario_id AND TP.ID = C.PRODUCTO_ID ";
	public static final String SQL_ELIMINAR_PRODUCTO_CARRITO =
			"DELETE FROM CARRITO WHERE PRODUCTO_ID = :producto_id AND USUARIO_ID = :usuario_id";
	public static final String SQL_VACIAR_CARRITO = 
			"DELETE FROM CARRITO WHERE USUARIO_ID = :usuario_id";
	
	
}
