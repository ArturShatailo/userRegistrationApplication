<%--
  Created by IntelliJ IDEA.
  User: Arthur
  Date: 8/5/2022
  Time: 12:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/style/style-c-m.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/style/style-admin.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/style/style-menu.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/script/jquery-3.6.0.min.js" />"></script>
    <script src="<c:url value="/resources/script/script-cookies.js" />"></script>
    <script src="<c:url value="/resources/script/script-admin-users.js" />"></script>
    <title>Users admin page</title>
</head>
<body>

<header>

    <div class="cookies">

    </div>

    <nav class="menu">
        <div class="menu-list">
            <a class="menu-button page-menu active-menu-link" href="admin-users.jsp">Users</a>
            <a class="menu-button page-menu" href="admin-transfer-requests.jsp">Transfer requests</a>
        </div>
    </nav>

    <h1>USERS ADMIN AREA</h1>
</header>

<main>
    <section class="search-section">
        <input id="search-value" class="search-value" type="email" name="search-value" placeholder="Value to search">
        <button class="button" id="search-button">
            Search
        </button>
    </section>
    <section class="display-section">

        <div class="display-scroll-wrapper">
            <div class="table-titles-section">
                <div>
                    <p>Name</p>
                    <p>Surname</p>
                    <p>Country</p>
                    <p>Email</p>
                    <p>Password</p>
                    <p>Action</p>
                </div>
            </div>
            <div class="display-users-section content-table-inner"></div>
        </div>

    </section>

</main>

</body>
</html>
