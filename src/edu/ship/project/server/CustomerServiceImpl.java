package edu.ship.project.server;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ship.project.client.CustomerService;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CustomerServiceImpl extends RemoteServiceServlet implements CustomerService {

	PersistenceManager pm = PMF.get().getPersistenceManager();
	
	public String loadCustomers() {
		// TODO: Loads a initial array list of customers
		
		// Customer cust = new Customer("Bob Smith");
		try{
			// pm.makePersistent(cust);
		}finally{
			// pm.close();
		}
		return null;
	}

	public String addCustomer(String name) {
		// Customer cust = new Customer(name);
		// add new customer to data store
		// TODO Auto-generated method stub
		return null;
	}

	public String editCustomer(String name, int key) {
		// find customer with specific key
		// change the customers name
		// TODO Auto-generated method stub
		return null;
	}

	public String deleteCustomer(int key) {
		// delete the customer with specific key
		// TODO Auto-generated method stub
		return null;
	}
}