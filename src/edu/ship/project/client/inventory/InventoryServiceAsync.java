package edu.ship.project.client.inventory;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ship.project.server.InventoryElement;


public interface InventoryServiceAsync {
    void addElement(int sku, java.lang.String descript, java.lang.String url, int price, int num, AsyncCallback<Integer> callback);
    void deleteElement(int sku, AsyncCallback<Void> asyncCallback);
    void loadElements(AsyncCallback<ArrayList<String>> callback);
    void loadInitialElements(AsyncCallback<ArrayList<String>> callback);
}
