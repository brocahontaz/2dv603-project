package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Guest;

public class Controller {

	private ObservableList<Guest> guests;
	private ObservableList<String> roomQualityChoices;
	private ObservableList<String> discountChoices;
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
	private TableView<model.Guest> searchResultTable;

	@FXML
	private TableColumn<model.Guest, String> firstNameCol;

	@FXML
	private TableColumn<model.Guest, String> lastNameCol;

	@FXML
	private TableColumn<model.Guest, String> passportCol;

	@FXML
	private TableColumn<model.Guest, String> telephoneCol;

	@FXML
	private ListView<model.Guest> searchResultsList = new ListView<model.Guest>();
	
	@FXML
    private ComboBox<String> roomQualityChoice;
	
	@FXML
    private ComboBox<String> discountChoice;

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
			guests = FXCollections.observableArrayList(dbParser.getAllGuests());
			searchResultTable.setItems(guests);
		});

	}

	@FXML
	void checkSingularGuest(MouseEvent event) {
		//System.out.println(searchResultTable.getSelectionModel().getSelectedItem().getFirstName());
		
	}
	
	@FXML
    void checkInGuest(MouseEvent event) {

    }

    @FXML
    void checkOutGuest(MouseEvent event) {
    	
    }
    
    @FXML
    void makeReservation(MouseEvent event) {

    }

	@FXML
	void initialize() {
		firstNameCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("lastName"));
		passportCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("passportNumber"));
		telephoneCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("telephoneNumber"));
		
		roomQualityChoices = FXCollections.observableArrayList(
			    "Single", "Double", "Suite");
		discountChoices = FXCollections.observableArrayList(
			    "5%", "10%", "15%", "20%");
		
		roomQualityChoice.setItems(roomQualityChoices);
		discountChoice.setItems(discountChoices);
		
		// guests = FXCollections.observableArrayList(dbParser.getAllGuests());
		// searchResultTable.setItems(guests);
	}

}