<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<html>
	<head>
		<style>
			td { border: 2px solid black }
			body {background-color: #<%= request.getSession().getAttribute("pickedBgCol") == null ? "FFFFFF" : request.getSession().getAttribute("pickedBgCol") %> }
			.return {
				padding: 10px;
				background-color: #FFFFFF;
				text-decoration: none;
				border: 2px solid black;
			}
			.return:hover {
				color: #FF00FF
			}
		</style>
	</head>
	
	<body>
		<table>
			<thead>
				<tr> <td> Value </td> <td> Sinus </td> <td> Cosinus </td> </tr>
			</thead>
			<tbody>
				<%
				List<Double> listSin = (List<Double>) request.getSession().getAttribute("dataSin");
				List<Double> listCos = (List<Double>) request.getSession().getAttribute("dataCos");
				List<Integer> values = (List<Integer>) request.getSession().getAttribute("dataValues");
				int size = listSin.size();
				for(int i = 0; i < size; i++) { %>
					<tr> <td> <%= values.get(i) %></td> <td> <%= listSin.get(i) %> </td> <td> <%= listCos.get(i) %> </td> </tr>
				<% } %>
			</tbody>
		</table>
		<br/>
		<a href="/webapp2/index.jsp" class="return">BACK</a>
	</body>
</html>