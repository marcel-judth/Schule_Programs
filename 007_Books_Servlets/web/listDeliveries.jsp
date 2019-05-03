<%-- 
    Document   : listDeliveries
    Created on : Feb 28, 2019, 5:42:05 AM
    Author     : schueler
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="Pojo.Delivery"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>List Deliveries</title>
    <style media="screen">
    </style>
  </head>
  <body>
    <div class="login-page">
      <div class="form">        
        <table border = "1">
            <tr><th>username</th><th>date of Deliver</th><th>amount</th></tr>
            <c:forEach var="delivery" items = "${alldeliveries}">
                <tr>
                    <td><c:out value="${delivery.username}"></c:out></td>
                    <td><c:out value="${delivery.deliverdate}"></c:out></td>
                    <td><c:out value="${delivery.delTotalPrice}"></c:out></td>
                </tr>
            </c:forEach>
        </table>
      </div>
    </div>


</body>
</html>

