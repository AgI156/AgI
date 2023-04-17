<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <base href="${ctx}/">
    <meta charset="utf-8">
    <title>修改房间信息</title>
    <link rel="stylesheet" href="assets/room/css/add.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/room/js/edit.js"></script>
    <script>
        const ctx = "${ctx}";
        const error = "${error}";
        const kind = "${room.kind}";
        const state = "${room.state}";
    </script>
</head>
<body>
<h1>修改房间信息</h1>
<form id="room-form" action="room/edit" method="post">
    <div>
        <label for="">*房间号：</label>
        <input type="text" name="name" autocomplete="off" placeholder="请输入房间号，不可为空"
               value="${room.name}">
    </div>
    <div class="clear kind">
        <label for="" class="fl">*房间类型：</label>
        <div class="fl">
            <input type="radio" id="kinds1" name="kind" value="小床房" checked>
            <label for="kinds1">小床房</label>
        </div>
        <div class="fl">
            <input type="radio" id="kinds2" name="kind" value="中床房">
            <label for="kinds2">中床房</label>
        </div>
        <div class="fl">
            <input type="radio" id="kinds3" name="kind" value="大床房">
            <label for="kinds3">大床房</label>
        </div>
    </div>
    <div class="clear state">
        <label for="" class="fl">*房间状态：</label>
        <div class="fl">
            <input type="radio" id="male" name="state" value="空闲" checked>
            <label for="male">空闲</label>
        </div>
        <div class="fl">
            <input type="radio" id="female1" name="state" value="有客">
            <label for="female1">有客</label>
        </div>
        <div class="fl">
            <input type="radio" id="female" name="state" value="维修">
            <label for="female">维修</label>
        </div>
    </div>
    <div>
        <label for="">*价格：</label>
        <input type="text" name="price" autocomplete="off" placeholder="请输入房间价格/每天"
               value="${room.price}">
    </div>
    <div>
        <label for="">备注：</label>
        <textarea name="description" value="${room.description}"></textarea>
    </div>
    <div>
        <label for=""></label>
        <button id="submit-btn" type="button">提交</button>
        <button type="reset">重置</button>
        <input type="hidden" name="id" value="${room.id}">
    </div>
</form>
</body>
</html>