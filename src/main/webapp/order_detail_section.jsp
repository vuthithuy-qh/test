<%-- 
    Document   : order_detail_section
    Created on : Jun 26, 2025, 12:50:51 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order detail Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order_detail_section.css"/>
    </head>
    <body>
        <c:choose>
            <c:when test="${not empty order}">
                <a href="${pageContext.request.contextPath}/member?section=orders">&laquo; Back order history</a>
                <h3 style="margin-top: 20px;">Order Detail #${order.id}</h3>
                <p>Sale Date: ${order.saleDateFormatted} - Status: 
                    <span class="status status-${order.status}">${order.status}</span>
                </p>
                <hr>
                <h4>Information of shipping</h4>
                <c:if test="${not empty order.customer and not empty order.customer.customerProfile}">
                    <p>
                        <strong>Customer: </strong> ${order.customer.customerProfile.name}<br>
                        <strong>Phone number</strong> ${order.customer.customerProfile.phone}<br>
                        <strong>Shipping address:</strong> ${order.customer.customerProfile.shippingAddress}

                    </p>
                </c:if>

                <c:if test="${empty order.customer or empty order.customer.customerProfile}">
                    <p><em>Customer information is not avaliable</em></p>
                </c:if>
               

                <h4>Products in orders</h4>
                <table class="order-details-table" border="1" width="100%">
                    <thead>
                        <tr>
                            <th>Products</th>
                            <th style="text-align: right">Single price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="detail" items="${order.orderDetails}">
                            <tr>
                                <td>
                                    <div style="display: flex; align-items: center;">
                                    
                                        <div>
                                            <strong>${detail.car.carModel.name}</strong>
                                            <span>VIN: ${detail.car.vin}</span>
                                        </div>

                                    </div>
                                <td style="text-align: right">
                                    <fmt:formatNumber value="${detail.singlePrice}" pattern="#,###"/> dong

                                </td>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>

                    <tfoot>
                        <tr>
                            <td style="text-align: right"><strong>Totals: </strong></td>
                            <td style="text-align: right"><strong> <fmt:formatNumber value="${order.billing.amount}" pattern = "#.###"/> dong </strong></td>
                        </tr>
                    </tfoot>

                </table>

                <c:if test="${order.status =='PROCCESSING' || order.status == 'PENDING'}">
                    <div style="margin-top: 20px">
                        <a href="${pageContext.request.contextPath}/member?section=cancel-order&orderId=${order.id}"
                           class="button-danger"
                           onclick="return confirm('Are you sure to cancel this order?');">
                            Cancel this order
                        </a>

                    </div>
                </c:if>

            </c:when>
            <c:otherwise>
                <p class="error-text">${errorMessage !=null ? errorMessage : "No found order" }</p>
                <a href="${pageContext.request.contextPath}/member?section=orders"> Back history of Orders</a>
            </c:otherwise>

        </c:choose>
    </body>
</html>