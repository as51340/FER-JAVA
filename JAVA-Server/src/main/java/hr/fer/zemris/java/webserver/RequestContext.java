package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents one response generated from server to client
 * @author Andi Škrgat
 * @version 1.0
 */
public class RequestContext {
	
	/**
	 * Stream where response will be sent
	 */
	private OutputStream outputStream;
	
	/**
	 * Charset used for text in response
	 */
	private Charset charset;
	
	/**
	 * Default encoding for response
	 */
	public String encoding = "UTF-8";
	
	/**
	 * Default status code that server will return 
	 */
	public int statusCode = 200;
	
	/**
	 * Default return status text
	 */
	public String statusText = "OK";
	
	/**
	 * Determines type of content in server's response 
	 */
	public String mimeType = "text/html";
	
	/**
	 * Length of some response 
	 */
	public Long contentLength = null;
	
	/**
	 * Parameters for response
	 */
	private Map<String, String> parameters;
	
	/**
	 * Temporary parameters for response
	 */
	private Map<String, String> temporaryParameters;
	
	/**
	 * Persistent parameters for response
	 */
	private Map<String, String> persistentParameters;
	
	/**
	 * List of all cookies
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * Dispatcher for this response
	 */
	private IDispatcher dispatcher;
	
	/**
	 * Flag used for generating header only one time
	 */
	private boolean headerGenerated = false;
	
	/**
	 * Session id
	 */
	private String sid;
	
	/**
	 * Constructor for initializing output stream, parameters(persistent and regulars) and cookies
	 * @param outputStream Stream where response will be sent
	 * @param parameters parameters for response
	 * @param persistentParameters Persistent parameters for response
	 * @param outputCookies  List of all cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String,String> parameters, Map<String,String> persistentParameters, 
			List<RCCookie> outputCookies, String sid) {
		this(outputStream, parameters, persistentParameters, outputCookies, null, null, sid);
	}
	
	/**
	 * Constructor for initializing output stream, parameters(persistent and regulars) and cookies
	 * @param outputStream Stream where response will be sent
	 * @param parameters Parameters for response
	 * @param persistentParameters Persistent parameters for response
	 * @param outputCookies  List of all cookies
	 * @param dispatcher Dispatcher for this response
	 * @param tempParams Temporary parameters for response
	 */
	public RequestContext(OutputStream outputStream, Map<String,String> parameters, Map<String,String> persistentParameters, 
			List<RCCookie> outputCookies, Map<String, String> tempParams, IDispatcher dispatcher, String sid) {
		if(outputStream == null) {
			throw new IllegalArgumentException("Output stream must not be null in request context");
		}
		this.outputStream = outputStream;
		if(parameters == null) {
			this.parameters = new HashMap<String, String>();
		} else {
			this.parameters = parameters;
		}
		if(persistentParameters == null) {
			this.persistentParameters = new HashMap<String, String>();
		} else {
			this.persistentParameters = persistentParameters;
		}
		if(outputCookies == null) {
			this.outputCookies = new ArrayList<RequestContext.RCCookie>();
		} else {
			this.outputCookies = outputCookies;
		}
		if(tempParams == null) {
			this.temporaryParameters = new HashMap<String, String>();
		} else {
			this.temporaryParameters = tempParams;
		}
		this.dispatcher = dispatcher;
		this.sid = sid;
	}
	
	/**
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * @return the outputStream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * @param outputStream the outputStream to set
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * @return the charset
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	/**
	 * @return the headerGenerated
	 */
	public boolean isHeaderGenerated() {
		return headerGenerated;
	}

