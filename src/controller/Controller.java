package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Guest;

public class Controller {

	private ObservableList<Guest> guests;
	private ObservableList<String> roomQualityChoices;
	private ObservableList<String> discountChoices;
	private DBParser dbParser = new DBParser();
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	@FXML
    private ProgressIndicator dbLoad;
	
	@FXML
	private TitledPane makeReservationBox;

	@FXML
	private Button pickGuestButton;

	@FXML
	private Button pickSpecificRoomButton;

	@FXML
	private DatePicker arrivalDate;

	@FXML
	private DatePicker departureDate;

	@FXML
	private ComboBox<String> roomQualityChoice;

	@FXML
	private ComboBox<String> discountChoice;

	@FXML
	private Button makeReservationButton;

	@FXML
	private TitledPane checkReservationsBox;

	@FXML
	private TitledPane checkRoomsBox;

	@FXML
	private TitledPane resultsBox2;

	@FXML
	private TableView<?> searchResultTable2;

	@FXML
	private TableColumn<?, ?> roomNumberCol;

	@FXML
	private TableColumn<?, ?> qualityCol;

	@FXML
	private TableColumn<?, ?> priceCol;

	@FXML
	private TableColumn<?, ?> reservedCol;

	@FXML
	private TitledPane checkInGuestsBox;

	@FXML
	private TextField checkInReservationID;

	@FXML
	private Button chooseReservationButtonCheckIn;

	@FXML
	private TextField checkInFirstName;

	@FXML
	private TextField checkInLastName;

	@FXML
	private TextField checkInAddress;

	@FXML
	private TextField checkInTelephone;

	@FXML
	private TextField checkInCreditCard;

	@FXML
	private TextField checkInPassportNumber;

	@FXML
	private TextField checkInArrivalDate;

	@FXML
	private TextField checkInDepartureDate;

	@FXML
	private Button checkInButton;

	@FXML
	private TitledPane checkOutGuestsBox;

	@FXML
	private TextField checkOutReservationID;

	@FXML
	private Button chooseReservationButtonCheckOut;

	@FXML
	private TextField checkOutFirstName;

	@FXML
	private TextField checkOutLastName;

	@FXML
	private TextField checkOutAddress;

	@FXML
	private TextField checkOutTelephone;

	@FXML
	private TextField checkOutCreditCard;

	@FXML
	private TextField checkOutPassportNumber;

	@FXML
	private TextField checkOutArrivalDate;

	@FXML
	private TextField checkOutDepartureDate;

	@FXML
	private Button checkOutButton;

	@FXML
	private TitledPane searchGuestsBox;

	@FXML
	private TextField searchGuestFirstName;

	@FXML
	private TextField searchGuestLastName;

	@FXML
	private TextField searchGuestAddress;

	@FXML
	private TextField searchGuestTelephone;

	@FXML
	private TextField searchGuestCreditCard;

	@FXML
	private TextField searchGuestPassportNumber;

	@FXML
	private Button searchGuestButton;

	@FXML
	private Button listAllGuestsButton;

	@FXML
	private TitledPane addGuestBox;

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
	private TitledPane resultsBox;

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
	private MenuItem vaxjoChange;

	@FXML
	private MenuItem kalmarChange;

	@FXML
	private MenuItem closeSystem;

	@FXML
	void checkInGuest(MouseEvent event) {

	}

	@FXML
	void checkOutGuest(MouseEvent event) {

	}

	@FXML
	void checkSingularGuest(MouseEvent event) {

	}

	@FXML
	void chooseReservationCheckIn(MouseEvent event) {

	}

	@FXML
	void chooseReservationCheckOut(MouseEvent event) {

	}

	@FXML
	void dragPane(MouseEvent event) {

	}

	@FXML
	void makeReservation(MouseEvent event) {

	}

	@FXML
	void pickGuest(MouseEvent event) {
		try {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/view/PickGuestPopup.fxml"));
		Scene scene = new Scene(root,800,600);		
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setScene(scene);
		popup.setMinHeight(400);
		popup.setMinWidth(600);
		popup.setMaxHeight(400);
		popup.setMaxWidth(600);
		popup.show();
		popup.setTitle("Guests");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void pickSpecificRoom(MouseEvent event) {
		try {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/view/PickRoomPopup.fxml"));
		Scene scene = new Scene(root,800,600);		
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setScene(scene);
		popup.setMinHeight(400);
		popup.setMinWidth(600);
		popup.setMaxHeight(400);
		popup.setMaxWidth(600);
		popup.show();
		popup.setTitle("Rooms");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void searchGuests(MouseEvent event) {
		executor.submit(() -> {
			guests = FXCollections.observableArrayList(dbParser.searchGuests(searchGuestFirstName.getText(),
					searchGuestLastName.getText(), searchGuestAddress.getText(), searchGuestTelephone.getText(),
					searchGuestCreditCard.getText(), searchGuestPassportNumber.getText()));
			searchResultTable.setItems(guests);
		});

	}

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
	void initialize() {
		firstNameCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("lastName"));
		passportCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("passportNumber"));
		telephoneCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("telephoneNumber"));

		roomQualityChoices = FXCollections.observableArrayList("Single", "Double", "Suite");
		discountChoices = FXCollections.observableArrayList("5%", "10%", "15%", "20%");

		roomQualityChoice.setItems(roomQualityChoices);
		discountChoice.setItems(discountChoices);

		// guests = FXCollections.observableArrayList(dbParser.getAllGuests());
		// searchResultTable.setItems(guests);
	}

}