<%-- 
    Document   : login
    Created on : 1 Jun 2025, 14:43:01
    Author     : vuthi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <style>
            .error {
                color: red;
                margin-bottom: 15px;
            }
        </style>
        <link rel="stylesheet" href="css/login.css"/>
    </head>
    <body>
        <div class="login-page-container">
            <div class="login-form-column">
                <div class="form-content">
                    <div class="form-header">
                        <span class="brand-id">TAI KHOAN</span>
                        <h1>Welcome</h1>
                        <p class="portal-info">Truy cap he thong cua ban</p>
                    </div>

                    <c:if test="${not empty errorMsg}">
                        <div class="error-message">${errorMsg}</div>
                    </c:if>
                    
                        
                        <c:if test="${not empty sessionScope.successMsg}">
                            <div class="success-message">${sessionScope.successMsg}</div>
                            <c:remove var ="successMsg" scope="session"/>
                        </c:if>

                    <form action="LoginServlet" method="post" class="login-form-actual">
                        <div class="form-group">
                            <label for="username">Tên đăng nhập:</label>
                            <input type="text" id="username" name="username" required>
                        </div>


                        <div class="form-group">
                            <label for="password">Mật khẩu:</label>
                            <input type="password" id="password" name="password" required>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">Tiep tuc</button>
                            <a href="register.jsp" class="btn btn-secondary">Tao tai khoan</a>
                        </div>


                    </form>
                </div>
            </div>

            <div class="login-image-column">
                <!--                <img src="https://i.pinimg.com/736x/5a/a9/ab/5aa9ab36cda8523ba66a2f2a8d4196a5.jpg">-->

            </div>

        </div>


    </body>
</html>
