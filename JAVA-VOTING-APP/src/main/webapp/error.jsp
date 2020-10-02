<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<style>
			body { background-color: #<%= request.getSession().getAttribute("pickedBgCol") == null ? "FFFFFF": request.getSession().getAttribute("pickedBgCol") %>;
					font-size: 1.5rem;
			}
			
		</style>
	</head>
	
	<body>
		<p> You didn't provide valid parameters. <br/>
			Parameters a and b should be between -100 and 100, n between 1 and 5</p>
	</body>
</html>