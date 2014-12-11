package edu.ship.project.client.pos;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author LaVonne Diller
 */
public class PointOfSaleClient {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	
		// When POS button is selected, 
		//		prompt for customer name/info, 
		//		validate & add to receipt info (change customerInfo's text
		// Add items to receipt (from inventory)
		// Show total
		
		//TODO: if paid, remove quantities from inventory & attach receipt to customer
		//TODO: in customer tab, show list of customer transactions & totals
		
		VerticalPanel customerLabel = new VerticalPanel();
		Label customerInfo = new Label("John Smith");
		customerLabel.add(customerInfo);
		customerLabel.add(new Label("100 Random Drive"));
		customerLabel.add(new Label("Anywhere, USA 10000"));
		customerLabel.add(new Label("555-555-5555"));
		customerLabel.add(new HTML("<br><br>"));
		
		HorizontalPanel receiptTitle = new HorizontalPanel();
		receiptTitle.add(new Label("Receipt: 001"));
		receiptTitle.setHeight("50px");
		
		
		HorizontalPanel receiptSubTitle = new HorizontalPanel();
		Label description = new Label("Item Description");
		Label quantity = new Label("Quantity");
		Label price = new Label("Price");
		receiptSubTitle.add(description);
		receiptSubTitle.add(quantity);
		receiptSubTitle.add(price);
		receiptSubTitle.setCellWidth(description, "200px");
		receiptSubTitle.setCellWidth(quantity, "100px");
		receiptSubTitle.setCellWidth(price, "50px");
		
		VerticalPanel receiptPanel = new VerticalPanel();
		receiptPanel.add(customerLabel);
		receiptPanel.add(receiptTitle);
		receiptPanel.add(receiptSubTitle);
		
		RootPanel.get("pointOfSaleContent").add(receiptPanel);
		RootPanel.get("pointOfSaleContent").setVisible(false);
	}

	public void getCustomer() {
		
	}
}
