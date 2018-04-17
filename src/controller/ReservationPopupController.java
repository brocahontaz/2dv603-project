package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ReservationPopupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button close;

    @FXML
    void close(MouseEvent event) {
    	((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        

    }
}
