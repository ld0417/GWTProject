package edu.ship.project.client.Directory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.ship.project.client.InventoryClient;
import edu.ship.project.client.customer.CustomerClient;

public class DirectoryClient {

	private String username;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Create Title Bar
		final Button logOutButton = new Button("Log Out");
		logOutButton.setSize("80px", "28px");
		Label welcomeLabel = new Label();
		welcomeLabel.setText("Hello, " + this.username);
		
		HorizontalPanel titlePanel = new HorizontalPanel();
		titlePanel.add(welcomeLabel);
		titlePanel.add(logOutButton);
		titlePanel.setSpacing(10);
		RootPanel.get("titleContainer").add(titlePanel);
		RootPanel.get("titleContainer").setVisible(false);
		
		
		// Create Start-Up Menu
		final Button inventoryButton = new Button("Inventory");
		inventoryButton.setSize("200px", "30px");
		final Button customerButton = new Button("Customers");
		customerButton.setSize("200px", "30px");
		final Button posButton = new Button("Point Of Sale");
		posButton.setSize("200px", "30px");
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(inventoryButton);
		vPanel.add(customerButton);
		vPanel.add(posButton);
		RootPanel.get("menuContainer").add(vPanel);
		RootPanel.get("menuContainer").setVisible(false);
		
		// Handles logging a user out
		logOutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("logInContainer").setVisible(true);
				RootPanel.get("inventoryContent").setVisible(false);
				RootPanel.get("titleContainer").setVisible(false);
				RootPanel.get("menuContainer").setVisible(false);
			}
		});
		
		// Handles opening the customer tab
		customerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("titleContainer").setVisible(false);
				RootPanel.get("menuContainer").setVisible(false);
				RootPanel.get("subTitleContainer").setVisible(true);
				RootPanel.get("customerContent").setVisible(true);
				RootPanel.get("inventoryContent").setVisible(false);
				customerWin.loadCustomerList();
			}
		});
		
		// Handles opening the customer tab
		inventoryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("titleContainer").setVisible(false);
				RootPanel.get("menuContainer").setVisible(false);
				RootPanel.get("subTitleContainer").setVisible(true);
				RootPanel.get("customerContent").setVisible(false);
				RootPanel.get("inventoryContent").setVisible(true);
			}
		});
		
		loadCustomerWindow();
		loadInventoryWindow();
	}
	
	

//	/**
//	 * This is the entry point method.
//	 */
//	public void onModuleLoad() {
//		// Create Title Bar
//		final Button logOutButton = new Button("Log Out");
//		logOutButton.setSize("80px", "28px");
//		
//		Label welcomeLabel = new Label();
//		welcomeLabel.setText("Hello, " + this.username);
//		
//		HorizontalPanel titlePanel = new HorizontalPanel();
//		titlePanel.add(welcomeLabel);
//		titlePanel.add(logOutButton);
//		titlePanel.setSpacing(20);
//		RootPanel.get("titleContainer").add(titlePanel);
//		
//		// Create Tabs
//		TabLayoutPanel tabs = new TabLayoutPanel(2, Unit.EM);
//		tabs.add(new HTML("Inventory Content"), "Inventory");
//		tabs.add(new HTML("Customer Content"), "Customers");
//		tabs.add(new HTML("Point Of Sale Content"), "POS");
//		RootPanel.get("tabContainer").add(tabs);
//		
//		// TODO: not sure how to set divs visible/invisible when moving between tabs
//		
//		// Handles logging a user out
//		logOutButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				RootPanel.get("logInContainer").setVisible(true);
//				RootPanel.get("titleContainer").setVisible(false);
//				RootPanel.get("tabContainer").setVisible(false);
//				RootPanel.get("customerContent").setVisible(false);
//				
//				//TODO: login button seems to be deactivated
//			}
//		});
//	}

	private CustomerClient customerWin = new CustomerClient();
	private void loadCustomerWindow() {
		System.err.println("customer window loaded");
		customerWin.setUsername(this.username);
		customerWin.onModuleLoad();
	}
	
	private void loadInventoryWindow() {
		InventoryClient inventoryWindow = new InventoryClient();
		inventoryWindow.onModuleLoad();
	}

	public void setUsername(String name) {
		this.username = name;
	}
}
