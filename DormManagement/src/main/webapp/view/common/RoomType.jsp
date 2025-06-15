<%-- 
    Document   : RoomType
    Created on : 15 June 2025, 10:03:34 pm
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quản lý Loại phòng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Danh sách Loại phòng</h2>
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Tên loại</th>
            <th>Diện tích</th>
            <th>Giá</th>
            <th>Sức chứa</th>
            <th>BuildingID</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="roomType" items="${roomTypeList}">
            <tr>
                <td>${roomType.roomTypeID}</td>
                <td>${roomType.typeName}</td>
                <td>${roomType.area}</td>
                <td>${roomType.price}</td>
                <td>${roomType.capacity}</td>
                <td>${roomType.buildingID}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 
