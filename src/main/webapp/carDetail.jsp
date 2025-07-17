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
//   <%-- FavoriteService favoriteService = new FavoriteService();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Car Detail</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/> 
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/common/header.jsp"/>

        <%-- Ki?m tra car có t?n t?i không --%>
        <% if (car == null) { %>
        <h1>Car not found</h1>
        <p>The requested car does not exist or has been removed.</p>
        <% } else { %>
        <h1>Car Detail - <%= car.getCarModel().getName() %></h1>
        <p>Price: <%= currencyFormat.format(car.getSellingPrice()) %></p>
        <p>Manufacture: <%= car.getCarModel().getManufacture().getName() %></p>
        <p>Year: <%= car.getCarModel().getYear() %></p>

        <a href="${pageContext.request.contextPath}/add-to-cart?carId=${car.id}" class="add-to-cart-btn">
            Add to cart
        </a>
            


        <hr>
        <h2>Feedbacks</h2>
        <% if (feedbackList != null && !feedbackList.isEmpty()) { 
            for (Feedback fb : feedbackList) { %>
        <div>
            <strong><%= fb.getAccount().getUsername() %></strong>: <%= fb.getContent() %>
            (Rating: <%= fb.getRating() %>)
        </div>
        <% }
        } else { %>
        <p>No feedback yet.</p>
        <% } %>

        <% if (currentUser != null) { %>
        <h3>Leave Feedback</h3>
        <form action="addFeedback" method="post">
            <input type="hidden" name="carId" value="<%= car.getId() %>">
            <textarea name="content" required></textarea>
            <select name="rating">
                <% for (int i = 1; i <= 5; i++) { %>
                <option value="<%= i %>" <%= i == 5 ? "selected" : "" %>><%= i %></option>
                <% } %>
            </select>
            <button type="submit">Submit</button>
        </form>
        <% } else { %>
<p><a href="login.jsp">Login</a> to leave feedback.</p>
        <% } %>
        <% } %>
    </body>
</html>