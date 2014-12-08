package edu.ship.project.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.jdo.*;

import edu.ship.project.client.customer.CustomerService;
import edu.ship.project.server.PMF;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CustomerServiceImpl extends RemoteServiceServlet implements CustomerService {
	
	/**
	 * The list of initial Customers to display.
	 */
	private static final List<Customer> CUSTOMERS = Arrays.asList(
			new Customer("John Smith"),
			new Customer("Jane Brown"),
			new Customer("Nate Kuhn"),
			new Customer("LaVonne Diller"));
	
	public ArrayList<String> loadInitialCustomers(){
		removePersistentObjects();
		return persistInitialObjects();
	}
	
	private void removePersistentObjects() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Customer.class);
		try{
			// Delete all persistence objects
			List<Customer> results = (List<Customer>) q.execute();
			
			while (results.size() > 0){
				System.err.println(results.size());
				for(Customer r: results){
					pm.deletePersistent(r);
				}	
				results = (List<Customer>) q.execute();
			}
		} finally{
			q.closeAll();
			pm.close();
		}
	}
	
	private ArrayList<String> persistInitialObjects() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Customer.class);
		ArrayList<String> customers = new ArrayList<String>();
		try{
			// Persist initial objects
			List<Customer> results = (List<Customer>) q.execute();
			while(results.size() < 4)
			{
				for(Customer c: CUSTOMERS){
					if(!customers.contains(c.getName()))
					{
						pm.makePersistent(c);
						customers.add(c.getName());
						System.err.println(c.getName());
					}
				}	
				results = (List<Customer>) q.execute();	
				System.err.println(results.size());
			}
		} finally{
			q.closeAll();
			pm.close();
		}
		return customers;
	}

	public ArrayList<String> loadCustomers(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Customer.class);
		ArrayList<String> customersToReturn = new ArrayList<String>();
		try{
			List<Customer> results = (List<Customer>) q.execute();
			if(results.size() > 0)
			{
				for(Customer r: results)
				{
					customersToReturn.add(r.getName());	
				}
			}
		} finally{
			q.closeAll();
			pm.close();
		}
		System.err.println(customersToReturn.toString());
		return customersToReturn;
	}

	public String addCustomer(String name) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Customer.class);
		Customer c = new Customer(name);
		try{
			List<Customer> results = (List<Customer>) q.execute();
			int orginialSize = results.size();
			System.err.println("orginial size " + orginialSize);
			while(results.size() <= orginialSize)
			{
				pm.makePersistent(c);
				results = (List<Customer>) q.execute();
				System.err.println("result size " + results.size());
			}
		}finally{
			pm.close();
		}
		return c.getName();
	}
	
	public String deleteCustomer(String name){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Customer.class);
		q.setFilter("name == nameParm");
		q.declareParameters("String nameParm");
		
		try{
			List<Customer> results = (List<Customer>) q.execute(name);
			Customer toBeDeleted = null;
			if (!results.isEmpty()) {
				for(Customer r: results){
					if(r.getName().equals(name)){
						toBeDeleted = r;
						System.err.println(toBeDeleted.getName());
					}
				}
				pm.deletePersistent(toBeDeleted);
			}
		}finally{
			q.closeAll();
			pm.close();
		}
		checkIsDeleted(name);
		return name;
	}

	private void checkIsDeleted(String name) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Customer.class);
		q.setFilter("name == nameParm");
		q.declareParameters("String nameParm");
		
		try{
			List<Customer> results = (List<Customer>) q.execute(name);
			if (!results.isEmpty()) 
			{
				for(Customer r: results)
				{
					if(r.getName().equals(name))
					{
						System.err.println(name + " not deleted");
						deleteCustomer(name);
					}
				}
			}
		}finally{
			q.closeAll();
			pm.close();
		}
	}
}
