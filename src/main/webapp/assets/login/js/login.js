$(function () {
    if (error) {
        layer.alert(error);
    }

    $("#login-btn").click(function () {
        //校验 todo

        $("#login-form").submit();
    });
});