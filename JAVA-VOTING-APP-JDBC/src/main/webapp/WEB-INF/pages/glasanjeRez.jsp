<%@ page import="hr.fer.zemris.java.hw14.model.Data"%>
<%@ page import="java.util.TreeSet"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
 		<style type="text/css">
			table.rez td {text-align: center;}
		</style>
	</head>
 	<body>
 		<h1>Rezultati glasanja</h1>
 		<p>Ovo su rezultati glasanja.</p>
 		<table border="1" cellspacing="0" class="rez">
 			<thead><tr><th>Name</th><th>Number of votes</th></tr></thead>
 			<tbody>
 			<%
 				List<Data> allData = (List<Data>) request.getSession().getAttribute("allData");
 			 			for(Data data: allData) { 
 			 			if(data.getVotes() != 0) {
 			%>	
 				<tr> <td> <%=data.getName()%> </td>  <td> <%=data.getVotes()%> </td></tr>
 			<%
 				} }
 			%>
	 		</tbody>
		 </table>
 		<h2>Grafički prikaz rezultata</h2>
 		<img alt="Pie-chart" src="/voting-app/servleti/glasanje-grafika" width="400" height="400" />

 		<h2>Rezultati u XLS formatu</h2>
 		<p>Rezultati u XLS formatu dostupni su <a href="/voting-app/servleti/glasanje-xls">ovdje</a></p>
 		<h2>Razno</h2>
 		<p>Highligts of winners</p>
 		<%
 			List<Data> winners = (List<Data>) request.getSession().getAttribute("winners");
 		%>
		 <ul>
			<%
				for(Data band: winners) {
			%>
				<li><a href="<%= band.getUrl() %>" target="_blank"><%= band.getName() %></a> </li>
			<% }%>
		 </ul>
	 </body>
</html>
