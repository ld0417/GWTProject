package edu.ship.project.client;

import edu.ship.project.client.Directory.DirectoryClient;
import edu.ship.project.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTProject implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Create Log-In HTML
		final TextBox userNameField = new TextBox();
		userNameField.setSize("200px", "20px");		
		userNameField.setText("Username");
		
		final PasswordTextBox passwordField = new PasswordTextBox();
		passwordField.setSize("200px", "20px");
		passwordField.setText("Password");
		
		Button logInButton = new Button("Log In");
		logInButton.setSize("80px", "30px");
		
		final Label errorLabel = new Label();
		
		VerticalPanel logInPanel = new VerticalPanel(); 
		logInPanel.add(userNameField); 
		logInPanel.add(passwordField);
		logInPanel.add(logInButton);
		logInPanel.add(errorLabel);
		RootPanel.get("logInContainer").add(logInPanel);
		
		// Focus the cursor on the name field when the app loads
		userNameField.setFocus(true);
		userNameField.selectAll();
	
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox and open Menu
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				RootPanel.get("logInContainer").setVisible(false);
				RootPanel.get("titleContainer").setVisible(true);
				RootPanel.get("menuContainer").setVisible(true);
			}
		});
 
		// Create a handler for the Log In Buttons & Fields
		class LogInHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendLogInInfoToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendLogInInfoToServer();
				}
			}
			
			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendLogInInfoToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String userNameToServer = userNameField.getText();
				String passwordToServer = passwordField.getText();
				if (!FieldVerifier.isValidName(userNameToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}
				if(!FieldVerifier.isValidPassword(passwordToServer)){
					errorLabel.setText("Please enter a least four characters");
					return;
				}
				
				greetingService.greetServer(userNameToServer, passwordToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								System.err.println("async greet server failed");
								// Show the RPC error message to the user
								dialogBox.setText("Remote Procedure Call - Failure");
								serverResponseLabel.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Alert");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		LogInHandler logInHandler = new LogInHandler();
		logInButton.addClickHandler(logInHandler);
		userNameField.addKeyUpHandler(logInHandler);
		
		loadMenu(userNameField.getText());
	}

	private void loadMenu(String name) {
		DirectoryClient directoryWin = new DirectoryClient();
		directoryWin.setUsername(name);
		directoryWin.onModuleLoad();
	}
}