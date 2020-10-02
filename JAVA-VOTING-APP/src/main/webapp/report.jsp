<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<style>
			.paraf {
				font-size: 1.5rem;
			}
			body {background-color: #<%= request.getSession().getAttribute("pickedBgCol") == null ? "FFFFFF" :request.getSession().getAttribute("pickedBgCol") %>}
		</style>
	</head>
	<body>
		<h1>OS usage</h1>
		<p class="paraf"> <i>Here are the results of OS usage in survey that we completed  </i></p>
		<img alt="OS usage" src="/webapp2/	reportImage"/>	
	</body>
</html>