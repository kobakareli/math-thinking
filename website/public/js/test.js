var taskSlider = new function() {

    var parent = null;

    this.init = function(parent) {
        this.parent = parent;
        this.navigation();
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

    this.navigation = function() {
        var ref = this;
        this.parent.find('.task .prev').on('click', function() {
            ref.prevTask();
        });
        this.parent.find('.task .next').on('click', function() {
            ref.nextTask();
        });
    }
}

$(document).ready(function() {
    taskSlider.init($('.test-page .tasks'));
});
