package controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import model.Guest;
import model.Hotel;
import model.Room;
import model.RoomQuality;

public class Controller {

	private ObservableList<Guest> guests;
	private ObservableList<Hotel> hotels;
	private ArrayList<RoomQuality> roomQualities;
	private ArrayList<Discount> hotelDiscounts;
	private DBParser dbParser = new DBParser();
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Stage roomPopup;
	private Stage guestPopup;
	private Guest pickedGuest = null;
	private Room pickedRoom = null;
	private static final String DEFAULT_HOTEL_CHOICE = "Hotel Preference";
	private Hotel defaultHotel = new Hotel(DEFAULT_HOTEL_CHOICE, "");

	/**
	 * TEXT FIELDS
	 */

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
	private TextField checkOutReservationID;

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
	private TextField checkInReservationID;

	@FXML
	private TextField makeReservationGuest;

	@FXML
	private TextField makeReservationRoom;

	/**
	 * TEXT
	 */

	@FXML
	private Text estimatedPrice;

	/**
	 * BUTTONS
	 */

	@FXML
	private Button searchGuestButton;

	@FXML
	private Button listAllGuestsButton;

	@FXML
	private Button pickGuestButton;

	@FXML
	private Button pickSpecificRoomButton;

	@FXML
	private Button makeReservationButton;

	@FXML
	private Button chooseReservationButtonCheckIn;

	@FXML
	private Button checkInButton;

	@FXML
	private Button checkOutButton;

	@FXML
	private Button chooseReservationButtonCheckOut;

	@FXML
	private Button addGuestButton;

	@FXML
	private Button clearReservationButton;

	/**
	 * TITLED PANES
	 */

	@FXML
	private TitledPane addGuestBox;

	@FXML
	private TitledPane makeReservationBox;

	@FXML
	private TitledPane checkReservationsBox;

	@FXML
	private TitledPane checkRoomsBox;

	@FXML
	private TitledPane resultsBox2;

	@FXML
	private TitledPane checkInGuestsBox;

	@FXML
	private TitledPane checkOutGuestsBox;

	@FXML
	private TitledPane searchGuestsBox;

	@FXML
	private TitledPane resultsBox;

	/**
	 * COMBO BOXES
	 */

	@FXML
	private ComboBox<RoomQuality> roomQualityChoice;

	@FXML
	private ComboBox<Integer> discountChoice;

	@FXML
	private ComboBox<Hotel> hotelChoice;

	/**
	 * PROGRESS INDICATORS
	 */

	@FXML
	private ProgressIndicator dbLoad;

	/**
	 * DATE PICKERS
	 */

	@FXML
	private DatePicker arrivalDate;

	@FXML
	private DatePicker departureDate;

	/**
	 * TABLE VIEWS
	 */

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
	private TableView<model.Guest> searchResultTable;

	@FXML
	private TableColumn<model.Guest, String> firstNameCol;

	@FXML
	private TableColumn<model.Guest, String> lastNameCol;

	@FXML
	private TableColumn<model.Guest, String> passportCol;

	@FXML
	private TableColumn<model.Guest, String> telephoneCol;

	/**
	 * MENU ITEMS
	 */

	@FXML
	private MenuItem vaxjoChange;

	@FXML
	private MenuItem kalmarChange;

	@FXML
	private MenuItem closeSystem;

	/**
	 * Check in guest
	 * 
	 * @param event
	 */
	@FXML
	void checkInGuest(MouseEvent event) {

	}

	/**
	 * Check out guest
	 * 
	 * @param event
	 */
	@FXML
	void checkOutGuest(MouseEvent event) {

	}

	@FXML
	void checkSingularGuest(MouseEvent event) {

	}

	/**
	 * Choose reservation to check in
	 * 
	 * @param event
	 */
	@FXML
	void chooseReservationCheckIn(MouseEvent event) {

	}

	/**
	 * Choose reservation to check out
	 * 
	 * @param event
	 */
	@FXML
	void chooseReservationCheckOut(MouseEvent event) {

	}

	/**
	 * Drag a pane
	 * 
	 * @param event
	 */
	@FXML
	void dragPane(MouseEvent event) {

	}

	/**
	 * Make a reservation
	 * 
	 * @param event
	 */
	@FXML
	void makeReservation(MouseEvent event) {
		// dbParser.makeReservation(pickedGuest.getPassportNumber(), );
	}

