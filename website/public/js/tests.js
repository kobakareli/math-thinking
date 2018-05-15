$(document).ready(function () {
    $('.select').select2();

    $('#sort').on('change', function() {
        window.location.href = $(this).data('url') + '/tests/1/' + $(this).val();
    });
});
