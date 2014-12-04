package edu.ship.project.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InventoryServiceAsync {
	void loadInventory(AsyncCallback<String> callback) throws IllegalArgumentException;
	void addInventory(String name, AsyncCallback<String> callback) throws IllegalArgumentException;
	void editInventory(String name, int key, AsyncCallback<String> callback) throws IllegalArgumentException;
	void deleteInventory(int key, AsyncCallback<String> callback) throws IllegalArgumentException;
}
