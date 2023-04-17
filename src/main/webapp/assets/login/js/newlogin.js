$(function () {
    if (error) {
        layer.alert(error);
    }

    //检测上次登录是否选择记住密码，如果是从缓存中自动填入input
    let rememberState = localStorage.getItem("remember");
    if (rememberState === "true") {
        console.log("aaaa");
        let username = localStorage.getItem("username");
        let password = localStorage.getItem("password");

        if (username) {
            $("#username").val(username);
        }
        if (password) {
            $("#password").val(password);
        }
    }


    //输入框失去焦点事件
    $("#username").blur(function () {
        let username = $("#username").val();
        let result = validateUsername(username);
        if (result !== true) {
            layer.msg(result);
        }
    });
    $("#password").blur(function () {
        let password = $("#password").val();
        let result = validatePassword(password);
        if (result !== true) {
            layer.msg(result);
        }
    });
    $("#captcha").blur(function () {
        let captcha = $("#captcha-input").val();
        let result = validateCaptcha(captcha);
        if (result !== true) {
            layer.msg(result);
        }
    });


    //登录事件
    $("#login-btn").click(function () {
        let username = $("#username").val();
        let password = $("#password").val();
        let captcha = $("#captcha-input").val();

        //前端正则表达式校验用户名
        if (username === "") {
            layer.msg("用户名不能为空");
            return false;
        }
        let regex1 = /^\w+@\w+\.(com|cn)+$/;
        if (!username.match(regex1)) {
            layer.msg("用户名不符合邮箱格式");
            return false;
        }
        if (password === "") {
            layer.msg("密码不能为空");
            return false;
        }
        let regex2 = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}/
        if(!password.match(regex2)){
            layer.msg("密码格式错误");
            return false;
        }
        if (captcha === "") {
            layer.msg("验证码不能为空");
            return false;
        }


        //校验是否选择记住密码，将密码存入缓存或者删除
        let state = $("#remember").prop("checked");
        if (state) {
            localStorage.setItem("remember", "true");
            localStorage.setItem("username", username);
            localStorage.setItem("password", password);
            console.log(localStorage.getItem("remember)"));
        } else {
            localStorage.removeItem("remember");
            localStorage.removeItem("username");
            localStorage.removeItem("password");
        }


        $("#login-form").submit();
    });


    $("#captcha").click(function () {
        // console.log("aaaa");
        $(this).attr("src", ctx + "/user/captcha?K=" + new Date().getTime());
    });

});


function validateUsername(username) {
    if (username === "") {
        return "用户名不能为空";
    }
    let regex = /^\w+@\w+\.(com|cn)+$/;
    if (!username.match(regex)) {
        return "用户名不符合邮箱格式";
    }
    return true;
}

function validatePassword(password) {
    if (password === "") {
        return "密码不能为空";
    }
    return true;
}

function validateCaptcha(captcha) {
    if (captcha === "") {
        return "验证码不能为空";
    }
    return true;
}