$(function() {
    $('input[type=radio][name=tabs-radio]').change(function() {
        $('.tabs div').hide();
        $(`.tabs div[data-name="${this.value}"]`).show();
    })
})