jQuery(document).ready(function() {
    var categoryIds = [];

    if(typeof articleCategories != 'undefined') {
        for(var i = 0; i < articleCategories.length; i++) {
            categoryIds.push(articleCategories[i]['id']);
        }
        jQuery('#categories').val(categoryIds);
    }

    $('#categories').select2();

    if($('.rich1').length) {
        CKEDITOR.replace( 'rich1' );
        CKEDITOR.replace( 'rich2' );
    }

    if($('input[name="title_en"]').val() == 'Contact Page text') {
        $('input[name="title_en"], input[name="title_ru"], input[name="title_ge"]').attr('readonly', '');
    }
});
