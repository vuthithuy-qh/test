<%-- 
    Document   : welcome_section
    Created on : Jun 27, 2025, 2:38:29 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Welcome, ${sessionScope.currentUser.username}!</h2>
        <p>Chon 1 muc ben trai de bat dau quan li tai khoan cua ban</p>
    </body>
</html>
