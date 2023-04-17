<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zxx">

<head>
    <base href="${ctx}/">
    <title>碘化银酒店管理系统登录页面</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="assets/login/css/newlogin.css" type="text/css" media="all"/>
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/login/js/newlogin.js"></script>
    <script>
        const ctx = "${ctx}";
        const error = "${error}";
    </script>
    <style>
        #captcha {
            width: 100px;
            height: 40px;
            margin-left: 30px;
            border: 1px solid #cccccc;
        }
    </style>
</head>
<body>
<div class="signinform">
    <h1>碘化银酒店登录</h1>
    <!-- container -->
    <div class="container">
        <!-- main content -->
        <div class="w3l-form-info">
            <div class="w3_info">
                <h2>登录</h2>
                <form id="login-form" action="user/login" method="post">
                    <div class="input-group">
                        <span><i class="fas fa-user" aria-hidden="true"></i></span>
                        <input type="text" id="username" name="username" placeholder="请输入用户名" required="">
                    </div>
                    <div class="input-group">
                        <span><i class="fas fa-key" aria-hidden="true"></i></span>
                        <input type="Password" id="password" name="password" placeholder="请输入密码" required="">
                    </div>
                    <div class="input-group">
                        <span><i class="fas fa-key" aria-hidden="true"></i></span>
                        <input type="text" id="captcha-input" name="captcha" placeholder="请输入验证码" required="">
                    </div>
                    <div class="form-row bottom">
                        <%--验证码--%>
                        <img id="captcha" src="user/captcha">
                        <div class="form-check">
                            <input type="checkbox" id="remember" name="remember" value="remember" checked>
                            <label for="remember"> 记住密码</label>
                        </div>
                    </div>
                    <button id="login-btn" class="btn btn-primary btn-block" type="button">登录</button>
                </form>
            </div>
        </div>
        <!-- //main content -->
    </div>
    <!-- //container -->
    <!-- footer -->
    <div class="footer">
        <p>&copy; 碘化银酒店管理系统是我的第一个项目</p>
    </div>
    <!-- footer -->
</div>


</body>

</html>