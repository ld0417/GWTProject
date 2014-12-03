package edu.ship.project.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("customer")
public interface CustomerService extends RemoteService {
	String loadCustomers();
	String addCustomer(String name);
	String editCustomer(String name, int key);
	String deleteCustomer(int key);
}
