package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class that implements multithreaded server with functionality for serving static documents but it can also generate dynamic content with the help of 
 * {@linkplain SmartScriptEngine} 
 * @author Andi Škrgat
 * @version 1.0
 */
public class SmartHttpServer {
	
	/**
	 * Address
	 */
	private String address;
	
	/**
	 * Server's domain name
	 */
	private String domainName;
	
	/**
	 * Port on which server listens
	 */
	private int port;
	
	/**
	 * Workers that will do its jobe
	 */
	private int workerThreads;
	
	/**
	 * Determines how much will durate one session
	 */
	private int sessionTimeout;
	
	/**
	 * Mime types-types of documents server can return
	 */
	private Map<String,String> mimeTypes = new HashMap<String, String>();
	
	/**
	 * Server's thread, runs all time
	 */
	private ServerThread serverThread = null;
	
	/**
	 * Pool of threads where one thread will serve one client
	 */
	private ExecutorService threadPool;
	
	/**
	 * Path to web root
	 */
	private Path documentRoot;
	
	/**
	 * Filename in which properties for server are stored
	 */
	private String configFileName;
	
	/**
	 * Map that maps path to the implementation of {@linkplain IWebWorker}
	 */
	private Map<String, IWebWorker> workersMap;
	
	/**
	 * Remembers all sessions
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	
	/**
	 * Random identifier generator
	 */
	private Random sessionRandom = new Random();
	
	/**
	 * Thread pool that periodically goes through all sessions and removes all that are expired(every 5 minutes)
	 */
	private ScheduledExecutorService scheduleThread;
	
	/**
	 * Keeper used for synchronization in obtaining session id because more client cannot simultaneously use map session
	 */
	private Object keeper = new Object();

	/**
	 * Schedules task to be periodically executed 
	 */
	private ScheduledFuture<?> future;

	/**
	 * @param configFileName gets filename in which server properties are stored
	 */
	public SmartHttpServer(String configFileName) {
		this.configFileName = configFileName;
		Properties prop = new Properties();
		try(BufferedReader br = Files.newBufferedReader(Paths.get(configFileName))) {
			prop.load(br);
		} catch(IOException ex) {
			throw new RuntimeException("Cannot read properties for server");
			
		}
		this.address = (String) prop.get("server.address");
		this.domainName = (String) prop.get("server.domainName");
		this.port = Integer.parseInt((String)prop.get("server.port"));
		this.workerThreads = Integer.parseInt((String)prop.get("server.workerThreads"));
		this.documentRoot = Paths.get((String) prop.get("server.documentRoot"));
		this.sessionTimeout = Integer.parseInt((String) prop.get("session.timeout"));
		String configFileMime = (String) prop.get("server.mimeConfig");
		Properties mimeProp = new Properties();
		try(BufferedReader br = Files.newBufferedReader(Paths.get(configFileMime))) {
			mimeProp.load(br);
		} catch(IOException ex) {
			throw new RuntimeException("Cannot read mime properties");
		}
		for(Object key: mimeProp.keySet()) {
			this.mimeTypes.put((String)key, (String) mimeProp.get(key));
		}
		Properties webProp = new Properties();
		String propWorkersFile = (String) prop.get("server.workers");
		try(BufferedReader br = Files.newBufferedReader(Paths.get(propWorkersFile))) {
			webProp.load(br);
		} catch(IOException ex) {
			throw new RuntimeException("Cannot read workers' properties");
		}
		workersMap = new HashMap<String, IWebWorker>();
		Set<String> pathOfWorkers = new HashSet<>();
		for(Object key: webProp.keySet()) {
			String path = (String) key;
			String fqcn = (String)webProp.get(path);
			if(pathOfWorkers.contains(fqcn) == true) {
				throw new RuntimeException("Cannot associate one worker for 2 different paths");
			}
			try {
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				@SuppressWarnings("deprecation")
				Object newObject = referenceToClass.newInstance();
				IWebWorker worker = (IWebWorker) newObject;
				workersMap.put(path, worker);
				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Cannot load worker");
			}
		}
	}
	
