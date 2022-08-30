
<%--
  Created by IntelliJ IDEA.
  User: Arthur
  Date: 8/2/2022
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link href="<c:url value="/resources/style/style-c-m.css" />" rel="stylesheet">
  <link href="<c:url value="/resources/style/style-funds.css" />" rel="stylesheet">
  <link href="<c:url value="/resources/style/style-menu.css" />" rel="stylesheet">
  <script src="<c:url value="/resources/script/jquery-3.6.0.min.js" />"></script>
  <script src="<c:url value="/resources/script/script-cookies.js" />"></script>
  <script src="<c:url value="/resources/script/script-funds.js" />"></script>
  <title>Funds</title>
</head>
<body>

<script>
  jQuery(".funds-page-menu").addClass("active-menu-link");
</script>

<header>

  <div class="cookies">

  </div>

  <nav class="personal-area-menu">
    <div class="personal-area-menu-list">
      <a class="menu-button funds-page-menu" href="funds.jsp"></a>
      <a class="menu-button pa-page-menu" href="personal-area.jsp"></a>
    </div>
  </nav>
  
  <h1>FUNDS</h1>
</header>

<section class="transfer-funds-form">
  <form class="form transfer-funds-form" action="transferFunds" method="post">
      <div class="form-group field">
        <input type="text" class="form-field" placeholder="Amount" name="amount" id='amount' required />
        <label for="amount" class="form-label">Amount</label>
      </div>
      <div class="form-group field">
        <input type="text" class="form-field" placeholder="From (Wallet)" name="from" id='from' required />
        <label for="from" class="form-label">From</label>
      </div>
      <div class="form-group field">
        <input type="text" class="form-field" placeholder="To (wallet)" name="toWallet" id='toWallet' required />
        <label for="toWallet" class="form-label">To (wallet)</label>
      </div>
    <label class="form-button-label">
      <input id="transferFunds" class="button" type="submit" name="transferFunds" value="Transfer"/>
    </label>
  </form>
</section>


<!--
<main>
  <div class="personal-area-info">
    <div><p class="name-icon pa-info-icon"></p><p>Balance </p><p><span id="user-name"></span><span> </span><span id="user-surname"></span></p></div>
    <div><p class="country-icon pa-info-icon"></p><p>Wallet </p><p><span id="user-country"></span></p></div>
    <div><p class="email-icon pa-info-icon"></p><p>Email </p><p><span id="user-email"></span></p></div>
  </div>
</main>

-->

</body>
</html>
