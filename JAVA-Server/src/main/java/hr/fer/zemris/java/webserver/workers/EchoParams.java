package hr.fer.zemris.java.webserver.workers;

import java.nio.charset.Charset;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Simply outputs back to the user the parameters it obtained formatted as HTML table
 * @author Andi Škrgat
 * @version 1.0
 */
public class EchoParams implements IWebWorker{
	

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		context.setCharset(Charset.forName("UTF-8"));
		context.write("<html>\r\n" + 
				"\r\n" + 
				"	<head>\r\n" + 
				"\r\n" + 
				"		<title> Formatted HTML table </title>\r\n" + 
				"		<meta name=\"keywords\" content=\"html, table\"/>\r\n" + 
				"		<style> table,td,th { border: 2px solid black;"
				+ "		border-collapse: collapse;"
				+ "		padding: 10px } </style>" +
				"	</head>\r\n" + 
				"	\r\n" + 
				"	<body>\r\n" + 
				"	\r\n" + 
				"		<table>\r\n" + 
				"			<thead>\r\n" + 
				"				<tr><th>Parameters from URL</th></tr>\r\n" + 
				"			</thrad>\r\n" + 
				"			\r\n" + 
				"			<tbody>");
		context.write("<tr> <td> Name </td> <td> Value </td> </tr>");
		for(String key: context.getParameterNames()) {
			context.write("<tr> <td>" + key + "</td> <td> " + context.getParameter(key) + "</td> </tr>");
		}
		
		context.write("</tbody> </body> </html>");
		context.removeTemporaryParameter("path");
	}
	

}
