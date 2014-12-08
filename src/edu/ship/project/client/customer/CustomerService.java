package edu.ship.project.client.customer;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("customer")
public interface CustomerService extends RemoteService {
	ArrayList<String> loadInitialCustomers();
	ArrayList<String> loadCustomers();
	String addCustomer(String name);
	String deleteCustomer(String name);
}