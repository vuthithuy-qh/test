<%-- 
    Document   : car_listing
    Created on : Jun 28, 2025, 7:27:49 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Car List</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/> 
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/car_listing.css"/>
        <%-- Nhúng font Poppins --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/common/header.jsp"/>
        
        <div class="container">
            <h1>Car List</h1>
            
            <form action="cars" method="get" class="search-form">
                <input type="text" name="keyword" value="${searchCriteria.keyword}" placeholder="Enter keyword..."/>
                
                <select name="manufactureId">
                    <option value="">--Pick Manufacture--</option>
                    <c:forEach var="manu" items="${filterData.manufactures}">
                        <option value="${manu.id}">
                            <c:if test="${searchCriteria.manufactureId == manu.id}">
                                selected
                            </c:if>
                                ${manu.name}
                        </option>
                    </c:forEach>
                </select>
                
                <select name="carTypeId">
                    <option value="">--Pick CarType--</option>
                    <c:forEach var="type" items="${filterData.carTypes}">
                        <<option value="${type.id}">
                            <c:if test="${searchCriteria.carTypeId == type.id}">
                                selected
                            </c:if>
                                ${type.name}
                        </option>
                    </c:forEach>
                </select>
                
                <select name="year">
                    <option value="">--Pick year manufactory--</option>
                    <c:forEach var="y" items="${filterData.years}">
                        <option value="${y}" <c:if test="${searchCriteria.year == y}">selected</c:if>>
                            ${y}
                        </option>
                    </c:forEach>
                </select>
                
                <select name="colorId">
                    <option value="">-- All color --</option>
                    <c:forEach var="color" items="${filterData.colors}">
                        <option value="${color.id}" 
                                <c:if test="${searchCriteria.colorId == color.id}"> selected</c:if>>
                                
                            ${color.name}
                        </option>
                    </c:forEach>
                </select>
                
                <select name="engineTypeId">
                    <option value="">--All Engine Type --</option>
                    <c:forEach var="engine" items="${filterData.engineTypes}">
                        <option value="${engine.id}"
                               <c:if test="${searchCriteria.engineTypeId == engine.id}">selected</c:if>>
                            ${engine.name}
                        </option>
                    </c:forEach>
                </select>

                
                <input type="number" name="minPrice" value="${searchCriteria.minPrice}" placeholder="Min Price" min="0"/>
                
                <input type="number" name="maxPrice" value="${searchCriteria.maxPrice}" placeholder="Ma Price" min="0"/>
                
                <button type="submit">Search</button>
                
            </form>
            
                <hr><!-- comment -->
                <div class="car-grid">
                    <c:forEach var="car" items="${pageData.cars}">
                        <div class="car-card">
                            <c:if test="${not empty car.images}">
                                <img src="${car.images[0].imageUrl}" alt="${car.carModel.name} Image" class="car-image"/>
                            </c:if>
                            <c:if test="${empty car.images}">
                                <img src="${pageContext.request.contextPath}/images/default_car.png" alt="No image available" class="car-image default-image"/>
                            </c:if>
                            <h3>${car.carModel.name}</h3>
                            <p>Price: ${car.sellingPrice}</p>
                            <p>Manufacture: ${car.carModel.manufacture.name}</p>
                            <p>Type: ${car.carModel.carType.name}</p>
                            <p>Year: ${car.carModel.year}</p>
                            <p>Color: ${car.carModel.color.name}</p>
                            <p>Engine Type: ${car.carModel.engineType.name}</p>
                            <a href="${pageContext.request.contextPath}/addtocart?carId=${car.id} " class="add-to-cart-btn">
                                Add to cart
                            </a>
                            <a href="${pageContext.request.contextPath}/car-detail?id=${car.id}" class="add-to-cart-btn">
                                View Details
                            </a>
                        </div>
                    </c:forEach>
                    
                </div>
                
                <div class="pagination">
                    <c:if test="${pageData.totalPages > 1}">
                        <c:forEach var="i" begin="1" end="${pageData.totalPages}">
                            <%--Gui lai thong tin tim kiem qua url--%>
                            <a href="cars?keyword=${searchCriteria.keyword}
                               &manufactureId=${searchCriteria.manufactureId}
                               &carTypeId = ${searchCriteria.carTypeId}
                               &year=${searchCriteria.year}
                               &minPrice=${searchCriteria.minPrice}
                               &maxPrice=${searchCriteria.maxPrice}
                               &page=${i}
                               "
                               class="${pageData.currentPage == i ? 'active' : ''}">

                                ${i}
                            </a>
                        </c:forEach>
                            
                    </c:if>
                        
                    
                </div>
            
            
        </div>
                
                
    </body>
</html>
