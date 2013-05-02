/*
 * jQuery CSS popUp
 * http://dixso.net/
 *
 * Copyright (c) 2009 Julio De La Calle Palanques
 *
 * Date: 2009-03-27 12:34:00 - (Viernes, 27 Mar 2009)
 * 
 */
 
//Variable que almacenar� la posici�n del scroll, por defecto tiene valor 0.
var scrollCachePosition = 0;

jQuery(function() {
	jQuery("#abrirPop").click(function(event) {
		scrollCachePosition = jQuery(window).scrollTop();
		//Env�o el scroll a la posici�n 0 (left), 0 (top), es decir, arriba de todo.
		//window.top.scroll(0,0);
               
		//Si el body es mas grande que la capa 'wrapper' incrementa la altura del body a la capa 'capaPopUp'.
		if (jQuery("body").outerHeight()>jQuery("#wrapper").outerHeight()){
			var altura=jQuery("body").outerHeight();
	    }else{
		//Si la capa 'wrapper' es m�s grande que el body incrementa la altura de la capa 'wrapper' a la capa 'capaPopUp'.
			var altura=jQuery("#wrapper").outerHeight();
		}
		window.document.getElementById("capaPopUp").style.height=altura+"px";
		event.preventDefault();
		//Muestro la capa con el efecto 'slideToggle'.
		jQuery("#capaPopUp").slideToggle();

		//Calculo la altura de la capa 'popUpDiv' y lo divido entre 2 para darle un margen negativo.
		var altura=jQuery("#popUpDiv").outerHeight();
		jQuery("#popUpDiv").css("margin-top","-"+parseInt(altura/2)+"px");
		
		//Calculo la anchura de la capa 'popUpDiv' y lo divido entre 2 para darle un margen negativo.
		var anchura=jQuery("#popUpDiv").outerWidth();
		jQuery("#popUpDiv").css("margin-left","-"+parseInt(anchura/2)+"px");
		
		//Muestro la capa con el efecto 'slideToggle'.
		jQuery("#popUpDiv").slideToggle();
	});
	jQuery("#cerrar").click(function(event) {
		event.preventDefault();
		//Cierro las capas con el efecto 'slideToggle'.
		jQuery("#capaPopUp").slideToggle();
		jQuery("#popUpDiv").slideToggle();
		//Si la variable scrollCachePosition es mayor que 0 incrementar� la posici�n del scroll al valor que se almacen�. 
		if (scrollCachePosition > 0) {
			window.top.scroll(0,scrollCachePosition);
			//Reseteamos la variable scrollCachePosition a 0 para poder ejecutar el script tantas veces sea necesario.
			scrollCachePosition = 0;
		}
	});

        jQuery("#abrirPop1").click(function(event) {
		scrollCachePosition = jQuery(window).scrollTop();
		//Env�o el scroll a la posici�n 0 (left), 0 (top), es decir, arriba de todo.
		//window.top.scroll(0,0);
              
		//Si el body es mas grande que la capa 'wrapper' incrementa la altura del body a la capa 'capaPopUp'.
		if (jQuery("body").outerHeight()>jQuery("#wrapper1").outerHeight()){
			var altura=jQuery("body").outerHeight();
	    }else{
		//Si la capa 'wrapper' es m�s grande que el body incrementa la altura de la capa 'wrapper' a la capa 'capaPopUp'.
			var altura=jQuery("#wrapper1").outerHeight();
		}
		window.document.getElementById("capaPopUp1").style.height=altura+"px";
		event.preventDefault();
		//Muestro la capa con el efecto 'slideToggle'.
		jQuery("#capaPopUp1").slideToggle();

		//Calculo la altura de la capa 'popUpDiv' y lo divido entre 2 para darle un margen negativo.
		var altura=jQuery("#popUpDiv1").outerHeight();
		jQuery("#popUpDiv1").css("margin-top","-"+parseInt(altura/2)+"px");

		//Calculo la anchura de la capa 'popUpDiv' y lo divido entre 2 para darle un margen negativo.
		var anchura=jQuery("#popUpDiv1").outerWidth();
		jQuery("#popUpDiv1").css("margin-left","-"+parseInt(anchura/2)+"px");

		//Muestro la capa con el efecto 'slideToggle'.
		jQuery("#popUpDiv1").slideToggle();
	});
	jQuery("#cerrar1").click(function(event) {
		event.preventDefault();
		//Cierro las capas con el efecto 'slideToggle'.
		jQuery("#capaPopUp1").slideToggle();
		jQuery("#popUpDiv1").slideToggle();
		//Si la variable scrollCachePosition es mayor que 0 incrementar� la posici�n del scroll al valor que se almacen�.
		if (scrollCachePosition > 0) {
			window.top.scroll(0,scrollCachePosition);
			//Reseteamos la variable scrollCachePosition a 0 para poder ejecutar el script tantas veces sea necesario.
			scrollCachePosition = 0;
		}
	});
});