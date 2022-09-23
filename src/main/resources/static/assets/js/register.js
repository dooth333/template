$(function(){
    $("form").submit(check_data);
    $("input").focus(clear_error);
});

function check_data() {
    var pwd1 = $("#password").val();
    var pwd2 = $("#confirm-password").val();
    if(pwd1 != pwd2) {
        $("#confirm-password-info").text("两次输入的密码不一致!");
        return false;
    }
    return true;
}

function clear_error() {
    $(this).nextAll("div").text("");
}