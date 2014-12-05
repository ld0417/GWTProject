package edu.ship.project.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InventoryClient implements EntryPoint {
	FlexTable inventoryTable = new FlexTable();
	ArrayList<Integer> products = new ArrayList<Integer>();
	
	private class InventoryProduct {
		int sku;		
		
		public InventoryProduct(int temp) {
			sku = temp;
		}
	}
	
	public void deleteInventoryElement(int sku) { 
		int removedIndex = products.indexOf(sku);
        products.remove(removedIndex);
		inventoryTable.removeRow(removedIndex+1);
	}
	
	@Override
	public void onModuleLoad() { 
		inventoryTable.setText(0, 0, "Description");
		inventoryTable.setText(0, 1, "SKU #");
		inventoryTable.setText(0, 2, "Link");
		inventoryTable.setText(0, 3, "Price");
		inventoryTable.setText(0, 4, "Inventory");
		
		String array[] = { "item description", "sku#",
				"<a href=\"#\">picture</a>", "$" };
		
		for (int row = 1; row < 6; ++row) {
			for (int col = 0; col < 6; ++col) {
				if (col < 4) {
					inventoryTable.setWidget(row, col, new Label(array[col] + row));
				} else {
					inventoryTable.setWidget(row, col, new Label("" + row));
				}
			}
			Button editButton = new Button("Edit");
			Button deleteButton = new Button("Delete");
			
			inventoryTable.setWidget(row, 5, editButton);
			inventoryTable.setWidget(row, 6, deleteButton);
			final int tempSKU = row;
			
			deleteButton.addClickHandler(new ClickHandler() { 
				public void onClick(ClickEvent event) {
					deleteInventoryElement(tempSKU);
				}
			});
			
			editButton.addClickHandler(new ClickHandler() { 
				public void onClick(ClickEvent event) {
					//TODO - Edit method with some kind of interface
				}
			});
			products.add(row);
		}
		
		int newRow = inventoryTable.getRowCount();
		for(int col = 0; col < 5; col++) {
			inventoryTable.setWidget(newRow, col, new TextBox());
		}
				
		Button addButton = new Button("Add Row");
		
		addButton.addClickHandler(new ClickHandler() { 
			public void onClick(ClickEvent event) {
				ArrayList<String> newProduct = new ArrayList<String>();
				
				for(int col = 0; col < 5; col++) {
					String str = ((TextBox) inventoryTable.getWidget(inventoryTable.getRowCount(), col)).getText();
					newProduct.add(str);
				}
				
				// First must remove the old one after extracting data
				inventoryTable.removeRow(inventoryTable.getRowCount());
				int tempRow = inventoryTable.getRowCount();
				// Add new row to the FlexTable & update the products list
				products.add(Integer.parseInt(newProduct.get(2)));
				for(int col = 0; col < 7; col++) {
					if(col < 5)
						inventoryTable.setWidget(tempRow, col, new Label(newProduct.get(col)));
					else {
						Button editButton = new Button("Edit");
						Button deleteButton = new Button("Delete");
						
						inventoryTable.setWidget(tempRow, 5, editButton);
						inventoryTable.setWidget(tempRow, 6, deleteButton);
						final int tempSKU = products.get(2);
						
						deleteButton.addClickHandler(new ClickHandler() { 
							public void onClick(ClickEvent event) {
								deleteInventoryElement(tempSKU);
							}
						});
						
						editButton.addClickHandler(new ClickHandler() { 
							public void onClick(ClickEvent event) {
								//TODO - Edit method with some kind of interface
							}
						});
					}
				}
				// Add a new row for users to add data
				for(int col = 0; col < 4; col++) {
					inventoryTable.setWidget(inventoryTable.getRowCount(), col, new TextBox());
				}
			}
		});
		
		RootPanel.get("inventoryContent").add(addButton);
		RootPanel.get("inventoryContent").add(inventoryTable);
		RootPanel.get("inventoryContent").setVisible(false);
	}

}
