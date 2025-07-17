<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Đặt lịch</title>
</head>
<body>
<h2>Đặt lịch ${type == 'TEST_DRIVE' ? 'lái thử' : 'tư vấn'}</h2>

<c:if test="${not empty errorMsg}">
    <p style="color:red">${errorMsg}</p>
</c:if>

<form action="submitbooking" method="post">
    <input type="hidden" name="type" value="${type}" />

    <!-- Họ tên -->
    <c:choose>
        <c:when test="${empty currentUser.customerProfile.name}">
            <label>Họ tên:</label>
            <input type="text" name="fullName" required />
        </c:when>
        <c:otherwise>
            <input type="hidden" name="fullName" value="${currentUser.customerProfile.name}" />
            <p><strong>Họ tên:</strong> ${currentUser.customerProfile.name}</p>
        </c:otherwise>
    </c:choose>

    <!-- Số điện thoại -->
    <c:choose>
        <c:when test="${empty currentUser.customerProfile.phone}">
            <label>Số điện thoại:</label>
            <input type="text" name="phone" required />
        </c:when>
        <c:otherwise>
            <input type="hidden" name="phone" value="${currentUser.customerProfile.phone}" />
            <p><strong>SĐT:</strong> ${currentUser.customerProfile.phone}</p>
        </c:otherwise>
    </c:choose>

    <!-- Ngày & giờ nếu là lái thử -->
    <c:if test="${type == 'TEST_DRIVE'}">
        <label>Ngày lái thử:</label>
        <input type="date" name="bookingDate" value="${testDate}" required />

        <label>Giờ lái thử:</label>
        <input type="time" name="bookingTime" value="${testTime}" required />
    </c:if>

    <!-- Ghi chú -->
    <label>Ghi chú:</label>
    <textarea name="note" rows="3" cols="40"></textarea>

    <br/><br/>
    <button type="submit">Xác nhận đặt lịch</button>
</form>
</body>
</html>
