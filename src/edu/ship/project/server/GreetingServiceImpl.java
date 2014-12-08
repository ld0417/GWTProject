package edu.ship.project.server;

import java.util.List;
import javax.jdo.PersistenceManager;
import edu.ship.project.client.*;
import edu.ship.project.shared.*;
import javax.jdo.Query;
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
//		PersistenceManager pm = PMF.get().getPersistenceManager();
//		//Customer c = new Customer("Bill Gates");
//        //pm.makePersistent(c);
//    	
//		Query q = pm.newQuery(Customer.class);
//		//Query q2 = pm.newQuery(Customer.class);
//	
//		try {
//		  List<Customer> results = (List<Customer>) q.execute();
//		  if (!results.isEmpty()) {
//		    for (Customer r : results) {
//		    	System.err.println(r.getName());
//		    }
//		    System.err.println("customer list printed");
//		  } else {
//			  System.err.println("there were no results");
//		  }
//		  
//		  //q2.deletePersistentAll(results);
//		  
//		} finally {
//			
//		  q.closeAll();
//		  //q2.closeAll();
//		  pm.close();
//		}
		
		
		
		
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
