package edu.ship.project.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>CustomerService</code>.
 */
public interface CustomerServiceAsync {
	void loadCustomers(AsyncCallback<String> callback) throws IllegalArgumentException;
	void addCustomer(String name, AsyncCallback<String> callback) throws IllegalArgumentException;
	void editCustomer(String name, int key, AsyncCallback<String> callback) throws IllegalArgumentException;
	void deleteCustomer(int key, AsyncCallback<String> callback) throws IllegalArgumentException;
}
