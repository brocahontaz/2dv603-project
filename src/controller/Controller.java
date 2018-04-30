package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
	private Stage splashScreen = new Stage();
	private Guest pickedGuest = null;
	private Guest pickedCheckGuest = null;
	public static final String DEFAULT_HOTEL_CHOICE = "Hotel Preference";
	public static final String DEFAULT_QUALITY_CHOICE = "Room Quality";
	private Hotel defaultHotel = new Hotel(DEFAULT_HOTEL_CHOICE, "");
	private boolean checkMakeReservationGuest = false;

	private int checkInResId = -1;
	private int checkOutResId = -1;

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
	
	@FXML
	private TextField checkinHotel;

	@FXML
	private TextField checkinQuality;

	@FXML
	private TextField checkinRoom;
	
	@FXML
	private TextField checkoutHotel;

	@FXML
	private TextField checkoutQuality;

	@FXML
	private TextField checkoutRoom;

	@FXML
	private TextField checkReservationGuest;
	
	@FXML
	private TextField checkReservationID;

	/**
	 * TEXT
	 */

	@FXML
	private Text estimatedPrice;
	
	@FXML
	private Text checkinPrice;
	
	@FXML
	private Text checkoutPrice;

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
	private Button clearCheckinButton;

	@FXML
	private Button checkOutButton;

	@FXML
	private Button clearCheckoutButton;

	@FXML
	private Button chooseReservationButtonCheckOut;

	@FXML
	private Button addGuestButton;

	@FXML
	private Button clearReservationButton;
	
	@FXML
	private Button pickCheckGuestButton;
	
	@FXML
	private Button clearSearchGuestFieldsButton;

	@FXML
	private Button clearAddGuestFieldsButton;
	
	@FXML
	private Button clearCheckReservationButton;

	@FXML
	private Button checkReservationButton;

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
	
	@FXML
	private ComboBox<Hotel> hotelCheckChoice;

	@FXML
	private ComboBox<?> roomQualityCheckChoice;

	/**
	 * PROGRESS INDICATORS
	 */

	@FXML
	private ProgressIndicator dbLoad;

	@FXML
	private ProgressIndicator checkinProgress;

	@FXML
	private ProgressIndicator checkoutProgress;

	@FXML
	private ProgressIndicator reservationsProgress;

	/**
	 * DATE PICKERS
	 */

	@FXML
	private DatePicker arrivalDate;

	@FXML
	private DatePicker departureDate;
	
	@FXML
	private DatePicker arrivalCheckDate;

	@FXML
	private DatePicker departureCheckDate;

	/**
	 * TABLE VIEWS
	 */

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
	private TableView<Reservation> checkResResultsTable;

	@FXML
	private TableColumn<Reservation, String> idCol;

	@FXML
	private TableColumn<Reservation, String> hotelCol;

	@FXML
	private TableColumn<Reservation, String> roomCol;

	@FXML
	private TableColumn<Reservation, String> arrivalCol;

	@FXML
	private TableColumn<Reservation, String> departureCol;

	/**
	 * MENU ITEMS
	 */

	@FXML
	private MenuItem vaxjoChange;

	@FXML
	private MenuItem kalmarChange;

	@FXML
	private MenuItem closeSystem;

	public int getQualityPrice(String hotelName, String quality) {
		List<Integer> temp = roomQualities.stream().filter(quality1 -> quality1.getHotelName().equals(hotelName))
				.filter(quality1 -> quality1.getQuality().equals(quality)).map(quality1 -> quality1.getPrice())
				.collect(Collectors.toList());
		if (temp.size() == 0) {
			temp.add(0);
		}
		return temp.get(0);
	}

	/**
	 * Check in guest
	 * 
	 * @param event
	 */
	@FXML
	void checkInGuest(MouseEvent event) {
		checkInButton.setDisable(true);
		executor.submit(() -> {
			if (dbParser.checkIn(Integer.toString(checkInResId)) == true) {
				// Running element manipulation on fx-thread
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Fx.titledPaneColorNotification(checkInGuestsBox, "success");
					}
				});
			} else {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Fx.titledPaneColorNotification(checkInGuestsBox, "danger");
					}
				});
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
		checkOutButton.setDisable(true);
		executor.submit(() -> {
			if (dbParser.checkOut(Integer.toString(checkOutResId)) == true) {

				// Running element manipulation on fx-thread
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						createBillPDF(Integer.toString(checkOutResId));
						Fx.titledPaneColorNotification(checkOutGuestsBox, "success");
					}
				});
			} else {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Fx.titledPaneColorNotification(checkOutGuestsBox, "danger");
					}
				});
			}
		});
	}

	@FXML
	void pickCheckGuest(MouseEvent event) {
		setupGuestPopUp(checkReservationGuest);
	}

	@FXML
	void checkReservation(MouseEvent event) {
		String resID = checkReservationID.getText().trim();

		if (resID.isEmpty() || resID == null) {

			checkReservation();

		} else if (resID.matches("[0-9]+")) {
			setupReservationInfoPopup(resID);
		}
	}

	private void checkReservation() {
		executor.submit(() -> {

			checkResResultsTable.getItems().clear();
			checkResResultsTable.setVisible(false);
			reservationsProgress.setVisible(true);
			String passport;
			String arrival;
			String departure;

			if (pickedCheckGuest != null) {
				passport = pickedCheckGuest.getPassportNumber();
			} else {
				passport = "";
			}

			String hotelCheck = hotelCheckChoice.getSelectionModel().getSelectedItem().getName();

			if (hotelCheck.equals(DEFAULT_HOTEL_CHOICE) || hotelCheck.isEmpty() || hotelCheck == null) {
				hotelCheck = "";
			}

			if (arrivalCheckDate.getValue() != null && departureCheckDate.getValue() != null) {

				arrival = arrivalCheckDate.getValue().toString().replaceAll("-", "");
				departure = departureCheckDate.getValue().toString().replaceAll("-", "");
				reservations = FXCollections.observableArrayList(
						dbParser.searchReservationsWithDates(passport, arrivalCheckDate.getValue().toEpochDay(),
								departureCheckDate.getValue().toEpochDay(), hotelCheck));

			} else if (arrivalCheckDate.getValue() != null) {

				arrival = arrivalCheckDate.getValue().toString().replaceAll("-", "");
				reservations = FXCollections.observableArrayList(dbParser.searchReservationsWithArrivalDate(passport,
						arrivalCheckDate.getValue().toEpochDay(), hotelCheck));

			} else if (departureCheckDate.getValue() != null) {

				departure = departureCheckDate.getValue().toString().replaceAll("-", "");
				reservations = FXCollections.observableArrayList(dbParser.searchReservationsWithDepartureDate(passport,
						departureCheckDate.getValue().toEpochDay(), hotelCheck));

			} else {

				reservations = FXCollections
						.observableArrayList(dbParser.searchReservationsWithoutDates(passport, hotelCheck));

			}


			checkResResultsTable.setItems(reservations);

			checkResResultsTable.setVisible(true);
			reservationsProgress.setVisible(false);
		});
	}

	public void reloadReservationTable() {
		checkReservation();
	}

	public void removeElementFromReservationTable(String id) {

		int tempId = Integer.parseInt(id);

		List<Reservation> tempList = reservations.stream().filter(res -> res.getId() == tempId)
				.collect(Collectors.toList());

		Reservation tempRes = tempList.get(0);

		reservations.remove(reservations.indexOf(tempRes));
	}

	private void setupReservationInfoPopup(String resID) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReservationInfoPopup.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			Stage reservationInfoPopup = new Stage();
			reservationInfoPopup.initModality(Modality.APPLICATION_MODAL);
			reservationInfoPopup.setScene(scene);
			reservationInfoPopup.setMinHeight(650);
			reservationInfoPopup.setMinWidth(600);
			reservationInfoPopup.setResizable(false);
			reservationInfoPopup.initStyle(StageStyle.UNDECORATED);
			root.getScene().getWindow().sizeToScene();
			reservationInfoPopup.setTitle("Reservation");
			reservationInfoPopup.show();
			loader.<ReservationInfoPopupController>getController().injectMainController(this);
			loader.<ReservationInfoPopupController>getController().setupReservation(resID);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void clearCheckReservation(MouseEvent event) {
		pickedCheckGuest = null;
		arrivalCheckDate.setValue(null);
		departureCheckDate.setValue(null);
		checkReservationGuest.clear();
		checkReservationID.clear();
		hotelCheckChoice.getSelectionModel().selectFirst();
	}

	@FXML
	void clearCheckin(MouseEvent event) {
		Fx.textFieldClear(checkInReservationID, checkInFirstName, checkInLastName, checkInAddress, checkInTelephone,
				checkInCreditCard, checkInPassportNumber, checkInArrivalDate, checkInDepartureDate, checkinHotel,
				checkinRoom, checkinQuality);
		checkinPrice.setText("0");
		checkInButton.setDisable(true);
		chooseReservationButtonCheckIn.setDisable(true);
	}

	@FXML
	void clearCheckout(MouseEvent event) {
		Fx.textFieldClear(checkOutReservationID, checkOutFirstName, checkOutLastName, checkOutAddress,
				checkOutTelephone, checkOutCreditCard, checkOutPassportNumber, checkOutArrivalDate,
				checkOutDepartureDate, checkoutHotel, checkoutRoom, checkoutQuality);
		checkoutPrice.setText("0");
		checkOutButton.setDisable(true);
		chooseReservationButtonCheckOut.setDisable(true);
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

	@FXML
	void getReservationInfo(MouseEvent event) {
		if (event.getClickCount() == 2) {
			String id = checkResResultsTable.getSelectionModel().getSelectedItem().getId() + "";
			setupReservationInfoPopup(id);
		}
	}

	private void setupGuestInfoPopup(Guest guest) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GuestInfoPopup.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 600, 650);
			Stage guestInfoPopup = new Stage();
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
		}

	}

	/**
	 * Choose reservation to check in
	 * 
	 * @param event
	 */
	@FXML
	void chooseReservationCheckIn(MouseEvent event) {
		String reservationID = checkInReservationID.getText().trim();
		if (reservationID.matches("[0-9]+")) {
			chooseReservationButtonCheckIn.setDisable(true);
			checkInButton.setDisable(true);

			executor.submit(() -> {
				checkinProgress.setVisible(true);

				ArrayList<Object> data = dbParser.getGuestAndReservationById(reservationID);
				// Running element manipulation on fx-thread
				if (data.size() > 0) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Fx.titledPaneColorNotificationButton(checkInGuestsBox, chooseReservationButtonCheckIn,
									"success", 1);
						}
					});

				} else {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Fx.titledPaneColorNotificationButton(checkInGuestsBox, chooseReservationButtonCheckIn,
									"danger");
						}
					});
				}
				checkinProgress.setVisible(false);
				Guest guest = (Guest) data.get(0);
				Reservation reservation = (Reservation) data.get(1);
				Room room = (Room) data.get(2);
				checkInFirstName.setText(guest.getFirstName());
				checkInLastName.setText(guest.getLastName());
				checkInAddress.setText(guest.getAddress());
				checkInTelephone.setText(guest.getTelephoneNumber());
				checkInCreditCard.setText(guest.getCreditCard());
				checkInPassportNumber.setText(guest.getPassportNumber());
				checkInArrivalDate.setText(reservation.getArrivalDate().toString());
				checkInDepartureDate.setText(reservation.getDepartureDate().toString());
				checkinHotel.setText(reservation.getHotel());
				checkinRoom.setText(Integer.toString(reservation.getRoomNumber()));
				checkinQuality.setText(room.getQuality());
				checkinPrice.setText(Integer.toString(reservation.getPrice()));

				checkInResId = reservation.getId();

				if (reservation.getCheckedIn() == false) {
					checkInButton.setDisable(false);
				}

			});
		}
	}

	/**
	 * Choose reservation to check out
	 * 
	 * @param event
	 */
	@FXML
	void chooseReservationCheckOut(MouseEvent event) {
		String reservationID = checkOutReservationID.getText().trim();
		if (reservationID.matches("[0-9]+")) {
			chooseReservationButtonCheckOut.setDisable(true);
			checkOutButton.setDisable(true);
			executor.submit(() -> {
				checkoutProgress.setVisible(true);
				ObservableList<Object> data = FXCollections
						.observableArrayList(dbParser.getGuestAndReservationById(reservationID));
				// Running element manipulation on fx-thread
				if (data.size() > 0) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Fx.titledPaneColorNotificationButton(checkOutGuestsBox, chooseReservationButtonCheckOut,
									"success", 1);
						}
					});

				} else {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Fx.titledPaneColorNotificationButton(checkOutGuestsBox, chooseReservationButtonCheckOut,
									"danger");
						}
					});
				}

				checkoutProgress.setVisible(false);
				Guest guest = (Guest) data.get(0);
				Reservation reservation = (Reservation) data.get(1);
				Room room = (Room) data.get(2);
				checkOutFirstName.setText(guest.getFirstName());
				checkOutLastName.setText(guest.getLastName());
				checkOutAddress.setText(guest.getAddress());
				checkOutTelephone.setText(guest.getTelephoneNumber());
				checkOutCreditCard.setText(guest.getCreditCard());
				checkOutPassportNumber.setText(guest.getPassportNumber());
				checkOutArrivalDate.setText(reservation.getArrivalDate().toString());
				checkOutDepartureDate.setText(reservation.getDepartureDate().toString());
				checkoutHotel.setText(reservation.getHotel());
				checkoutRoom.setText(Integer.toString(reservation.getRoomNumber()));
				checkoutQuality.setText(room.getQuality());
				checkoutPrice.setText(Integer.toString(reservation.getPrice()));

				checkOutResId = reservation.getId();

				if (reservation.getCheckedIn() == true) {
					checkOutButton.setDisable(false);
				}

			});
		}
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

		Hotel tmpHotel = hotelChoice.getSelectionModel().getSelectedItem();

		if (tmpHotel == null) {
			tmpHotel = new Hotel();
		}

		RoomQuality tmpQuality = roomQualityChoice.getSelectionModel().getSelectedItem();

		if (tmpQuality == null) {
			tmpQuality = new RoomQuality();
		}
		setupReservationPopUp(tmpHotel, tmpQuality);
	}

	private void setupReservationPopUp(Hotel tmpHotel, RoomQuality tmpQuality) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReservationPopup.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			Stage reservationPopup = new Stage();
			reservationPopup.initModality(Modality.APPLICATION_MODAL);
			reservationPopup.setScene(scene);
			reservationPopup.setMinHeight(400);
			reservationPopup.setMinWidth(600);
			reservationPopup.setResizable(false);
			reservationPopup.initStyle(StageStyle.UNDECORATED);
			root.getScene().getWindow().sizeToScene();
			reservationPopup.setTitle("Reservation");
			loader.<ReservationPopupController>getController().injectMainController(this);
			loader.<ReservationPopupController>getController().acceptValues(pickedGuest, arrivalDate.getValue(),
					departureDate.getValue(), tmpHotel, tmpQuality,
					discountChoice.getSelectionModel().getSelectedItem());
			reservationPopup.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void clearReservation(MouseEvent event) {
		pickedGuest = null;
		makeReservationGuest.clear();
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
		makeReservationButton.setDisable(true);

	}

	/**
	 * Pick a guest
	 * 
	 * @param event
	 */
	@FXML
	void pickGuest(MouseEvent event) {
		setupGuestPopUp(makeReservationGuest);
	}

	private void setupGuestPopUp(TextField textfield) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PickGuestPopup.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 600, 400);
			Stage guestPopup = new Stage();
			guestPopup.initModality(Modality.APPLICATION_MODAL);
			guestPopup.setScene(scene);
			guestPopup.setMinHeight(400);
			guestPopup.setMinWidth(600);
			guestPopup.setResizable(false);
			guestPopup.initStyle(StageStyle.UNDECORATED);
			root.getScene().getWindow().sizeToScene();
			guestPopup.setTitle("Guests");
			loader.<PickGuestPopupController>getController().setTextField(textfield);
			loader.<PickGuestPopupController>getController().injectMainController(this);
			guestPopup.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void displayPickedGuest(Guest guest, TextField Textfield) {
		if (Textfield.equals(makeReservationGuest)) {
			pickedGuest = guest;
		} else if (Textfield.equals(checkReservationGuest)) {
			pickedCheckGuest = guest;
		}

		Textfield.setText(guest.getFirstName() + " " + guest.getLastName());
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
			guests = FXCollections.observableArrayList(
					dbParser.searchGuests(searchGuestFirstName.getText().trim(), searchGuestLastName.getText().trim(),
							searchGuestAddress.getText().trim(), searchGuestTelephone.getText().trim(),
							searchGuestCreditCard.getText().trim(), searchGuestPassportNumber.getText().trim()));
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
			if (dbParser.addNewGuest(addGuestFirstName.getText().trim(), addGuestLastName.getText().trim(),
					addGuestAddress.getText().trim(), addGuestTelephone.getText().trim(),
					addGuestCreditCard.getText().trim(), addGuestPassport.getText().trim()) == true) {
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

	/**
	 * Handles the enable/disable of button Search in tab Guest Management
	 * 
	 * @param event
	 */
	@FXML
	void guestManagementButtonSearch(KeyEvent event) {

		boolean buttonSearchGuest = (searchGuestFirstName.getText().isEmpty() && searchGuestLastName.getText().isEmpty()
				&& searchGuestAddress.getText().isEmpty() && searchGuestTelephone.getText().isEmpty()
				&& searchGuestCreditCard.getText().isEmpty() && searchGuestPassportNumber.getText().isEmpty());

		searchGuestButton.setDisable(buttonSearchGuest);

	}

	/**
	 * Handles the enable/disable of button Add in tab Guest Management
	 * 
	 * @param event
	 */
	@FXML
	void guestManagementButtonAdd(KeyEvent event) {
		boolean buttonAddGuest = (addGuestFirstName.getText().isEmpty() || addGuestLastName.getText().isEmpty()
				|| addGuestAddress.getText().isEmpty()
				|| !(addGuestTelephone.getText().length() >= Fx.TELEPHONE_MIN_LENGTH)
				|| !(addGuestCreditCard.getText().length() == Fx.CREDITCARD_LENGTH)
				|| !(addGuestPassport.getText().length() == Fx.PASSPORT_LENGTH));

		addGuestButton.setDisable(buttonAddGuest);

	}

	/**
	 * Handles the enable/disable of button Choose Reservation(Add) in tab
	 * Checkin/Checkout Management
	 * 
	 * @param event
	 */
	@FXML
	void checkInButtonChoseReservation(KeyEvent event) {
		boolean chooseReservation = checkInReservationID.getText().isEmpty();
		chooseReservationButtonCheckIn.setDisable(chooseReservation);
	}

	/**
	 * Handles the enable/disable of button Choose Reservation(Search) in tab
	 * Checkin/Checkout Management
	 * 
	 * @param event
	 */
	@FXML
	void checkOutButtonChoseReservation(KeyEvent event) {
		boolean chooseReservation = checkOutReservationID.getText().isEmpty();
		chooseReservationButtonCheckOut.setDisable(chooseReservation);
	}

	/**
	 * Handles the enable/disable of button Make Reservation in tab Reservation
	 * Management
	 * 
	 * @param event
	 */
	@FXML
	void arrivalDepatureAction(ActionEvent event) {
		boolean isDisabled = (arrivalDate.getValue() == null || departureDate.getValue() == null
				|| checkMakeReservationGuest == false);
		makeReservationButton.setDisable(isDisabled);
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

	/**
	 * Clears all textfields in search guests in the tab Guest Management
	 * 
	 * @param event
	 */
	@FXML
	void clearSearchGuestFields(MouseEvent event) {
		searchGuestButton.setDisable(true);
		Fx.textFieldClear(searchGuestFirstName, searchGuestLastName, searchGuestAddress, searchGuestTelephone,
				searchGuestCreditCard, searchGuestPassportNumber);
	}

	@FXML
	void clearAddGuestFields(MouseEvent event) {
		addGuestButton.setDisable(true);
		Fx.textFieldClear(addGuestFirstName, addGuestLastName, addGuestAddress, addGuestTelephone, addGuestCreditCard,
				addGuestPassport);
	}

	private void initializeHotels() {

		executor.submit(() -> {
			hotels = FXCollections.observableArrayList(dbParser.getHotels());
			hotels.add(0, defaultHotel);

			hotelChoice.setItems(hotels);
			hotelChoice.getSelectionModel().selectFirst();

			hotelCheckChoice.setItems(hotels);
			hotelCheckChoice.getSelectionModel().selectFirst();

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


	}

	private void initializeHotelQualities() {
		roomQualities = dbParser.getQualities();
	}

	private void setHotelQualities(Hotel hotel) {
		ArrayList<RoomQuality> temp1 = new ArrayList<RoomQuality>();
		RoomQuality defQual = new RoomQuality();
		defQual.setQuality(DEFAULT_QUALITY_CHOICE);
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


		return (sb.toString());
	}

	private int getDays() {
		long arrival = arrivalDate.getValue().toEpochDay();
		long departure = departureDate.getValue().toEpochDay();
		int days = (int) Math.abs(arrival - departure);

		return days;
	}

	private void setupSplashScreen() {
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
		}
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
		}

	}

	private void checkIfMakeReservationGuestIsEmpty(String value) {
		if (value.length() > 0) {
			checkMakeReservationGuest = true;
		} else {
			checkMakeReservationGuest = false;
		}
	}

	private void setTextFormattersForAddGuest() {
		Fx.setTextFormatter(addGuestFirstName, 1, Fx.FIRSTNAME_LENGTH, Fx.Regex.NO_NUMBERS);
		Fx.setTextFormatter(addGuestLastName, 1, Fx.LASTNAME_LENGTH, Fx.Regex.NO_NUMBERS);
		Fx.setTextFormatter(addGuestCreditCard, Fx.CREDITCARD_LENGTH, Fx.CREDITCARD_LENGTH, Fx.Regex.ONLY_NUMBERS);
		Fx.setTextFormatter(addGuestPassport, Fx.PASSPORT_LENGTH, Fx.PASSPORT_LENGTH, Fx.Regex.ONLY_NUMBERS);
		Fx.setTextFormatter(addGuestTelephone, Fx.TELEPHONE_MIN_LENGTH, Fx.TELEPHONE_LENGTH, Fx.Regex.ONLY_NUMBERS);
	}

	private void setTextFormattersForSearchGuest() {
		Fx.setTextFormatter(searchGuestFirstName, 1, Fx.FIRSTNAME_LENGTH, Fx.Regex.NO_NUMBERS);
		Fx.setTextFormatter(searchGuestLastName, 1, Fx.LASTNAME_LENGTH, Fx.Regex.NO_NUMBERS);
		Fx.setTextFormatter(searchGuestCreditCard, Fx.CREDITCARD_LENGTH, Fx.CREDITCARD_LENGTH, Fx.Regex.ONLY_NUMBERS);
		Fx.setTextFormatter(searchGuestPassportNumber, Fx.PASSPORT_LENGTH, Fx.PASSPORT_LENGTH, Fx.Regex.ONLY_NUMBERS);
		Fx.setTextFormatter(searchGuestTelephone, Fx.TELEPHONE_MIN_LENGTH, Fx.TELEPHONE_LENGTH, Fx.Regex.ONLY_NUMBERS);
	}

	private void setTextFormattersForCheckInCheckOut() {
		Fx.setTextFormatter(checkInReservationID, 1, Fx.RESERVATION_ID_LENGTH, Fx.Regex.ONLY_NUMBERS);
		Fx.setTextFormatter(checkOutReservationID, 1, Fx.RESERVATION_ID_LENGTH, Fx.Regex.ONLY_NUMBERS);
	}

	private void setCellFactoriesForGuestResultsTable() {
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("lastName"));
		passportCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("passportNumber"));
		telephoneCol.setCellValueFactory(new PropertyValueFactory<Guest, String>("telephoneNumber"));
	}

	private void setCellFactoriesForReservationResultsTable() {
		idCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("id"));
		hotelCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("hotel"));
		roomCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("roomNumber"));
		arrivalCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("arrivalDate"));
		departureCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("departureDate"));
	}

	/**
	 * Adds an observable listener to textfield makeReservationGuest in tab
	 * Reservation Management, to listen to changes in the textfield and fire an
	 * ActionEvent to method arrivalDepatureAction when changes occur
	 */
	private void makeReservationGuestListener() {
		makeReservationGuest.textProperty().addListener((observable, oldValue, newValue) -> {
			Event event = new ActionEvent();
			checkIfMakeReservationGuestIsEmpty(newValue);
			arrivalDate.fireEvent(event);
		});
	}

	private void createBillPDF(String id) {

		try {

			File template = new File("C:\\Temp\\hotelpdftemplate.pdf");
			PDDocument templateDocument = PDDocument.load(template);
			PDDocumentCatalog docCatalog = templateDocument.getDocumentCatalog();
			PDAcroForm acroForm = docCatalog.getAcroForm();
			acroForm.setNeedAppearances(false);

			String fullname = checkOutFirstName.getText() + " " + checkOutLastName.getText();
			
			acroForm.getField("reservationID").setValue(id);
			acroForm.getField("reservationID").setReadOnly(true);
			acroForm.getField("name").setValue(fullname);
			acroForm.getField("name").setReadOnly(true);
			acroForm.getField("dateBox").setValue(LocalDate.now().toString());
			acroForm.getField("dateBox").setReadOnly(true);
			acroForm.getField("address").setValue(checkOutAddress.getText());
			acroForm.getField("address").setReadOnly(true);
			acroForm.getField("telephone").setValue(checkOutTelephone.getText());
			acroForm.getField("telephone").setReadOnly(true);
			acroForm.getField("creditCard").setValue(checkOutCreditCard.getText());
			acroForm.getField("creditCard").setReadOnly(true);
			acroForm.getField("passport").setValue(checkOutPassportNumber.getText());
			acroForm.getField("passport").setReadOnly(true);
			acroForm.getField("arrivalDate").setValue(checkOutArrivalDate.getText());
			acroForm.getField("arrivalDate").setReadOnly(true);
			acroForm.getField("departureDate").setValue(checkOutDepartureDate.getText());
			acroForm.getField("departureDate").setReadOnly(true);
			acroForm.getField("hotel").setValue(checkoutHotel.getText());
			acroForm.getField("hotel").setReadOnly(true);
			acroForm.getField("quality").setValue(checkoutQuality.getText());
			acroForm.getField("quality").setReadOnly(true);
			acroForm.getField("room").setValue(checkoutRoom.getText());
			acroForm.getField("room").setReadOnly(true);
			acroForm.getField("price").setValue(checkoutPrice.getText());
			acroForm.getField("price").setReadOnly(true);

			PDPageTree pages = docCatalog.getPages();
			PDDocument document = new PDDocument();
			document.addPage(pages.get(0));
			document.save("C:\\Temp\\" + id + ".pdf");

			templateDocument.close();
			document.close();
			openBillPDF(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void openBillPDF(String id) {
		try {
			File file = new File("C:\\temp\\" + id + ".pdf");
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize
	 */
	@FXML
	void initialize() {
		setupSplashScreen();
		makeReservationGuestListener();
		setCellFactoriesForGuestResultsTable();
		setCellFactoriesForReservationResultsTable();
		setTextFormattersForSearchGuest();
		setTextFormattersForAddGuest();
		setTextFormattersForCheckInCheckOut();
		initializeHotels();
	}

}