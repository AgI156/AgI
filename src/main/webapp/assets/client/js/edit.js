$(function () {
    if (error) {
        layer.alert(error);
    }

    if (sex) {
        $(":radio[name = sex][value=" + sex + "]").prop("checked", true);
    }

    laydate.render({
        elem: "#birthday"
    });

    $("#submit-btn").click(function () {
        let id = $(":input[name=id]").val();
        if (id.trim() === "") {
            layer.msg("必须指定要修改用户的主键（编号）");
            return false;
        }
        //校验
        let clientId = $("input[name = clientId]").val();
        if (clientId.trim() === "") {
            layer.msg("卡号不可为空");
            return false;
        }
        let name = $("input[name = name]").val();
        if (name.trim() === "") {
            layer.msg("姓名不可为空");
            return false;
        }
        let sex = $("input[name = sex]:checked").val();
        if ("男" !== sex && "女" !== sex) {
            layer.msg("性别必须是男或女");
            return false;
        }
        let phone = $("input[name = phone]").val();
        if (phone.trim() === "") {
            layer.msg("手机不可为空");
            return false;
        }
        let birthday = $(":input[name=birthday]").val();
        let regex = /\d{4}-\d{2}-\d{2}/;
        if (!birthday.match(regex)) {
            layer.msg("日期格式不正确");
            return false;
        }
        // let age = $(":input[name=age]").val();
        let description = $(":input[name=description]").val();
        // console.log("ssss");
        //通过
        // $("#client-form").submit();
        const url = ctx + "/client/edit";
        $.post(url, {
            id,
            clientId,
            name,
            sex,
            birthday,
            // age,
            phone,
            description
        }, function (resp) {
            if (resp.error) {
                layer.alert(resp.error);
            } else {
                location.href = ctx + "/client/list";
            }
        }, "json");
    });
});