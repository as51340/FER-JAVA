<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<!DOCTYPE html>

<html>
<head>
	<style>
		body { background-color: #<%= request.getSession().getAttribute("pickedBgCol") == null ? "FFFFFF": request.getSession().getAttribute("pickedBgCol") %>}
		nav  a{
			text-decoration: none;
			border: 1px dotted blue;
		}
		nav > a:hover {
			color: #FF00FF;
		}
		.return {
			padding: 10px;
			border: 2px solid black;
			background-color: #FFFFFF;
		}
		.return:hover {
			color: #FF00FF
		}
	</style>
</head>

	<body>
		<nav>
			<a href="/webapp2/setcolor?pickedBgCol=FFFFFF">WHITE </a> <br/>
			<a href="/webapp2/setcolor?pickedBgCol=FF0000">RED</a> <br/>
			<a href="/webapp2/setcolor?pickedBgCol=00FF00">GREEN</a> <br/>
			<a href="/webapp2/setcolor?pickedBgCol=00FFFF">CYAN</a> <br/>
			<p>
				<a href="/webapp2/index.jsp" class="return">BACK</a> 
			</p>
		</nav>
	</body>
</html>