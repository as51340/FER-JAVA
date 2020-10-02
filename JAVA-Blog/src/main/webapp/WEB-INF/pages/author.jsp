<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>

<html>

	<head>
		<style>
			body {
				margin: 0px;
			}
			.headerDiv {
				display: flex;
				justify-content: space-between;	
				align-items: center;
				background-color: #DCDCDC; 
			}
			#yourBlog {
				color: green;
				font-weight: bold;
				font-family: sans-serif;
				font-stretch: ultra-condensed;
				font-size: 40px;
				margin-bottom: 15px;
				text-decoration: none;
			}
			#someNote {
				float: right;
				font-weight: bold;
				font-family: sans-serif;
				font-stretch: ultra-expanded;
			}
			.top2 {
				background-color: #333;
				overflow: hidden;
				display: flex;
				justify-content: space-between;
				margin-bottom: 30px;
			}
			.top2left > a {
				float: left;
				color: #f2f2f2;
  				text-align: center;
  				padding: 14px 16px;
  				text-decoration: none;
  				font-size: 17px;
			}
			.top2right > a {
				float: left;
				color: #f2f2f2;
  				text-align: center;
  				padding: 14px 16px;
  				text-decoration: none;
  				font-size: 17px;
			}
			#new {
				padding: 15px;
				background-color: #F9F6DD;
				color: 	##669D68;
				text-decoration: none;
				font-size: 28px;

			}
			#list {
				margin-right: 150px;
				float:right;
				list-style-type: square;
			}
			#list a {
				text-decoration: none;
				font-size: 28px;
				background-color: #D3D3D3 ;
				
			}
			
			#list a:hover{
				background-color: black;
				color: white;	
			}
			#logo {
				display: inline-block;
				height: 100px;
			}
			#conver {
				position: fixed;
				left: 25%;
			}
		</style>
	</head>
	
	<body>
		<% boolean logged = false; 
			String firstName = null;
			String lastName = null;
			String nick = null;
			String id = null;%>
		<header>
			<div class="headerDiv">
				<a id="yourBlog" href="/blog/servleti/main">CoronaBlog.com</a>
				<img id="logo" src="<%= request.getContextPath() %>/images/logo.jpg" alt="Logo">
				<div id="rightTop">
					<h2 id="someNote">Let everybody see what you think about corona</h2>
				</div>
			</div>
			<div class=top2>
				<div class="top2left">
					<a href="/blog/servleti/main">Home</a>
					<a href="#">About</a>
					<a href="#">Contact</a>
					<a href="https://www.who.int/emergencies/diseases/novel-coronavirus-2019/events-as-they-happen">Corona update</a>
				</div>
				<div class="top2right">
					<% Object objID =  request.getSession().getAttribute("current.user.id"); 	
					if(objID == null) { %>
						<a href="#">Not logged in</a>
					<%}else {
						logged = true;
						firstName = (String)request.getSession().getAttribute("current.user.fn");
						lastName = (String) request.getSession().getAttribute("current.user.ln"); 
						nick = (String) request.getSession().getAttribute("current.user.nick");
						id = ((Long) objID).toString();%>
						<a href="/blog/servleti/author/<%=nick%>"><%= firstName + " " + lastName %></a>	
						<a href="/blog/servleti/logout">Logout</a>
					<% } %>
				</div>
			</div>
		</header>
		<% if(logged == true) {%>
				<a id="new" href="/blog/servleti/author/<%= nick %>/new">Create new entry</a> <br>
		<% } %>
		<% List<BlogEntry> entries = (List<BlogEntry>) request.getAttribute("entries"); %>
		
		<ul id="list">
			<% for(BlogEntry entry: entries) { %>
				<li><a href="/blog/servleti/author/<%= (String)request.getAttribute("nick")%>/<%=entry.getId() %>"><%= entry.getTitle() %></a></li>
			<% } %>
		</ul>
		<img id="conver" src="<%= request.getContextPath() %>/images/conver.jpg">
	</body>

</html>