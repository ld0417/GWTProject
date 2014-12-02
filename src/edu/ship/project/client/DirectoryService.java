package edu.ship.project.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("directory")
public interface DirectoryService extends RemoteService {
	String directoryServer(String input) throws IllegalArgumentException;
}