<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <base href="${ctx}/">
    <meta charset="utf-8">
    <title>修改客户信息</title>
    <link rel="stylesheet" href="assets/client/css/edit.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/lib/laydate/laydate.js"></script>
    <script src="assets/client/js/edit.js"></script>
    <script>
        const ctx = "${ctx}";
        const error = "${error}";
        const sex = "${client.sex}";
    </script>
</head>
<body>
<h1>修改客户信息</h1>
<form id="client-form" action="client/edit" method="post">
    <div>
        <label for="">*卡号：</label>
        <input type="text" name="clientId" autocomplete="off" placeholder="请输入客户卡号，不可为空"
               value="${client.clientId}" readonly>
    </div>
    <div>
        <label for="">*姓名：</label>
        <input type="text" name="name" placeholder="请输入客户姓名，不可为空" value="${client.name}">
    </div>
    <div class="clear sex">
        <label for="" class="fl">性别：</label>
        <div class="fl">
            <input type="radio" id="male" name="sex" value="男" checked>
            <label for="male">男</label>
        </div>
        <div class="fl">
            <input type="radio" id="female" name="sex" value="女">
            <label for="female">女</label>
        </div>
    </div>
    <%--<div>
        <label for="">年龄：</label>
        <input type="text" name="age" value="${client.age}">
    </div>--%>
    <div>
        <label for="">出生日期：</label>
        <input type="text" id="birthday" name="birthday" value="${client.birthday}">
    </div>
    <div>
        <label for="">*电话：</label>
        <input type="text" name="phone" placeholder="请输入客户电话，不可为空" value="${client.phone}">
    </div>
    <div>
        <label for="">备注：</label>
        <textarea name="description" value="${client.description}"></textarea>
    </div>
    <div>
        <label for=""></label>
        <button id="submit-btn" type="button">提交</button>
        <button type="reset">重置</button>
        <input type="hidden" name="id" value="${client.id}">
    </div>
</form>
</body>
</html>