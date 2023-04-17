<%--
  Created by IntelliJ IDEA.
  User: dhy123
  Date: 2023/3/2
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <base href="${ctx}/">
    <meta charset="utf-8">
    <title>房间列表页</title>
    <link rel="stylesheet" href="assets/room/css/list.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script>
        const ctx = "${ctx}";
    </script>
    <script src="assets/room/js/list.js"></script>
</head>
<body>
<div class="search">
    <form action="" class="clear" method="post">
        <div>
            <label for="name">房间号：</label>
            <input type="text" id="name" placeholder="请输入房间号">
        </div>
        <div>
            <label for="kind">房间类型：</label>
            <select id="kind">
                <option value="">不限</option>
                <option value="小床房">小床房</option>
                <option value="中床房">中床房</option>
                <option value="大床房">大床房</option>
            </select>
        </div>
        <div>
            <label for="state">房间状态：</label>
            <select id="state">
                <option value="">不限</option>
                <option value="空闲">空闲</option>
                <option value="有客">有客</option>
                <option value="维修">维修</option>
            </select>
        </div>
        <div>
            <label for="price-from">房间价格：</label>
            <input type="text" id="price-from" placeholder="请输入数字">—
            <input type="text" id="price-to" placeholder="请输入数字">
        </div>
    </form>
</div>
<div class="action clear">
    <div>
        <button id="add-btn">添加</button>
    </div>
    <div>
        <button id="edit-btn">修改</button>
    </div>
    <div>
        <button id="search-btn">查询</button>
    </div>
    <div>
        <button id="del-btn">删除</button>
    </div>
    <div>
        <button id="reset-btn">重置</button>
    </div>
</div>
<div class="display">
    <table id="tbl">
        <thead>
        <tr>
            <th><input type="checkbox" id="check-all"></th>
            <th>主键</th>
            <th>房间号</th>
            <th>房间类型</th>
            <th>房间状态</th>
            <th>房间价格</th>
            <th>房间备注</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</div>
<div class="paginate clear">
    <div class="fl">
        <span>总页数：</span>
        <span id="pages"></span>
        <span>总记录数：</span>
        <span id="total"></span>
        <span id="page-no" style="display: none"></span>
    </div>
    <span class="jilu">每页显示最大记录：</span>
    <select id="page-size" class="">
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="30">30</option>
        <option value="40">40</option>
        <option value="50">50</option>
    </select>
    <ul class="clear">
        <li class="first"><a href="#">首页</a></li>
        <li class="prev"><a href="#">上一页</a></li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li class="next"><a href="#">下一页</a></li>
        <li class="last"><a href="#">尾页</a></li>
    </ul>
</div>
</body>
</html>
