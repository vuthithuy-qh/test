<%-- 
    Document   : invoice
    Created on : 15 June 2025, 10:05:03 pm
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý Hóa đơn</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Danh sách Hóa đơn</h2>
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>RoomID</th>
            <th>Ngày lập</th>
            <th>Tổng tiền</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="invoice" items="${invoiceList}">
            <tr>
                <td>${invoice.invoiceID}</td>
                <td>${invoice.roomID}</td>
                <td>${invoice.invoiceDate}</td>
                <td>${invoice.totalAmount}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 
