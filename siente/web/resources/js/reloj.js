// var timefalta=document.getElementById("finfecha2").value;
                                                

/*var fut = new Date (timefalta);*/
// window.proyectFin =  new Date("2011","10","20","23","59","59");

//window.proyectFin =  new Date(timefalta);
 function mostrarReloj(){   
    var canvas_segundos = document.getElementById("canvas_segundos");
    var canvas_minutos = document.getElementById("canvas_minutos");
    var canvas_horas = document.getElementById("canvas_horas");
    var canvas_dias = document.getElementById("canvas_dias");
    var ctx_s = canvas_segundos.getContext("2d");
    var ctx_m = canvas_minutos.getContext("2d");
    var ctx_h = canvas_horas.getContext("2d");
    var ctx_d = canvas_dias.getContext("2d");

    var W = 120;
    var H = 120;
    var bgcolor = "silver";
    var color = "#1D6FBC";
    var text;
    var text_width;
    var ancho  = 40;

    var segundos;
    var minutos;
    var segundos_s;
    var horas;
    var minutos_s;
    var dias;
    var horas_s;
    var total_dias = 1000;
    var segundos_slit;

    function init(){
        var  timefalta=document.getElementById("finfecha").value;
        var futuro = new Date (timefalta);
        //var futuro = window.proyectFin;
        var ahora = new Date();
       var faltan = futuro - ahora;
        if(faltan < 0){
            circle(ctx_s,599,0, 0);
            circle(ctx_m,60,0, 0);
            circle(ctx_h,24,0, 0);
            circle(ctx_d,total_dias,0, 0);
        }else{
            segundos_slit = Math.round(faltan/100)%600;
            segundos = Math.round(faltan/1000);
            minutos = Math.floor(segundos/60);
            segundos_s = segundos%60;
            horas = Math.floor(minutos/60);
            minutos_s = minutos%60;
            dias = Math.floor(horas/24);
            horas_s = horas%24;

            circle(ctx_s,599,segundos_slit, segundos_s);
            circle(ctx_m,60,minutos_s, minutos_s);
            circle(ctx_h,24,horas_s, horas_s);
            circle(ctx_d,total_dias,dias, dias);
        }
    }

    function circle(element,cicle, date, title){
        element.clearRect(0, 0, W, H);
        element.beginPath();
        element.strokeStyle = bgcolor;
        element.lineWidth = 8;
        element.arc(W/2, H/2, 55, 0, Math.PI*2, false);
        element.stroke();
        var radians_d = (date * (360/cicle)) * Math.PI / 180;
        element.beginPath();
        element.strokeStyle = color;
        element.lineWidth = 7;
        element.arc(W/2, H/2, 55,  0 - 90*Math.PI/180, radians_d - 90*Math.PI/180, false);
        element.stroke();
        element.fillStyle = '#1D6FBC';
        element.font = "40px bebas";
        text = (title<10?'0'+title:''+title);
        text_width = element.measureText(text).width;
        element.fillText(text, W/2 - text_width/2, H/2 + 15);
    }



    setInterval(function(){
        init();
    },10);

}