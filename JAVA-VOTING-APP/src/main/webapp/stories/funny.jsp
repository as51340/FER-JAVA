<%@page import="java.awt.Color"%>
<%@page import="java.util.Random"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%!	private String getRandomColor() {
	String[] colors = {"#FFF000", "#FF00FF", "#0000FF", "#00FF00", "#00FFFF", "#FFFF00", "#000FFF", "#F0F0F0", "#000000", "#0F0F0F", "#F0F8FF",
			"#FAEBD7", "#A52A2A", "##B8860B", "#696969"};
	Random random = new Random();
	return colors[Math.abs(random.nextInt() % 6)];
} %>

<html>
	<head>
		<style>
			body {color: <%= getRandomColor() %>;
				  background-color: #<%= request.getSession().getAttribute("pickedBgCol") == null ? "FFFFFF" :request.getSession().getAttribute("pickedBgCol") %>;
				 }
		</style>
	</head>
	<body>
		<h1>Common sense</h1>
		<p> 
			During the heat of the space race in the 1960's, NASA quickly discovered that ballpoint pens <br/>
			would not work in the zero gravity confines of its space capsules. After considerable research and development,  <br/>
			the Astronaut Pen was developed at a cost of $1 million. The pen worked in zero gravity, upside down, <br/>
			underwater, on almost any surface including glass and also enjoyed some modest success as a novelty item back here on earth. <br/>
			The Soviet Union, when faced with the same problem, used a pencil. <br/>
		</p>
	</body>
</html>



