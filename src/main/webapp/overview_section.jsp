<%-- 
    Document   : overview_section
    Created on : Jun 24, 2025, 11:45:56 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Overview Page</title>
    </head>
    <body>
        <h2>Ovewview Account</h2>
        <c:if test="${not empty dashboardData}">
            <c:if test="${not empty dashboardData.profile}">
                <h3>${dashboardData.profile.name}</h3>
                <p>Member tier: <strong>${dashboardData.memberTier}</strong></p>
                <p>Loyalty Points:
                    <fmt:formatNumber value="${dashboardData.profile.loyaltyPoints}" />
                </p>
            </c:if>

            <p>Total orders: ${dashboardData.totalOrders}</p>

            <h4>Recent order</h4>
            <c:if test="${not empty dashboardData.recentOrder and not empty dashboardData.recentOrder.orderDetails}">
                <p>Order ID: ${dashboardData.recentOrder.id}</p>
                <p>
                    Model:
                    <c:forEach var="detail" items="${dashboardData.recentOrder.orderDetails}">
                    <div class="product-item">
                        <c:choose>
                            <c:when test="${not empty detail.car and not empty detail.car.carModel}">
                                - ${detail.car.carModel.name}
                            </c:when>
                            <c:otherwise>
                                - N/A
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </p>
            <p>Date: ${dashboardData.recentOrder.saleDate}</p>
            <p>Status: ${dashboardData.recentOrder.status}</p>
        </c:if>
    </c:if>

    <c:if test="${empty dashboardData}">
        <p>Không có dữ liệu tổng quan để hiển thị.</p>
    </c:if>
    <h3>DEBUG OUTPUT</h3>
    <pre>
Recent Order: ${dashboardData.recentOrder}
OrderDetails: ${dashboardData.recentOrder.orderDetails}
    </pre>




</body>
</html>
