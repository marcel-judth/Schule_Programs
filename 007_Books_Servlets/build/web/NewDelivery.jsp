<%-- 
    Document   : NewBook
    Created on : Mar 3, 2019, 2:59:16 AM
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
          <h1>New Delivery</h1>
          <h2>Found books:</h2>
        
        <form class="login-form" method="post" action="<%= request.getContextPath() %>/NewDelivery">
            username: <input type="text" name="username" placeholder="username"/><br>
            date of delivery: <input type="date" name="dateDelivery" placeholder="date of delivery"/><br>
            <input id="formButton" type="submit" name="list" value="List"/>
            <input id="formButton" type="submit" name="newDelivery" value="New Delivery"/>
            <input id="formButton" formaction="Login" formmethod="get" type="submit" name="back" value="back"/>

            <p>${sessionMessage}</p>
            <table border="1">
                <tr><th>Order</th><th>ID</th><th>Author</th><th>Title</th></tr>
                <c:forEach var="book" items = "${orderedBooks}">
                    <tr><td><input  type="checkbox" name="ckorder" value="${book.id}"/></td><td><c:out value="${book.title}"></c:out></td></tr>
                </c:forEach>
            </table>
        </form>
       </div>       
    </div>
  </body>
</html>


