//funciones generales

function checkout_paso_4(json) {
	if (json.numeroTarjeta) {
		let ultimos4 = json.numeroTarjeta.slice(-4);
		json.numeroTarjetaMasked = "**** **** **** " + ultimos4;
	}
	let html = Mustache.render(plantilla_checkout_4, json)
	$("#contenedor").html(html)
	$("#boton_confirmar_pedido").click(function(){
		$.post("pedidosREST/paso4").done(function(res){
			alert(res)
			if(res == "ok"){
				obtenerProductos()
			}
				
		})
	})

}
function checkout_paso_3() {
    // Obtenemos los productos que están en el carrito
    $.get("carritoREST/obtener", function(productos) {
        // Renderizamos la plantilla con los productos del carrito
        let html = Mustache.render(plantilla_checkout_3, { array_productos: productos });
        $("#contenedor").html(html);

        // Manejo del botón de aceptar
        $("#aceptar_paso_3").click(function() {
            let faccion_favorita = $("#faccion_favorita").val();
            if (faccion_favorita === "0") {
                alert("Selecciona una facción");
                return;
            }

            let valoraciones = [];
            let valido = true;

            // Recolectamos las puntuaciones de los productos
            $(".puntuacion_producto").each(function() {
                let puntuacion = parseFloat($(this).val());
                let idProducto = $(this).data("id-producto");
				

                if (isNaN(puntuacion) || puntuacion < 0 || puntuacion > 5) {
                    alert("Introduce una puntuación válida entre 0 y 5 para todos los productos");
                    valido = false;
                    return false;
                }

                valoraciones.push({ idProducto: idProducto, puntuacion: puntuacion });
            });

            if (!valido) return;

            // Enviamos facción y valoraciones al backend con POST
            $.post("pedidosREST/paso3", {
                faccion: faccion_favorita,
                valoraciones: JSON.stringify(valoraciones)
            }, function(resumenPedido) {
                // 'resumenPedido' contiene el pedido actualizado, lo pasamos al paso 4
                checkout_paso_4(resumenPedido);
            })
        });
    });
}




	



function checkout_paso_2(){
	$("#contenedor").html(plantilla_checkout_2)
	$("#aceptar_paso_2").submit(function(e){
		e.preventDefault()
		tipo_tarjeta = $("#tipo_tarjeta").find(":selected").val()
		if(tipo_tarjeta == "0"){
			alert("selecciona un tipo de tarjeta")
			return
		}
		numero = $("#numero_tarjeta").val()
		titular = $("#titular_tarjeta").val()
		$.post("pedidosREST/paso2", {
			tarjeta: tipo_tarjeta,
			numero: numero,
			titular: titular
		}).done(function(res){	
			//Usar la plantilla de checkout paso 3 y pocesarla con el json recibido
			if(res == "ok"){
				checkout_paso_3()
			}
		})
	})
}


function checkout_paso_1() {
	nombre = $("#campo_nombre").val()
	direccion = $("#campo_direccion").val()
	provincia = $("#campo_provincia").val()
	portal = $("#campo_portal").val()
	piso = $("#campo_piso").val()
	telefono = $("#campo_telefono").val()
	$.post("pedidosREST/paso1",{
		nombre: nombre, 
		direccion: direccion,
		provincia: provincia,
		portal:portal,
		piso: piso,
		telefono:telefono
		
	}).done(function(res) {
		alert(res)
		if(res == "ok"){
			checkout_paso_2()
		}
	})
}//end checkout_paso_1



function checkout_paso_0(){
	$("#contenedor").html(plantilla_checkout)
	$("#aceptar_paso_1").submit(function(e){
		e.preventDefault()
		checkout_paso_1()

	})
}//end checkout_paso_0



function mostrarCarrito(){
	//$("#contenedor").html(plantilla_carrito)
	if(nombre_login == ""){
			alert("Tienes que identificarte para acceder al carrito")
		}else{
			$.get("carritoREST/obtener", function(r){
				if(r.length == 0){
					alert("Aun no has agregado nada al carrito")
				}else{
					let html = Mustache.render(plantilla_carrito,r)
					$("#contenedor").html(html)
					$("#realizar_pedido").click(checkout_paso_0)
					//decir que hay que hacer cuando se haga click en el enlace de borrar producto
					$(".enlace-borrar-producto-carrito").click(function(){
						if( ! confirm("¿Estas seguro?")){
							return
						}
						let idProducto = $(this).attr("id-producto")
						$.post("carritoREST/eliminar", {
							id: idProducto}).done(function(res){
								alert(res)
								if(res== "ok"){
									mostrarCarrito()
								}
							})
						
					})//end click
					
					
				}//end else
			})//end get
			
}//end else
}// end mostrarCarrito



function comprar_producto(){
	if(nombre_login == ""){
		alert("Tienes que identificarte para comprar productos")
	}else{
		var id_producto = $(this).attr("id-producto");
		// invocar a una operacion de CarrritoREST para agregar el producto al carrrito
		$.post("carritoREST/agregarProducto", {
			id: id_producto, 
			cantidad: 1
		}).done(function(res){
			alert(res)
		})
	}	
}// end comprar_producto

function obtenerProductos(){
$.get("productosREST/obtener", function(r){
//codigo a ejecutar cuando reciba la respuesta del recurso indicado
//alert("Recibido: " + r)
var productos = r
console.log(productos)
var html = Mustache.render( plantilla_productos, { mensaje_bienvenida : "", array_productos: productos } )
$("#contenedor").html(html)
$(".enlace_comprar_producto").click(comprar_producto);

})//end $.get

}//end obtenerProductos


function mostrarLogin(){
	if (nombre_login != "") {
	    if (confirm("Ya has iniciado sesión como " + nombre_login + ". ¿Quieres cerrar sesión?")) {
	        nombre_login = "";
	        $("#login_usuario").html("Usuario no identificado");
	        alert("Sesión cerrada correctamente");
			$("#enlace_identificarme").html("Identificarme")

	    }
	    return;
	}
$("#contenedor").html(plantilla_login)
$("#form_login").submit(function(e){

e.preventDefault()
var email = $("#email").val()
var pass = $("#pass").val()
$.post("usuariosREST/login", {
	email: email, 
	pass: pass
}).done(function(res){
	var parte1 = res.split(",")[0]
	var parte2 = res.split(",") [1]
	if(parte1 =="ok"){
		alert("Bienvenido " + parte2 + " ya puedes comprar")
		nombre_login = parte2
		$("#login_usuario").html("hola " + parte2)
		$("#enlace_identificarme").html("Cerrar Sesion")
		obtenerProductos()
		
	}else{
		alert(res)

	}
})//end done	
})//end submit
}//end mostrarLogin

function mostrarRegistro(){
$("#contenedor").html(plantilla_registro)
//vamos a interceptar el envio de formulario
$("#form_registro").submit(function(e){
e.preventDefault()
//alert("Se intenta enviar form")
//recoger los datos del form y mandarselos a UsuariosREST
var nombre = $("#nombre").val()
var email = $("#email").val()
var pass = $("#pass").val()
var localidad = $("#localidad").val()
var cp = $("#cp").val()
$.post("usuariosREST/registrar", {
nombre: nombre,
email: email,
pass: pass,
localidad: localidad,
cp: cp
}).done(function(res){
alert(res)
})//end donde
})// end submit form
}// end mostrar registro

//atencion a eventos:

$("#enlace_productos").click(obtenerProductos)
$("#enlace_identificarme").click(mostrarLogin)
$("#enlace_registrarme").click(mostrarRegistro)
$("#enlace_carrito").click(mostrarCarrito)

