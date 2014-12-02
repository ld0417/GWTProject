package edu.ship.project.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String name, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void greetServer(String name, String password, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
