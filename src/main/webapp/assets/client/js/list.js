$(function () {
    // console.log("aaaa");
    // search(1, 10);


    //分页事件
    $(".paginate>ul").on("click", "li", function (e) {
        e.preventDefault();//取消默认行为
        let val = $(this).text().trim();
        let ps = parseInt($("#page-size").val());
        let pageNo = parseInt($("#page-no").text());
        let pages = parseInt($("#pages").text());


        if ($(this).hasClass("first")) {
            search(1, ps);
        } else if ($(this).hasClass("last")) {
            search(pages, ps);
        } else if ($(this).hasClass("prev")) {
            pageNo--;
            if (pageNo < 1) {
                pageNo = 1;
            }
            search(pageNo, ps);
        } else if ($(this).hasClass("next")) {
            pageNo++;
            if (pageNo > pages) {
                pageNo = pages;
            }
            search(pageNo, ps);
        } else {//数字页码
            search(parseInt(val), ps);
        }
    });

    //查询按钮事件
    $("#search-btn").click(function () {
        let ps = parseInt($("#page-size").val());
        search(1, ps);
    });

    $("#search-btn").trigger("click");

    //删除按钮事件
    $("#del-btn").click(function () {
        let $chs = $("#tbl>tbody>tr>td:first-child>:checked");
        if ($chs.length === 0) {
            layer.msg("请选择您要删除的记录");
            // console.log("汉字");
        } else {
            let ids = [];
            $chs.each(function (idx, item) {
                let id = $(item).closest("tr").children().eq(1).text();
                ids.push(id);
            });
            // console.log(ids);

            if (ids.length > 0) {
                layer.confirm("是否确认删除", function () {
                    deleteById();
                });
            }

            function deleteById() {
                let url = ctx + "/client/delete";
                $.ajax(url, {
                    method: "post",
                    data: {
                        ids
                    },
                    dataType: "json",
                    traditional: true,//可以向后台提交数组参数
                    success: function (resp) {//成功之后的回调函数
                        if (resp.success) {
                            layer.msg(resp.msg || "删除成功");
                            location.reload();
                        } else {
                            layer.alert(resp.error || "删除失败，请稍后再试");
                        }
                    },
                    error: function (resp) {//失败之后的回调函数
                        layer.alert(resp.error || "删除失败，请稍后再试");
                    }
                });
            }


        }
    });

    //重置按钮
    $("#reset-btn").click(function () {
        $(".search>form")[0].reset();
    });

    //添加事件按钮
    $("#add-btn").click(function () {
        location.href = ctx + "/client/add";
    });

    //修改事件按钮
    $("#edit-btn").click(function () {
        let $chs = $("#tbl>tbody>tr>td:first-child>:checked");
        if ($chs.length === 0) {
            layer.msg("请选中您要修改的记录");
        } else if ($chs.length > 1) {
            layer.msg("一次只能选中一条记录进行修改");
        } else {
            let id = $chs.closest("tr").children().eq(1).text();
            location.href = ctx + "/client/edit?id=" + id;
        }
    });

    //日期插件
    laydate.render({
        elem: "#birthday-from"
    });
    laydate.render({
        elem: "#birthday-to"
    });

    //全选与取消全选
    $("#tbl #check-all").click(function () {
        // console.log("aaa");
        let checked = $(this).prop("checked");
        $("#tbl tbody>tr>td:first-child>:checkbox").prop("checked", checked);
    });

    //行选中事件
    $("#tbl tbody").on("click", "tr>td:not(first-child)", function () {
        let $tr = $(this).parent();
        let $ch = $tr.children().eq(0).children();
        $ch.prop("checked", !$ch.prop("checked"));
    });

    //导出按钮事件
    $("#export-btn").click(function () {
        let ids = [];

        let $chs = $("#tbl>tbody>tr>td:first-child>:checked");
        $chs.each(function (idx, item) {
            let id = $(item).closest("tr").children().eq(1).text();
            ids.push(id);
        });


        if (ids.length === 0) {
        } else {
            // console.log("准备发出请求")
            let url = ctx + "/client/export?type=chosen";
            for (let i = 0; i < ids.length; i++) {
                console.log(ids[i]);
                url += "&ids=" + ids[i];
            }

            location.href = url;
        }
    });

    //导入按钮事件
    $("#import-btn").click(function () {
        location.href = ctx + "/client/import";//导入
    });

});

//查询列表
function search(pageNo = 1, pageSize = 10) {
    let clientId = $(".search #client-id").val();
    let name = $(".search #name").val();
    let sex = $(".search #sex").val();
    // let ageFrom = $(".search #age-from").val();
    // let ageTo = $(".search #age-to").val();
    let birthdayFrom = $(".search #birthday-from").val();
    let birthdayTo = $(".search #birthday-to").val();
    let phone = $(".search #phone").val();

    let data = {
        pageNo,
        pageSize
    }

    if (clientId.trim() !== "") {
        data.clientId = clientId;
    }
    if (name.trim() !== "") {
        data.name = name;
    }
    if (sex.trim() !== "") {
        data.sex = sex;
    }
    /*if (ageFrom.trim() !== "") {
        data.ageFrom = ageFrom;
    }
    if (ageTo.trim() !== "") {
        data.ageTo = ageTo;
    }*/
    if (birthdayFrom.trim() !== "") {
        data.birthdayFrom = birthdayFrom;
    }
    if (birthdayTo.trim() !== "") {
        data.birthdayTo = birthdayTo;
    }
    if (phone.trim() !== "") {
        data.phone = phone;
    }

    //发送一个ajax请求
    let url = ctx + "/client/list";//请求地址
    $.post(url, data, function (resp) {
        if (resp.error) {
            // console.log(resp.error);
            layer.msg(resp.error);
            return false;
        }


        let rows = resp.clients;
        let pi = resp.pi;

        $("#pages").text(pi.pages);
        $("#total").text(pi.count);
        $("#page-no").text(pi.pageNo);

        //回调。当请求响应完成，即调用此回调函数
        // console.log(resp);
        $("#tbl>tbody").empty();
        for (let i = 0; i < rows.length; i++) {
            let clt = rows[i];
            let $tr = $("<tr>");
            $tr.append($("<td><input type='checkbox'></td>"));
            $tr.append($("<td>" + clt.id + "</td>"));
            $tr.append($("<td>" + clt.clientId + "</td>"));
            $tr.append($("<td>" + clt.name + "</td>"));
            $tr.append($("<td>" + clt.sex + "</td>"));
            // $tr.append($("<td>" + clt.age + "</td>"));
            $tr.append($("<td>" + clt.birthday + "</td>"));
            $tr.append($("<td>" + clt.phone + "</td>"));
            $tr.append($("<td>" + clt.roomName + "</td>"));
            $tr.append($("<td>" + (clt.description || "") + "</td>"));

            $("#tbl>tbody").append($tr);
        }

        //更新页码条
        let navFirst = pi.navFirst;
        let navLast = pi.navLast;
        $(".paginate>ul>li:not(.first):not(.last):not(.prev):not(.next)").remove();
        // console.log(navLast);
        // console.log(navFirst);
        for (let i = navFirst; i <= navLast; i++) {
            let $li = $("<li><a href='#'>" + i + "</a></li>");
            if (i === pi.pageNo) {
                $li.addClass("active");
            }
            $(".paginate li.next").before($li);
        }

    }, "json");
}