	/**
	 * Starts server
	 */
	protected void start() {
	// … start server thread if not already running …
	// … init threadpool by Executors.newFixedThreadPool(...); …
		scheduleThread = Executors.newScheduledThreadPool(1, new MyThreadFactory());
		future = scheduleThread.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				for(String key: sessions.keySet()) {
					if(sessions.get(key).validUntil < Instant.now().getEpochSecond()) {
						System.out.println("Session removed!");
						sessions.remove(key);
					}
				}
				
			}
		}, 0, 300, TimeUnit.SECONDS);
		if(serverThread == null) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread = new ServerThread();
			serverThread.run();
		}
	}
	
	/**
	 * Stops thread that does server's job and shutdowns all workers
	 */
	protected void stop() { //TODO
		for(Future<?> res: serverThread.results) {
			try {
				res.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e.getLocalizedMessage());
			}
		}
		threadPool.shutdown();
		scheduleThread.shutdown();
	}
	
	/**
	 * Implementation of server's thread
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	protected class ServerThread extends Thread {
		
		/**
		 * All results of multithreaded programs 
		 */
		List<Future<?>> results = new ArrayList<>();
		@Override
		public void run() {
			try(ServerSocket serverSocket = new ServerSocket()) {
				serverSocket.bind(new InetSocketAddress((InetAddress)null, port));
				while(true) {
					Socket client = null;
					try {
						client = serverSocket.accept();
					} catch(IOException ex) {
						throw new RuntimeException("Client cannot listen on port");
					}
					ClientWorker cw = new ClientWorker(client);
					results.add(threadPool.submit(cw));
				}
				
			} catch (IOException e) {
				throw new RuntimeException("Cannot open port " + port);
			}
		}
	}
	
	/**
	 * Implements job that will be done for each client 
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		/**
		 * Client's socket
		 */
		private Socket csocket;
		
		/**
		 * Socket's input stream
		 */
		private InputStream istream;
		
		/**
		 * Socket's output stream
		 */
		private OutputStream ostream;
		
		/**
		 * Protocol's version
		 */
		private String version;
		
		/**
		 * HTTP method(GET, POST...)
		 */
		private String method;
		
		/**
		 * Host
		 */
		private String host;
		
		/**
		 * Parameters used in {@linkplain RequestContext}
		 */
		private Map<String,String> params = new HashMap<String, String>();
		
		/**
		 * Temporary parameters used in {@linkplain RequestContext}
		 */
		private Map<String,String> tempParams = new HashMap<String, String>();
		
		/**
		 * Persistent parameters used in {@linkplain RequestContext}
		 */
		private Map<String,String> permParams = new HashMap<String, String>();
		
		/**
		 * List of cookies used in sessions
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/**
		 * Session ID
		 */
		private String SID;
		
		/**
		 * Default response
		 */
		private RequestContext rc = null;
		
		/**
		 * Constructor for client's worker
		 * @param csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}
		
		
		@Override
		public void run() {
			try {
				istream = new BufferedInputStream(csocket.getInputStream());	
				ostream =  new BufferedOutputStream(csocket.getOutputStream());
				List<String> request = Arrays.asList(readRequest().split("\n"));
				if(request.size() < 1) {
					sendError(ostream, 400, "Bad request error");
					csocket.close();
					return;
				}
				String firstLine = request.get(0);
				List<String> parsedFirstLine = extractFirstLine(firstLine);
				method = parsedFirstLine.get(0);
				if(method.equals("GET") == false) {
					sendError(ostream, 400, "Bad request error");
					csocket.close();
					return;
				}
				version = parsedFirstLine.get(2);
				if(version.equals("HTTP/1.0") == false && version.equals("HTTP/1.1") == false) {
					sendError(ostream, 500, "Bad request error");
					csocket.close();
					return;
				}
				boolean hostServed = false;
				if(request.size() > 1) {
					for (String line: request) {
						if(line.startsWith("Host:") == true) {
							hostServed = true;
							serveHostProperty(line);
							break;
						}
					}
				}
				if(hostServed == false) {
					this.host = domainName;
				}
				String path = parsedFirstLine.get(1);
				synchronized (keeper) {
					String sidCandidate = checkSession(request);
					if(sidCandidate == null) {
							createAndStoreNewCookie(); //new client
					} else {
						SessionMapEntry entry = sessions.get(sidCandidate); //existing
						if(entry != null) {
							if(entry.host.equals(this.host) == false) { //with no valid host
								createAndStoreNewCookie();
							} else {
								if(entry.validUntil < Instant.now().toEpochMilli() / 1000) { //with no valid time left 
									sessions.remove(sidCandidate);
									createAndStoreNewCookie();
								} else {
									entry.validUntil = Instant.now().toEpochMilli() / 1000 + sessionTimeout; //valid and update
									permParams = entry.map;
								}
							}		
						} else {
							createAndStoreNewCookie(); //new client
						}
						
					}	
				}
				resolvePath(path);
			} catch(IOException ex) {
				throw new RuntimeException("Cannot get input or output stream from socket");
			} 
			try {
				csocket.close();
			} catch (IOException e) {
				throw new RuntimeException("Closing socket failed");
			}
		}
		
		/**
		 * Creates new cookie and sends it in response so client's browser can store 
		 */
		private void createAndStoreNewCookie() {
			String generatedSID = generateSID(sessionRandom);
			this.SID = generatedSID;
			SessionMapEntry sessEntry = new SessionMapEntry();
			sessEntry.sid = generatedSID;
			sessEntry.validUntil = Instant.now().toEpochMilli() / 1000 + sessionTimeout;
			sessEntry.map = new ConcurrentHashMap<String, String>();
			sessEntry.host = this.host;
			sessions.put(generatedSID, sessEntry);
			permParams = sessEntry.map;
			if(rc == null) {
				rc = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
			}
			rc.addRCCookie(new RCCookie("sid", generatedSID, null, this.host, "/", "HttpOnly"));
		}
		
		/**
		 * Checks if there is a header in user's request that specifies some used cookie
		 * @param request client's request
		 * @return session id if cookie exist or {@link null}  if it doesn't
		 */
		private String checkSession(List<String> request) {
			String sidCandidate = null;
			for(String line: request) {
				if(line.startsWith("Cookie:") == false) {
					continue;
				}
				line = line.substring(8);
				String[] all = line.split("; ");
				for(String entry: all) {
					String[] nameValue = entry.split("=");
					if(nameValue[0].equals("sid") == true) {
						sidCandidate = nameValue[1];
						sidCandidate = sidCandidate.substring(1);
						sidCandidate = sidCandidate.substring(0, sidCandidate.length() -1);
					}
				}
				break;
			}
			return sidCandidate;
		}
		
		/**
		 * Method used for generating session id
		 * @param sessionRandom {@linkplain Random} for generating random values
		 * @return generated session id
		 */
		private String generateSID(Random sessionRandom) {
			StringBuilder sb = new StringBuilder();
			int lowerBound = 64;
			for(int i = 1; i <= 20; i++) {
				int rand = Math.abs(sessionRandom.nextInt() % 26) + 1;
				sb.append((char) (rand + lowerBound));
			}
			return sb.toString();
		}
		
		/**
		 * Resolves parameters and path from URL
		 * @param path parses user's requested URL
		 */
		private void resolvePath(String path) {
			String[] objs = path.split("\\?");
			if(objs.length > 1) {
				String[] mappings = objs[1].split("\\&");
				for(String mapping: mappings) {
					String[] nameValue = mapping.split("=");
					params.put(nameValue[0], nameValue[1]);
				}
			}
			try {
				internalDispatchRequest(objs[0], true);
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new RuntimeException("Error in dispatching request");
			}
		}
		
		/**
		 * Sets host property to trimmed value after "Host:". If host value is of form some-name:number, it remembers only "some-name" part
		 * @param line line where host property occurrs
		 */
		private void serveHostProperty(String line) {
			line = line.substring(5);
			line = line.trim();
			String[] hostArr = line.split(":");
			host = hostArr[0];
		}
		
		/**
		 * Sends error response
		 * @param os output stream for response
		 * @param statusCode for response
		 * @param statusText for response
		 */
		private void sendError(OutputStream os, int statusCode, String statusText) {
			if(rc == null) {
				rc = new RequestContext(os, params, permParams, outputCookies, tempParams, this, this.SID);	
			} else {
				rc.setOutputStream(os);
				rc.setPersistentParameters(permParams);
			}
			rc.statusCode = statusCode;
			rc.statusText = statusText;
			rc.setTemporaryParameters(tempParams);
			try {
				rc.write("<!DOCTYPE> <html> <h1>" +statusCode + statusText + "</h1> </html>"); //check
			} catch (IOException e) {
				throw new RuntimeException("Cannot generate error");
			} 
		}
		
		/**
		 * Extracts method requested path and version from client's request
		 * @param firstLine first line of request
		 * @return
		 */
		private List<String> extractFirstLine(String firstLine) {
			List<String> sol = new ArrayList<>();
			sol = Arrays.asList(firstLine.split(" "));
			return sol;
		}
		
		
		/**
		 * Reads client's request with the help of automat that will register end when //TODO
		 * @return request from client as {@linkplain List<String>}
		 */
			private String readRequest() throws IOException {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					int state = 0;
			l:		while(true) {
						int b = istream.read();
						if(b==-1) return null;
						if(b!=13) {
							bos.write(b);
						}
						switch(state) {
						case 0: 
							if(b==13) { state=1; } else if(b==10) state=4;
							break;
						case 1: 
							if(b==10) { state=2; } else state=0;
							break;
						case 2: 
							if(b==13) { state=3; } else state=0;
							break;
						case 3: 
							if(b==10) { break l; } else state=0;
							break;
						case 4: 
							if(b==10) { break l; } else state=0;
							break;
						}
					}
					return bos.toString(StandardCharsets.UTF_8);
		}
		
		/**
		 * Dispatch request to {@linkplain IWebWorker} to return server's response
		 * @param urlPath client's requested url
		 */
		private  void dispatchToWorker(String urlPath) {
			try {
				if(rc == null) {
					rc = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
				}
				workersMap.get(urlPath).processRequest(rc);
			} catch (Exception e) {
				throw new RuntimeException("Cannot dispatch request to worker");
			}
		}
	
		/**
		 * Specific implementation for dispatching client's requests	
		 * @param urlPath url 
		 * @param directCall flag if this method is directly called from {@linkplain ClientWorker}
		 * @throws Exception
		 */
		private void internalDispatchRequest(String urlPath, boolean directCall) throws Exception{
			if(urlPath.startsWith("/private") == true && directCall == true) {
				sendError(ostream,403, "Forbidden");
				return;
			}
			
			if(urlPath.startsWith("/ext") == true) {
				urlPath = urlPath.substring(5);
				try {
					Class<?> refToClass = this.getClass().getClassLoader().loadClass("hr.fer.zemris.java.webserver.workers." +	urlPath);
					@SuppressWarnings("deprecation")
					Object ins = refToClass.newInstance();
					IWebWorker worker =(IWebWorker) ins;
					if(rc == null) {
						rc = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
					}
					worker.processRequest(rc);
				} finally {
					return;
				}
			}
			if(workersMap.containsKey(urlPath) == true) {
				dispatchToWorker(urlPath);
				return;
			} 
			String extension = urlPath.substring(urlPath.lastIndexOf(".") +1);
			if(extension.equals("smscr") == true) {
				Path finalPath = documentRoot.resolve(urlPath.substring(1));
				if(checkFile(finalPath) == false) {
					return;
				}
				InputStream is = new BufferedInputStream(Files.newInputStream(finalPath));
				byte[] data = is.readAllBytes();
				String temp = new String(data, "UTF-8");
				DocumentNode docNode = null;
				try {
					docNode = new SmartScriptParser(temp).getDocumentNode();	
				} catch(SmartScriptParserException ex) {
					ex.printStackTrace();
					is.close();
					return;
				}
		
				if(rc == null) {
					rc = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);	
				}
				SmartScriptEngine sse = new SmartScriptEngine(docNode, rc);
				try {
					sse.execute();	
				} finally {
					is.close();
					return;	
				}
			}
			Path finalPath = documentRoot.resolve(urlPath.substring(1));
			if(checkFile(finalPath) == false) {
				return;
			}
			String mimeType = mimeTypes.get(extension);
			if(mimeType == null) {
				mimeType = "application/octet-stream";
			}
			if(rc == null) {
				rc= new RequestContext(ostream, params, permParams, outputCookies,tempParams, this, SID);	
			} else {
				rc.setPersistentParameters(permParams);
				rc.setOutputStream(ostream);
			}
			rc.statusCode = 200;
			rc.statusText = "Success";
			rc.setMimeType(mimeType);
			rc.setCharset(StandardCharsets.UTF_8);
			rc.encoding = "UTF-8";
			boolean mimeFlag = false;
			if(mimeType.equals("text/html") == true || mimeType.equals("text/plain") == true) {
				mimeFlag = true;
			}
			try {
				BufferedInputStream br = new BufferedInputStream(Files.newInputStream(finalPath));
				byte[] data = new byte[4096];
				int r;
				while(true) {	
					r = br.read(data);
					if(r <1) {
						break;
					}
					if(mimeFlag == true) { //serve file
						String s = new String(data, 0,r, "UTF-8");
						rc.write(s + "\r\n");	
					} else {
						rc.write(data);
					}
				}
				br.close();	
			} catch (IOException e) {
				throw new RuntimeException("Error content to client");
			}	
		}
		
		/**
		 * Checks if file exists in root folder and if file actually is file and if can be read
		 * @param path path where file should be
		 */
		private boolean checkFile(Path path) {
			File file = path.toFile();
			if(file.exists() == false || file.isFile() == false || file.canRead() == false) {
				sendError(ostream, 404, "Page not found");
				return false;
			}
			return true;
		}
			
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
	}
	
	/**
	 * One session entry
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private static class SessionMapEntry {
		
		/**
		 * Session id
		 */
		String sid;
		
		/**
		 * Host
		 */
		String host;
		
		/**
		 * How long is valid
		 */
		long validUntil;
		
		/**
		 * Stores clients data
		 */
		Map<String, String> map;
	}
	
	/**
	 * Creates daemon thread
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private class MyThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
		
	}
	
	/**
	 * Main method from where we start server
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			throw new RuntimeException("Expected server properties");
		}
		SmartHttpServer server = new SmartHttpServer(args[0]);
//		SmartHttpServer server = new SmartHttpServer("./config/server.properties");
		server.start();
	}
}
