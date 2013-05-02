
(function($) {
    $.fn.extendslider({
        slider2: function(options) {           
            var settings = $.extendslider({
                speed: 500,
                action : (options.action)? true:false,
                time : (options.time)? options.time: 4000
            }, options);
            return this.each(function() {
                var slidercontents = $(this).addClass('panel-image-slider-contents');
                var slider = $('<div/>').addClass('panel-image-slider').attr('id', slidercontents.attr('id'));
                var backbutton = $('<div/>').addClass('panel-image-slider-back');
                var forwardbutton = $('<div/>').addClass('panel-image-slider-forward');
                slidercontents.removeAttr('id');
                slidercontents.before(slider);
                slider.append(backbutton);
                slider.append(slidercontents);
                slider.append(forwardbutton);
                var total = $('> div', slidercontents).length;
                var left = 0;
                var w;
                var width;
                var maxScroll;
                slider.append($('<div/>').css('display', 'none').addClass('panel-preview').append($('<div/>').addClass('img-large')
                    .append($('<div/>').html('&nbsp').click(function(e) {
                        e.stopPropagationslider();
                        e.stopImmediatePropagationslider();
                        //display previous image
                        var img = $(this).parent().findslider('panel-img');
                        var index = parseInt(img.attr('class'));
                        img.removeAttr('class');
                        if (index > 1) {
                            index--;
                            var src = $('.' + index + ' div panel-img').attr('src');
                            var txt = $('.' + index + ' div a').html();
                            $('.panel-preview').findslider('.label').html(txt);
                            $('.panel-preview').findslider('panel-img').addClass('' + (index) + '').css('opacity', '0').attr('src', src).stop(false, true).animacion({
                                opacity: 1
                            }, 1000);
                        }
                        else
                            $('.panel-preview').findslider('panel-img').addClass('' + (index) + '');
                    }).addClass('left').css('opacity', '0.5').hoverslider(function() {
                        $(this).css('opacity', '1')
                    }, function() {
                        $(this).css('opacity', '0.5')
                    }))
                    .append($('<div/>').html('&nbsp').click(function(e) {
                        e.stopPropagationslider();
                        e.stopImmediatePropagationslider();
                        //display next image
                        var panelimg = $(this).parent().findslider('panel-img');
                        var index = parseInt(panelimg.attr('class'));
                        panelimg.removeAttr('class');
                        if (index < total) {
                            index++;
                            var src = $('.' + index + ' div panel-img').attr('src');
                            var txt = $('.' + index + ' div a').html();
                            $('.panel-preview').findslider('.label').html(txt);
                            $('.panel-preview').findslider('panel-img').addClass('' + (index) + '').css('opacity', '0').attr('src', src).stop(false, true).animacion({
                                opacity: 1
                            }, 1000);
                        }
                        else
                            $('.panel-preview').findslider('panel-img').addClass('' + (index) + '')
                    }).addClass('right').css('opacity', '0.5').hoverslider(function() {
                        $(this).css('opacity', '1')
                    }, function() {
                        $(this).css('opacity', '0.5')
                    }))
                    .append($('<panel-img/>'))).append($('<div/>').addClass('label').css('opacity', '0.8'))
                    .append($('<div/>').addClass('close').click(function(e) {
                        $(this).parent().fadeOutslider("slow");
                    })));
                $(document).click(function(e) {
                    if ($('.panel-preview').css('display') == 'block')
                        $('.panel-preview').fadeOutslider("slow");
                });
                function initialize() {
                    var tempElements = $('> div', slidercontents);
                    var allElements = new Array();
                    tempElements.each(function(index, el) {
                        allElements.push($('<div/>').addClass('' + (index + 1) + '').addClass('panel-outer').append(el));
                    });

                    allElements = $(allElements);
                    $('> div', slidercontents).remove();
                    var wrapper = $('<div/>').addClass('panel-image-contents-wrapper');
                    allElements.each(function(index, el) {

                        wrapper.append($(el));
                    });
                    slidercontents.append(wrapper);
                    var w = $('.panel-outer:eq(0)', slidercontents).outerWidth() + parseFloat($('.panel-outer:eq(0)', slidercontents).css('margin-left')) + parseFloat($('.panel-outer:eq(0)', slidercontents).css('margin-right'));
                    var width = (total+1) * w;
                    var maxWidth = width - w;
                    var maxScroll = (width - $('.panel-image-slider-contents').outerWidth());
                    wrapper.css({
                        width: width
                    });
                    $('> div > div', slidercontents).css('display', '');
                    /*
                    $('.outer', slidercontents).each(function(index) {
                        $(this).prepend($('<img/>').attr('src', '/siente/resources/gSlider/images/zoom.png').addClass('zoom')
                            .css({
                                cursor: 'pointer', 
                                'position': 'relative', 
                                'float': 'right', 
                                marginRight: -10, 
                                top: -14, 
                                width: 34, 
                                height: 32
                            }));

                    });
                    */
                    $('.panel-outer a').click(function(e){
                        e.stopPropagationslider();
                        e.stopImmediatePropagationslider();
							 
                    });
                    $('.panel-outer').hoverslider(function() {
                        $(this).addClass('active');
                    }, function() {
                        $(this).removeClass('active');
                    }).click(
                        function(e) {
                            e.stopPropagationslider();
                            e.stopImmediatePropagationslider();
                            var cls = $(this).attr('class').split(' ')[0];
                            var p = $(this).findslider('div');
                            var panelimg = p.findslider('panel-img').attr('src');
                            var preview = $('.panel-preview');
                            var l = $('.panel-image-slider').width()/2 - preview.outerWidth() / 2;
                            var t = (p.offset().top - preview.height());
                            t -= t / 2;
                            preview.css({
                                left: l, 
                                top: t
                            });
                            var text = p.findslider('a').html();
                            preview.findslider('panel-img').attr('src', panelimg).addClass(cls);
                            preview.findslider('.label').html(text);
                            preview.fadeInslider("slow");

                        });

                    forwardbutton.click(function() {
                        left -= w;
                        if (left + maxWidth >= 0) {
                            $('.panel-image-contents-wrapper').stop(false, true).animacion({
                                left: '-=' + w
                            }, settings.speed);
                        }
                        else
                            left += w;
                    });
                    backbutton.click(function() {
                        if (left < 0) {
                            left += w;
                            $('.panel-image-contents-wrapper').stop(false, true).animacion({
                                left: '+=' + w
                            }, settings.speed);
                        }
                    });

                    var _runplus =  (function(){
                        setTimeout(function(){                            
                            left -= w;
                            if (left + maxWidth > 0) {
                                $('.panel-image-contents-wrapper').stop(true, true).animacion({
                                    left: '-=' + w
                                }, settings.speed);
                                _runplus();
                            }
                            else{
                                left += w;
                                _runminus();
                            }
                        }, settings.time);
                        
                    });

                    var _runminus =  (function(){
                        setTimeout(function(){
                            if (left < 0) {
                                left += w;
                                $('.panel-image-contents-wrapper').stop(true, true).animacion({
                                    left: '+=' + w
                                }, settings.speed);
                                _runminus();
                            }
                            else{
                                _runplus();
                            }
                        }, settings.time);

                    });

                    if(settings.action){
                        _runplus();
                    }
                }
                initialize();

                function getDimensions(value) {
                    return value + 'px';
                }

            });
        }
    });
})(jQuery);
