$(document).ready(function() {
    var coll = $(".collapsible");

    $.each(coll, function(indx, val) {
        $(val).on('click', function() {
            var content = $(this.nextElementSibling);
            if ($(this).hasClass('active')) {
              content.hide();
            } else {
              content.show();
            }
            $(this).toggleClass("active");
        });
    });

    $('.closed .answer').on('input', function() {
        $('.closed .answer').css('background-color', 'white');
    });

    $('.submit-answer-button').on('click', function() {
        if($('.submit-answer .open').length > 0) {
            if($( ".answer:checked" ).length > 0) {
                var value = $(".answer:checked").val();
                $.post( "ajax/submit/answer", {user: user, task: task, answer: value}, function( data ) {
                    if(data == '1') {
                        $('.submit-answer .open .answer-' + value).css('color', 'green');
                    }
                    else {
                        $('.submit-answer .open .answer-' + value).css('color', 'red');
                    }
                });
            }
        }
        else {
            var value = $('.closed .answer').val();
            $.post( "ajax/submit/answer", {user: user, task: task, answer: value}, function( data ) {
                if(data == '1') {
                    $('.closed .answer').css('background-color', 'green');
                }
                else {
                    $('.closed .answer').css('background-color', 'red');
                }
            });
        }
    });
});
