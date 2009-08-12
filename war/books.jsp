<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="com.google.appengine.api.users.*" %>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>Kousen IT Recommmended Books</title>
    <link rel="StyleSheet" href="StenliStyle.css" type="text/css"></link>
  </head>

  <body>
    <h1><a href="http://www.kousenit.com"><img alt="Kousen IT logo" src="KousenIT_LoRes.jpg" align="middle" width="200px"/></a>Recommended Books</h1>
    
    <% UserService us = UserServiceFactory.getUserService(); %>
	
    <table cellspacing="1">
    	<thead>
      <tr>
      	<th>Book</th>
      	<th>Info</th>
        <th>Comments</th>        
      </tr>
      	</thead>
      	<tbody>
      <c:forEach items="${books}" var="book">
      	<tr>
        	<td><a href="${book.detailPageURL}"><img alt="${book.title} picture" src="${book.mediumImageURL}"></a></td>
        	<td>
        		<ul>
        			<li>ISBN: ${book.asin}</li>
        			<li style="font-weight: bold;"><a href="${book.detailPageURL}">${book.title}</a></li>
        			<li style="font-style: italic;">${book.author}</li>
        			<li>${book.formattedPrice}</li>
        		</ul>
        	</td>
        	<td>${book.recommendation}</td>
        	<% if (us.isUserLoggedIn() &&  us.isUserAdmin()) { %>
        	<td><form action="removebook" method="post">
        			<input type="hidden" name="id" value="${book.id}"/>
        			<input type="submit" value="Remove" />
        		</form></td>
        	<% } %>
      	</tr>
      </c:forEach>
      </tbody>
    </table>
	<% if (us.isUserLoggedIn()) {
    %>
    <p><a href="<%= us.createLogoutURL("/listbooks") %>">Logout</a></p>
    <% } else { %>
    <p><a href="<%= us.createLoginURL("/listbooks") %>">Login</a></p>
    <% } %>
    <% if (us.isUserLoggedIn() && us.isUserAdmin()) { %>
	    <a href="addbook.html">Add a book</a>
    <% } %>
  </body>
</html>
