package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;

public class SplashController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private ProgressIndicator appLoadProgress;

    @FXML
    void initialize() {
    	appLoadProgress.setProgress(-1);
    }
}
