$(document).ready(function () {
    $('.select').select2();

    $('#sort').on('change', function() {
        window.location.href = $(this).data('url') + '/articles/1/' + $(this).val();
    });
});
