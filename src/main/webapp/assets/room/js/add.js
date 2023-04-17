$(function () {
    if (error) {
        layer.alert(error);
    }

    if (kind) {
        $(":radio[name = kind][value=" + kind + "]").prop("checked", true);
    }

    if (state) {
        $(":radio[name = state][value=" + state + "]").prop("checked", true);
    }


    $("#submit-btn").click(function () {

        //校验
        let name = $("input[name = name]").val();
        if (name.trim() === "") {
            layer.msg("房间号不可为空");
            return false;
        }
        let kind = $("input[name = kind]:checked").val();
        if ("小床房" !== kind && "中床房" !== kind && "大床房" !== kind) {
            layer.msg("房间类型错误");
            return false;
        }
        let state = $("input[name = state]:checked").val();
        if ("空闲" !== state && "有客" !== state && "维修" !== state) {
            layer.msg("房间状态错误");
            return false;
        }
        let price = $("input[name = price]").val();
        if (price.trim() === "") {
            layer.msg("房间价格不能为空");
            return false;
        }

        // console.log("ssss");
        //通过
        $("#room-form").submit();


    });
});