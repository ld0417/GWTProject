package edu.ship.project.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface InventoryService extends RemoteService {
	String loadInventory();
	String addInventory(String name);
	String editInventory(String name, int key);
	String deleteInventory(int key);
}
