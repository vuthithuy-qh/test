<%--
    Document   : cart
    Created on : Jun 28, 2025, 7:27:49 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Giỏ Hàng - Car Showroom</title>
    <%-- Nhúng font Poppins (đã được sử dụng ở các trang khác) --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <%-- Nhúng CSS chung cho header --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
    <%-- Nhúng CSS mới cho trang giỏ hàng --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css"/>
</head>
<body>
    <%-- Bao gồm header chung --%>
    <jsp:include page="/common/header.jsp"/>

    <div class="container cart-page-container">
        <h1 class="page-title">Your Shopping Cart</h1>

        <%-- Alert Messages --%>
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-error">
                <i class="fas fa-exclamation-triangle"></i> ${errorMsg}
                <button type="button" class="close-btn">&times;</button>
            </div>
        </c:if>

        <c:if test="${not empty successMsg}">
            <div class="alert alert-success">
                <i class="fas fa-check-circle"></i> ${successMsg}
                <button type="button" class="close-btn">&times;</button>
            </div>
        </c:if>

        <%-- Cart Content --%>
        <c:choose>
            <c:when test="${empty cartItems}">
                <%-- Empty Cart --%>
                <div class="empty-cart">
                    <i class="fas fa-shopping-cart"></i>
                    <h4>Your cart is empty</h4>
                    <p class="mb-4">Add your favorite cars to the cart to experience them!</p>
                    <a href="home.jsp" class="btn primary-btn">
                        <i class="fas fa-car"></i> View Cars Now
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="cart-layout">
                    <%-- Cart Items List --%>
                    <div class="cart-items-list">
                        <c:forEach var="item" items="${cartItems}">
                            <div class="cart-item">
                                <div class="item-image-wrapper">
                                    <c:choose>
                                        <c:when test="${not empty item.car.images}">
                                            <img src="${item.car.images[0].imageUrl}"
                                                 alt="${item.car.carModel.name}"
                                                 class="car-image">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="car-image default-image-placeholder">
                                                <i class="fas fa-car"></i>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="item-details">
                                    <h3 class="item-title">${item.car.carModel.name}</h3>
                                    <div class="item-meta">
                                        <span><i class="fas fa-palette"></i> ${item.car.carModel.color.name}</span>
                                        <span><i class="fas fa-calendar"></i> ${item.car.carModel.year}</span>
                                        <span><i class="fas fa-cog"></i> ${item.car.carModel.engineType.name}</span>
                                    </div>
                                    <div class="item-meta">
                                        <span><i class="fas fa-industry"></i> ${item.car.carModel.manufacture.name}</span>
                                    </div>
                                    <div class="item-price">
                                        <fmt:formatNumber value="${item.car.sellingPrice}"
                                                          type="currency"
                                                          currencySymbol="₫"
                                                          maxFractionDigits="0"/>
                                    </div>
                                </div>
                                <div class="item-actions">
                                    <form method="post" action="cart">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="carId" value="${item.car.id}">
                                        <button type="submit" class="btn delete-btn"
                                                onclick="return confirm('Are you sure you want to remove this car from the cart?')">
                                            <i class="fas fa-trash"></i> Remove
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <%-- Total and Actions --%>
                    <div class="cart-summary">
                        <div class="summary-box">
                            <h2 class="summary-title">
                                <i class="fas fa-calculator"></i> Order Summary
                            </h2>

                            <div class="summary-row">
                                <span>Number of cars:</span>
                                <strong>${itemCount} car(s)</strong>
                            </div>

                            <div class="summary-row total-amount-row">
                                <span class="h4">Total Amount:</span>
                                <strong class="h4 total-price">
                                    <fmt:formatNumber value="${totalAmount}"
                                                      type="currency"
                                                      currencySymbol="₫"
                                                      maxFractionDigits="0"/>
                                </strong>
                            </div>

                            <%-- Consultation Button --%>
                            <form method="post" action="cart" class="form-action-btn">
                                <input type="hidden" name="action" value="consultation">
                                <button type="submit" class="btn primary-btn full-width-btn">
                                    <i class="fas fa-comments"></i> Consult Now
                                </button>
                            </form>

                            <%-- Test Drive Section --%>
                            <div class="test-drive-section">
                                <h3 class="test-drive-title">
                                    <i class="fas fa-car-side"></i> Schedule a Test Drive
                                </h3>
                                <form method="post" action="cart">
                                    <input type="hidden" name="action" value="test_drive">

                                    <div class="form-group">
                                        <label for="testDate">Choose Date:</label>
                                        <input type="date"
                                               class="input-field"
                                               id="testDate"
                                               name="testDate"
                                               min="${pageContext.request.getAttribute('today')}"
                                               required>
                                    </div>

                                    <div class="form-group">
                                        <label for="testTime">Choose Time:</label>
                                        <select class="input-field"
                                                id="testTime"
                                                name="testTime"
                                                required>
                                            <option value="">-- Choose Time --</option>
                                            <option value="09:00">09:00</option>
                                            <option value="10:00">10:00</option>
                                            <option value="11:00">11:00</option>
                                            <option value="14:00">14:00</option>
                                            <option value="15:00">15:00</option>
                                            <option value="16:00">16:00</option>
                                            <option value="17:00">17:00</option>
                                        </select>
                                    </div>

                                    <button type="submit" class="btn secondary-btn full-width-btn">
                                        <i class="fas fa-calendar-check"></i> Schedule Test Drive
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <%-- Footer --%>
    <footer class="site-footer">
        <div class="container text-center">
            <p>&copy; 2025 Car Showroom. All rights reserved.</p>
        </div>
    </footer>

    <script>
        // Set minimum date to today
        document.addEventListener('DOMContentLoaded', function() {
            const today = new Date().toISOString().split('T')[0];
            const testDateInput = document.getElementById('testDate');
            if (testDateInput) {
                testDateInput.setAttribute('min', today);
            }

            // Auto dismiss alerts after 5 seconds
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                const closeBtn = alert.querySelector('.close-btn');
                if (closeBtn) {
                    closeBtn.addEventListener('click', function() {
                        alert.style.display = 'none'; // Simple hide
                    });
                }
                setTimeout(function() {
                    alert.style.opacity = '0';
                    alert.style.transition = 'opacity 0.5s ease-out';
                    setTimeout(() => alert.style.display = 'none', 500);
                }, 5000);
            });
        });
    </script>
</body>
</html>
