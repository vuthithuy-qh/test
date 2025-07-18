<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Font and Icons -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<!-- HEADER START -->
<header class="main-header">
    <div class="header-content">
        
        <!-- LOGO -->
        <div class="logo-section">
            <a href="${pageContext.request.contextPath}/home.jsp" class="logo-link">
                <img src="https://i.pinimg.com/736x/b9/d8/38/b9d838225bdb9dfdf924315456623479.jpg" alt="Car Showroom Logo" class="header-logo-img">
                <span class="logo-text">Car Showroom</span>
            </a>
        </div>

        <!-- SEARCH -->
        <div class="search-bar-header">
            <form action="${pageContext.request.contextPath}/cars" method="get" class="header-search-form">
                <input type="text" name="keyword" placeholder="Search for cars..." class="header-search-input"/>
                <button type="submit" class="header-search-button">
                    <i class="fas fa-search"></i>
                </button>
            </form>
        </div>

        <!-- NAVIGATION -->
        <nav class="main-nav">
            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <span>Hello, ${sessionScope.currentUser.username}!</span>
                    <a href="${pageContext.request.contextPath}/cart">Booking</a>

                    <c:set var="requestURI" value="${pageContext.request.requestURI}"/>
                    <c:if test="${!fn:contains(requestURI, '/member')}">
                        <a href="${pageContext.request.contextPath}/member">My Account</a>
                    </c:if>

                    <a href="${pageContext.request.contextPath}/logout">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login.jsp">Login</a>
                    <a href="${pageContext.request.contextPath}/register.jsp" class="register-btn">Register</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </div>
</header>
