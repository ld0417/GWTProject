package edu.ship.project.client;

import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CustomerClient implements EntryPoint {
	/**
	 * Create a remote service proxy to talk to the server-side Customer service.
	 */
	private final CustomerServiceAsync customerService = GWT.create(CustomerService.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		TextCell cell = new TextCell();
		//TODO: See cell widgets api
		
//		CellList cl = new CellList(cell);
//		
//		cl.setRowData(values);
		
		
		
		FlexTable ft = new FlexTable();
		ft.setBorderWidth(1);
		ft.setSize("200px", "24px");
		ft.setText(0, 0, "Hello");
		ft.setText(0, 1, "Bob");
		ft.setText(0, 2, "Smith");
		RootPanel.get("customerContent").add(ft);
		RootPanel.get("customerContent").setVisible(false);
		
	}
}
