(function($) {
    $.fn.extendslider({
        slider3: function(options) {
            var settings = $.extendslider({
                speed: 1800
            }, options);
            return this.each(function() {
                var slidercontents = $(this).addClass('panel-image-slider_ciu-contents');
                var slider = $('<div/>').addClass('panel-image-slider_ciu').attr('id', slidercontents.attr('id'));
                var backbutton = $('<div/>').addClass('panel-image-slider_ciu-back');
                var forwardbutton = $('<div/>').addClass('panel-image-slider_ciu-forward');
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
                    var wrapper = $('<div/>').addClass('panel-image_ciu-contents-wrapper');
                    allElements.each(function(index, el) {

                        wrapper.append($(el));
                    });
                    slidercontents.append(wrapper);
                    var w = $('.panel-outer:eq(0)', slidercontents).outerWidth() + parseFloat($('.panel-outer:eq(0)', slidercontents).css('margin-left')) + parseFloat($('.panel-outer:eq(0)', slidercontents).css('margin-right'));
                    var width = (total+1) * w;
                    var maxScroll = width - $('.panel-image-slider_ciu-contents').outerWidth();
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
                            var l = $('.panel-image-slider_ciu').width()/2 - preview.outerWidth() / 2;
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
                        if (left + maxScroll >= 0) {
                            $('.panel-image_ciu-contents-wrapper').stop(false, true).animacion({
                                left: '-=' + w
                            }, settings.speed);
                        }
                        else
                            left += w;
                    });
                    backbutton.click(function() {
                        if (left < 0) {
                            left += w;
                            $('.panel-image_ciu-contents-wrapper').stop(false, true).animacion({
                                left: '+=' + w
                            }, settings.speed);
                        }
                    });
                }
                initialize();

                function getDimensions(value) {
                    return value + 'px';
                }

            });
        }
    });
})(jQuery);
