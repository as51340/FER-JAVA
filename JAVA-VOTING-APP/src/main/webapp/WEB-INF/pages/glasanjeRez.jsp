<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
 		<style type="text/css">
			table.rez td {text-align: center;}
			body { background-color: #<%= request.getSession().getAttribute("pickedBgCol") == null ? "FFFFFF": request.getSession().getAttribute("pickedBgCol") %> }
		</style>
	</head>
 	<body>
 		<h1>Rezultati glasanja</h1>
 		<p>Ovo su rezultati glasanja.</p>
 		<table border="1" cellspacing="0" class="rez">
 			<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
 			<tbody>
 			 	<% 	Map<String, String> idNameLink = (Map<String, String>) request.getSession().getAttribute("idNameLink"); 
 			 		Map<String, Integer> idVotes = (Map<String, Integer>) request.getSession().getAttribute("idVotes"); 
 			 		int max = -1;
 			 		 for(String id: idVotes.keySet()) { 
 			 			String[] arr = idNameLink.get(id).split("\t"); 
 			 			int votes = idVotes.get(id);
 			 			if(votes > max) {
 			 				max = votes;
 			 			} %>
 			 			<tr><td><%= arr[0] %></td><td><%= votes %></td></tr>		
 			 		<% } %>
	 	</tbody>
		 </table>
 		<h2>Grafički prikaz rezultata</h2>
 		<img alt="Pie-chart" src="/webapp2/glasanje-grafika" width="400" height="400" />

 		<h2>Rezultati u XLS formatu</h2>
 		<p>Rezultati u XLS formatu dostupni su <a href="/webapp2/glasanje-xls">ovdje</a></p>
 		<h2>Razno</h2>
 		<p>Primjeri pjesama pobjedničkih bendova:</p>
		 <ul>
		 <% for(String id: idVotes.keySet()) {
			 if(idVotes.get(id) == max) { %>
				 <li><a href="<%= idNameLink.get(id).split("\t")[1] %>" target="_blank"><%= idNameLink.get(id).split("\t")[0] %></a></li>	 
			 <% }} %>
		 </ul>
	 </body>
</html>
