<%-- 
    Document   : Room
    Created on : 15 June 2025, 10:04:05 pm
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quản lý Phòng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Danh sách Phòng</h2>
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Số phòng</th>
            <th>RoomTypeID</th>
            <th>BuildingID</th>
            <th>Trạng thái</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="room" items="${roomList}">
            <tr>
                <td>${room.roomID}</td>
                <td>${room.roomNumber}</td>
                <td>${room.roomTypeID}</td>
                <td>${room.buildingID}</td>
                <td>${room.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 
