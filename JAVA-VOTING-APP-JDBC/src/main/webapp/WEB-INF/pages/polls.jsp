<%@ page import="hr.fer.zemris.java.hw14.model.Poll"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<style>
			a { text-decoration: none;
				color: #000000;
				font-size: 1.5rem;
			}
			a:hover {
				opacity: 1;
				color: #FF0FF0:
				padding: 3px;
				background-color: #F0E68C;

			}
			body {background-color: #D4D0DB;
				box-sizing: border-box;
			}
			img {
				display: inline;				
				width: 50vw;
				height: 50vh;
				vertical-align: middle;
			}
			
		</style>
	
	</head>
	
	<body>
		<%List<Poll> list = (List<Poll>) request.getSession().getAttribute("polls");
			String[] images = new String[]{"/images/bands.jpg", "/images/players.jpg"};
			int i = 0;
			for(Poll poll: list) { %>
					<div>
					<a href="/voting-app/servleti/glasanje?pollID=<%= poll.getPollId()%>"><img src="<%= request.getContextPath() + images[i++]%>"/></a>
					<a href="/voting-app/servleti/glasanje?pollID=<%= poll.getPollId()%>"><%= poll.getTitle() %></a> 
					</div>		
			 <% }%>
	</body>
</html>

