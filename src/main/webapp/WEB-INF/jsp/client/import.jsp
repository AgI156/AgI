<%--
  Created by IntelliJ IDEA.
  User: dhy123
  Date: 2023/3/15
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <base href="${ctx}/">
    <title>导入人口库数据</title>
    <!-- 引入jquery -->
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script type="text/javascript">
        const ctx = "${ctx}";
    </script>
    <script src="assets/client/js/import.js"></script>
    <style>
        body {
            margin: 0;
            box-sizing: border-box;
            padding-top: 15px;
        }

        #import-form > div {
            margin-bottom: 15px;
        }
    </style>
</head>
<body class="container-md">
<div class="col-md-12">
    <form id="import-form" action="client/import" method="post" enctype="multipart/form-data">
        <div>
            <label>上传文件：</label>
            <input id="file" name="file" type="file" class="form-control">
        </div>
        <div>
            <button type="submit" id="import-btn">提交</button>
        </div>
    </form>
</div>
</body>
</html>