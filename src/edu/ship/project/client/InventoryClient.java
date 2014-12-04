package edu.ship.project.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RootPanel;

public class InventoryClient implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// Grids must be sized explicitly, though they can be resized later.
		Grid grid = new Grid(6, 7);

		grid.setText(0, 0, "Description");
		grid.setText(0, 1, "SKU #");
		grid.setText(0, 2, "Link");
		grid.setText(0, 3, "Price");
		grid.setText(0, 4, "Inventory");

		String array[] = { "item description", "sku#",
				"<a href=\"#\">picture</a>", "$" };
		
		//TODO - Figure out how to incorporate a Click Handler for all grid operations.
		//grid.addClickHandler(handler);
		
		// Put some values in the grid cells.
		for (int row = 1; row < 6; ++row) {
			for (int col = 0; col < 6; ++col) {
				if (col < 4) {
					grid.setText(row, col, array[col] + row);
				} else {
					grid.setText(row, col, "" + row);
				}
			}
			grid.setWidget(row, 5, new Button("Edit"));
			grid.setWidget(row, 6, new Button("Delete"));
		}
		
		//Insert last row to be an addable row (fillable)?
		grid.insertRow(grid.getRowCount());
		
		grid.setCellPadding(5);
		grid.setBorderWidth(1);
		RootPanel.get("inventoryContent").add(grid);
		RootPanel.get("inventoryContent").setVisible(false);
	}

}
