document.onkeypress=function(e) {
    var esIE=(document.all);
    tecla=(esIE) ? event.keyCode : e.which;
    if(tecla==13){
        return false;
    }
    return true;
}

if (window.history) {
    function noBack(){window.history.forward()}
    noBack();
    prueba="1";
   // window.onload=noBack;
    window.onpageshow=function(evt){if(evt.persisted)noBack()}
    window.onunload=function(){void(0)}
}

