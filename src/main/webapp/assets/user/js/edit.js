$(function () {
    if (error) {
        layer.alert(error);
    }

    $("#submit-btn").click(function () {
        let username = $(":input[name=username]").val();
        let oldPassword = $(":input[name=oldpassword]").val();
        let newPassword = $(":input[name=newpassword]").val();
        let againPassword = $(":input[name=againpassword]").val();

        //校验
        if (oldPassword.trim() === "") {
            layer.msg("旧密码不能为空");
            return false;
        }
        //校验新密码与第二次输入的新密码是否相同
        if (newPassword.trim() === "") {
            layer.msg("新密码不能为空");
            return false;
        }
        if (againPassword.trim() === "") {
            layer.msg("第二次输入新密码不能为空");
            return false;
        }
        if (newPassword.trim() !== againPassword.trim()) {
            layer.msg("两次输入的密码不同");
            return false;
        }
        let regex = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}/
        if(!newPassword.match(regex)){
            layer.msg("新密码格式错误");
            return false;
        }


        // console.log("ssss");
        //通过
        // $("#room-form").submit();
        const url = ctx + "/user/edit";
        $.post(url, {
            username,
            oldPassword,
            newPassword,
            againPassword
        }, function (resp) {
            if (resp.error) {
                // console.log("aaaaaa");
                layer.alert(resp.error);
            } else {
                top.location.href = ctx + "/index";
                // location.href = ctx + "/index";
            }
        }, "json");
    });
});