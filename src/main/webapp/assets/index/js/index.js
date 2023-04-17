$(function () {
    //iframe跳转事件
    $(".nav").on("click", "li>a", function (e) {
        e.preventDefault();//阻止默认行为，阻止页面跳转
        let val = $(this).attr("href");
        $(".main>iframe").attr("src", val);
    });

    //退出
    $("#logout-btn").click(function (e) {
        e.preventDefault();
        location.href = "user/logout";
    });

    //点击修改密码，layer弹窗
    $("#edit-btn").click(function () {
        let username = $(":input[name=username]").val();
        // console.log("aaaaa");
        // console.log(username);
        layer.open({
            type: 2,
            title: '修改用户密码',
            shadeClose: true,
            area: ['600px', '500px'],
            content: ctx + '/user/edit?username=' + username
        });
    });

});