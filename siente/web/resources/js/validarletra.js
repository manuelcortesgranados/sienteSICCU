function validar(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true; //Tecla de retroceso (para poder borrar)
    patron = /\d/; //Solo acepta números
    te = String.fromCharCode(tecla);
    return patron.test(te);
}