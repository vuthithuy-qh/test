<%@ page import="java.util.List" %>
<%@ page import="model.Car" %>
<%@ page import="model.Feedback" %>
<%@ page import="model.Account" %>
<%@ page import="java.text.NumberFormat" %>
<%--<%@ page import="service.favorite.FavoriteService" %>--%>

<%
    Car car = (Car) request.getAttribute("car");
    List<Feedback> feedbackList = (List<Feedback>) request.getAttribute("feedbackList");
    Account currentUser = (Account) session.getAttribute("currentUser");

    NumberFormat currencyFormat = NumberFormat.getNumberInstance();
    //  <%-- FavoriteService favoriteService = new FavoriteService(); --%>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Car Detail - <% if (car != null) { %><%= car.getCarModel().getName() %><% } else { %>Car Not Found<% } %></title>
    <%-- Nhúng font Poppins --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <%-- Nhúng CSS chung cho header --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
    <%-- Nhúng CSS m?i cho trang chi ti?t xe --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/car-detail.css"/>
    <%-- Nhúng Font Awesome ?? dùng icon --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <%-- Bao g?m header chung --%>
    <jsp:include page="/common/header.jsp"/>

    <div class="container car-detail-page">
        <%-- Ki?m tra car có t?n t?i không --%>
        <% if (car == null) { %>
            <div class="not-found-message">
                <h1>Car not found</h1>
                <p>The requested car does not exist or has been removed.</p>
                <a href="${pageContext.request.contextPath}/cars" class="btn primary-btn">
                    <i class="fas fa-arrow-left"></i> Back to Car List
                </a>
            </div>
        <% } else { %>
            <div class="hero-section">
                <div class="main-image-gallery">
                    <% if (car.getImages() != null && !car.getImages().isEmpty()) { %>
                        <img src="<%= car.getImages().get(0).getImageUrl() %>" alt="<%= car.getCarModel().getName() %>" class="main-car-image">
                        <%-- Có th? thêm gallery ?nh ? ?ây n?u có nhi?u ?nh --%>
                    <% } else { %>
                        <div class="main-car-image default-image-placeholder">
                            <i class="fas fa-car"></i>
                            <span>No Image Available</span>
                        </div>
                    <% } %>
                </div>
                <div class="hero-details">
                    <h1 class="car-name"><%= car.getCarModel().getName() %></h1>
                    <p class="car-tagline">Experience Luxury, Performance, and Innovation.</p>
                    <div class="car-price">
                        <span class="label">Starting From:</span>
                        <span class="price-value">
                            <%= currencyFormat.format(car.getSellingPrice()) %> VND
                        </span>
                    </div>
                    <div class="hero-actions">
                        <%-- Nút Add to Cart --%>
                        <a href="${pageContext.request.contextPath}/addtocart?carId=<%= car.getId() %>" class="btn primary-btn">
                            <i class="fas fa-shopping-cart"></i> Add to Cart
                        </a>
                        <%-- Nút Book a Test Drive (chuy?n h??ng ??n trang gi? hàng) --%>
                        <a href="${pageContext.request.contextPath}/addtocart?carId=<%= car.getId() %>&action=book_test_drive" class="btn secondary-btn">
                            <i class="fas fa-calendar-alt"></i> Book a Test Drive
                        </a>
                    </div>
                </div>
            </div>

            <div class="detail-navigation">
                <button class="nav-item active" data-target="highlights">Highlights</button>
                <button class="nav-item" data-target="design">Design</button>
                <button class="nav-item" data-target="performance">Performance</button>
                <button class="nav-item" data-target="specifications">Specifications</button>
                <button class="nav-item" data-target="feedback">Feedback</button>
            </div>

            <div class="detail-content">
                <div id="highlights" class="content-section active">
                    <h2>Key Highlights</h2>
                    <%
                        String carTypeDescription = (car.getCarModel() != null && car.getCarModel().getCarType() != null && car.getCarModel().getCarType().getDescription() != null)
                                ? car.getCarModel().getCarType().getDescription()
                                : "Discover the essence of modern automotive engineering. This model combines cutting-edge technology with unparalleled comfort and style, setting new benchmarks in its class. From its intuitive infotainment system to its advanced safety features, every detail is crafted for an exceptional driving experience.";
                    %>
                    <p><%= carTypeDescription %></p>
                    <ul>
                        <li><i class="fas fa-check-circle"></i> Advanced Infotainment System with large touchscreen.</li>
                        <li><i class="fas fa-check-circle"></i> Premium Leather Interior with ergonomic seating.</li>
                        <li><i class="fas fa-check-circle"></i> State-of-the-art Driver Assistance Systems.</li>
                        <li><i class="fas fa-check-circle"></i> Efficient and Powerful Engine Options.</li>
                        <li><i class="fas fa-check-circle"></i> Dynamic LED Lighting Signature.</li>
                    </ul>
                </div>

                <div id="design" class="content-section">
                    <h2>Exquisite Design</h2>
                    <%-- CarModel không có thu?c tính description riêng cho thi?t k?.
                         N?u b?n mu?n n?i dung này là ??ng, b?n c?n thêm thu?c tính description
                         vào class CarModel ho?c m?t class liên quan khác.
                         Hi?n t?i, s? dùng placeholder. --%>
                    <p>The design of this vehicle is a testament to sophisticated aesthetics and aerodynamic efficiency. Its sleek lines, bold grille, and sculpted bodywork create a commanding presence on the road. Every curve and contour is meticulously crafted to blend form and function seamlessly. For specific design details, please refer to the model's official brochure.</p>
                    <ul>
                        <li><i class="fas fa-brush"></i> Aerodynamic Body Lines for reduced drag.</li>
                        <li><i class="fas fa-lightbulb"></i> Matrix LED Headlights with dynamic turn signals.</li>
                        <li><i class="fas fa-grip-lines"></i> Signature Grille Design for a distinctive look.</li>
                        <li><i class="fas fa-wheelchair"></i> Sporty Alloy Wheels enhancing overall appeal.</li>
                        <li><i class="fas fa-car-alt"></i> Panoramic Sunroof for an open-air experience.</li>
                    </ul>
                </div>

                <div id="performance" class="content-section">
                    <h2>Dynamic Performance</h2>
                    <%
                        String engineTypeDescription = (car.getCarModel() != null && car.getCarModel().getEngineType() != null && car.getCarModel().getEngineType().getDescription() != null)
                                ? car.getCarModel().getEngineType().getDescription()
                                : "Engineered for thrilling performance and responsive handling, this car delivers an exhilarating driving experience. Its finely tuned suspension and precise steering ensure confidence and control in every turn. Whether on the highway or winding roads, power is always at your command.";
                    %>
                    <p><%= engineTypeDescription %></p>
                    <ul>
                        <li><i class="fas fa-tachometer-alt"></i> Rapid Acceleration from 0-100 km/h in X seconds.</li>
                        <li><i class="fas fa-gas-pump"></i> Optimized Fuel Efficiency for long journeys.</li>
                        <li><i class="fas fa-cogs"></i> Smooth Automatic Transmission.</li>
                        <li><i class="fas fa-road"></i> Adaptive Suspension for superior ride comfort.</li>
                        <li><i class="fas fa-bolt"></i> Responsive Braking System.</li>
                    </ul>
                </div>

                <div id="specifications" class="content-section">
                    <h2>Technical Specifications</h2>
                    <div class="specs-grid">
                        <div class="spec-item"><strong>Model:</strong> <%= car.getCarModel().getName() %></div>
                        <div class="spec-item"><strong>Manufacture:</strong> <%= car.getCarModel().getManufacture().getName() %></div>
                        <div class="spec-item"><strong>Type:</strong> <%= car.getCarModel().getCarType().getName() %></div>
                        <div class="spec-item"><strong>Year:</strong> <%= car.getCarModel().getYear() %></div>
                        <div class="spec-item"><strong>Color:</strong> <%= car.getCarModel().getColor().getName() %></div>
                        <div class="spec-item"><strong>Engine Type:</strong> <%= car.getCarModel().getEngineType().getName() %></div>
                        <div class="spec-item"><strong>Price:</strong> <%= currencyFormat.format(car.getSellingPrice()) %> VN?</div>
                        <%-- Thêm các thông s? k? thu?t khác n?u có trong CarModel ho?c các class liên quan --%>
                        <div class="spec-item"><strong>Fuel Type:</strong> <%= car.getCarModel().getEngineType().getFuelType() != null ? car.getCarModel().getEngineType().getFuelType() : "N/A" %></div>
                        <div class="spec-item"><strong>Horsepower:</strong> <%= car.getCarModel().getEngineType().getHorsepower() != null ? car.getCarModel().getEngineType().getHorsepower() + " HP" : "N/A" %></div>
                        <div class="spec-item"><strong>Dimensions:</strong> 4762 x 1847 x 1438 mm</div> <%-- Placeholder, c?n d? li?u th?c t? --%>
                        <div class="spec-item"><strong>Fuel Tank Capacity:</strong> 60 Liters</div> <%-- Placeholder, c?n d? li?u th?c t? --%>
                        <div class="spec-item"><strong>Seating Capacity:</strong> 5</div> <%-- Placeholder, c?n d? li?u th?c t? --%>
                    </div>
                    <p class="specs-note">Note: Specifications may vary based on trim and region.</p>
                </div>

                <div id="feedback" class="content-section">
                    <h2>Customer Feedback</h2>
                    <div class="feedback-list">
                        <% if (feedbackList != null && !feedbackList.isEmpty()) { %>
                            <% for (Feedback fb : feedbackList) { %>
                                <div class="feedback-item">
                                    <p class="feedback-author"><strong><%= fb.getAccount().getUsername() %></strong> (Rating: <%= fb.getRating() %>/5)</p>
                                    <div class="feedback-stars">
                                        <% for (int i = 1; i <= 5; i++) { %>
                                            <i class="fas fa-star <% if (i <= fb.getRating()) { %>filled<% } %>"></i>
                                        <% } %>
                                    </div>
                                    <p class="feedback-comment"><%= fb.getContent() %></p>
                                </div>
                            <% } %>
                        <% } else { %>
                            <p class="no-feedback">No feedback yet. Be the first to share your thoughts!</p>
                        <% } %>
                    </div>

                    <div class="leave-feedback-section">
                        <h3>Leave Your Feedback</h3>
                        <% if (currentUser != null) { %>
                            <form method="post" action="car-detail">
                                <input type="hidden" name="action" value="add_feedback">
                                <input type="hidden" name="carId" value="<%= car.getId() %>">
                                <div class="form-group">
                                    <label for="feedbackComment">Your Comment:</label>
                                    <textarea id="feedbackComment" name="comment" rows="4" class="input-field" placeholder="Share your experience..." required></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="feedbackRating">Rating:</label>
                                    <select id="feedbackRating" name="rating" class="input-field">
                                        <% for (int i = 5; i >= 1; i--) { %>
                                            <option value="<%= i %>" <% if (i == 5) { %>selected<% } %>><%= i %> Stars</option>
                                        <% } %>
                                    </select>
                                </div>
                                <button type="submit" class="btn primary-btn">Submit Feedback</button>
                            </form>
                        <% } else { %>
                            <p class="login-prompt"><a href="login.jsp">Login</a> to leave feedback.</p>
                        <% } %>
                    </div>
                </div>
            </div>
        <% } %> <%-- End of if (car == null) else block --%>
    </div>

    <%-- Footer --%>
    <footer class="site-footer">
        <div class="container text-center">
            <p>&copy; 2025 Car Showroom. All rights reserved.</p>
        </div>
    </footer>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const navItems = document.querySelectorAll('.detail-navigation .nav-item');
            const contentSections = document.querySelectorAll('.detail-content .content-section');

            // Set initial active state for content section
            const initialActiveNav = document.querySelector('.detail-navigation .nav-item.active');
            if (initialActiveNav) {
                const initialTargetId = initialActiveNav.dataset.target;
                const initialTargetSection = document.getElementById(initialTargetId);
                if (initialTargetSection) {
                    initialTargetSection.classList.add('active');
                }
            }


            navItems.forEach(item => {
                item.addEventListener('click', function() {
                    // Remove active class from all nav items
                    navItems.forEach(nav => nav.classList.remove('active'));
                    // Add active class to clicked nav item
                    this.classList.add('active');

                    // Hide all content sections
                    contentSections.forEach(section => section.classList.remove('active'));

                    // Show the target content section
                    const targetId = this.dataset.target;
                    const targetSection = document.getElementById(targetId);
                    if (targetSection) {
                        targetSection.classList.add('active');
                    }
                });
            });

            // G? b? x? lý JavaScript cho nút "Book a Test Drive" vì nó s? chuy?n h??ng URL
            // const bookTestDriveBtn = document.getElementById('bookTestDriveBtn');
            // if (bookTestDriveBtn) {
            //     bookTestDriveBtn.addEventListener('click', function(e) {
            //         e.preventDefault();
            //         // Activate the feedback tab
            //         navItems.forEach(nav => nav.classList.remove('active'));
            //         const feedbackNavItem = document.querySelector('.nav-item[data-target="feedback"]');
            //         if (feedbackNavItem) {
            //             feedbackNavItem.classList.add('active');
            //         }
            //         contentSections.forEach(section => section.classList.remove('active'));
            //         const feedbackSection = document.getElementById('feedback');
            //         if (feedbackSection) {
            //             feedbackSection.classList.add('active');
            //             feedbackSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
            //         }
            //     });
            // }

            // Set minimum date to today for test drive form (n?u b?n v?n mu?n form này trên trang cart)
            const testDateInput = document.getElementById('testDate');
            if (testDateInput) {
                const today = new Date().toISOString().split('T')[0];
                testDateInput.setAttribute('min', today);
            }
        });
    </script>
</body>
</html>
