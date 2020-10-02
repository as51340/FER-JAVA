	<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogComment"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

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
			#text {
				left: 0px;
				font-size: 20px;
			}
			#edit {
				text-decoration: none;
				font-size: 30px;
				font-style: italic;
			}
			.var {
				font-size: 25px;
				font-weight: bolder;
			}
			.comment {
				font-weight: bolder;
				text-align: center;
				font-style: oblique;
			}
			.comment a {
				margin-left: 250px;
				text-decoration: none;
				color: gray;
			}
			.comment a:hover {
				opacity: 0.5;
			}
			#logo {
				display: inline-block;
				height: 100px;
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
		<% BlogEntry entry = (BlogEntry)request.getSession().getAttribute("entry"); %>
		<p id="text"><pre><span class="var">ID:</span> <%= entry.getId() %>			<span class="var">Title:</span> <%= entry.getTitle()%>			<span class="var">Text:</span> <%= entry.getText() %></pre></p>
		<% if(logged == true) { %>	
			<a id="edit" href="/blog/servleti/author/<%= nick %>/edit">Edit your entry</a> <br>
		<% } %>
		
		<% List<BlogComment> comments = (List<BlogComment>)request.getAttribute("comments"); %>
			<% for(BlogComment comment: comments) {%>
				<div class="comment"><%= comment.getMessage() %><a href="/blog/servleti/author/<%= comment.getBlogEntry().getCreator().getNick() %>">BY:<%= comment.getBlogEntry().getCreator().getNick() %></a></div>
			<% } %>
		<% if(logged == true) { %>
		<textarea name="comment" rows="10" cols="50" form ="form" placeholder="Enter your comment..."></textarea>	
		<form method="POST" id="form">
			<input type="hidden" name="entryID" value="<%= id %>">
			<input type="submit" value="Submit">
			<input type="hidden" name="pageName" value="comment">	
		</form>	
		<% } %>
	</body>
	
	
	
</html>