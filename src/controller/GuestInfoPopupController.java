package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Guest;

public class GuestInfoPopupController {
	
	private Stage guestInfoPopup;


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
    	
    }
    

    void setupGuestInfoPopup(Guest guest) {
		System.out.print("--Setting up Guest Info popup.. ");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GuestInfoPopup.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 600, 550);
			guestInfoPopup = new Stage();
			guestInfoPopup.initModality(Modality.APPLICATION_MODAL);
			guestInfoPopup.setScene(scene);
			guestInfoPopup.setMinHeight(550);
			guestInfoPopup.setMinWidth(600);
			guestInfoPopup.setResizable(false);
			guestInfoPopup.initStyle(StageStyle.UNDECORATED);
			root.getScene().getWindow().sizeToScene();
			guestInfoPopup.setTitle("Guests");
			guestInfoPopup.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("done!\r");
		displayGuestInfo(guest);
    }
    
    void displayGuestInfo(Guest guest) {
    	System.out.println(guest.getFirstName());
    	/*guestInfoFirstname.setText("guest.getFirstName()");
    	guestInfoLastname.setText(guest.getLastName());
    	guestInfoAddress.setText(guest.getAddress());
    	guestInfoTelephone.setText(guest.getTelephoneNumber());
    	guestInfoCreditCard.setText(guest.getCreditCard());
    	guestInfoPassport.setText(guest.getPassportNumber());*/
    }

}

