package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import model.Guest;

public class GuestInfoPopupController {
	
	private DBParser dbParser = new DBParser();
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private String tempPassportnumber; // There to make it able to change the current passport number aswell
	
	@FXML
	private TitledPane guestInfoBox;
	
	@FXML
    private TextField guestInfoFirstname;

    @FXML
    private TextField guestInfoLastname;

    @FXML
    private TextField guestInfoAddress;

    @FXML
    private TextField guestInfoTelephone;

    @FXML
    private TextField guestInfoCreditCard;

    @FXML
    private TextField guestInfoPassport;

	
    @FXML
    private Button closeGuestInfoPopup;

    @FXML
    void closeGuestInfoPopup(MouseEvent event) {
    	((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    /*
     * Updates the guests info in the database
     */
	@FXML
	void updateGuest(MouseEvent event) {		
		if (guestInfoFirstname.getText().isEmpty()) {
			guestInfoFirstname.setPromptText("You need to enter a firstname!");
			colorNotificationTitledPane(guestInfoBox, "danger");
			return;
		}
		if (guestInfoLastname.getText().isEmpty()) {
			guestInfoLastname.setPromptText("You need to enter a lastname!");
			colorNotificationTitledPane(guestInfoBox, "danger");
			return;
		}
		if (guestInfoAddress.getText().isEmpty()) {
			guestInfoAddress.setPromptText("You need to enter a adress!");
			colorNotificationTitledPane(guestInfoBox, "danger");
			return;
		}
		if (guestInfoTelephone.getText().isEmpty()) {
			guestInfoTelephone.setPromptText("You need to enter a telephone number!!");
			colorNotificationTitledPane(guestInfoBox, "danger");
			return;
		}
		if (guestInfoCreditCard.getText().isEmpty()) {
			guestInfoCreditCard.setPromptText("You need to enter a credit card number!");
			colorNotificationTitledPane(guestInfoBox, "danger");
			return;
		}
		if (guestInfoPassport.getText().isEmpty()) {
			guestInfoPassport.setPromptText("You need to enter a passportnumber!");
			colorNotificationTitledPane(guestInfoBox, "danger");
			return;
		}

		executor.submit(() -> {
			String firstName = guestInfoFirstname.getText();
			String lastName = guestInfoLastname.getText();
			String address = guestInfoAddress.getText();
			String telephone = guestInfoTelephone.getText();
			String creditCard = guestInfoCreditCard.getText();
			String passport = guestInfoPassport.getText();
			
			if (dbParser.updateGuest(firstName, lastName, address, telephone, creditCard, passport, tempPassportnumber) == true) {
				colorNotificationTitledPane(guestInfoBox, "success");
			} else {
				colorNotificationTitledPane(guestInfoBox, "danger");
			}				
		});
	}

	/*
	 * Setting the textfields contents to the guests current information
	 */
    void setupGuestInfoPopup(Guest guest) {		
    	guestInfoFirstname.setText(guest.getFirstName());
    	guestInfoLastname.setText(guest.getLastName());
    	guestInfoAddress.setText(guest.getAddress());
    	guestInfoTelephone.setText(guest.getTelephoneNumber());
    	guestInfoCreditCard.setText(guest.getCreditCard());
    	guestInfoPassport.setText(guest.getPassportNumber());
    	tempPassportnumber = guest.getPassportNumber();
    }
    
	private void colorNotificationTitledPane(TitledPane pane, String cssStyle) {
		executor.submit(() -> {
			try {
				pane.getStyleClass().remove("info");
				pane.getStyleClass().add(cssStyle);
				Thread.sleep(3000);
				pane.getStyleClass().remove(cssStyle);
				pane.getStyleClass().add("info");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
  
}

