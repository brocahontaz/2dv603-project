package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;

/**
 * Controller for the Splash window
 * 
 * @author Johan Andersson, Fredrik Norrman, David Larsson
 *
 */
public class SplashController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ProgressIndicator appLoadProgress;

	/**
	 * Initialize
	 */
	@FXML
	void initialize() {
		appLoadProgress.setProgress(-1);
	}
}
