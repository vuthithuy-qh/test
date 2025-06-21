<!DOCTYPE html>
<html>
    <head>
        <title>User</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/home.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="logo">
                    <img src="https://i.pinimg.com/736x/b9/d8/38/b9d838225bdb9dfdf924315456623479.jpg" 
                         alt="Logo cong ty">
                </div>

                <div class="brand-option">
                    <ul class="brand">
                        <li class="br"><a href="index.html">LogOut</a></li>
                        <li class="br"><a href="newcar.jsp">NewCar</a></li>
                        <li class="br"><a href="oldCar.jsp">OldCar</a></li>
                        <li class="br"><a href="sellCar.jsp">SellCar</a></li>
                        <li class="br"><a href="contact.jsp">Contact</a></li>

                    </ul>
                </div>

                <div class="login-icon">
                    <a href="customerProfile.jsp"><img src="https://i.pinimg.com/736x/32/36/cf/3236cf9b0c83ebed354164e61a978749.jpg" alt="My account"></a>
                    <p>HELLO ${sessionScope.currentUser.username}!</p>
                </div>
            </div>

            <section class="brower-by-type">
                <div class="container2">
                    <h2>Browser by Type</h2>
                    <div class="type-grid">
                        <div class="type-item">
                            <span class="type-icon"><img src="https://i.pinimg.com/736x/73/1b/3f/731b3f83f906493794819133a91f4f87.jpg"></span>
                            <p>Sedan</p>
                        </div>

                        <div class="type-item">
                            <span class="type-icon"><img src="https://i.pinimg.com/736x/6f/4f/21/6f4f21502b5fbaf9fcb5f100550d3f2e.jpg"></span>
                            <p>Electronics</p>
                        </div>

                        <div class="type-item">
                            <span class="type-icon"><img src="https://i.pinimg.com/736x/4e/63/a5/4e63a5ec518f60211986a726bc1ba129.jpg"></span>
                            <p>SUV</p>
                        </div>


                        <div class="type-item">
                            <span class="type-icon"><img src="https://i.pinimg.com/736x/a4/e1/4c/a4e14c8321b20cf9e5a562279fc6956c.jpg"></span>
                            <p>Hatchback</p>
                        </div>

                        <div class="type-item">
                            <span class="type-icon"><img src="https://i.pinimg.com/736x/c2/6c/38/c26c38eea1c14fd95a358cc3b81804fc.jpg"></span>
                            <p>Coupe</p>
                        </div>

                        <div class="type-item">
                            <span class="type-icon"><img src="https://i.pinimg.com/736x/ab/d6/5b/abd65b841d29b7d58936da3806df5af3.jpg"></span>
                            <p>Sport</p>
                        </div>

                        <div class="type-item">
                            <span class="type-icon"><img src="https://i.pinimg.com/736x/77/de/e5/77dee527e0215e9daa101880926cf6f1.jpg"></span>
                            <p>Van</p>
                        </div>

                        <div class="type-item">
                            <span class="type-icon"><img src="https://i.pinimg.com/736x/41/d7/16/41d7168a9c6820b2f10da3aa959e36cf.jpg"></span>
                            <p>Hybrid</p>
                        </div>

                    </div>

                </div>
            </section>

            <section class="cta-section">
                <div class="container2 cta-container">
                    <div class="cta-box looking-for-car">
                        <h3>Are you looking for a Car?</h3>
                        <p>We are committed to providing our customers with exceptional service.</p>
                        <a href="shopping.jsp" class="btn btn-primary">Get Started <span class="arrow"> -></span></a>
                        <div class="cta-icon">
                            <img src="https://i.pinimg.com/736x/c7/69/db/c769db7c55c7387f9e2352154d20d656.jpg" 
                                 alt="looking for car icon">  
                        </div>
                    </div>

                    <div class="cta-box sell-a-car">
                        <h3>Do you want to sell a car?</h3>
                        <p>We are committed to providing our customers with exceptional service.</p>
                        <a href="selling.jsp" class="btn btn-dark">Get Started <span class="arrow">-></span></a>
                        <div class="cta-icon">
                            <img src="https://i.pinimg.com/736x/c8/c6/9b/c8c69b4d9b112090a9d6e590c8b79c90.jpg" 
                                 alt="sell a car icon"><!-- comment -->
                        </div>
                    </div>

                </div>


            </section>



            <footer class="footer">
                <p>&copy; 2025 TTTC company. Bao luu moi quyen</p>
            </footer>

        </div>

    </body>
</html>