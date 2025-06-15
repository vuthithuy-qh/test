<%-- 
    Document   : admin
    Created on : 15 June 2025, 10:01:20 pm
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý Admin</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Danh sách Admin</h2>
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Full Name</th>
            <th>Ngày tạo</th>
            <th>Ngày cập nhật</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="admin" items="${adminList}">
            <tr>
                <td>${admin.adminID}</td>
                <td>${admin.username}</td>
                <td>${admin.email}</td>
                <td>${admin.fullName}</td>
                <td>${admin.createdAt}</td>
                <td>${admin.updatedAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 
