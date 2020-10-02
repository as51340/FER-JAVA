<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.Duration" %>
<%! private String getTime(long timeCreated) { 
		long timeNow = System.currentTimeMillis();
		long mili = timeNow - timeCreated;
		long diff = (timeNow -timeCreated) / 1000;
		long days = Duration.ofSeconds(diff).toDays();
		diff -= days * 24 * 60 * 60;
		long hours = Duration.ofSeconds(diff).toHours();
		diff -= hours * 60 * 60 ;
		long min = Duration.ofSeconds(diff).toMinutes();
		diff -= min * 60;
		long sec = diff;
		long tmp = 0;
		String s = new String();
		if(days > 0) {
			s+= Long.toString(days) + (days==1 ? " day " : " days ");
			tmp += days * 24 * 60 * 60 * 1000;
		}
		if(hours > 0) {
			s+= Long.toString(hours) + (hours == 1 ? " hour " : " hours " );
			tmp += hours * 60 * 60 * 1000;
		}
		if(min > 0) {
			s+= Long.toString(min) +( min == 1 ? " minute " : " minutes ");
			tmp +=  min * 60 * 1000;
		}
		if(sec > 0) {
			s+= Long.toString(sec) + (sec == 1 ? " second ": " seconds ");
			tmp += sec * 1000;
		}
		s += mili -tmp + " miliseconds";
		return s;
} %>
<html>
	<style>
		body {background-color: #<%= request.getSession().getAttribute("pickedBgCol") == null ? "FFFFFF" :request.getSession().getAttribute("pickedBgCol") %>}
		.time {
			font-size: 1.5rem;
		}
	</style>
	<body>
		 <p> Application has been active for <span class="time"> <%= getTime((Long)request.getServletContext().getAttribute("time")) %> </span></p>
	</body>
</html>