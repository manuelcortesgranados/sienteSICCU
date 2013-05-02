function completModal(){
    document.getElementById('before').addEventListener('click', function() {rotar_right(1)}, false);
    document.getElementById('slider-li-one-label').setAttribute('class', 'active');
    document.getElementById('slider-li-one-label').addEventListener('click', function() {rotar_right(2)}, false);
    document.getElementById('slider-li-two-label').addEventListener('click', function() {rotar_right(1)}, false);
    document.getElementById('slider-li-tree-label').addEventListener('click', function() {rotar_right(3)}, false);
    rotar_right(2);
}

function rotar_right(_post){
    switch (_post) {
        case 1:
            document.getElementById('slider-li-one-label').setAttribute('class', 'inactive');
            document.getElementById('slider-li-tree-label').setAttribute('class', 'inactive');
            document.getElementById('slider-li-two-label').setAttribute('class', 'active');
            document.getElementById('slider-li-one').setAttribute('class', 'animation-one-right');
            document.getElementById('slider-li-two').setAttribute('class', 'animation-one-right');
            document.getElementById('slider-li-tree').setAttribute('class', 'animation-one-right');
            document.getElementById('after').addEventListener('click', function() {rotar_right(2)}, false);
            document.getElementById('before').addEventListener('click', function() {rotar_right(3)}, false);
            break; 
        case 2:
            document.getElementById('slider-li-one-label').setAttribute('class', 'active');
            document.getElementById('slider-li-two-label').setAttribute('class', 'inactive');
            document.getElementById('slider-li-tree-label').setAttribute('class', 'inactive');
            document.getElementById('slider-li-one').setAttribute('class', 'animation-one-left');
            document.getElementById('slider-li-two').setAttribute('class', 'animation-one-left');
            document.getElementById('slider-li-tree').setAttribute('class', 'animation-one-left');
            document.getElementById('before').addEventListener('click', function() {rotar_right(1)}, false);
            document.getElementById('after').removeEventListener('click', function(){}, false);
            break;
        case 3:
            document.getElementById('slider-li-tree-label').setAttribute('class', 'active');
            document.getElementById('slider-li-two-label').setAttribute('class', 'inactive');
            document.getElementById('slider-li-one-label').setAttribute('class', 'inactive');
            document.getElementById('slider-li-one').setAttribute('class', 'animation-one-right-two');
            document.getElementById('slider-li-two').setAttribute('class', 'animation-one-right-two');
            document.getElementById('slider-li-tree').setAttribute('class', 'animation-one-right-two');
            document.getElementById('after').addEventListener('click', function() {rotar_right(4)}, false);
            document.getElementById('before').removeEventListener('click', function(){}, false);
            break;
        case 4:    
            document.getElementById('slider-li-tree-label').setAttribute('class', 'inactive');
            document.getElementById('slider-li-one-label').setAttribute('class', 'inactive');
            document.getElementById('slider-li-two-label').setAttribute('class', 'active');
            document.getElementById('slider-li-one').setAttribute('class', 'animation-one-left-two');
            document.getElementById('slider-li-two').setAttribute('class', 'animation-one-left-two');
            document.getElementById('slider-li-tree').setAttribute('class', 'animation-one-left-two');
            document.getElementById('after').addEventListener('click', function() {rotar_right(2)}, false);
            document.getElementById('before').addEventListener('click', function() {rotar_right(3)}, false);
            break;
    }

   
    
}