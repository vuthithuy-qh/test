<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Ký Tài Khoản</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="css/register.css">
    </head>
    <body>
        <div class="register-wrapper">
            <div class="image-display">
                <img src="https://thanhnien.mediacdn.vn/Uploaded/dieutrangqc/2022_11_15/5-series-3834.jpg" class="car-bg-image main-car-image">
                <!--                <img src="https://media.vov.vn/sites/default/files/styles/large/public/2023-05/519bf8fda1ef7fb126fe.jpg" class="car-bg-image secondary-car-image">-->
                <!--                <img src="https://photo.znews.vn/w660/Uploaded/qfrqy/2015_12_05/16220534961x33072013MercedesBenzVisionGranTurismoconcepte1446483647459.jpg" class="car-bg-image third-car-image">-->
            </div>
            <div class="registration-form-area">
                <h1>Đăng Ký Tài Khoản</h1>
                
                

                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    String successMessage = (String) request.getAttribute("successMessage");
                    if (errorMessage != null) {
                %>
                <p class="message error-message"><%= errorMessage %></p>
                <%
                    } else if (successMessage != null) {
                %>
                <p class="message success-message"><%= successMessage %></p>
                <%
                    }
                %>

                <form action="registerServlet" method="post">
                    <input type="text" placeholder="Tên tài khoản" name="username">
                    <input type="email" placeholder="Email" name="email">
                    <input type="password" placeholder="Mật khẩu" name="password">
                    <input type="password" placeholder="Xác nhận mật khẩu" name="confirmPassword">
                    <button type="submit">Đăng Ký</button>
                </form>
                <p class="login-link">Đã có tài khoản? <a href="login.jsp">Đăng nhập ngay</a></p>
            </div>
        </div>
    </body>
</html>