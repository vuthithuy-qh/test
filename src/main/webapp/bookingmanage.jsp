<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Đặt lịch</title>
    <style>
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
        form.inline { display: inline; }
    </style>
</head>
<body>
<h2>Quản lý Đặt lịch (Booking)</h2>

<form action="ManageBookingServlet" method="get">
    <label>Lọc theo trạng thái:</label>
    <select name="status">
        <option value="">-- Tất cả --</option>
        <option value="WAITING">WAITING</option>
        <option value="DONE">DONE</option>
    </select>
    <label>Ngày (yyyy-mm-dd):</label>
    <input type="date" name="bookingDate" />
    <button type="submit">Lọc</button>
</form>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Customer ID</th>
        <th>Tên khách</th>
        <th>Loại</th>
        <th>Ngày</th>
        <th>Giờ</th>
        <th>Trạng thái</th>
        <th>Ghi chú</th>
        <th>Ngày tạo</th>
        <th>Thao tác</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="b" items="${bookings}">
        <tr>
            <td>${b.id}</td>
            <td>${b.customer.id}</td>
            <td>${b.customer.customerProfile.name}</td>
            <td>${b.type}</td>
            <td>${b.bookingDate}</td>
            <td><c:out value="${b.bookingTime}" default="-" /></td>
            <td>${b.status}</td>
            <td><c:out value="${b.note}" default="-" /></td>
            <td>${b.createdAt}</td>
            <td>
                <c:if test="${b.status == 'WAITING'}">
                    <form action="ManageBookingServlet" method="post" class="inline">
                        <input type="hidden" name="action" value="updateStatus" />
                        <input type="hidden" name="id" value="${b.id}" />
                        <input type="hidden" name="newStatus" value="DONE" />
                        <button type="submit">Xác nhận</button>
                    </form>
                </c:if>
                <c:if test="${b.status == 'DONE'}">
                    <span>✓ Hoàn tất</span>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>