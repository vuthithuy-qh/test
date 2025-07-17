<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Car" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Quản lý sản phẩm</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        h1 {
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table, th, td {
            border: 1px solid #ccc;
        }

        th, td {
            padding: 10px;
            text-align: center;
        }

        .btn {
            padding: 6px 12px;
            margin: 2px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
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
            padding: 10px 20px;
            margin-bottom: 10px;
            display: inline-block;
        }

        .alert {
            padding: 10px;
            margin-bottom: 15px;
            color: white;
        }

        .alert-success {
            background-color: #28a745;
        }

        .alert-error {
            background-color: #dc3545;
        }
    </style>
</head>
<body>

<h1>Danh sách sản phẩm</h1>

<!-- Thông báo thành công/thất bại -->
<c:if test="${not empty success}">
    <div class="alert alert-success">${success}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-error">${error}</div>
</c:if>

<!-- Nút thêm sản phẩm -->
<a href="ProductManagement?action=new" class="btn btn-create">+ Thêm sản phẩm</a>

<!-- Bảng danh sách sản phẩm -->
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>VIN</th>
            <th>Giá nhập</th>
            <th>Giá bán</th>
            <th>Ngày nhập</th>
            <th>Trạng thái</th>
            <th>Model</th>
            <th>Thao tác</th>
        </tr>
    </thead>
    <tbody>
        <c:if test="${empty carList}">
            <tr><td colspan="8">Không có sản phẩm nào.</td></tr>
        </c:if>
        <c:forEach var="car" items="${carList}">
    <tr>
        <td>${car.id}</td>
        <td>${car.vin}</td>
        <td>${car.importPrice}</td>
        <td>${car.sellingPrice}</td>
        <td>${car.importDate}</td>
        <td>${car.status}</td>
        <td>${car.carModel.name}</td>
        <td>
            <a href="ProductManagement?action=edit&productId=${car.id}">Sửa</a>
            <a href="ProductManagement?action=delete&id=${car.id}">Xóa</a>
        </td>
    </tr>
</c:forEach>

    </tbody>
</table>
 <a href="dashboard.jsp" class="btn btn-back">Dashboard</a>
</body>
</html>
