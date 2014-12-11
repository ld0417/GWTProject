package edu.ship.project.client.inventory;

import edu.ship.project.client.customer.CustomerService;
import edu.ship.project.client.customer.CustomerServiceAsync;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class InventoryClient implements EntryPoint {
	FlexTable inventoryTable = new FlexTable();
	HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	private final InventoryServiceAsync InventoryService = GWT.create(InventoryService.class);

	private void deleteInventoryElement(final int removeIndex) {
		//Get the SKU value from the cell
		String str = ((Label)inventoryTable.getWidget(removeIndex, 1)).getText();
		InventoryService.deleteElement(Integer.parseInt(str), new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
			}
			public void onSuccess(Void ingore) {
				inventoryTable.removeRow(removeIndex);
			}
		});
		map.remove(removeIndex);
		// Need to figure out how to remove an object from this list...
		// list.remove(removeIndex);
	}

	private void editInventoryElement(final int editIndex) {
		// put remove Labels/widgets then insert a row of TextBoxes with the
		// values
		// that were pulled in the beginning

		// final int editIndex = map.get(sku);
		// Should SKU be able to change?
		// Need to figure out how to remove an object from this list...
		// list.remove(editIndex);
		map.remove(editIndex);
		
		String tempStr = ((Label)inventoryTable.getWidget(editIndex, 1)).getText();
		InventoryService.deleteElement(Integer.parseInt(tempStr), new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
			}
			public void onSuccess(Void ingore) {
			}
		});

		// GET VALUES FROM LABELS
		ArrayList<String> editProduct = new ArrayList<String>();
		for (int col = 0; col < 5; col++) {
			Widget widg = inventoryTable.getWidget(editIndex, col);
			// System.out.println(widg.getClass().toString());
			if (widg instanceof Label) {
				String str = ((Label) widg).getText();
				editProduct.add(str);
			}
			if (widg instanceof Image) {
				String str = ((Image) widg).getUrl();
				editProduct.add(str);
			}
		}

		for (int col = 0; col < 5; col++) {
			TextBox textBox = new TextBox();
			textBox.getElement().setAttribute("placeholder",
					editProduct.get(col));
			inventoryTable.setWidget(editIndex, col, textBox);
		}

		// ADD DONE BUTTON
		Button doneButton = new Button("Done");
		inventoryTable.setWidget(editIndex, 5, doneButton);

		// ADD ONCLICK - CHECK ALL COLUMNS AND DELETE ROW AND INSERT NEW ROW
		// WITH LABELS
		doneButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// Change the TextBoxes back to Labels & Check if everything is
				// filled
				ArrayList<String> newProduct = new ArrayList<String>();
				for (int col = 0; col < 5; col++) {
					Widget widg = inventoryTable.getWidget(editIndex, col);
					if (widg instanceof TextBox) {
						String str = ((TextBox) widg).getValue();
						if (!str.equals(""))
							newProduct.add(str);
						else
							newProduct.add(((TextBox) widg).getElement()
									.getPropertyString("placeholder"));
					}
				}

				// Ensure that all TextBoxes have the correct Values (Should
				// check for SKU duplicates)
				boolean textBoxesFull = true;
				if (newProduct.size() > 0) {
					for (String s : newProduct) {
						if (s.equals(""))
							textBoxesFull = false;
					}
				}

				if (textBoxesFull) {
					// First must remove the old one after extracting data
					map.put(editIndex, Integer.parseInt(newProduct.get(1)));
					for (int col = 0; col < 7; col++) {
						if (col == 2) {
							final Image image = new Image(newProduct.get(col));
							image.addErrorHandler(new ErrorHandler() {
								public void onError(ErrorEvent event) {
									System.out
											.println("Error loading picture....");
								}
							});
							inventoryTable.setWidget(editIndex, col, image);
						} else if (col < 5)
							inventoryTable.setWidget(editIndex, col, new Label(
									newProduct.get(col)));
						else {
							Button editButton = new Button("Edit");
							Button deleteButton = new Button("Delete");

							inventoryTable.setWidget(editIndex, 5, editButton);
							inventoryTable
									.setWidget(editIndex, 6, deleteButton);

							deleteButton.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									deleteInventoryElement(inventoryTable
											.getCellForEvent(event)
											.getRowIndex());
								}
							});

							editButton.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									editInventoryElement(inventoryTable
											.getCellForEvent(event)
											.getRowIndex());
								}
							});
						}
					}
					
					AsyncCallback callback = new AsyncCallback<Void>() {
						public void onFailure(Throwable error) {
							Window.alert("Failed to add element!");
						}
						
						public void onSuccess(Void ignore) {
							//Could display?
						}
					};
					
					InventoryService.addElement(Integer.parseInt(newProduct.get(1)), newProduct.get(0), newProduct.get(2), Integer.parseInt(newProduct.get(3)), Integer.parseInt(newProduct.get(4)), callback);
				}
			}
		});
	}

	@Override
	public void onModuleLoad() { 
		//Load the top labels/row titles
		inventoryTable.setWidget(0, 0, new Label("Description"));
		inventoryTable.setWidget(0, 1, new Label("SKU #"));
		inventoryTable.setWidget(0, 2, new Label("Image/URL"));
		inventoryTable.setWidget(0, 3, new Label("Price"));
		inventoryTable.setWidget(0, 4, new Label("Number in Inventory"));
		
		/*inventoryService.loadElements(
				new AsyncCallback<ArrayList<InventoryElement>>() {
					public void onFailure(Throwable caught) {
						System.err.println("async customer server load failed");
						Window.alert("Initial Customer Load Failed");
					}

					public void onSuccess(ArrayList<InventoryElement> result) {
						System.err.println("async customer server load passed");
						for(final InventoryElement r: result){
							int row = inventoryTable.getRowCount();
							inventoryTable.setWidget(row, 0, new Label(r.getDescription()));
							inventoryTable.setWidget(row, 1, new Label("" + r.getSku()));
							inventoryTable.setWidget(row, 2, new Image(r.getUrl()));
							inventoryTable.setWidget(row, 3, new Label("" + r.getPrice()));
							inventoryTable.setWidget(row, 4, new Label("" + r.getNum()));
							
							//Add to the Client's list.
							list.add(r);
						    
							//Add the buttons
							Button editButton = new Button("Edit");
							Button deleteButton = new Button("Delete");
							
							inventoryTable.setWidget(row, 5, editButton);
							inventoryTable.setWidget(row, 6, deleteButton);
							
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
		});*/
		
		
		loadElements();
		
		
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
					//list.add(new InventoryElement(Integer.parseInt(newProduct.get(1)), newProduct.get(0), newProduct.get(2), Integer.parseInt(newProduct.get(3)), Integer.parseInt(newProduct.get(4))));
					
					AsyncCallback callback = new AsyncCallback<Void>() {
						public void onFailure(Throwable error) {
							Window.alert("Failed to add element!");
						}
						
						public void onSuccess(Void ignore) {
							//Could display?
						}
					};
					
					InventoryService.addElement(Integer.parseInt(newProduct.get(1)), newProduct.get(0), newProduct.get(2), Integer.parseInt(newProduct.get(3)), Integer.parseInt(newProduct.get(4)), callback);
					
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

	private void loadElements() {
		InventoryService
				.loadInitialElements(new AsyncCallback<ArrayList<String>>() {

					public void onFailure(Throwable error) {
					}

					public void onSuccess(ArrayList<String> elements) {
						for (int x = 0; x < elements.size(); x+=5) {
							int tempRow = inventoryTable.getRowCount();
							inventoryTable.setWidget(tempRow, 0,
									new Label(elements.get(x+1)));
							inventoryTable.setWidget(tempRow, 1, new Label(""
									+ elements.get(x)));
							inventoryTable.setWidget(tempRow, 2,
									new Image(elements.get(x+2)));
							inventoryTable.setWidget(tempRow, 3, new Label(""
									+ elements.get(x+3)));
							inventoryTable.setWidget(tempRow, 4, new Label(""
									+ elements.get(x+4)));
							
							Button editButton = new Button("Edit");
							Button deleteButton = new Button("Delete");

							inventoryTable.setWidget(tempRow, 5, editButton);
							inventoryTable.setWidget(tempRow, 6, deleteButton);

							deleteButton.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									deleteInventoryElement(inventoryTable
											.getCellForEvent(event)
											.getRowIndex());
								}
							});

							editButton.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									editInventoryElement(inventoryTable
											.getCellForEvent(event)
											.getRowIndex());
								}
							});
						}
					}
				});

	}

}
