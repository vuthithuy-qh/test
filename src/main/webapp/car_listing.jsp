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
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/common/header.jsp"/>

        <div class="container">
            <h1 class="page-title">Available Models</h1>

            <form action="cars" method="get" class="search-form">
                <div class="form-group">
                    <label for="keyword">Search by Keyword</label>
                    <input type="text" id="keyword" name="keyword" value="${searchCriteria.keyword}" placeholder="e.g., Camry, CR-V..."/>
                </div>

                <div class="form-group">
                    <label for="manufacture">Manufacture</label>
                    <select id="manufacture" name="manufactureId">
                        <option value="">-- All Manufactures --</option>
                        <c:forEach var="manu" items="${filterData.manufactures}">
                            <option value="${manu.id}" <c:if test="${searchCriteria.manufactureId == manu.id}">selected</c:if>>
                                ${manu.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="carType">Car Type</label>
                    <select id="carType" name="carTypeId">
                        <option value="">-- All Car Types --</option>
                        <c:forEach var="type" items="${filterData.carTypes}">
                            <option value="${type.id}" <c:if test="${searchCriteria.carTypeId == type.id}">selected</c:if>>
                                ${type.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="year">Year of Manufactory</label>
                    <select id="year" name="year">
                        <option value="">-- All Years --</option>
                        <c:forEach var="y" items="${filterData.years}">
                            <option value="${y}" <c:if test="${searchCriteria.year == y}">selected</c:if>>
                                ${y}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="color">Color</label>
                    <select id="color" name="colorId">
                        <option value="">-- All Colors --</option>
                        <c:forEach var="color" items="${filterData.colors}">
                            <option value="${color.id}" <c:if test="${searchCriteria.colorId == color.id}"> selected</c:if>>
                                ${color.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="engineType">Engine Type</label>
                    <select id="engineType" name="engineTypeId">
                        <option value="">-- All Engine Types --</option>
                        <c:forEach var="engine" items="${filterData.engineTypes}">
                            <option value="${engine.id}" <c:if test="${searchCriteria.engineTypeId == engine.id}">selected</c:if>>
                                ${engine.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group price-range">
                    <label>Price Range</label>
                    <div class="price-inputs"> <!-- THẺ DIV MỚI ĐỂ BỌC CÁC INPUT GIÁ -->
                        <input type="number" name="minPrice" value="${searchCriteria.minPrice}" placeholder="Min Price" min="0"/>
                        <input type="number" name="maxPrice" value="${searchCriteria.maxPrice}" placeholder="Max Price" min="0"/>
                    </div>
                </div>

                <button type="submit" class="search-button">Search Cars</button>
            </form>

            <div class="car-grid">
                <c:forEach var="car" items="${pageData.cars}">
                    <div class="car-card">
                        <div class="car-image-container">
                            <c:if test="${not empty car.images}">
                                <img src="${car.images[0].imageUrl}" alt="${car.carModel.name} Image" class="car-image"/>
                            </c:if>
                            <c:if test="${empty car.images}">
                                <img src="${pageContext.request.contextPath}/images/default_car.png" alt="No image available" class="car-image default-image"/>
                            </c:if>
                        </div>
                        <div class="car-details">
                            <h3>${car.carModel.name}</h3>
                            <%-- Format giá tiền --%>
                            <p class="price">Price: ${String.format("%,.0f", car.sellingPrice)} VNĐ</p>
                            <p><strong>Manufacture:</strong> ${car.carModel.manufacture.name}</p>
                            <p><strong>Type:</strong> ${car.carModel.carType.name}</p>
                            <p><strong>Year:</strong> ${car.carModel.year}</p>
                            <p><strong>Color:</strong> ${car.carModel.color.name}</p>
                            <p><strong>Engine Type:</strong> ${car.carModel.engineType.name}</p>
                            <div class="car-actions">
                                <a href="${pageContext.request.contextPath}/addtocart?carId=${car.id}" class="btn primary-btn">Book a test drive</a>
                                <a href="${pageContext.request.contextPath}/car-detail?id=${car.id}" class="btn secondary-btn">Discover</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="pagination">
                <c:if test="${pageData.totalPages > 1}">
                    <c:forEach var="i" begin="1" end="${pageData.totalPages}">
                        <a href="cars?keyword=${searchCriteria.keyword}
                               &manufactureId=${searchCriteria.manufactureId}
                               &carTypeId=${searchCriteria.carTypeId}
                               &year=${searchCriteria.year}
                               &minPrice=${searchCriteria.minPrice}
                               &maxPrice=${searchCriteria.maxPrice}
                               &page=${i}"
                           class="${pageData.currentPage == i ? 'active' : ''}">
                            ${i}
                        </a>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </body>
</html>