	@FXML
	void clearReservation(MouseEvent event) {
		pickedGuest = null;
		makeReservationGuest.setText("");
		arrivalDate.setValue(null);
		departureDate.setValue(null);
		roomQualityChoice.getSelectionModel().clearSelection();
		discountChoice.getSelectionModel().clearSelection();
		hotelChoice.getSelectionModel().clearSelection();
		displayAllQualities();
		displayAllDiscounts();
	}

	/**
	 * Pick a guest
	 * 
	 * @param event
	 */
	@FXML
	void pickGuest(MouseEvent event) {
		guestPopup.show();
	}

	private void setupGuestPopUp() {
		System.out.print("--Setting up guest popup.. ");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PickGuestPopup.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 600, 400);
			guestPopup = new Stage();
			guestPopup.initModality(Modality.APPLICATION_MODAL);
			guestPopup.setScene(scene);
			guestPopup.setMinHeight(400);
			guestPopup.setMinWidth(600);
			guestPopup.setResizable(false);
			guestPopup.initStyle(StageStyle.UNDECORATED);
			root.getScene().getWindow().sizeToScene();
			guestPopup.setTitle("Guests");
			loader.<PickGuestPopupController>getController().injectMainController(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("done!\r");
	}

	public void displayPickedGuest(Guest guest) {
		pickedGuest = guest;
		makeReservationGuest.setText(guest.getFirstName() + " " + guest.getLastName());
	}

	/**
	 * Pick a room
	 * 
	 * @param event
	 */
	@FXML
	void pickSpecificRoom(MouseEvent event) {
		roomPopup.show();
	}

	private void setupRoomPopUp() {
		System.out.print("--Setting up room popup.. ");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PickRoomPopup.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 600, 400);
			roomPopup = new Stage();
			roomPopup.initModality(Modality.APPLICATION_MODAL);
			roomPopup.setScene(scene);
			roomPopup.setMinHeight(400);
			roomPopup.setMinWidth(600);
			roomPopup.setResizable(false);
			roomPopup.initStyle(StageStyle.UNDECORATED);
			root.getScene().getWindow().sizeToScene();
			roomPopup.setTitle("Rooms");
			loader.<PickRoomPopupController>getController().injectMainController(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("done!\r");
	}

	public void displayPickedRoom(Room room) {
		pickedRoom = room;
		makeReservationRoom.setText(room.getRoomNumber() + "");
	}

	private void colorNotificationTitledPane(TitledPane pane, String cssStyle) {
		executor.submit(() -> {
			try {
				pane.getStyleClass().remove("info");
				pane.getStyleClass().add(cssStyle);
				Thread.sleep(3000);
				pane.getStyleClass().remove(cssStyle);
				pane.getStyleClass().add("info");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/**
	 * Search guests, filtered
	 * 
	 * @param event
	 */
	@FXML
	void searchGuests(MouseEvent event) {
		executor.submit(() -> {
			guests = FXCollections.observableArrayList(dbParser.searchGuests(searchGuestFirstName.getText(),
					searchGuestLastName.getText(), searchGuestAddress.getText(), searchGuestTelephone.getText(),
					searchGuestCreditCard.getText(), searchGuestPassportNumber.getText()));
			searchResultTable.setItems(guests);
		});

	}

	/**
	 * Add a new guest
	 * 
	 * @param event
	 */
	@FXML
	void addNewGuest(MouseEvent event) {
		if (addGuestFirstName.getText().isEmpty()) {
			colorNotificationTitledPane(addGuestBox, "danger");
			return;
		}
		if (addGuestLastName.getText().isEmpty()) {
			colorNotificationTitledPane(addGuestBox, "danger");
			return;
		}
		if (addGuestAddress.getText().isEmpty()) {
			colorNotificationTitledPane(addGuestBox, "danger");
			return;
		}
		if (addGuestTelephone.getText().isEmpty()) {
			colorNotificationTitledPane(addGuestBox, "danger");
			return;
		}
		if (addGuestCreditCard.getText().isEmpty()) {
			colorNotificationTitledPane(addGuestBox, "danger");
			return;
		}
		if (addGuestPassport.getText().isEmpty()) {
			colorNotificationTitledPane(addGuestBox, "danger");
			return;
		}

		executor.submit(() -> {
			if (dbParser.addNewGuest(addGuestFirstName.getText(), addGuestLastName.getText(), addGuestAddress.getText(),
					addGuestTelephone.getText(), addGuestCreditCard.getText(), addGuestPassport.getText()) == true) {
				colorNotificationTitledPane(addGuestBox, "success");
			} else {
				colorNotificationTitledPane(addGuestBox, "danger");
			}
		});

	}

	/**
	 * Close the software
	 * 
	 * @param event
	 */
	@FXML
	void closeSystem(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * Write "HejIKonsolen" in the console
	 * 
	 * @param event
	 */
	@FXML
	void skrivHejIKonsolen(MouseEvent event) {
		System.out.println("HejIKonsolen");
	}

	/**
	 * List all guests
	 * 
	 * @param event
	 */
	@FXML
	void listAllGuests(MouseEvent event) {

		executor.submit(() -> {
			guests = FXCollections.observableArrayList(dbParser.getAllGuests());
			searchResultTable.setItems(guests);
		});

	}

	private void initializeHotels() {

		System.out.println("#Initializing hotels.. ");

		hotels = FXCollections.observableArrayList(dbParser.getHotels());
		hotels.add(0, defaultHotel);

		hotelChoice.setItems(hotels);
		hotelChoice.getSelectionModel().selectFirst();

		initializeHotelQualities();
		initializeHotelDiscounts();

		for (Hotel hotel : hotels) {

			setHotelQualities(hotel);
			setHotelDiscounts(hotel);

		}

		displayAllQualities();
		displayAllDiscounts();

		hotelChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Hotel>() {
			@Override
			public void changed(ObservableValue<? extends Hotel> observable, Hotel oldValue, Hotel newValue) {
				if (newValue != null) {

					displayHotelQualities(newValue);
					displayHotelDiscounts(newValue);

				}
			}

		});

		roomQualityChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RoomQuality>() {
			@Override
			public void changed(ObservableValue<? extends RoomQuality> observable, RoomQuality oldValue,
					RoomQuality newValue) {
				if (newValue != null) {
					//estimatedPrice.setText(Integer.toString((newValue.getPrice())));
					displayEstimatedPrice();
				}
			}

		});
		
		discountChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue,
					Integer newValue) {
				if (newValue != null) {
					displayEstimatedPrice();
				}
			}

		});

		System.out.println("#Hotels initialized!");
	}

