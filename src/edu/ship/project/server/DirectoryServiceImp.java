package edu.ship.project.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.ship.project.client.DirectoryService;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DirectoryServiceImp extends RemoteServiceServlet implements DirectoryService {
	
	public String directoryServer(String input) throws IllegalArgumentException {
		
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Input: " + input + "<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
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