<%-- 
    Document   : building
    Created on : 15 June 2025, 10:02:34 pm
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý Tòa nhà</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Danh sách Tòa nhà</h2>
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Tên tòa nhà</th>
            <th>AdminID</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="building" items="${buildingList}">
            <tr>
                <td>${building.buildingID}</td>
                <td>${building.buildingName}</td>
                <td>${building.adminID}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 
