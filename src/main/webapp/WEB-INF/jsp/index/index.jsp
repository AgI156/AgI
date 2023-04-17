<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <base href="${ctx}/">
    <meta charset="utf-8"/>
    <title>酒店管理系统页面</title>
    <link rel="stylesheet" href="assets/index/css/index.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/index/js/index.js"></script>
    <script>
        const ctx = "${ctx}";
    </script>
</head>
<body>
<header>
    <div>
        <li><h1 class="dhy">碘化银酒店后台管理系统</h1></li>
    </div>
</header>
<div class="ctr clear">
    <div class="lft fl">
        <ul class="nav">
            <li><a href="client/list">客户信息管理</a></li>
            <li><a href="client/add">添加新的客户</a></li>
            <li><a href="room/list">房间信息管理</a></li>
            <li><a href="room/add">添加新的房间</a></li>
        </ul>
        <ul class="logout">
            <li><a id="edit-btn" href="javascript:void(0)">修改密码</a></li>
            <li><a id="logout-btn" href="javascript:void(0)">退出系统</a></li>
        </ul>
    </div>
    <div class="main fl">
        <iframe src=""></iframe>
    </div>
    <div class="rit fl"></div>
</div>
<footer>
    <input type="hidden"  name="username" value="${user.username}">
</footer>
</body>
</html>
