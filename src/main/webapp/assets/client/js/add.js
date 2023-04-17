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

    //获取空闲房间信息
    fetchRoom();

    $("#submit-btn").click(function () {

        //校验
        let clientId = $(":input[name=clientId]").val();
        if (clientId.trim() === "") {
            layer.msg("卡号不可为空");
            return false;
        }
        let name = $(":input[name=name]").val();
        if (name.trim() === "") {
            layer.msg("姓名不可为空");
            return false;
        }
        let sex = $(":input[name=sex]:checked").val();
        if ("男" !== sex && "女" !== sex) {
            layer.msg("性别必须是男或女");
            return false;
        }
        let birthday = $(":input[name=birthday]").val();
        let regex = /\d{4}-\d{2}-\d{2}/;
        if (!birthday.match(regex)) {
            layer.msg("出生日期格式不正确");
            return false;
        }
        let phone = $(":input[name=phone]").val();
        if (phone.trim() === "") {
            layer.msg("手机不可为空");
            return false;
        }
        let roomId = $(":input[name=roomId]").val();
        console.log(roomId);
        if (roomId.trim() === "") {
            layer.msg("房间id不可为空");
            return false;
        }


        // console.log("ssss");
        //通过
      });

    //学号失去焦点，客户卡号重名判断
    $(":input[name=clientId]").blur(function () {
        let clientId = $(this).val();
        checkClientId(clientId, function (b) {
            if (b) {
                layer.msg("已存在相同客户卡号");
            }
        });
    });


});


//判断数据库中客户卡号是否重复
function checkClientId(clientId, callback) {
    let url = ctx + "/client/checkClientId";
    $.ajax(url, {
        method: "post",
        data: {
            clientId
        },
        dataType: "json",
        success: function (resp) {
            callback(resp.exist);
        }
    });

}

//获取空闲房间信息
function fetchRoom() {
    const url = ctx + "/room/combolist";
    $.post(url, {}, function (resp) {
        if (resp.success) {
            let data = resp.data;
            $("#roomId").empty();
            data.forEach(function (item) {
                // console.log(item.id);
                $("#roomId").append($("<option value='" + item.id + "'>" + item.name + "</option>"))
            });
        }
    }, "json");
}