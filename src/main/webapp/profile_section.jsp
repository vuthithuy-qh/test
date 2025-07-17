<%-- 
    Document   : profile_section
    Created on : Jun 25, 2025, 11:43:42 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile Page</title>
        <<link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile_section.css"/>
        
    </head>
    <body
        <h3>Infor of account</h3> 
    <p>Manage information of account to security</p>
    <hr><!-- comment -->
    <c:if test="${not empty errors}">
        <div class="error summary" style="border: 1px solid red; padding: 10px; margin-bottom: 20px; background-color: #ffebee ">
            <strong>Please correct errors: </strong>
            <ul>
                <c:forEach var="entry" items="${errors}">
                    <li>${entry.value}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <form action="${pageContext.request.contextPath}/member" method="post">
        <input type="hidden" name="action" value="update_profile"><!-- comment -->
        
        
        <table class="profile-form">
            <tr>
                <td class="form-label">Full name: </td>
                <td>
                    <input type="text" name ="name" value="${profile.name}"><!-- comment -->
                    <c:if test="${not empty errors.nameError}">
                        <span class="error-text">${errors.nameError}</span>
                    </c:if>
                </td>
            </tr>
            <tr>
                <<td class="form-label">Phonenumber: </td>
                <td>
                    <input type="text" name="phone" value="${profile.phone}">
                    <c:if test="${not empty errors.phoneError}">
                        <span class="error-text">${errors.phoneError}</span>
                    </c:if>
                </td>
            </tr>
            
            <tr>
                <td class="form-label">Address: </td>
                <td>
                    <textarea name ="address" class="form-textarea">${profile.address}</textarea>
                </td>
            </tr>
            <tr>
                <td class="form-label">Shipping address</td>
                <td>
                    <textarea name="shippingAddress" class="form-textarea">${profile.shippingAddress}</textarea>
                </td>
            </tr>
            <tr>
                <<td class="form-label">BirthDate:</td>
                <td>
                  
                    <input type="date" name="birthdate" value="${formattedBirthdate}" class="form-input">
                </td>
            </tr>
            
            <tr>
                <td class="form-label">Gender</td>
                <td>
                    <input type="radio" name="gender" value="Male" ${profile.gender == 'Male' ? 'checked' : ''}>Male
                    <input type="radio" name="gender" value="Female" ${profile.gender == 'Female' ? 'checked' : ''}>Female
                    <input type="radio" name="gender" value="Difference" ${profile.gender == 'Difference' ? 'checked' : ''}>Difference
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit" class="submit-button">Save change </button>
                </td>
            </tr>
            
            
        </table>
    </form>
        
    
            
    </body>
    
</html>
