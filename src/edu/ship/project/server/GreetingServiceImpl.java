package edu.ship.project.server;


import javax.jdo.PersistenceManager;
import edu.ship.project.client.GreetingService;
import edu.ship.project.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	public String greetServer(String name, String password) throws IllegalArgumentException {
		
//		// TEST
//		System.err.println("starting loadInitialCustomers");
//		
//		PersistenceManager pm = PMF.get();
//		
//		System.err.println("persistance failed");
//	    Customer c = new Customer("Jake Brown");
//	    
//	    // To save multiple objects in JDO, call the makePersistentAll(...) method with a Collection of objects. 
//        pm.makePersistent(c);
//        System.err.println("Jake Brown persisted");
//	    pm.close();
		
		// Verify that input is correct
		if(!FieldVerifier.isValidName(name)){
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}
		if(!FieldVerifier.isValidPassword(password)){
			throw new IllegalArgumentException("Password must be at least 4 characters long");
		}
		
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		
		// Escape data from client to avoid cross-site script vulnerabilities
		name = escapeHtml(name);
		password = escapeHtml(password);
		userAgent = escapeHtml(userAgent);
		
		return "Hello, " + name + "!<br><br>You have successfully logged in.<br><br>";
	}
	
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