	private void initializeHotelQualities() {
		roomQualities = dbParser.getQualities();

	}

	private void setHotelQualities(Hotel hotel) {

		ArrayList<RoomQuality> temp = new ArrayList<RoomQuality>();

		if (!hotel.equals(defaultHotel)) {

			for (RoomQuality rq : roomQualities) {
				if (rq.getHotelName().equals(hotel.getName())) {
					temp.add(rq);
				}
			}

		} else {
			for (RoomQuality rq : roomQualities) {
				if (!temp.contains(rq)) {
					temp.add(rq);
				}
			}
		}

		hotel.setQualities(temp);
	}

	private void displayHotelQualities(Hotel hotel) {
		roomQualityChoice.setItems(FXCollections.observableArrayList(hotel.getQualities()));
	}

	private void displayAllQualities() {
		displayHotelQualities(defaultHotel);
	}

	private void initializeHotelDiscounts() {
		hotelDiscounts = dbParser.getDiscounts();
	}

	private void setHotelDiscounts(Hotel hotel) {

		ArrayList<Integer> temp = new ArrayList<Integer>();

		if (!hotel.equals(defaultHotel)) {

			for (Discount discount : hotelDiscounts) {
				if (discount.getHotelName().equals(hotel.getName())) {
					temp.add(discount.getDiscountPercentage());
				}
			}

		} else {
			for (Discount discount : hotelDiscounts) {
				if (!temp.contains(discount.getDiscountPercentage())) {
					temp.add(discount.getDiscountPercentage());
				}
			}
		}

		hotel.setDiscounts(temp);
	}

	private void displayHotelDiscounts(Hotel hotel) {
		discountChoice.setItems(FXCollections.observableArrayList(hotel.getDiscounts()));
	}

	private void displayAllDiscounts() {
		displayHotelDiscounts(defaultHotel);
	}
	
	private void displayEstimatedPrice() {
		
	}
	
	private int calculateEstimatedPrice() {
		
		
		
		return 0;
	}

	/**
	 * Initialize
	 */
	@FXML
	void initialize() {
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("lastName"));
		passportCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("passportNumber"));
		telephoneCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("telephoneNumber"));

		initializeHotels();

		System.out.println("#Setting up popup windows..");

		setupRoomPopUp();
		setupGuestPopUp();

		System.out.println("#Popups done!");

	}

}