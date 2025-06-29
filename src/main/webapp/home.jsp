<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>User</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/home.css" type="text/css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
    </head>
    <body>
        <div class="container">
            
                <jsp:include page="common/header.jsp"/>
            
                <form action="cars" method="get" class="home-search-form">
                    <input type="text" name="keyword" placeholder="Find car"/>
                    <button type="submit">Find car now</button>
                </form>

            <section class="brower-by-type">
                <div class="container2">
                   
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

           



            <footer class="footer">
                <p>&copy; 2025 TTTC company. Bao luu moi quyen</p>
            </footer>

        </div>

    </body>
</html>