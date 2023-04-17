<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <base href="${ctx}/">
    <title>登录页面</title>
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/login/js/login.js"></script>
    <script>
        const error = "${error}";
    </script>
</head>
<body>
<form id="login-form" action="user/login" method="post">
    <div>
        <label for="">用户名：</label>
        <input type="text" name="username">
    </div>

    <div>
        <label for="">密码：</label>
        <input type="password" name="password">
    </div>
    <div>
        <button id="login-btn" type="button">登录</button>
    </div>
</form>
</body>
</html>
