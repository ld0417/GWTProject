package edu.ship.project.client.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author LaVonne Diller and http://www.tutorialspoint.com/gwt/gwt_celllist_widget.htm
 */
public class CustomerClient implements EntryPoint {
	
	/**
	 * A simple data type that represents a customer.
	 */
	private static class Customer{
		private static int nextId = 0;
		private final int id;
		private String name;
		
		public Customer(String name){
			Customer.nextId++;
			this.id = Customer.nextId;
			this.name = name;
		}
	}
	
	/**
	 * The list of data to display.
	 */
	private static final List<Customer> CUSTOMERS = Arrays.asList(
			new Customer("John Smith"),
			new Customer("Jane Brown"),
			new Customer("Nate Kuhn"),
			new Customer("LaVonne Diller"));
		
	/**
	 * Create a remote service proxy to talk to the server-side Customer service.
	 */
	private final CustomerServiceAsync customerService = GWT.create(CustomerService.class);
	
	private FlexTable customerTable = new FlexTable();
	private TextBox addNameBox = new TextBox();
	private ArrayList<String> customers = new ArrayList<String>();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Create Customer Table
		customerTable.setSize("200px", "24px");
		customerTable.setText(0, 0, "Name");
		customerTable.setText(0, 1, "Edit");
		customerTable.setText(0, 2, "Delete");
		
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
		
		// Handlers To Add Customer
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addCustomer();
			}
		});
		
		addNameBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addCustomer();
				}
			}
	    });
		
		// Load initial customers 
		addInitialCustomers();
	}

	private void addInitialCustomers() {
		for(final Customer c: CUSTOMERS){
			int row = customerTable.getRowCount();
		    customers.add(c.name);
		    customerTable.setText(row, 0, c.name);
		    
		    // Add a button to edit customer
		    Button editCustomerButton = new Button("Edit");
		    customerTable.setWidget(row, 1, editCustomerButton);

		    // Handler to edit customer
		    editCustomerButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  editCustomer(c.name);
		      }
		    });
		    
		    // Add a button to delete customer from the table.
		    Button removeCustomerButton = new Button("X");
		    customerTable.setWidget(row, 2, removeCustomerButton);

		    // Handler to delete customer
		    removeCustomerButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  deleteCustomer(c.name);
		      }
		    });
		}
	}

	/**
	 * Adds a customer to the customer table
	 */
	private void addCustomer() {
		 final String symbol = addNameBox.getText().trim();
		 addNameBox.setFocus(true);
		 
		 // TODO: Place in Field Verifier
		 // Name must be between 1 and 10 chars that are numbers, letters, or dots.
		 if (!symbol.matches("^[A-z\\s]$")) {
		      Window.alert("'" + symbol + "' is not a valid symbol.");
		      addNameBox.selectAll();
		      return;
		 }
		 addNameBox.setText("");
		 if (this.customers.contains(symbol)){
			 return;
		 }
		 else {
			int row = customerTable.getRowCount();
		    customers.add(symbol);
		    customerTable.setText(row, 0, symbol);
		    
		    // Add a button to edit customer
		    Button editCustomerButton = new Button("Edit");
		    customerTable.setWidget(row, 1, editCustomerButton);

		    // Handler to edit customer
		    editCustomerButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  editCustomer(symbol);
		      }
		    });
		    
		    // Add a button to delete customer from the table.
		    Button removeCustomerButton = new Button("x");
		    customerTable.setWidget(row, 2, removeCustomerButton);
		    
		    // Handler to delete customer
		    removeCustomerButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  deleteCustomer(symbol);
		      }
		    });
		 }
	}
	
	private void deleteCustomer(String name) {
		int removedIndex = customers.indexOf(name);
        customers.remove(removedIndex);
        customerTable.removeRow(removedIndex + 1);
	}
	
	private void editCustomer(String name) {
		int editIndex = customers.indexOf(name);
		customers.add(editIndex, name);
	}
}