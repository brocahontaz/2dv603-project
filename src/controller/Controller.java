package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Discount;
import model.Guest;
import model.Hotel;
import model.Reservation;
import model.Room;
import model.RoomQuality;
import utilities.Fx;

public class Controller {

	private ObservableList<Guest> guests;
	private ObservableList<Hotel> hotels;
	private ObservableList<Reservation> reservations;
	private ArrayList<RoomQuality> roomQualities;
	private ArrayList<Discount> hotelDiscounts;
	private DBParser dbParser = new DBParser();
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Stage primaryStage;
	private Stage roomPopup;
	private Stage guestPopup;
	private Stage guestInfoPopup;
	private Stage reservationPopup;
	private Stage splashScreen = new Stage();
	private Guest pickedGuest = null;
	private Room pickedRoom = null;
	private static final String DEFAULT_HOTEL_CHOICE = "Hotel Preference";
	private Hotel defaultHotel = new Hotel(DEFAULT_HOTEL_CHOICE, "");
	private ReservationPopupController reservationController;

	@FXML
	private BorderPane rootPane;

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
		executor.submit(() -> {
			if (!(checkInReservationID.getText().isEmpty())) {
				if (dbParser.checkIn(checkInReservationID.getText()) == true) {
					Fx.titledPaneColorNotification(checkInGuestsBox, "success");
				} else {
					Fx.titledPaneColorNotification(checkInGuestsBox, "danger");
				}
			} else {
				Fx.titledPaneColorNotification(checkInGuestsBox, "danger");
			}

		});
	}

	/**
	 * Check out guest
	 * 
	 * @param event
	 */
	@FXML
	void checkOutGuest(MouseEvent event) {
		executor.submit(() -> {
			if (!(checkOutReservationID.getText().isEmpty())) {
				if (dbParser.checkOut(checkOutReservationID.getText()) == true) {
					Fx.titledPaneColorNotification(checkOutGuestsBox, "success");
				} else {
					Fx.titledPaneColorNotification(checkOutGuestsBox, "danger");
				}
			} else {
				Fx.titledPaneColorNotification(checkOutGuestsBox, "danger");
			}
		});
	}

	/*
	 * Prompt a window with guest info when double clicking on guest in result.
	 */
	@FXML
	void checkSingularGuest(MouseEvent event) throws IOException {
		if (event.getClickCount() == 2) {
			Guest guest = searchResultTable.getSelectionModel().getSelectedItem();
			setupGuestInfoPopup(guest);
		}
	}

	private void setupGuestInfoPopup(Guest guest) {
		System.out.print("--Setting up Guest Info popup.. ");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GuestInfoPopup.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 600, 650);
			guestInfoPopup = new Stage();
			guestInfoPopup.initModality(Modality.APPLICATION_MODAL);
			guestInfoPopup.setScene(scene);
			guestInfoPopup.setMinHeight(650);
			guestInfoPopup.setMinWidth(600);
			guestInfoPopup.setResizable(false);
			guestInfoPopup.initStyle(StageStyle.UNDECORATED);
			root.getScene().getWindow().sizeToScene();
			guestInfoPopup.setTitle("Guests");
			guestInfoPopup.show();
			loader.<GuestInfoPopupController>getController().setupGuestInfoPopup(guest);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception from Crontroller setupGuestInfoPopup");
		}
		System.out.print("done!\r");
	}

	/**
	 * Choose reservation to check in
	 * 
	 * @param event
	 */
	@FXML
	void chooseReservationCheckIn(MouseEvent event) {
		String reservationID = checkInReservationID.getText();
		executor.submit(() -> {
			if (reservationID.matches("^[0-9]*$")) {
				ObservableList<Object> data = FXCollections
						.observableArrayList(dbParser.getGuestAndReservationById(reservationID));
				Guest guest = (Guest) data.get(0);
				Reservation reservation = (Reservation) data.get(1);
				checkInFirstName.setText(guest.getFirstName());
				checkInLastName.setText(guest.getLastName());
				checkInAddress.setText(guest.getAddress());
				checkInTelephone.setText(guest.getTelephoneNumber());
				checkInCreditCard.setText(guest.getCreditCard());
				checkInPassportNumber.setText(guest.getPassportNumber());
				checkInArrivalDate.setText(reservation.getArrivalDate() + "");
				checkInDepartureDate.setText(reservation.getDepartureDate() + "");
			}
		});
	}

	/**
	 * Choose reservation to check out
	 * 
	 * @param event
	 */
	@FXML
	void chooseReservationCheckOut(MouseEvent event) {
		String reservationID = checkOutReservationID.getText();
		executor.submit(() -> {
			if (reservationID.matches("^[0-9]*$")) {
				ObservableList<Object> data = FXCollections
						.observableArrayList(dbParser.getGuestAndReservationById(reservationID));
				Guest guest = (Guest) data.get(0);
				Reservation reservation = (Reservation) data.get(1);
				checkOutFirstName.setText(guest.getFirstName());
				checkOutLastName.setText(guest.getLastName());
				checkOutAddress.setText(guest.getAddress());
				checkOutTelephone.setText(guest.getTelephoneNumber());
				checkOutCreditCard.setText(guest.getCreditCard());
				checkOutPassportNumber.setText(guest.getPassportNumber());
				checkOutArrivalDate.setText(reservation.getArrivalDate() + "");
				checkOutDepartureDate.setText(reservation.getDepartureDate() + "");
			}
		});
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
		reservationController.acceptValues(pickedGuest, pickedRoom, arrivalDate.getValue().toString(),
				departureDate.getValue().toString(), hotelChoice.getSelectionModel().getSelectedItem(),
				roomQualityChoice.getSelectionModel().getSelectedItem(),
				discountChoice.getSelectionModel().getSelectedItem());
		reservationPopup.show();
	}

	private void setupReservationPopUp() {
		System.out.print("--Setting up reservation popup.. ");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReservationPopup.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 600, 400);
			reservationPopup = new Stage();
			reservationPopup.initModality(Modality.APPLICATION_MODAL);
			reservationPopup.setScene(scene);
			reservationPopup.setMinHeight(400);
			reservationPopup.setMinWidth(600);
			reservationPopup.setResizable(false);
			reservationPopup.initStyle(StageStyle.UNDECORATED);
			root.getScene().getWindow().sizeToScene();
			reservationPopup.setTitle("Reservation");
			reservationController = loader.<ReservationPopupController>getController();
			reservationController.injectMainController(this);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception from Crontroller setupReservationPopUp");
		}
		System.out.print("done!\r");
	}

	@FXML
	void clearReservation(MouseEvent event) {
		pickedGuest = null;
		makeReservationGuest.clear();
		makeReservationRoom.clear();
		arrivalDate.setValue(null);
		departureDate.setValue(null);
		roomQualityChoice.getSelectionModel().clearSelection();
		roomQualityChoice.getSelectionModel().select(0);
		roomQualityChoice.setDisable(false);
		discountChoice.getSelectionModel().clearSelection();
		hotelChoice.getSelectionModel().clearSelection();
		hotelChoice.getSelectionModel().select(0);
		hotelChoice.setDisable(false);
		displayAllQualities();
		displayAllDiscounts();
		estimatedPrice.setText("0");
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
			System.out.println("Exception from Crontroller setupGuestPopUp");
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
		makeReservationRoom.clear();
		roomQualityChoice.getSelectionModel().clearSelection();
		roomQualityChoice.setDisable(false);
		roomQualityChoice.getSelectionModel().selectFirst();
		discountChoice.getSelectionModel().clearSelection();
		hotelChoice.getSelectionModel().clearSelection();
		hotelChoice.setDisable(false);
		hotelChoice.getSelectionModel().selectFirst();
		estimatedPrice.setText("0");
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
			System.out.println("Exception from Crontroller setupRoomPopUp");
		}
		System.out.print("done!\r");
	}

	public void displayPickedRoom(Room room) {
		pickedRoom = room;
		makeReservationRoom.setText(room.getRoomNumber() + "");
		Hotel tempHotel = hotels.stream().filter(hotel -> hotel.getName().equals(room.getHotelName())).findFirst()
				.get();
		hotelChoice.getSelectionModel().select(tempHotel);
		hotelChoice.setDisable(true);
		RoomQuality tempQuality = roomQualities.stream()
				.filter(quality -> quality.getQuality().equals(room.getQuality()))
				.filter(quality -> quality.getHotelName().equals(room.getHotelName())).findFirst().get();
		roomQualityChoice.getSelectionModel().select(tempQuality);
		roomQualityChoice.setDisable(true);
	}

	/**
	 * Search guests, filtered
	 * 
	 * @param event
	 */
	@FXML
	void searchGuests(MouseEvent event) {
		executor.submit(() -> {

			dbLoad.setVisible(true);
			searchResultTable.setVisible(false);
			searchResultTable.getItems().clear();

			searchGuestButton.setDisable(true);
			searchResultTable.getItems().clear();
			guests = FXCollections.observableArrayList(dbParser.searchGuests(searchGuestFirstName.getText(),
					searchGuestLastName.getText(), searchGuestAddress.getText(), searchGuestTelephone.getText(),
					searchGuestCreditCard.getText(), searchGuestPassportNumber.getText()));
			if (guests.size() > 0) {
				Fx.titledPaneColorNotification(searchGuestsBox, "success");
			} else {
				Fx.titledPaneColorNotification(searchGuestsBox, "danger");
			}
			searchResultTable.setItems(guests);
			searchGuestButton.setDisable(false);

			dbLoad.setVisible(false);
			searchResultTable.setVisible(true);

		});
	}

	/**
	 * Add a new guest
	 * 
	 * @param event
	 */
	@FXML
	void addNewGuest(MouseEvent event) {
		executor.submit(() -> {
			if (dbParser.addNewGuest(addGuestFirstName.getText(), addGuestLastName.getText(), addGuestAddress.getText(),
					addGuestTelephone.getText(), addGuestCreditCard.getText(), addGuestPassport.getText()) == true) {
				Fx.titledPaneColorNotification(addGuestBox, "success");
				Fx.textFieldClear(addGuestFirstName, addGuestLastName, addGuestAddress, addGuestTelephone,
						addGuestCreditCard, addGuestPassport);
				addGuestButton.setDisable(true);
			} else {
				Fx.titledPaneColorNotification(addGuestBox, "danger");
				Fx.textFieldClear(addGuestFirstName, addGuestLastName, addGuestAddress, addGuestTelephone,
						addGuestCreditCard, addGuestPassport);
				addGuestButton.setDisable(true);
			}
		});

	}

	@FXML
	void keyReleasedProperty(KeyEvent event) {
		boolean isDisabled = (addGuestFirstName.getText().isEmpty() || addGuestLastName.getText().isEmpty()
				|| addGuestAddress.getText().isEmpty() || addGuestTelephone.getText().isEmpty()
				|| addGuestCreditCard.getText().isEmpty() || addGuestPassport.getText().isEmpty());
		addGuestButton.setDisable(isDisabled);
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

			dbLoad.setVisible(true);
			searchResultTable.setVisible(false);
			searchResultTable.getItems().clear();

			listAllGuestsButton.setDisable(true);
			searchResultTable.getItems().clear();
			guests = FXCollections.observableArrayList(dbParser.getAllGuests());
			searchResultTable.setItems(guests);
			listAllGuestsButton.setDisable(false);

			dbLoad.setVisible(false);
			searchResultTable.setVisible(true);
		});
	}

	private void initializeHotels() {
		System.out.println("#Initializing hotels.. ");

		executor.submit(() -> {
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

			hideSplashDisplayMain();

			hotelChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Hotel>() {
				@Override
				public void changed(ObservableValue<? extends Hotel> observable, Hotel oldValue, Hotel newValue) {
					if (newValue != null) {

						displayHotelQualities(newValue);
						displayHotelDiscounts(newValue);

					} else {
						displayAllQualities();
						displayAllDiscounts();
					}

				}

			});

			roomQualityChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RoomQuality>() {
				@Override
				public void changed(ObservableValue<? extends RoomQuality> observable, RoomQuality oldValue,
						RoomQuality newValue) {
					if (newValue != null) {
						displayEstimatedPrice();
					}
				}

			});

			discountChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					if (newValue != null) {
						displayEstimatedPrice();
					}
				}

			});

			arrivalDate.valueProperty().addListener((ov, oldValue, newValue) -> {
				displayEstimatedPrice();

			});

			departureDate.valueProperty().addListener((ov, oldValue, newValue) -> {
				displayEstimatedPrice();

			});

		});

		// System.out.println(hotels);

		System.out.println("#Hotels initialized!");
	}

	private void initializeHotelQualities() {
		// executor.submit(() -> {
		roomQualities = dbParser.getQualities();
		// });

	}

	private void setHotelQualities(Hotel hotel) {
		ArrayList<RoomQuality> temp1 = new ArrayList<RoomQuality>();
		RoomQuality defQual = new RoomQuality();
		defQual.setQuality("Room Quality");
		defQual.setPrice(0);
		temp1.add(0, defQual);
		if (!hotel.equals(defaultHotel)) {
			roomQualities.stream().filter(room -> room.getHotelName().equals(hotel.getName())).forEach(temp1::add);
		} else {
			roomQualities.stream().distinct().forEach(temp1::add);
		}

		hotel.setQualities(temp1);
	}

	private void displayHotelQualities(Hotel hotel) {
		roomQualityChoice.setItems(FXCollections.observableArrayList(hotel.getQualities()));
		// roomQualityChoice.getSelectionModel().selectFirst();
		if (roomQualityChoice.getSelectionModel().getSelectedItem() == null) {
			roomQualityChoice.getSelectionModel().select(0);
		}

	}

	private void displayAllQualities() {
		displayHotelQualities(defaultHotel);
	}

	private void initializeHotelDiscounts() {
		// executor.submit(() -> {
		hotelDiscounts = dbParser.getDiscounts();
		// });
	}

	private void setHotelDiscounts(Hotel hotel) {

		ArrayList<Integer> temp1 = new ArrayList<Integer>();
		temp1.add(0);

		if (!hotel.equals(defaultHotel)) {

			hotelDiscounts.stream().filter(discount -> discount.getHotelName().equals(hotel.getName()))
					.map(discount -> discount.getDiscountPercentage()).sorted().forEach(temp1::add);

		} else {
			hotelDiscounts.stream().distinct().map(discount -> discount.getDiscountPercentage()).sorted()
					.forEach(temp1::add);
		}

		hotel.setDiscounts(temp1);
	}

	private void displayHotelDiscounts(Hotel hotel) {
		discountChoice.setItems(FXCollections.observableArrayList(hotel.getDiscounts()));
		if (discountChoice.getSelectionModel().getSelectedItem() == null) {
			discountChoice.getSelectionModel().select(0);
		}
	}

	private void displayAllDiscounts() {
		displayHotelDiscounts(defaultHotel);
	}

	private void displayEstimatedPrice() {

		estimatedPrice.setText(calculateEstimatedOverallPrice());

	}

	private String calculateEstimatedOverallPrice() {

		ArrayList<Integer> prices = new ArrayList<Integer>();
		ArrayList<RoomQuality> temp = new ArrayList<RoomQuality>();

		String qual = roomQualityChoice.getSelectionModel().getSelectedItem().getQuality();
		if ((hotelChoice.getSelectionModel().isEmpty() || hotelChoice.getSelectionModel().isSelected(0))
				&& !roomQualityChoice.getSelectionModel().isSelected(0)) {
			temp = (ArrayList<RoomQuality>) roomQualities.stream().filter(quality -> quality.getQuality().equals(qual))
					.collect(Collectors.toList());
		} else {
			temp.add(roomQualityChoice.getSelectionModel().getSelectedItem());
		}

		for (RoomQuality quality : temp) {
			int tempPrice = quality.getPrice();
			if (!discountChoice.getSelectionModel().isEmpty()) {
				double discount = (double) discountChoice.getSelectionModel().selectedItemProperty().getValue() / 100;
				tempPrice *= (1.00 - discount);
			}
			if (arrivalDate.getValue() != null && departureDate.getValue() != null) {
				tempPrice *= getDays();
			}
			prices.add(tempPrice);
		}

		Collections.sort(prices);

		StringBuilder sb = new StringBuilder();
		for (int price : prices) {
			sb.append(price);
			if (prices.size() > 1 && prices.indexOf(price) == 0) {
				sb.append(" - ");
			}
		}

		System.out.println(prices);

		return (sb.toString());
	}

	private int getDays() {
		long arrival = arrivalDate.getValue().toEpochDay();
		long departure = departureDate.getValue().toEpochDay();
		int days = (int) Math.abs(arrival - departure);

		return days;
	}

	private void setupSplashScreen() {
		System.out.print("--Setting up splash screen.. ");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SplashScreen.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 600, 400);
			// splashScreen = new Stage();
			splashScreen.initModality(Modality.APPLICATION_MODAL);
			splashScreen.setScene(scene);
			splashScreen.setMinHeight(400);
			splashScreen.setMinWidth(600);
			splashScreen.setResizable(false);
			splashScreen.initStyle(StageStyle.UNDECORATED);
			root.getScene().getWindow().sizeToScene();
			splashScreen.setTitle("");

			splashScreen.initStyle(StageStyle.TRANSPARENT);
			scene.setFill(Color.TRANSPARENT);

			splashScreen.show();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception from Crontroller setupSplashScreen");
		}
		System.out.print("done!\r");
	}

	public void setStage(Stage stage) {
		this.primaryStage = stage;

	}

	private void hideSplashDisplayMain() {

		try {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					splashScreen.hide();
					primaryStage.show();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception from Crontroller hideSplashDisplayMain");
		}

	}

	/**
	 * Initialize
	 */
	@FXML
	void initialize() {

		setupSplashScreen();

		firstNameCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("lastName"));
		passportCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("passportNumber"));
		telephoneCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("telephoneNumber"));

		initializeHotels();

		System.out.println("#Setting up popup windows..");

		setupRoomPopUp();
		setupGuestPopUp();
		setupReservationPopUp();

		addGuestButton.setDisable(true);

		System.out.println("#Popups done!");

	}

}