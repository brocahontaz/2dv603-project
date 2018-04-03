package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PickGuestPopupController {
	
	@FXML
    private Button popupGuestSearchButton;

    @FXML
    private Button closeGuestsPopUpButton;
    
    @FXML
    private TextField popupGuestSearch;
    
	@FXML
    void closeGuestsPopUp(MouseEvent event) {
		((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void popupGuestSearch(MouseEvent event) {

    }

}
