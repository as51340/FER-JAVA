<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map"%>

<html>
	<head>
		<style>
			body { background-color: #<%= request.getSession().getAttribute("pickedBgCol") == null ? "FFFFFF": request.getSession().getAttribute("pickedBgCol") %> }
		</style>
	</head>
	<body>
 	<h1>Glasanje za omiljeni bend:</h1>
 	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
 	<ol>
 	<% 	Map<String, String> idNameLink = (Map<String, String>) request.getSession().getAttribute("idNameLink"); 
 		for(String id: idNameLink.keySet()) { %>
 			<% String[] arr = idNameLink.get(id).split("\t"); %>
 			<li><a href="glasanje-glasaj?id=<%= id %>"><%= arr[0] %></a> </li>
 		<% } %>
 	</ol>
	 </body>
</html>