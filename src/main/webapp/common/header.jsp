<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<header class="main-header">
    <div class="header-content">
        <a href="${pageContext.request.contextPath}/home.jsp" class="logo">
            <img src="https://i.pinimg.com/736x/b9/d8/38/b9d838225bdb9dfdf924315456623479.jpg" >
        </a>
            <nav class="main-nav">
                <c:choose>
                    <c:when test="${not empty sessionScope.currentUser}">
                        <span>Hello, ${sessionScope.currentUser.username}!</span>
                        <a href="${pageContext.request.contextPath}/cart">
                            Cart (<span>${empty sessionScope.cart ? 0 : fn:length(sessionScope.cart)}</span>
                        </a>
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