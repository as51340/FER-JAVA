<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<style>
			body {
				margin: 0px; 
			}
			* {
				box-sizing: border-box;
			}
			.headerDiv {
				display: flex;
				justify-content: space-between;	
				align-items: center;
				background-color: #DCDCDC; 
				margin-top: 0px
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
			#logo {
				display: inline-block;
				height: 100px;
			}
			.top2 {
				background-color: #333;
				overflow: hidden;
				display: flex;
				justify-content: space-between;
				margin-bottom: 0px;
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
			#register {
				text-decoration: none;
				font-size: 20px;
				font-style: italic;
				font-weight: lighter;
				font-stretch: expanded;
			}
			.sidebar {
  				margin: 0px;
 				padding: 0px;
  				width: 250px;
  				background-color: #BFE3A8;
  				position: fixed;
  				height: 100%;
  				overflow: auto;
  				right: 0px;
  				
			}
			.sidebar a {
  				display: block;
  				color: #2060A8;
  				padding: 16px;
  				text-decoration: none;
  				font-size: 25px;
			}
			.users  a {
				text-decoration: none;
				font-size: 20px;
			}
			.users a:hover {
				opacity: 0.5;
			}
			#corona {
				display: inline-block;
				position: fixed;
				left: 20%;
				top: 18%;
				width: 65.3%;
				height: 82%;
			}
			#form {
				margin-top: 20px;
				font-size: 25px;	
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
		<div class="sidebar">
			<h2>Read about corona from scientists</h2>
			<a href="https://vijesti.hrt.hr/617441/ikic-drugi-val-je-realnost-ali-sada-imamo-prednost">Prof. dr. sc. Ivan Đikić</a>
			<a href="https://www.total-croatia-news.com/lifestyle/42957-igor-rudan">Prof. dr. sc. Igor Rudan</a>
			<a href="https://www.theguardian.com/world/2020/mar/22/coronavirus-meet-the-scientists-who-are-now-household-names">Foreign scientists</a>
			<a href="https://vijesti.hrt.hr/625909/alemka-markotic-u-dnevniku-htv-a">Prof. dr. sc. Alemka Markotić</a>
		</div>
		<% if(logged == false) { %>
			<form id ="form" method="POST">
				<fieldset style="width: 300px">
					<legend>Login form</legend>
					Nick: <input type="text" name="nickLogin" value="<%= request.getAttribute("text") == null ? "": request.getAttribute("text") %>"/> <br><br>
					Password: <input type="password" name="passLogin"> <br><br>
					<input type="submit" value="SUBMIT">
				</fieldset>
		</form>
		<% } %>
		<a id="register" href="/blog/servleti/register">New users can register here</a>
		<h2>See all threads started from user</h2>
		<ol class="users">
			<% List<BlogUser> users = (List<BlogUser>) request.getAttribute("users");
				for(BlogUser user: users) {%>
					<li> <a href="/blog/servleti/author/<%= user.getNick()%>"> <%= user.getFirstName() + " " + user.getLastName() %> </a>
				<% } %>
		</ol>
		<img id="corona" src="<%= request.getContextPath() %>/images/corona.jpg">
	</body>

</html>
