<%-- 
    Document   : ListBooks
    Created on : Feb 28, 2019, 5:42:05 AM
    Author     : schueler
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="Pojo.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>Insert new Book</title>
    <style media="screen">
    </style>
  </head>
  <body>
    <div class="login-page">
      <div class="form">
        <h1>List Books for user ${username}</h1>
        
        <form class="login-form" method="GET" action="<%= request.getContextPath() %>/ListBooks">
            <input type="text" name="author" placeholder="author"/><br>
            <input type="text" name="title" placeholder="title"/><br>
          <input id="formButton" type="submit" name="Search" value="Search"/>
          <input id="formButton" type="submit" formmethod="post" name="Order" value="Order"/>
          <input id="formButton" type="submit" name="Back" value="Back"/>
          <p>${sessionMessage}</p>
        
        
        <h2>Found books:</h2>
        <table border="1">
            <tr><th>Order</th><th>ID</th><th>Author</th><th>Title</th></tr>
            <c:forEach var="book" items = "${foundBooks}">
                <tr><td><input  type="checkbox" name="ckorder" value="${book.id}"/></td><td><c:out value="${book.title}"></c:out></td></tr>
            </c:forEach>
        </table>
        </form>
        <h2>Ordered books:</h2>
        <table>
            <tr><th>Order</th><th>ID</th><th>Author</th><th>Title</th></tr>
            <c:forEach var="book" items = "${orderedBooks}">
                <tr>
                    <td><c:out value="${book.title}"></c:out></td>
                    <td><c:out value="${book.author}"></c:out></td>
                </tr>
            </c:forEach>
        </table>
      </div>
    </div>


</body>
</html>

