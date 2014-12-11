package edu.ship.project.client.inventory;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("inventory")
public interface InventoryService extends RemoteService {
	ArrayList<String> loadInitialElements();
	ArrayList<String> loadElements();
	int addElement(int sku, String descript, String url, int price, int num);
	void deleteElement(int sku);
}
