<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Car" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Quản lý hệ thống</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                display: flex;
                margin: 0;
            }

            .sidebar {
                width: 220px;
                background-color: #2c3e50;
                color: white;
                height: 100vh;
                padding: 20px 10px;
            }

            .sidebar h2 {
                text-align: center;
            }

            .sidebar a {
                display: block;
                color: white;
                padding: 10px;
                text-decoration: none;
                margin: 5px 0;
            }

            .sidebar a:hover {
                background-color: #34495e;
            }

            .main-content {
                flex: 1;
                padding: 20px;
                background: #f4f4f4;
            }

            .btn {
                padding: 6px 12px;
                border: none;
                border-radius: 4px;
                text-decoration: none;
                cursor: pointer;
            }

            .btn-edit {
                background-color: #ffc107;
                color: black;
            }

            .btn-delete {
                background-color: #dc3545;
                color: white;
            }

            .btn-create {
                background-color: #28a745;
                color: white;
            }
        </style>
    </head>
    <body>

        <div class="sidebar">
            <h2>User Admin</h2>
            <a href="ProductManagement">Quản lý sản phẩm</a>
            <a href="dashboard.jsp?section=users">Quản lý người dùng</a>
            <a href="dashboard.jsp?section=orders">Quản lý đơn hàng</a>
            <a href="#">Phân quyền</a>
            <a href="#">Hồ sơ cá nhân</a>
            <a href="#">Cài đặt</a>
        </div>

        <div class="main-content">
            <c:choose>
                <c:when test="${param.section eq 'products'}">
                    <c:import url="productList.jsp" />
                </c:when>
                <c:when test="${param.section eq 'users'}">
                    <c:import url="userList.jsp" />
                </c:when>
                <c:when test="${param.section eq 'orders'}">
                    <c:import url="orderList.jsp" />
                </c:when>
                <c:otherwise>
                    <h2>Xin chào admin, hãy chọn chức năng bên trái</h2>
                </c:otherwise>
            </c:choose>
        </div>

    </body>
</html>
