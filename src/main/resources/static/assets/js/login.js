$(function(){
    $("input").focus(clear_error);
});

function clear_error() {
    $(this).nextAll("div").text("");
}