package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Guest;

public class GuestInfoPopupController {
	
	private DBParser dbParser = new DBParser();
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private String tempPassportnumber; // There to make it able to change the current passport number aswell
	
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
    
	@FXML
	void updateGuest(MouseEvent event) {
		executor.submit(() -> {
			String firstName = guestInfoFirstname.getText();
			String lastName = guestInfoLastname.getText();
			String address = guestInfoAddress.getText();
			String telephone = guestInfoTelephone.getText();
			String creditCard = guestInfoCreditCard.getText();
			String passport = guestInfoPassport.getText();
			dbParser.updateGuest(firstName, lastName, address, telephone, creditCard, passport, tempPassportnumber);	
		});
	}

    void setupGuestInfoPopup(Guest guest) {		
    	guestInfoFirstname.setText(guest.getFirstName());
    	guestInfoLastname.setText(guest.getLastName());
    	guestInfoAddress.setText(guest.getAddress());
    	guestInfoTelephone.setText(guest.getTelephoneNumber());
    	guestInfoCreditCard.setText(guest.getCreditCard());
    	guestInfoPassport.setText(guest.getPassportNumber());
    	tempPassportnumber = guest.getPassportNumber();
    }
  
}