	/**
	 * @param headerGenerated the headerGenerated to set
	 */
	public void setHeaderGenerated(boolean headerGenerated) {
		this.headerGenerated = headerGenerated;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated == true) {
			throw new RuntimeException("Property can't be changed when header has already been generated");
		}
		this.encoding = encoding;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated == true) {
			throw new RuntimeException("Property can't be changed when header has already been generated");
		}
		this.statusCode = statusCode;
	}

	/**
	 * @param statusText the statusText to set
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated == true) {
			throw new RuntimeException("Property can't be changed when header has already been generated");
		}
		this.statusText = statusText;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated == true) {
			throw new RuntimeException("Property can't be changed when header has already been generated");
		}
		this.mimeType = mimeType;
	}

	/**
	 * @param contentLength the contentLength to set
	 */
	public void setContentLength(Long contentLength) {
		if(headerGenerated == true) {
			throw new RuntimeException("Property can't be changed when header has already been generated");
		}
		this.contentLength = contentLength;
	}

	/**
	 * Cookie on web page
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	public static class RCCookie {
		
		/**
		 * Cookie's name
		 */
		private String name;
		
		/**
		 * Cookie's value
		 */
		private String value;
		
		/**
		 * Cookies' domain
		 */
		private String domain;
		
		/**
		 * Path TODO
		 */
		private String path;
		
		/**
		 * Living time for some cookie
		 */
		private Integer maxAge;
		
		/**
		 * Field so we can set for example http only
		 */
		private String secure;
		
		/**
		 * Initializes properties for some cookie
		 * @param name Cookie's name
		 * @param value Cookie's value
		 * @param domain Cookies' domain
		 * @param path TODO
		 * @param maxAge Living time for some cookie
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, String secure) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.secure = secure;
		}
	}

	/**
	 * @return the temporaryParameters
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	
	/**
	 * @param temporaryParameters the temporaryParameters to set
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}
	

	/**
	 * @return the persistentParameters
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	
	/**
	 * @param persistentParameters the persistentParameters to set
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	
	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}
	
	/**
	 * Method that retrieves value from parameters map (or null if no association exists)
	 * @param name that will be used for searching in parameters map
	 * @return value for given name
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in parameters map (note, this set must be read-only)
	 * @return set in which are all names of parameters
	 */
	public Set<String> getParameterNames() {
		return parameters.keySet();
	}
	
	/**
	 * Method that retrieves value from persistentParameters map (or null if no association exists)
	 * @param name for searching in persistent parameters
	 * @return value from persisten parameters
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * @return all keys from persistent parameters
	 */
	public Set<String> getPersistentParameterNames() {
		return persistentParameters.keySet();
	}

	/**
	 * Method that stores a value to persistentParameters map
	 * @param name key for map
	 * @param value value that will be mapped to key
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Method that removes a value from persistentParameters map
	 * @param name key whose mapping will be removed
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Method that retrieves value from temporaryParameters map (or null if no association exists)
	 * @param name key for temporary parameters
	 * @return mapped value for that key
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * @return all keys for temporary parameters
	 */
	public Set<String> getTemporaryParameterNames() {
		return temporaryParameters.keySet();
	}

	/**
	 * Method that retrieves an identifier which is unique for current user session 
	 * @return empty string 
	 */
	public String getSessionID() {
		return sid;
	}

	/**
	 * Method that stores a value to temporaryParameters map
	 * @param name key that will be inserted 
	 * @param value value that will be mapped to the given name
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporaryParameters map
	 * @param name key whose mapping will remove from temporary map
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Adds cookie to the output list
	 * @param cookie
	 */
	public void addRCCookie(RCCookie cookie) {
		if(headerGenerated == true) {
			throw new RuntimeException("Property can't be changed when header has already been generated");
		}
		outputCookies.add(cookie);
	}
	
	/**
	 * Writes data into outputStream from {@linkplain RequestContext}. If header is not generated first writes header and then data. 
	 * @param data to be written into {@link outputStream}
	 * @return same instance of {@linkplain RequestContext}
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(headerGenerated == false) {
			outputStream.write(writeHeader().getBytes(charset));
		}
		outputStream.write(data);
		outputStream.flush();
		return this;
	}
	
	/**
	 * Writes data into outputStream from {@linkplain RequestContext}. If header is not generated first writes header and then data. 
	 * @param data to be written into {@link outputStream}
	 * @return same instance of {@linkplain RequestContext}
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(headerGenerated == false) {
			outputStream.write(writeHeader().getBytes(charset));
		}
		outputStream.write(data, offset, len);
		outputStream.flush();
		return this;
	}
	
	/**
	 * Writes data into outputStream from {@linkplain RequestContext}. If header is not generated first writes header and then data. 
	 * @param text to be written into {@link outputStream}
	 * @return same instance of {@linkplain RequestContext}
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if(headerGenerated == false) {
			outputStream.write(writeHeader().getBytes(charset));
		}
		byte[] data = text.getBytes(charset);
		outputStream.write(data);
		outputStream.flush();
		return this;
	}
	
	/**
	 * Creates header from data accepted in costructor for response to the client
	 * @return generated header
	 */
	private String writeHeader() {
		headerGenerated = true;
		charset = Charset.forName(encoding);
		String header = "HTTP/1.1 ".concat(Integer.toString(statusCode)).concat(" ").concat(statusText).concat("\r\n");
		header = header.concat("Content-Type: ").concat(mimeType);
		if(mimeType.trim().startsWith("text/")) {
			header = header.concat("; charset=").concat(encoding);
		}
		header = header.concat("\r\n");
		if(contentLength != null) {
			header = header.concat("Content-Length:").concat(contentLength.toString()).concat("\r\n");
		}
		if(outputCookies.size() != 0) {
			for(RCCookie cookie: outputCookies) {
				header = header.concat("Set-Cookie: ").concat(cookie.name).concat("=\"").concat(cookie.value).concat("\"");
				if(cookie.domain != null) {
					header = header.concat("; Domain=").concat(cookie.domain);
				}
				if(cookie.path != null) {
					header = header.concat("; Path=").concat(cookie.path).concat("");
				}
				if(cookie.maxAge != null) {
					header = header.concat("; Max-Age=").concat(cookie.maxAge.toString());	
				}
				header = header.concat("; " + cookie.secure);
				
				header = header.concat("\r\n");
			}
		}
		header = header.concat("\r\n");
		return header;
	}
	
	
	
	
	
}

