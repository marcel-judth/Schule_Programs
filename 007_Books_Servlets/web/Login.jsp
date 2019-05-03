<%-- 
    Document   : Login
    Created on : Feb 28, 2019, 4:48:38 AM
    Author     : schueler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title></title>
  </head>
  <body>
    <div class="login-page">
      <div class="form">
        <form class="register-form">
        </form>
          <form class="login-form" method="post" action="<%= request.getContextPath() %>/Login"> <br>
          <input type="text" name="username" placeholder="username"/> <br>
          <input type="password" name="password" placeholder="password"/> <br>
          <input id="formButton"  type="submit" name="login" value="login" />
          
          
          <input id="formButton" formaction="ListBooks" formmethod="get" type="submit" name="listBooks" value="List Books" ${loginOK}/>
          <input id="formButton" formaction="NewBook" formmethod="get" type="submit" name="newBook" value="New Book" ${admin}/>
          <input id="formButton" formaction="NewUser" formmethod="get" type="submit" name="newUser" value="New User" ${admin}/>
          <input id="formButton" formaction="NewDelivery" formmethod="get" type="submit" name="newDelivery" value="New Delivery" ${admin}/>
          <input id="formButton" formaction="listDeliveries" formmethod="get" type="submit" name="allDelivery" value="All Delivery"  ${loginOK}/>
          <p>${sessionMessage}</p>
        </form>
      </div>
    </div>
  </body>
</html>

