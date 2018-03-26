package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;

public class Controller {

	private ArrayList<model.Guest> guests;
	private DBParser dbParser = new DBParser();
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private MenuItem vaxjoChange;

	@FXML
	private MenuItem kalmarChange;

	@FXML
	private MenuItem closeSystem;

	@FXML
	private Button formButton;

	@FXML
	private TextField addGuestFirstName;

	@FXML
	private TextField addGuestLastName;

	@FXML
	private TextField addGuestAddress;

	@FXML
	private TextField addGuestTelephone;

	@FXML
	private TextField addGuestCreditCard;

	@FXML
	private TextField addGuestPassport;

	@FXML
	private Button addGuestButton;

	@FXML
	private Button listAllGuests;
	
	@FXML
    private TitledPane searchGuestsBox;
	
	@FXML
    private TitledPane resultsBox;
	
	@FXML
    private TitledPane addGuestBox;
	
	@FXML
    private ListView<model.Guest> searchResultsList = new ListView<model.Guest>();

	@FXML
	void addNewGuest(MouseEvent event) {
		if (addGuestFirstName.getText().isEmpty()) {
			return;
		}
		if (addGuestLastName.getText().isEmpty()) {
			return;
		}
		if (addGuestAddress.getText().isEmpty()) {
			return;
		}
		if (addGuestTelephone.getText().isEmpty()) {
			return;
		}
		if (addGuestCreditCard.getText().isEmpty()) {
			return;
		}
		if (addGuestPassport.getText().isEmpty()) {
			return;
		}

		executor.submit(() -> {
			dbParser.addNewGuest(addGuestFirstName.getText(), addGuestLastName.getText(), addGuestAddress.getText(),
					addGuestTelephone.getText(), addGuestCreditCard.getText(), addGuestPassport.getText());
		});

	}

	@FXML
	void closeSystem(ActionEvent event) {
		System.exit(0);
	}
	
	@FXML
    void dragPane(MouseEvent event) {
		
    }

	@FXML
	void skrivHejIKonsolen(MouseEvent event) {
		System.out.println("HejIKonsolen");
	}

	@FXML
	void listAllGuests(MouseEvent event) {

		executor.submit(() -> {
			guests = dbParser.getAllGuests();
			searchResultsList.getItems().addAll(guests);
			for (model.Guest guest : guests) {
				System.out.println(guest.getFirstName() + " " + guest.getLastName());
				
				
				
			}
		});

	}
	
	@FXML
    void initialize() {
		DragResizeMod.makeResizable(searchGuestsBox);
		DragResizeMod.makeResizable(resultsBox);
		DragResizeMod.makeResizable(addGuestBox);
	}

}