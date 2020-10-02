<%@ page import="hr.fer.zemris.java.hw14.model.Poll"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map"%>
<%@ page import="hr.fer.zemris.java.hw14.model.Data" %>
<%@ page import="java.util.List" %>

<html>
	<head>
		<style>
		</style>
	</head>
	<body>
	<%Poll poll = (Poll) request.getSession().getAttribute("poll"); %>
 	<h1><%= poll.getTitle() %></h1>
 	<p> <%= poll.getMessage()%></p>
 	<ol>
 	<%
 		List<Data> allData = (List<Data>) request.getSession().getAttribute("allData");
 	 		for(Data data: allData) {	%>
 			<li> <a href="glasanje-glasaj?id=<%= data.getId() %>"> <%= data.getName() %> </a> </li>
 		<% }%>
 	</ol>
	 </body>
</html>