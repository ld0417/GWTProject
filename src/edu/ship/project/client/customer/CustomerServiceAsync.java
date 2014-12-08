package edu.ship.project.client.customer;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>CustomerService</code>.
 */
public interface CustomerServiceAsync {
	void loadCustomers(AsyncCallback<ArrayList<String>> callback) throws IllegalArgumentException;
	void addCustomer(String name, AsyncCallback<String> callback) throws IllegalArgumentException;
	void deleteCustomer(String name, AsyncCallback<String> callback) throws IllegalArgumentException;
	void loadInitialCustomers(AsyncCallback<String> callback) throws IllegalArgumentException;
}
