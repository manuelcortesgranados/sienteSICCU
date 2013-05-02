var nav4 = window.Event ? true : false;

//function acceptNum(evt)
//{ 
//	// NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57 
//	var key = nav4 ? evt.which : evt.keyCode; 
//	return (key <= 13 || (key >= 48 && key <= 57));
//}

function acceptNum(e)
{ 
        // NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57 
        var keynum
        var keychar
        var numcheck
        if(window.event) // IE
        {
          keynum = e.keyCode
        }
        else if(e.which) // Netscape/Firefox/Opera
        {
        keynum = e.which
        }
        keychar = String.fromCharCode(keynum)
        numcheck = /\d/
        return !numcheck.test(keychar)
} 
function acceptLetra(evt)
{ 
	// NOTE: Backspace = 32, 'A' = 65  'Z ' = 90  , 'a' = 97, 'z' = 122 
	var key = nav4 ? evt.which : evt.keyCode; 
	return (key == 32 || (key >= 65 && key <= 90)|| (key >= 97 && key <= 122));
} 
function acceptLetraNum(evt)
{ 
	// NOTE:  '0' = 48, '9' = 57 , 'A' = 65  'Z ' = 90  , 'a' = 97, 'z' = 122 
	var key = nav4 ? evt.which : evt.keyCode; 
	return ((key >= 48 && key <= 57)||(key >= 65 && key <= 90)|| (key >= 97 && key <= 122));
} 
function Enviar(form)
{
    for (i = 0; i < form.elements.length; i++)
    
	    {
		if (form.elements[i].type == 'text' && form.elements[i].value == '')
		    { 
			alert('Error hay campos vacios');form.elements[i].focus();
            return false;
		     }
        if (form.elements[i].type == 'password' && form.elements[i].value == '')
		    {
			alert('Error hay campos vacios');form.elements[i].focus();
            return false;
		     }
	    }
        
	   if (confirm("Está seguro de enviar los datos?"))
	{
		return (true);
	}
	else
	{
		return (false);
		}
		form.submit();
}
function printit()
{  
	if (window.print)
	   	window.print() ;  
    else
    {
    	var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';
		document.body.insertAdjacentHTML('beforeEnd', WebBrowser);
    	WebBrowser1.ExecWB(6, 2);//Use a 1 vs. a 2 for a prompting dialog box    WebBrowser1.outerHTML = "";  
	}
} 
function validarEmail(valor)
{
	 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(valor))
	 {
   		alert("La dirección de email " + valor    + " es correcta.")
   		return (true)
  	 }
  	 else
  	 {
   		alert("La dirección de email es incorrecta.");
   		return (false);
  	 }
}
function ValidarUsuario() 
{ 
	if(formulario.usu_nom.value=="" || formulario.usu_pas.value=="" || formulario.usu_rep.value=="")
	{
		alert('Llene todos los campos'); 
		return false;
	}
	else
		if(formulario.usu_pas.value!=formulario.usu_rep.value)
		{
			alert('Las contraseñas no concuerda');
			return false;
		}
		else
		{
			if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(formulario.usu_ema.value))
			{
                formulario.submit();
                return true;
  			}
            else
  			{
   				alert("La dirección de email es incorrecta.");
   				return false;
  			}
		}
}
function Salir()
{
	if(confirm("Esta seguro que desea salir?"))
	 window.close();	
         
}
function pipe()
{
	if(confirm("Esta seguro que desea enviar?"))
        formulario.submit(); 
}

function Limpiar(Formu)
{ 
    total = 0 
    UltimaOperacion = "+" 
    Formu.display.value = ""
}
function NewWindow(mypage, myname, w, h, scroll) 
{
	var winl = (screen.width - w) / 2;
	var wint = (screen.height - h) / 2;
	winprops = 'height='+h+',width='+w+',top='+wint+',left='+winl+',scrollbars='+scroll+',resizable'
	win = window.open(mypage, myname, winprops)
	if (parseInt(navigator.appVersion) >= 4) 
	{ 
		win.window.focus(); 
	}
}
function Salir1()
{
	
	 window.close();	
         
}

function cambiarImagen(id,img){
    
    document.getElementById(id).src = img;
}
//Lineas para deshabilitar botones de adelante y atras
var nHist = window.history.length;
if(window.history[nHist] != window.location)
  window.history.forward();

function mnsInterventor(){
    try {
        
        var _img = "resources/imgs/msgerror.png";
        $('<div />')
        .css('padding','4px')
        .html('<div style="border: none; font-size:11px"><table><tr><td style="vertical-align: text-top;padding: 3px 3px 3px 3px;"><img src="'+_img+'" style="height: 42px;" /></td><td style="vertical-align: top;padding: 5px 10px 10px 10px;font-weight: bold;font-size: 11px;"><p>SEÑOR INTERVENTOR, LE RECOMENDANMOS PARA DILIGENCIAR CORRECTAMENTE SU REPORTE DE OBRAS Y SOLICITAR SU RESPECTIVA VALIDACION CONSULTAR LA CIRCULAR 65 EN <a href="www.colombiahumanitaria.gov.co" style="color:blue;">www.colombiahumanitaria.gov.co</a> Link Circulares.</p></td></tr></table></div>')
        .appendTo(document.body)
        .children()
        .tabs()
        .end()
        .dialog({
            modal: true,
            title: "SIENTE",
            resizable: false,
            stack: true,
            closeOnEscape : false,
            minWidth : 600,
            open : function(){
                
            },
            close: function() {
                try{
                    $(this).children().tabs('destroy').remove().end().dialog('destroy').remove();
                } catch (e){
                    return false;
                }
            },
            buttons: [{
                text: "Aceptar",
                click: function() {
                    try{
                        $(this).dialog("close");
                    } catch (e){
                        return false;
                    }
                }
            }
            ]
        })
    } catch (e){
        return false;
    }
}