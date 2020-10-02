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
			#headline {
				float: right;
				width: 500px;
				height: 250px;
			}
			#logo {
				display: inline-block;
				height: 100px;
			}
			#story {
				float: right;
				width: 500px;
				height: 250px; 	
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
		<form method="POST" id="form">
			<fieldset>
				<legend>Create new entry</legend>
				Title:<input type="text" name="entryTitle"> <br>
				<input type="submit" value="Submit">
				<input type="hidden" name="pageName" value="newEntryPage">
			</fieldset>
		</form>
		
		<textarea rows="10" cols="50" name="entryText" form="form" placeholder="Enter text here..."></textarea>
		<a href="https://www.enago.com/academy/write-irresistible-research-paper-title/">
		<img id="headline" src="<%= request.getContextPath() %>/images/headline.jpg"></a>
		<a href="https://hbr.org/2005/01/whats-your-story"><img id="story" src="<%= request.getContextPath() %>/images/story.jpg"></a>	
		
	</body>
</html>