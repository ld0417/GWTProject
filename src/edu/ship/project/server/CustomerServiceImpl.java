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
		
	public String loadInitialCustomers(){
		removePersistentObjects();
		loadInitialObjects();
		return "passed";
	}
	
	private void removePersistentObjects() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Customer.class);
		try{		
			List<Customer> results = (List<Customer>) q.execute();
			for(Customer r: results){
				pm.deletePersistent(r);
			}
		} finally{
			q.closeAll();
			pm.close();
		}
	}
	
	private void loadInitialObjects() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{		
			for(Customer c: CUSTOMERS){
				pm.makePersistent(c);
			}
		} finally{

			pm.close();
		}		
	}

	public ArrayList<String> loadCustomers(){
		//removePersistentObjects();
		//loadInitialObjects();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Customer.class);
		ArrayList<String> customersToReturn = new ArrayList<String>(); 
		try{			
			List<Customer> results = (List<Customer>) q.execute();
			System.err.println(results.toString());
			if(!results.isEmpty())
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
		Customer c = new Customer(name);
		try{
			pm.makePersistent(c);
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
					}
				}
				pm.deletePersistent(toBeDeleted);
			}
		}finally{
			q.closeAll();
			pm.close();
		}
		return name;
	}
}
