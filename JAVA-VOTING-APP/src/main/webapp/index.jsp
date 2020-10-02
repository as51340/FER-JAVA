<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> -->

<!DOCTYPE html>

<html>
	<head>
		<style>
			body { background-color: #<%= request.getSession().getAttribute("pickedBgCol") == null ? "FFFFFF": request.getSession().getAttribute("pickedBgCol") %> }
			nav > a {
				text-decoration: none;
			}
			nav > a:hover {
				color: #008080;
				opacity: 1;
				padding: 5px;
				background-color: #F0E68C;
			}
			
}
		</style>
	</head>

	<body>
		<nav>
			<a href="/webapp2/colors.jsp"> Background color chooser</a> <br/>
			<a href="/webapp2/trigonometric?a=0&b=90">Calculate trigonometric values</a>
		</nav>	
		<br/>
		<fieldset>
			<legend>Enter your boundaries</legend>
			<form action="trigonometric" method="GET">
 				Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 				Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 				<input type="submit" value="Tabeliraj">
 				<input type="reset" value="Reset">
			</form>
		</fieldset>
		<br/>
		<nav>
			<a href="/webapp2/stories/funny.jsp">Get funny story</a> <br/>
			<a href="/webapp2/powers?a=1&b=100&n=3" target="_blank">Calculate power</a> <br/>
			<a href="/webapp2/appinfo.jsp">Get informations about app</a>
		</nav>		
	</body>
	
</html>