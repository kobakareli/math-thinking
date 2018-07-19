var taskSlider = new function() {

    var parent = null;

    this.init = function(parent) {
        this.parent = parent;
        this.navigation();
        $.each(parent.find('.task'), function(indx, val) {
            $(val).find('input').on('input', function(e) {
                if($(this).val() != "") {
                    $(this).parent().find('input').attr('filled', 'false');
                    $(this).attr('filled', 'true');
                }
                else {
                    $(this).attr('filled', 'false');
                }
                var valid = true;
                $.each($('.test-page').find('.task'), function(indx, val) {
                    if($(val).find('input[filled="true"]').length == 0) {
                        valid = false;
                    }
                });
                if(!valid) {
                    $('.submit-form-button').addClass('disabled');
                }
                else {
                    $('.submit-form-button').removeClass('disabled');
                }
            });
        });
        this.submitClick();
    },

    this.nextTask = function() {
        var cur = this.parent.find('.task.active');
        if(cur.hasClass('last')) {
            return;
        }
        cur.removeClass('active');
        cur.addClass('prev');
        var next = cur.next();
        next.removeClass('next');
        next.addClass('active');
    },

    this.prevTask = function() {
        var cur = this.parent.find('.task.active');
        if(cur.hasClass('first')) {
            return;
        }
        cur.removeClass('active');
        cur.addClass('next');
        var prev = cur.prev();
        prev.removeClass('prev');
        prev.addClass('active');
    },

    this.submitClick = function() {
        var ref = this;
        $(this.parent).find('.submit-form-button').on('click', function(e) {
            e.preventDefault();
            if($(this).hasClass('disabled')) {
                return;
            }
            ref.submit();
        })
    }

    this.navigation = function() {
        var ref = this;
        this.parent.find('.task .prev').on('click', function() {
            ref.prevTask();
        });
        this.parent.find('.task .next').on('click', function() {
            ref.nextTask();
        });
    }

    this.submit
}

$(document).ready(function() {
    taskSlider.init($('.test-page .tasks'));
});
