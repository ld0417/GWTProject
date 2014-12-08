package edu.ship.project.client.Directory;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>DirectoryService</code>.
 */
public interface DirectoryServiceAsync {
	void directoryServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
}
