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
});
