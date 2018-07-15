var start = 1;

jQuery(document).ready(function() {
    // set select values

    var categoryIds = [];

    if(typeof articleCategories != 'undefined') {
        for(var i = 0; i < articleCategories.length; i++) {
            categoryIds.push(articleCategories[i]['id']);
        }
        jQuery('#categories').val(categoryIds);
        categoryIds = [];
    }

    if(typeof testSuperCategories != 'undefined') {
        for(var i = 0; i < testSuperCategories.length; i++) {
            categoryIds.push(testSuperCategories[i]['id']);
        }
        jQuery('#supercategories').val(categoryIds);
        categoryIds = [];
    }

    if(typeof testCategories != 'undefined') {
        for(var i = 0; i < testCategories.length; i++) {
            categoryIds.push(testCategories[i]['id']);
        }
        jQuery('#categories').val(categoryIds);
        categoryIds = [];
    }


    var testTaskIds = [];

    if(typeof testTasks != 'undefined') {
        for(var i = 0; i < testTasks.length; i++) {
            testTaskIds.push(testTasks[i]['id']);
        }
        jQuery('#tasks').val(testTaskIds);
        testTaskIds = [];
    }


    var subCategoryIds = [];

    if(typeof subCategories != 'undefined') {
        for(var i = 0; i < subCategories.length; i++) {
            subCategoryIds.push(subCategories[i]['id']);
        }
        jQuery('#categories').val(subCategoryIds);
        subCategoryIds = [];
    }

    $('#categories').select2();
    $('#supercategories').select2();
    $('#tasks').select2();


    $('#supercategories').on('change', function() {
        $.get('/admin/ajax/tests/categories/' + $(this).val(), function(data) {
            $('.categories-container').html(data);
            $('#categories').select2();
            $('#categories').on('change', function() {
                $.get('/admin/ajax/tests/tasks/' + $(this).val(), function(data2) {
                    $('.tasks-container').html(data2);
                    $('#tasks').select2();
                });
            });
        });
    });

    if($('.rich1').length) {
        CKEDITOR.replace( 'rich1' );
        CKEDITOR.replace( 'rich2' );
        CKEDITOR.replace( 'rich3' );
        CKEDITOR.replace( 'rich4' );
        CKEDITOR.replace( 'rich5' );
        CKEDITOR.replace( 'rich6' );
    }

    if($('input[name="has_options"]').is(':checked')){
        setTimeout(function() {
            $('.hide:not(.cke)').addClass('show');
            //$('.cke').addClass('hide');
            //$('#cke_rich1, #cke_rich4').removeClass('hide');
        }, 1000);
    }

    $('input[name="has_options"]').click(function(){
        $('.hide:not(.cke)').addClass('show');
        /*if($(this).is(':checked')){
            $('.cke').addClass('hide');
            $('#cke_rich1, #cke_rich4').removeClass('hide');
        } else {
            $('.cke').removeClass('hide');
        }*/
    });

    $('.copy').on('click', function(e) {
        e.preventDefault();
        copyToClipboard($(this).attr('href'));
        $(this).text('Copied');
        var ref = $(this);
        setTimeout(function() {
            ref.text('Copy');
        }, 5000);
    });

    uploadsScroll();

});

function copyToClipboard(text) {
    var $temp = $("<input>");
    $("body").append($temp);
    $temp.val(text).select();
    document.execCommand("copy");
    $temp.remove();
}

function uploadsScroll() {
    var timeout;
    var finished = false;
    console.log('called');
    if($('.uploads').length > 0) {
        console.log(" >0");
        $(window).on('scroll', function() {
            if($(window).scrollTop() + $(window).height() >= $(document).height() - 100 && !finished) {
                clearTimeout(timeout);
                var ref = $('.uploads');
                timeout = setTimeout(function() {
                    $.get( ref.data('url') + '/' + start, function( data ) {
                        ref.append(data);
                        start += 1;
                        if(ref.children().length < start*8) {
                            finished = true;
                        }
                    });
                }, 100);
            }
        });
    }
}
