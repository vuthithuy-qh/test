<%-- 
    Document   : dashboard
    Created on : Jun 1, 2025, 11:26:31 AM
    Author     : nguye
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - CarShop</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body>
<div class="sidebar">
    <div class="sidebar-title">CarShop Admin</div>
    <a class="sidebar-link" href="userManagement.jsp">Quản lý người dùng</a>
    <a class="sidebar-link" href="productManagement.jsp">Quản lý sản phẩm (ô tô)</a>
    <a class="sidebar-link" href="orderManagement.jsp">Quản lý đơn hàng</a>
    <a class="sidebar-link" href="revenueStats.jsp">Thống kê doanh thu</a>
    <a class="sidebar-link" href="categoryManagement.jsp">Quản lý danh mục</a>
    <a class="sidebar-link" href="index.html">Đăng xuất</a>
</div>


<div class="content">
    <h1>Xin chào, Admin!</h1>
    <p>Chọn chức năng ở menu bên trái để quản lý hệ thống CarShop.</p>
    
</div>
    <footer class="dashboard-footer">
    <p>&copy; 2025 TTTC company. Bảo lưu mọi quyền</p>
</footer>
</body>
</html>
