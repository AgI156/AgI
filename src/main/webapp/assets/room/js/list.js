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
        // console.log("aaaaa");
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
                if (id === "1") {
                    layer.msg("不能删除标记房间");
                } else {
                    ids.push(id);
                }
            });
            // console.log(ids);

            if (ids.length > 0) {
                layer.confirm("是否确认删除", function () {
                    deleteById();
                });
            }

            function deleteById() {
                let url = ctx + "/room/delete";
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
        location.href = ctx + "/room/add";
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
            if (id === "1") {
                layer.msg("不能修改标记房间");
            } else {
                location.href = ctx + "/room/edit?id=" + id;
            }
        }
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


});

//查询列表
function search(pageNo = 1, pageSize = 10) {
    let name = $(".search #name").val();
    let kind = $(".search #kind").val();
    let state = $(".search #state").val();
    let priceFrom = $(".search #price-from").val();
    let priceTo = $(".search #price-to").val();
    // console.log("价格是" + priceTo);


    let data = {
        pageNo,
        pageSize
    }

    if (name.trim() !== "") {
        data.name = name;
    }
    if (kind.trim() !== "") {
        data.kind = kind;
    }
    if (state.trim() !== "") {
        data.state = state;
    }
    if (priceFrom.trim() !== "") {
        data.priceFrom = priceFrom;
    }
    if (priceTo.trim() !== "") {
        data.priceTo = priceTo;
    }


    //发送一个ajax请求
    let url = ctx + "/room/list";//请求地址
    $.post(url, data, function (resp) {
        if (resp.error) {
            // console.log(resp.error);
            layer.msg(resp.error);
            return false;
        }


        let rows = resp.rooms;
        let pi = resp.pi;

        $("#pages").text(pi.pages);
        $("#total").text(pi.count);
        $("#page-no").text(pi.pageNo);

        //回调。当请求响应完成，即调用此回调函数
        // console.log(resp);
        $("#tbl>tbody").empty();
        // console.log(rows.length);
        for (let i = 0; i < rows.length; i++) {
            let rm = rows[i];
            let $tr = $("<tr>");
            $tr.append($("<td><input type='checkbox'></td>"));
            $tr.append($("<td>" + rm.id + "</td>"));
            $tr.append($("<td>" + rm.name + "</td>"));
            $tr.append($("<td>" + rm.kind + "</td>"));
            $tr.append($("<td>" + rm.state + "</td>"));
            $tr.append($("<td>" + rm.price + "</td>"));
            $tr.append($("<td>" + (rm.description || "") + "</td>"));

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