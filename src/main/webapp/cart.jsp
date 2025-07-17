<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Giỏ Hàng - Car Showroom</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .cart-item {
            border: 1px solid #dee2e6;
            border-radius: 10px;
            margin-bottom: 20px;
            transition: box-shadow 0.3s ease;
        }
        .cart-item:hover {
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .car-image {
            width: 200px;
            height: 150px;
            object-fit: cover;
            border-radius: 8px;
        }
        .price {
            color: #dc3545;
            font-weight: bold;
            font-size: 1.2em;
        }
        .total-section {
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: white;
            border-radius: 15px;
        }
        .btn-action {
            border-radius: 25px;
            padding: 10px 25px;
            font-weight: 500;
            transition: all 0.3s ease;
        }
        .btn-consultation {
            background: linear-gradient(45deg, #28a745, #20c997);
            border: none;
            color: white;
        }
        .btn-consultation:hover {
            background: linear-gradient(45deg, #20c997, #17a2b8);
            transform: translateY(-2px);
        }
        .btn-test-drive {
            background: linear-gradient(45deg, #17a2b8, #007bff);
            border: none;
            color: white;
        }
        .btn-test-drive:hover {
            background: linear-gradient(45deg, #007bff, #6f42c1);
            transform: translateY(-2px);
        }
        .empty-cart {
            text-align: center;
            padding: 60px 20px;
            color: #6c757d;
        }
        .empty-cart i {
            font-size: 4rem;
            margin-bottom: 20px;
            color: #dee2e6;
        }
        .datetime-input {
            border-radius: 10px;
            border: 2px solid #e9ecef;
        }
        .datetime-input:focus {
            border-color: #007bff;
            box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25);
        }
    </style>
</head>
<body class="bg-light">
    <!-- Header -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="home.jsp">
                <i class="fas fa-car"></i> Car Showroom
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="home.jsp">
                    <i class="fas fa-home"></i> Trang chủ
                </a>
                <span class="navbar-text text-white">
                    <i class="fas fa-user"></i> ${currentUser.username}
                </span>
            </div>
        </div>
    </nav>

    <div class="container my-4">
        <div class="row">
            <div class="col-12">
                <h2 class="mb-4">
                    <i class="fas fa-shopping-cart text-primary"></i> 
                    Giỏ Hàng Của Bạn
                </h2>

                <!-- Alert Messages -->
                <c:if test="${not empty errorMsg}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-triangle"></i> ${errorMsg}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <c:if test="${not empty successMsg}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="fas fa-check-circle"></i> ${successMsg}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Cart Content -->
                <c:choose>
                    <c:when test="${empty cartItems}">
                        <!-- Empty Cart -->
                        <div class="empty-cart">
                            <i class="fas fa-shopping-cart"></i>
                            <h4>Giỏ hàng của bạn đang trống</h4>
                            <p class="mb-4">Hãy thêm những chiếc xe yêu thích vào giỏ hàng để trải nghiệm!</p>
                            <a href="home.jsp" class="btn btn-primary btn-lg">
                                <i class="fas fa-car"></i> Xem xe ngay
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <!-- Cart Items -->
                        <div class="row">
                            <div class="col-lg-8">
                                <c:forEach var="item" items="${cartItems}">
                                    <div class="cart-item p-3">
                                        <div class="row align-items-center">
                                            <div class="col-md-3">
                                                <c:choose>
                                                    <c:when test="${not empty item.car.images}">
                                                        <img src="${item.car.images[0].imageUrl}" 
                                                             alt="${item.car.carModel.name}" 
                                                             class="car-image">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="car-image bg-light d-flex align-items-center justify-content-center">
                                                            <i class="fas fa-car text-muted" style="font-size: 2rem;"></i>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="col-md-6">
                                                <h5 class="mb-2">${item.car.carModel.name}</h5>
                                                <div class="text-muted mb-2">
                                                    <small>
                                                        <i class="fas fa-palette"></i> ${item.car.carModel.color.name} |
                                                        <i class="fas fa-calendar"></i> ${item.car.carModel.year} |
                                                        <i class="fas fa-cog"></i> ${item.car.carModel.engineType.name}
                                                    </small>
                                                </div>
                                                <div class="text-muted">
                                                    <small>
                                                        <i class="fas fa-industry"></i> ${item.car.carModel.manufacture.name}
                                                    </small>
                                                </div>
                                                <div class="price mt-2">
                                                    <fmt:formatNumber value="${item.car.sellingPrice}" 
                                                                    type="currency" 
                                                                    currencySymbol="₫" 
                                                                    maxFractionDigits="0"/>
                                                </div>
                                            </div>
                                            <div class="col-md-3 text-end">
                                                <form method="post" action="cart" style="display: inline;">
                                                    <input type="hidden" name="action" value="remove">
                                                    <input type="hidden" name="carId" value="${item.car.id}">
                                                    <button type="submit" class="btn btn-outline-danger btn-sm"
                                                            onclick="return confirm('Bạn có chắc muốn xóa xe này khỏi giỏ hàng?')">
                                                        <i class="fas fa-trash"></i> Xóa
                                                    </button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <!-- Total and Actions -->
                            <div class="col-lg-4">
                                <div class="total-section p-4 sticky-top">
                                    <h4 class="mb-4">
                                        <i class="fas fa-calculator"></i> Tổng quan
                                    </h4>
                                    
                                    <div class="d-flex justify-content-between mb-3">
                                        <span>Số lượng xe:</span>
                                        <strong>${itemCount} xe</strong>
                                    </div>
                                    
                                    <div class="d-flex justify-content-between mb-4 pb-3 border-bottom">
                                        <span class="h5">Tổng tiền:</span>
                                        <strong class="h5">
                                            <fmt:formatNumber value="${totalAmount}" 
                                                            type="currency" 
                                                            currencySymbol="₫" 
                                                            maxFractionDigits="0"/>
                                        </strong>
                                    </div>

                                    <!-- Consultation Button -->
                                    <form method="post" action="cart" class="mb-3">
                                        <input type="hidden" name="action" value="consultation">
                                        <button type="submit" class="btn btn-consultation btn-action w-100">
                                            <i class="fas fa-comments"></i> Tư vấn ngay
                                        </button>
                                    </form>

                                    <!-- Test Drive Section -->
                                    <div class="card bg-light">
                                        <div class="card-body">
                                            <h6 class="card-title text-dark">
                                                <i class="fas fa-car-side"></i> Đặt lịch trải nghiệm
                                            </h6>
                                            <form method="post" action="cart">
                                                <input type="hidden" name="action" value="test_drive">
                                                
                                                <div class="mb-3">
                                                    <label for="testDate" class="form-label text-dark">Chọn ngày:</label>
                                                    <input type="date" 
                                                           class="form-control datetime-input" 
                                                           id="testDate" 
                                                           name="testDate" 
                                                           min="${pageContext.request.getAttribute('today')}"
                                                           required>
                                                </div>
                                                
                                                <div class="mb-3">
                                                    <label for="testTime" class="form-label text-dark">Chọn giờ:</label>
                                                    <select class="form-control datetime-input" 
                                                            id="testTime" 
                                                            name="testTime" 
                                                            required>
                                                        <option value="">-- Chọn giờ --</option>
                                                        <option value="09:00">09:00</option>
                                                        <option value="10:00">10:00</option>
                                                        <option value="11:00">11:00</option>
                                                        <option value="14:00">14:00</option>
                                                        <option value="15:00">15:00</option>
                                                        <option value="16:00">16:00</option>
                                                        <option value="17:00">17:00</option>
                                                    </select>
                                                </div>
                                                
                                                <button type="submit" class="btn btn-test-drive btn-action w-100">
                                                    <i class="fas fa-calendar-check"></i> Đặt lịch trải nghiệm
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4 mt-5">
        <div class="container text-center">
            <p>&copy; 2025 Car Showroom. All rights reserved.</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Set minimum date to today
        document.addEventListener('DOMContentLoaded', function() {
            const today = new Date().toISOString().split('T')[0];
            document.getElementById('testDate').setAttribute('min', today);
        });
        
        // Auto dismiss alerts after 5 seconds
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
</body>
</html>