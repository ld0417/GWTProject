package edu.ship.project.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.jdo.*;

import edu.ship.project.client.inventory.InventoryService;
import edu.ship.project.server.PMF;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class InventoryServiceImpl extends RemoteServiceServlet implements InventoryService {
	
	/**
	 * The list of initial Customers to display.
	 */
	private static final List<InventoryElement> ELEMENTS = Arrays.asList(
			new InventoryElement(1, "Element 1", "http://www.tutorialspoint.com/images/gwt-mini.png", 1, 1),
			new InventoryElement(2, "Element 2", "http://www.tutorialspoint.com/images/gwt-mini.png", 2, 2),
			new InventoryElement(3, "Element 3", "http://www.tutorialspoint.com/images/gwt-mini.png", 3, 3));
	
	public ArrayList<String> loadInitialElements(){
		removePersistentObjects();
		return persistInitialObjects();
	}
	
	private void removePersistentObjects() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(InventoryElement.class);
		try{
			// Delete all persistence objects
			List<InventoryElement> results = (List<InventoryElement>) q.execute();
			
			while (results.size() > 0){
				System.err.println(results.size());
				for(InventoryElement r: results){
					pm.deletePersistent(r);
				}	
				results = (List<InventoryElement>) q.execute();
			}
		} finally{
			q.closeAll();
			pm.close();
		}
	}
	
	private ArrayList<String> persistInitialObjects() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(InventoryElement.class);
		ArrayList<String> elements = new ArrayList<String>();
		try{
			// Persist initial objects
			List<InventoryElement> results = (List<InventoryElement>) q.execute();
			while(results.size() < 4)
			{
				for(InventoryElement e: ELEMENTS){
					boolean contains = false;
					for(String e1 : elements) {
						if(e.equals(e1) && contains == false)
						{
							contains = true;
							pm.makePersistent(e);
							//elements.add(new InventoryElement(e.getSku(), e.getDescription(), e.getUrl(), e.getPrice(), e.getNum()));
							elements.add("" +e.getSku());	
							elements.add(e.getDescription());
							elements.add(e.getUrl());
							elements.add("" +e.getPrice());
							elements.add("" +e.getNum());
							
							System.err.println(e.getSku());
						}
					}
				}	
				results = (List<InventoryElement>) q.execute();	
				System.err.println(results.size());
			}
		} finally{
			q.closeAll();
			pm.close();
		}
		return elements;
	}

	public ArrayList<String> loadElements(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(InventoryElement.class);
		ArrayList<String> elementsToReturn = new ArrayList<String>();
		try{
			List<InventoryElement> results = (List<InventoryElement>) q.execute();
			if(results.size() > 0)
			{
				for(InventoryElement r: results)
				{
					elementsToReturn.add("" +r.getSku());	
					elementsToReturn.add(r.getDescription());
					elementsToReturn.add(r.getUrl());
					elementsToReturn.add("" +r.getPrice());
					elementsToReturn.add("" +r.getNum());
				}
			}
		} finally{
			q.closeAll();
			pm.close();
		}
		System.err.println(elementsToReturn.toString());
		return elementsToReturn;
	}

	public int addElement(int sku, String descript, String url, int price, int num) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(InventoryElement.class);
		InventoryElement e = new InventoryElement(sku, descript, url, price, num);
		try{
			List<InventoryElement> results = (List<InventoryElement>) q.execute();
			int orginialSize = results.size();
			System.err.println("orginial size " + orginialSize);
			while(results.size() <= orginialSize)
			{
				pm.makePersistent(e);
				results = (List<InventoryElement>) q.execute();
				System.err.println("result size " + results.size());
			}
		}finally{
			pm.close();
		}
		return e.getSku();
	}
	
	public void deleteElement(int sku){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(InventoryElement.class);
		q.setFilter("sku == skuParam");
		q.declareParameters("int skuParam");
		
		try{
			List<InventoryElement> results = (List<InventoryElement>) q.execute(sku);
			InventoryElement toBeDeleted = null;
			if (!results.isEmpty()) {
				for(InventoryElement r: results){
					if(r.getSku() == (sku)){
						toBeDeleted = r;
						System.err.println(toBeDeleted.getSku());
					}
				}
				pm.deletePersistent(toBeDeleted);
			}
		}finally{
			q.closeAll();
			pm.close();
		}
		checkIsDeleted(sku);
	}

	private void checkIsDeleted(int sku) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Customer.class);
		q.setFilter("sku == skuParam");
		q.declareParameters("int skuParam");
		
		try{
			List<InventoryElement> results = (List<InventoryElement>) q.execute(sku);
			if (!results.isEmpty()) 
			{
				for(InventoryElement r : results)
				{
					if(r.getSku() == (sku))
					{
						System.err.println(sku + " not deleted");
						deleteElement(sku);
					}
				}
			}
		}finally{
			q.closeAll();
			pm.close();
		}
	}
}
