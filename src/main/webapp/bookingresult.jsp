<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Kết quả đặt lịch</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 30px;
            background-color: #f9f9f9;
        }

        .result-box {
            max-width: 600px;
            margin: auto;
            padding: 30px;
            border-radius: 10px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .success {
            color: #28a745;
            font-size: 20px;
            font-weight: bold;
        }

        .error {
            color: #dc3545;
            font-size: 18px;
        }

        .btn-group {
            margin-top: 30px;
        }

        .btn-group a {
            display: inline-block;
            padding: 10px 20px;
            margin: 0 10px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }

        .btn-group a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="result-box">
    <c:if test="${not empty successMsg}">
        <div class="success">${successMsg}</div>
    </c:if>

    <c:if test="${not empty errorMsg}">
        <div class="error">${errorMsg}</div>
    </c:if>

    <div class="btn-group">
        <a href="home.jsp">Về trang chủ</a>
    </div>
</div>

</body>
</html>
