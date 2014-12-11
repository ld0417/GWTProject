package edu.ship.project.client.customer;

import java.util.ArrayList;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author LaVonne Diller
 */
public class CustomerClient implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Customer service.
	 */
	private final CustomerServiceAsync customerService = GWT.create(CustomerService.class);
	private FlexTable customerTable = new FlexTable();
	private TextBox addNameBox = new TextBox();
	private String username;
	private ArrayList<String> customers = new ArrayList<String>();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Create Title Bar
		System.err.println("customer on module load called");
		final Button logOutButton = new Button("Log Out");
		logOutButton.setSize("80px", "28px");
		final Button menuButton = new Button("Menu");
		menuButton.setSize("80px", "28px");
		Label welcomeLabel = new Label();
		welcomeLabel.setText("Hello, " + this.username);
		
		HorizontalPanel titlePanel = new HorizontalPanel();
		titlePanel.add(welcomeLabel);
		titlePanel.add(menuButton);
		titlePanel.add(logOutButton);
		titlePanel.setSpacing(10);
		RootPanel.get("subTitleContainer").add(titlePanel);
		RootPanel.get("subTitleContainer").setVisible(false);
		
		// Create Customer Table
		customerTable.setSize("200px", "24px");
		customerTable.setText(0, 0, "Name");
		customerTable.setText(0, 1, "Delete");
		//customerTable.setText(0, 2, "Transactions");
		
		// Create Add Customer Panel
		HorizontalPanel customerInput = new HorizontalPanel();
		Button addButton = new Button("Add");
		customerInput.add(addNameBox);
		customerInput.add(addButton);
		
		// Create Customer Panel
		VerticalPanel customerPanel = new VerticalPanel();
		customerPanel.add(customerTable);
		customerPanel.add(customerInput);
		
		RootPanel.get("customerContent").add(customerPanel);
		RootPanel.get("customerContent").setVisible(false);
		
		addNameBox.setFocus(true);
		
		// Handles logging a user out
		logOutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("logInContainer").setVisible(true);
				
				RootPanel.get("subTitleContainer").setVisible(false);
				RootPanel.get("customerContent").setVisible(false);
				RootPanel.get("inventoryContent").setVisible(false);
				RootPanel.get("pointOfSaleContent").setVisible(false);
			}
		});
		
		// Handles going back to the menu
		menuButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("subTitleContainer").setVisible(false);
				RootPanel.get("customerContent").setVisible(false);
				RootPanel.get("inventoryContent").setVisible(false);
				RootPanel.get("pointOfSaleContent").setVisible(false);
				
				RootPanel.get("titleContainer").setVisible(true);
				RootPanel.get("menuContainer").setVisible(true);
			}
		});
		
		// Handlers To Add Customer
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addCustomer();
			}
		});
		
		// Handlers To Add Customer
		addNameBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addCustomer();
				}
			}
	    });
		
		System.err.println("load inital lists here");
		loadInitialCustomerList();
	}
	
	private void loadInitialCustomerList(){
		customerService.loadInitialCustomers(
				new AsyncCallback<ArrayList<String>>() {
					public void onFailure(Throwable caught) {
						System.err.println("async customer server initial load failed");
						Window.alert("Initial Customer Load Failed");
					}

					public void onSuccess(ArrayList<String> result) {
						System.err.println("async customer server initial load passed");
						// Lists are loaded when Customer button is selected
					}
		});
	}
	
	public void loadCustomerList() {
		// Clear table & array List
		System.err.println("row count: " + customerTable.getRowCount());
		if(customerTable.getRowCount() > 1)
		{
			customerTable.removeAllRows();
			customers.clear();	
		}
		customerTable.setText(0, 0, "Name");
		customerTable.setText(0, 1, "Delete");
		// Load saved objects
		loadCustomers();
	}
	
	private void loadCustomers() {
		customerService.loadCustomers(
				new AsyncCallback<ArrayList<String>>() {
					public void onFailure(Throwable caught) {
						System.err.println("async customer server load failed");
						Window.alert("Initial Customer Load Failed");
					}

					public void onSuccess(ArrayList<String> result) {
						System.err.println("async customer server load passed");
						for(final String r: result){
							int row = customerTable.getRowCount();
							customerTable.setText(row, 0, r);
							customers.add(r);
						    
						    // Add a button to delete customer from the table.
						    Button removeCustomerButton = new Button("x");
						    customerTable.setWidget(row, 1, removeCustomerButton);
		
						    // Handler to delete customer
						    removeCustomerButton.addClickHandler(new ClickHandler() {
						    	public void onClick(ClickEvent event) {
						    		deleteCustomer(r);
						    	}
						    });
						}
					}
		});
	}

	/**
	 * Adds a customer to the customer table
	 */
	private void addCustomer() {
		 String input = addNameBox.getText().trim();
		 addNameBox.setFocus(true);
		 if ( (!input.matches("[a-zA-Z]*")) && (!input.contains(" ")) ) {
		      Window.alert("'" + input + "' is not a valid symbol.");
		      addNameBox.selectAll();
		 }else{
			 addNameBox.setText("");
			 customerService.addCustomer(input, 
					new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							System.err.println("async customer server - add customer failed");
							Window.alert("Command To Add Customer Failed");
						}
	
						public void onSuccess(final String result) {
							System.err.println("async customer server - add customer success");
							
							int row = customerTable.getRowCount();
						    customerTable.setText(row, 0, result);
						    customers.add(result);
				
						    // Add a button to delete customer from the table.
						    Button removeCustomerButton = new Button("x");
						    customerTable.setWidget(row, 1, removeCustomerButton);
						    
						    // Handler to delete customer
						    removeCustomerButton.addClickHandler(new ClickHandler() {
						      public void onClick(ClickEvent event) {
						    	  deleteCustomer(result);
						      }
						    });
						}
				});
		 }

	}
	
	private void deleteCustomer(String name) {
		customerService.deleteCustomer(name, 
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						System.err.println("async customer server - delete customer failed");
						Window.alert("Command To Delete Customer Failed");
					}

					public void onSuccess(String result) {
						System.err.println("async customer server - delete customer success");
						int removedIndex = customers.indexOf(result);
						customers.remove(removedIndex);
				        customerTable.removeRow(removedIndex + 1);	
					}
		});
	}
	
	public void setUsername(String name) {
		this.username = name;
	}
}