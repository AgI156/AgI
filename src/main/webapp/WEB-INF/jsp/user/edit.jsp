<%--
  Created by IntelliJ IDEA.
  User: dhy123
  Date: 2023/3/7
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <base href="${ctx}/">
    <meta charset="utf-8">
    <title>修改用户密码</title>
    <link rel="stylesheet" href="assets/user/css/edit.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/user/js/edit.js"></script>
    <script>
        const ctx = "${ctx}";
        const error = "${error}";
    </script>
</head>
<body>
<h1>修改用户密码</h1>
<form id="user-form" action="user/edit" method="post">
    <div>
        <label for="">用户名：</label>
        <input type="text" name="username" autocomplete="off" readonly value="${user.username}">
    </div>
    <div>
        <label for="">原密码：</label>
        <input type="text" name="oldpassword" autocomplete="off" placeholder="请输入原密码">
    </div>
    <div>
        <label for="">新密码：</label>
        <input type="text" name="newpassword" autocomplete="off" placeholder="请输入新密码">
    </div>
    <div>
        <label for="">确认密码：</label>
        <input type="text" name="againpassword" autocomplete="off" placeholder="请重新输入新密码">
    </div>
    <div>
        <label for=""></label>
        <button id="submit-btn" type="button">提交</button>
        <button type="reset">重置</button>
    </div>
</form>
</body>
</html>
