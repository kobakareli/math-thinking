$(document).ready(function () {
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });

    $('input[name=search]').val('');

    $('.header-button.search-button').on('click', function() {
        $('.search-wrapper.search-id').toggleClass('active');
        $('.search-input input').focus();
        $('.language-switcher').toggle();
        $(this).toggleClass('active');

        setTimeout(function() {
            $.get('/ajax/categories/' + $('#super-category').val(), function(data) {
                $('.categories-wrapper').html(data);
                $('#categories').removeClass('form-control');
                $('#categories').addClass('select');
                $('#categories').select2();
            });
        }, 200);
    });

    $('.black-filter').on('click', function() {
        $('.header-button.search-button').click();
    });

    if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
        $('.language-switcher .wrapper').focusin(function() {
            $('.language-switcher .wrapper').addClass('dropped');
        });

        $('.language-switcher .wrapper').focusout(function() {
            $('.language-switcher .wrapper').removeClass('dropped');
        });

        $('.lang a').click(function(e) {
            if(!$('.language-switcher .wrapper').hasClass('dropped')) {
                e.preventDefault();
                $('.language-switcher .wrapper').addClass('dropped');
            }

        });
    }

    $('.input-parent input').focusin(function() {
        $(this).parent().addClass('active');
    });

    $('.input-parent input').focusout(function() {
        if($(this).val().length == 0) {
            $(this).parent().removeClass('active');
        }
    });

    $('.input-parent input').on('change', function() {
        if($(this).val().length > 0) {
            $(this).parent().addClass('active');
        }
    });

    $('#super-category, #categories').select2();
    $('#date-from, #date-to').pickadate();

    $('#super-category').on('change', function() {
        $.get('/ajax/categories/' + $(this).val(), function(data) {
            $('.categories-wrapper').html(data);
            $('#categories').removeClass('form-control');
            $('#categories').addClass('select');
            $('#categories').select2({
                maximumSelectionLength: 1
            });
        });
    });

    $('.search-input input').on('change', function() {
        ajaxSearch();
    });

    function ajaxSearch() {
        var term = $('.search-input input').val();
        if(term == '') {
            term = '-1';
        }
        var category = $('#categories').val();
        if(category == '') {
            category = '-1';
        }
        var url = '/ajax/search/' + category + '/' + term;
        var from = false;
        if($('#date-from').val() != '') {
            from = true;
            url += '/' + $('#date-from').val();
        }
        if($('#date-to').val() != '') {
            if(!from) {
                url += '/to';
            }
            url += '/' + $('#date-to').val();
        }
        $.get(url, function(data) {
            $('.results').html(data);
            $('.results a').on('click', function(e) {
                e.preventDefault();
                var type = "tasks";
                if($(this).parent().hasClass('article-results')) {
                    type = "articles";
                }
                else if($(e.target).parent().hasClass('test-results')) {
                    type = "tests";
                }
                var url = $(this).attr('href');
                search(url, type);
            });
        });
    }

    function search(url, type) {
        var term = $('.search-input input').val();
        if(term == '') {
            term = '-1';
        }
        var category = $('#categories').val();
        if(category == '') {
            category = '-1';
        }
        url += '/' + type + '/' + term + '/' + category;
        var from = false;
        if($('#date-from').val() != '') {
            from = true;
            url += '/' + $('#date-from').val();
        }
        if($('#date-to').val() != '') {
            if(!from) {
                url += '/to';
            }
            url += '/' + $('#date-to').val();
        }
        window.location.href=url;
    }

    $('.fb-button').on('click', function(e) {
        e.preventDefault();
        FB.ui({
              method: 'share',
              href: window.location.href,
              /*picture: $('.og_image').attr('content'),
              name: $('.og_title').attr('content'),
              description: $('.og_description').attr('content')*/
            }
        );
    });
});
