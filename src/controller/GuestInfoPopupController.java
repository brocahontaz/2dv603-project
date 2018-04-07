package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Guest;

public class GuestInfoPopupController {
	
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

    void setupGuestInfoPopup(Guest guest) {		
    	guestInfoFirstname.setText(guest.getFirstName());
    	guestInfoLastname.setText(guest.getLastName());
    	guestInfoAddress.setText(guest.getAddress());
    	guestInfoTelephone.setText(guest.getTelephoneNumber());
    	guestInfoCreditCard.setText(guest.getCreditCard());
    	guestInfoPassport.setText(guest.getPassportNumber());
    }
  
}

