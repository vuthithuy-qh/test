<%-- 
    Document   : member-dashboard
    Created on : Jun 24, 2025, 12:59:47 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Member Page</title>
         

        <%-- Nhúng font Poppins --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/member_dashboard.css"/>
    </head>
    <body>
        <%--Nap header chung vao day --%>
        <jsp:include page="common/header.jsp"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
        <div class="dashboard-container">
            <div class="left-navifation">
                <div class="user-summary">
                    <h4>${sessionScope.currentUser.username}</h4><!-- comment -->
                    <p>${sessionScope.currentUser.email}</p>
                </div>
                <ul>
                    <li class="${activeSection == 'overview' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/member?section=overview">Overview</a>   
                    </li>
                    <li class="${activeSection == 'profile' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/member?section=profile">Information of Account</a>
                    </li>
                    <li class="${activeSection == 'orders' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/member?section=orders">History of Order</a>
              
                    </li>
                    
                    <li>
                        <a href="${pageContext.request.contextPath}/logout">Log out</a>
                    </li>
                </ul>
            </div>
                    
                    <div class="main-content">
                        <%-- Hien thi thong bao nguoi dung neu co --%>
                        <c:if test="${not empty successMsg}">
                            <div class="success-message">${successMsg}</div>
                        </c:if>
                            
                            <c:if test="${not empty errors}">
                                
                            </c:if>
                            
                        <%-- dung c: choose de nap file JSP con tuong ung voi cac section --%>
                        <c:choose>
                            
                            <c:when test="${activeSection == 'welcome' || empty activeSection}">
                                <jsp:include page="welcome_section.jsp" />
                            </c:when>
                            <c:when test="${activeSection == 'overview'}">
                                <jsp:include page="overview_section.jsp" />
                            </c:when>
                            <c:when test="${activeSection == 'profile'}">
                                <jsp:include page="profile_section.jsp"/>
                            </c:when>
                            <c:when test="${activeSection == 'orders'}">
                                <jsp:include page="orders_section.jsp"/>
                            </c:when>
                            <c:when test="${activeSection == 'order-detail'}">
                                <jsp:include page="order_detail_section.jsp"/>
                            </c:when>

                            <c:otherwise>
                                <p>Chuc nang khong hop le</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
            
        </div>
    </body>
</html>
