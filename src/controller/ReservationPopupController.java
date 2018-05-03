package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Guest;
import model.Hotel;
import model.Room;
import model.RoomQuality;
import utilities.Fx;

public class ReservationPopupController {

	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Controller controller;
	private RoomQuality roomQualityChoice;
	private Hotel hotelChoice;
	private int discountChoice;
	private LocalDate departureDate;
	private LocalDate arrivalDate;
	private Guest guest;
	private DBParser dbParser = new DBParser();
	private ObservableList<Room> rooms;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField firstname;

	@FXML
	private TextField lastname;

	@FXML
	private TextField passport;

	@FXML
	private TextField telephone;

	@FXML
	private TextField arrival;

	@FXML
	private TextField departure;

	@FXML
	private TextField hotel;

	@FXML
	private TextField room;

	@FXML
	private TextField quality;

	@FXML
	private Text discount;

	@FXML
	private Text price;

	@FXML
	private Text reservationID;

	@FXML
	private Button close;

	@FXML
	private Button cancelResButton;

	@FXML
	private TableView<Room> roomResultsTable;

	@FXML
	private TableColumn<Room, String> colHotel;

	@FXML
	private TableColumn<Room, String> colQuality;

	@FXML
	private TableColumn<Room, String> colRoomNumber;

	@FXML
	private Button makeResButton;

	@FXML
	private ProgressIndicator progress;

	@FXML
	private TitledPane title;

	@FXML
	void close(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void selectRoom(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Room tmpRoom = roomResultsTable.getSelectionModel().getSelectedItem();
			hotel.setText(tmpRoom.getHotelName());
			quality.setText(tmpRoom.getQuality());
			room.setText(Integer.toString(tmpRoom.getRoomNumber()));
		}
	}

	@FXML
	void confirmReservation(MouseEvent event) {
		executor.submit(() -> {
			// String trimmedArrival = arrivalDate.toString().replaceAll("-", "");
			// String trimmedDeparture = departureDate.toString().replaceAll("-", "");

			int key = dbParser.makeReservation(guest.getPassportNumber(), room.getText(), hotel.getText(),
					arrivalDate.toEpochDay(), departureDate.toEpochDay(), price.getText());

			System.out.println("Arrival " + arrivalDate.toEpochDay());
			System.out.println("Departure " + departureDate.toEpochDay());

			System.out.println("Arrival convert" + LocalDate.ofEpochDay(arrivalDate.toEpochDay()));
			System.out.println("Arrival convert" + LocalDate.ofEpochDay(departureDate.toEpochDay()));

			System.out.println(key);

			if (key != -1) {
				// Running element manipulation on fx-thread
				Platform.runLater(() -> {
					reservationID.setText(Integer.toString(key));
					Fx.titledPaneColorNotification(title, "success");
					loadAvailableRooms();
				});
			} else {
				Platform.runLater(() -> {
					Fx.titledPaneColorNotification(title, "danger");
				});
			}
		});
	}

	public void acceptValues(Guest guest, LocalDate arrivalDate, LocalDate departureDate, Hotel hotelChoice,
			RoomQuality roomQualityChoice, int discountChoice) {

		this.guest = guest;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.hotelChoice = hotelChoice;
		this.roomQualityChoice = roomQualityChoice;
		this.discountChoice = discountChoice;

		setFields();

	}

	private void setFields() {
		firstname.setText(guest.getFirstName());
		lastname.setText(guest.getLastName());
		passport.setText(guest.getPassportNumber());
		telephone.setText(guest.getTelephoneNumber());
		arrival.setText(arrivalDate.toString());
		departure.setText(departureDate.toString());

		if (hotelChoice.getName() != null && hotelChoice.getName() != ""
				&& !(hotelChoice.getName() == Controller.DEFAULT_HOTEL_CHOICE)) {
			hotel.setText(hotelChoice.getName());
		} else {
			hotelChoice = new Hotel();
			hotelChoice.setName("");
			hotel.clear();
		}
		if (roomQualityChoice.getQuality() != null && roomQualityChoice.getQuality() != ""
				&& !(roomQualityChoice.getQuality() == Controller.DEFAULT_QUALITY_CHOICE)) {
			quality.setText(roomQualityChoice.getQuality());
		} else {
			roomQualityChoice = new RoomQuality();
			roomQualityChoice.setQuality("");
			quality.clear();
		}
		if (discountChoice > 0) {
			discount.setText("(-" + discountChoice + "%)");
		}

		loadAvailableRooms();

	}

	private void loadAvailableRooms() {

		executor.submit(() -> {
			roomResultsTable.setVisible(false);
			progress.setVisible(true);
			room.clear();
			rooms = FXCollections.observableArrayList(dbParser.checkAvailableRoomsBetweenDates(arrivalDate.toEpochDay(),
					departureDate.toEpochDay(), hotelChoice.getName(), roomQualityChoice.getQuality()));
			roomResultsTable.setItems(rooms);
			progress.setVisible(false);
			roomResultsTable.setVisible(true);
		});

	}

	private void addChangeListeners() {
		room.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!hotel.getText().isEmpty() && !quality.getText().isEmpty()) {
				calculatePrice();
			}
		});
		hotel.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!hotel.getText().isEmpty() && !quality.getText().isEmpty()) {
				calculatePrice();
			}
		});
		quality.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!hotel.getText().isEmpty() && !quality.getText().isEmpty()) {
				calculatePrice();
			}
		});
	}

	private void calculatePrice() {
		int temp = controller.getQualityPrice(hotel.getText(), quality.getText());

		temp *= getDays();

		if (discountChoice > 0) {
			double discountTmp = (double) discountChoice / 100;
			temp *= (1.00 - discountTmp);
		}

		price.setText(Integer.toString(temp));

	}

	private int getDays() {
		long arrival = arrivalDate.toEpochDay();
		long departure = departureDate.toEpochDay();
		int days = (int) Math.abs(arrival - departure);

		return days;
	}

	@FXML
	void initialize() {
		colHotel.setCellValueFactory(new PropertyValueFactory<Room, String>("hotelName"));
		colQuality.setCellValueFactory(new PropertyValueFactory<Room, String>("quality"));
		colRoomNumber.setCellValueFactory(new PropertyValueFactory<Room, String>("roomNumber"));
		addChangeListeners();
	}

	public void injectMainController(Controller controller) {
		this.controller = controller;
	}
}
