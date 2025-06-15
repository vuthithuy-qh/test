<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quản lý Sinh viên</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Danh sách Sinh viên</h2>
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Full Name</th>
            <th>Ngày sinh</th>
            <th>Giới tính</th>
            <th>Phone</th>
            <th>Địa chỉ</th>
            <th>Trạng thái</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="student" items="${studentList}">
            <tr>
                <td>${student.studentID}</td>
                <td>${student.username}</td>
                <td>${student.email}</td>
                <td>${student.fullName}</td>
                <td>${student.dob}</td>
                <td>${student.gender}</td>
                <td>${student.phone}</td>
                <td>${student.address}</td>
                <td>${student.statusRoom}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 