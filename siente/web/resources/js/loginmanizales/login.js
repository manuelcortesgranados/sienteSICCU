function style_background(){  
    
    var _listImg = ["/manizales/resources/imgs/login/bg.jpg","/manizales/resources/imgs/login/bg2.jpg"];
    var _position = 0;
    var _heigth  = 0;
    var _img = "";
    
    
    function dimension(){
        var nom = navigator.appName;
        if (nom == "Microsoft Internet Explorer"){
            if(parseInt(document.body.offsetHeight) < 780){
                _heigth = 800;
            }
        }else if (nom == "Netscape"){
            if(parseInt(window.innerHeight) < 780){
                _heigth = 800;
            }
        }
        session();
    }
    
    function session(){        
        if("localStorage" in window) {
            if(localStorage.backgroud == undefined){
                localStorage.setItem('backgroud', (_listImg.length - 1));
                localStorage.setItem('position', 0);
                img();
            }else{
                var _total = parseInt(localStorage.getItem('backgroud'));
                _position = parseInt(localStorage.getItem('position'));
                if( _position <  _total){
                    _position = _position +1;                    
                    localStorage.setItem('position', ''+_position);
                    img();
                }else{
                    localStorage.setItem('position', 0);
                    img();
                }
            }
            
        }
    }
            
    function img(){
        _img = _listImg[parseInt(localStorage.getItem('position'))];
        if(_img==null || _img==undefined || _img=="" || _img==false){
            _img = "/manizales/resources/imgs/login/bg2.jpg";
            img_background();
        }else{
            img_background();
        }
    }    
    
    function img_background(){
        document.body.style.background = 'url("'+_img+'")no-repeat center center fixed';
        document.body.style.WebkitBackgroundSize = 'cover';
        document.body.style.MozBackgroundSize = 'cover';
        document.body.style.OBackgroundSize = 'cover';
        document.body.style.MsBackgroundSize = 'cover';
        document.body.style.backgroundSize = 'cover';
        document.body.style.overflowX = 'hidden';
        if(_heigth != 0){
            document.body.style.height = _heigth;
        }
    }
    dimension();
    
}


