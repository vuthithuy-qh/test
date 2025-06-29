<%-- 
    Document   : order_section
    Created on : Jun 26, 2025, 12:09:17 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orders Page</title>
    </head>
    <body>
        <h3>Orders History</h3>
        <c:choose>
            <c:when test="${not empty pageData.orders}">
                <p>Recent order history</p>
                <table class="order-history-table">
                    <thead>
                        <tr>
                            <th>OrderID</th>
                            <th>Date order</th>
                            <th>Status</th>
                            <th style="text-align: right">Total money</th>
                            <th>Actions</th>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="order" items="${pageData.orders}">
                            <tr>
                                <td><strong>#${order.id}</strong></td>
                                <td>
                                    <span class="sale-date">${order.saleDateFormatted}</span>

                                </td>
                                <td>
                                    <span class="status status-${order.status}">${order.status}</span>

                                </td>
                                <td style="text-align: right">
                                    <strong> <fmt:formatNumber value="${order.billing.amount}" pattern="#,###" /> dong </strong>

                                </td>
                                

                                <td>
                                    <a href="${pageContext.request.contextPath}/member?section=order-detail&orderId=${order.id}">See more details</a>
                                    
                                    <c:if test="${order.status == 'PENDING' }">  <%-- chi hien nut huy neu trang thai la pending --%>
                                        <br>
                                        <a href="${pageContext.request.contextPath}/member?section=cancel-order&orderId=${order.id}"
                                           onclick="return confirm('Are you sure cancel this order?');"
                                           style="color: red; text-decoration: none;">Cancel this order </a>
                                        
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>

                    </tbody>

                </table>

                <c:if test="${pageData.totalPages > 1}">
                    <div class="pagination">
                        <c:if test="${pageData.currentPage >1}">
                            <a href="${pageContext.request.contextPath}/member?section=orders&page=${pageData.currentPage -1}">&laquo; Previous</a>

                        </c:if>

                        <c:forEach var="i" begin="1" end="${pageData.totalPages}">
                            <a href="${pageContext.request.contextPath}/member?section=orders&page=${i}" 
                               class="${pageData.currentPage == i ? 'active' : '' }">${i}
                            </a>
                        </c:forEach>

                        <c:if test="${pageData.currentPage < pageData.totalPages}">
                            <a href="${pageContext.request.contextPath}/member?section=orders&page=${pageData.currentPage + 1}">after &raquo;</a> 
                        </c:if>

                    </div>

                </c:if>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <p>you have No order</p>
                    <a href="${pageContext.request.contextPath}/products" class="button">Start Shopping</a><!-- comment -->

                </div>
            </c:otherwise>
        </c:choose>

    </body>
</html>
