package edu.ship.project.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class InventoryClient implements EntryPoint {
	FlexTable inventoryTable = new FlexTable();
	//ArrayList<Integer> products = new ArrayList<Integer>();
	// Key is the value of row in the table and maps SKU.
	HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	
	
	private class InventoryProduct {
		int sku;		
		
		public InventoryProduct(int temp) {
			sku = temp;
		}
	}
	
	private void deleteInventoryElement(int removeIndex) { 
        map.remove(removeIndex);
		inventoryTable.removeRow(removeIndex);
	}
	
	private void editInventoryElement(final int editIndex) {
		// put remove Labels/widgets then insert a row of TextBoxes with the values 
		// that were pulled in the beginning	
		
		//final int editIndex = map.get(sku);
		// Should SKU be able to change?
		
		map.remove(editIndex);
		//	If so remove it from the arraylist so it can be added again later (i.e. changed)
		
		//GET VALUES FROM LABELS
		ArrayList<String> editProduct = new ArrayList<String>();
		for(int col = 0; col < 5; col++) {
			Widget widg = inventoryTable.getWidget(editIndex, col);
			//System.out.println(widg.getClass().toString());
			if(widg instanceof Label) {
				String str =  ((Label)widg).getText();
				editProduct.add(str);
			}
			if(widg instanceof Image) {
				String str = ((Image)widg).getUrl();
				editProduct.add(str);
			}
		}
		
		for(int col = 0; col < 5; col++) {
			TextBox textBox = new TextBox();
			textBox.getElement().setAttribute("placeholder", editProduct.get(col));
			inventoryTable.setWidget(editIndex, col, textBox);
		}
		
		//ADD DONE BUTTON
		Button doneButton = new Button("Done");
		inventoryTable.setWidget(editIndex, 5, doneButton);
		
		//ADD ONCLICK - CHECK ALL COLUMNS AND DELETE ROW AND INSERT NEW ROW WITH LABELS
		doneButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//Change the TextBoxes back to Labels & Check if everything is filled
				ArrayList<String> newProduct = new ArrayList<String>();
				for(int col = 0; col < 5; col++) {
					Widget widg = inventoryTable.getWidget(editIndex, col);
					if(widg instanceof TextBox) {
						String str =  ((TextBox)widg).getValue();
						if(!str.equals(""))
							newProduct.add(str);
						else
							newProduct.add(((TextBox)widg).getElement().getPropertyString("placeholder"));
					}
				}
				
				//Ensure that all TextBoxes have the correct Values (Should check for SKU duplicates)
				boolean textBoxesFull = true;
				if(newProduct.size() > 0) {
					for(String s : newProduct) {
						if(s.equals("")) 
							textBoxesFull = false;
					}
				}
				
				if(textBoxesFull) {
					// First must remove the old one after extracting data
					//inventoryTable.removeRow(editIndex);
					//int tempRow = editIndex;
					// Add new row to the FlexTable & update the products list
					map.put(editIndex, Integer.parseInt(newProduct.get(1)));
					//products.add(Integer.parseInt(newProduct.get(1)));
					for(int col = 0; col < 7; col++) {
						if(col == 2) {
							final Image image = new Image(newProduct.get(col));
							image.addErrorHandler(new ErrorHandler() {
								public void onError(ErrorEvent event) {
									System.out.println("Error loading picture....");
								}
							});
							inventoryTable.setWidget(editIndex, col, image);
						} else if(col < 5)
							inventoryTable.setWidget(editIndex, col, new Label(newProduct.get(col)));
						else {
							Button editButton = new Button("Edit");
							Button deleteButton = new Button("Delete");
							
							inventoryTable.setWidget(editIndex, 5, editButton);
							inventoryTable.setWidget(editIndex, 6, deleteButton);
							//final int tempSKU = products.get(2);
							
							deleteButton.addClickHandler(new ClickHandler() { 
								@Override
								public void onClick(ClickEvent event) {
									deleteInventoryElement(inventoryTable.getCellForEvent(event).getRowIndex());
								}
							});
							
							editButton.addClickHandler(new ClickHandler() { 
								@Override
								public void onClick(ClickEvent event) {
									editInventoryElement(inventoryTable.getCellForEvent(event).getRowIndex());
								}
							});
						}
					}
				}
			}
		});
	}
	
	@Override
	public void onModuleLoad() { 
		inventoryTable.setText(0, 0, "Description");
		inventoryTable.setText(0, 1, "SKU #");
		inventoryTable.setText(0, 2, "Link");
		inventoryTable.setText(0, 3, "Price");
		inventoryTable.setText(0, 4, "Inventory");
		
		String array[] = { "item description", "",
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
			//final int tempSKU = row;
			
			deleteButton.addClickHandler(new ClickHandler() { 
				@Override
				public void onClick(ClickEvent event) {
					deleteInventoryElement(inventoryTable.getCellForEvent(event).getRowIndex());
				}
			});
			
			editButton.addClickHandler(new ClickHandler() { 
				@Override
				public void onClick(ClickEvent event) {
					editInventoryElement(inventoryTable.getCellForEvent(event).getRowIndex());
				}
			});
			map.put(row, row);
			//products.add(row);
		}
		
		int newRow = inventoryTable.getRowCount();
		for(int col = 0; col < 5; col++) {
			inventoryTable.setWidget(newRow, col, new TextBox());
		}
				
		Button addButton = new Button("Add Row");
		
		addButton.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<String> newProduct = new ArrayList<String>();
				
				for(int col = 0; col < 5; col++) {
					Widget widg = inventoryTable.getWidget(inventoryTable.getRowCount()-1, col);
					if(widg instanceof TextBox) {
						String str =  ((TextBox)widg).getValue();
						newProduct.add(str);
					}
				}
				
				// What can be blank on an inventory item?
				// Check to make sure all textboxes are filled.
				boolean textBoxesFull = true;
				if(newProduct.size() > 0) {
					for(String s : newProduct) {
						if(s.equals("")) 
							textBoxesFull = false;
					}
				}
				
				if(textBoxesFull) {
					// First must remove the old one after extracting data
					inventoryTable.removeRow(inventoryTable.getRowCount()-1);
					int tempRow = inventoryTable.getRowCount();
					// Add new row to the FlexTable & update the products list
					map.put(tempRow, Integer.parseInt(newProduct.get(1)));
					//products.add(Integer.parseInt(newProduct.get(1)));
					for(int col = 0; col < 7; col++) {
						if(col == 2) {
							final Image image = new Image(newProduct.get(col));
							image.addErrorHandler(new ErrorHandler() {
								public void onError(ErrorEvent event) {
									System.out.println("Error loading picture....");
								}
							});
							inventoryTable.setWidget(tempRow, col, image);
						} else if(col < 5)
							inventoryTable.setWidget(tempRow, col, new Label(newProduct.get(col)));
						else {
							Button editButton = new Button("Edit");
							Button deleteButton = new Button("Delete");
							
							inventoryTable.setWidget(tempRow, 5, editButton);
							inventoryTable.setWidget(tempRow, 6, deleteButton);
							//final int tempSKU = products.get(2);
							
							deleteButton.addClickHandler(new ClickHandler() { 
								@Override
								public void onClick(ClickEvent event) {
									deleteInventoryElement(inventoryTable.getCellForEvent(event).getRowIndex());
								}
							});
							
							editButton.addClickHandler(new ClickHandler() { 
								@Override
								public void onClick(ClickEvent event) {
									editInventoryElement(inventoryTable.getCellForEvent(event).getRowIndex());
								}
							});
						}
					}
					// Add a new row for users to add data
					tempRow = inventoryTable.getRowCount();
					for(int col = 0; col < 5; col++) {
						inventoryTable.setWidget(tempRow, col, new TextBox());
					}
				}
			}
		});
		
		RootPanel.get("inventoryContent").add(addButton);
		RootPanel.get("inventoryContent").add(inventoryTable);
		RootPanel.get("inventoryContent").setVisible(false);
	}

}
