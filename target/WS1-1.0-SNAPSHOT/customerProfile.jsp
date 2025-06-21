<%-- 
    Document   : customerProfile
    Created on : Jun 15, 2025, 4:05:53 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Information</title>
        <link rel="stylesheet" href="css/customerprofile.css"/>
    </head>
    <body>
        <div class="profile-container">
            <h2>Information of customer</h2>
            
            <c:if test="${not empty errorMsg}">
                <div class="error-message">${errorMsg}</div>
            </c:if>
            
            <c:if test="${not empty successMsg}">
                <div class="success-message">${successMsg}</div>
            </c:if>
            
            <form action="CustomerProfileServlet" method="post">
                <label>Name: </label>
                <input type="text" name="name" value="${profile.name} " required=""><br>
                <label>Phone:  </label>
                <input type="text" name="phone" value="${profile.phone}  " required=""><br><!-- comment -->
                <label>Address </label>
                <input type="text" name="address" value="${profile.address} " required=""><br><!-- comment -->
                <label>BirthDate </label>
                <input type="date" name="birthdate" value="${profile.birthdate}  " required=""><br><!-- comment -->
                <label>Gender </label>
                <select name="gender">
                    <option value="">---Pick One---</option>
                    <option value="Male" ${profile.gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${profile.gender == 'Female' ? 'selected' : ''}>Female</option>
                    <option value="Difference" ${profile.gender == 'Difference' ? 'selected' : ''}>Difference</option>
                </select><br>

                
                <label>Shipping address</label>
                <input type="text" name="shippingAddress" value="${profile.shippingAddress} "><br>
                <button type="submit">Update</button>
            </form>
        </div>
    </body>
</html>
