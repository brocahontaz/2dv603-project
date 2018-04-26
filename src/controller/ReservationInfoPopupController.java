package controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import model.Guest;
import model.Reservation;
import model.Room;

public class ReservationInfoPopupController {

	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private DBParser dbParser = new DBParser();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField reservationID;

	@FXML
	private TextField arrivalDate;

	@FXML
	private TextField departureDate;

	@FXML
	private TextField hotel;

	@FXML
	private TextField quality;

	@FXML
	private TextField room;

	@FXML
	private TextField firstname;

	@FXML
	private TextField lastname;

	@FXML
	private TextField passport;

	@FXML
	private TextField address;

	@FXML
	private TextField telephone;

	@FXML
	private TextField creditcard;

	@FXML
	private Text price;

	@FXML
	private CheckBox checkedIN;

	@FXML
	private CheckBox checkedOUT;

	@FXML
	private Button close;

	@FXML
	private ProgressIndicator progress;

	@FXML
	private Button cancelReservationButton;

	@FXML
	private TitledPane reservationTitle;

	private String id;
	private Controller controller;

	public void injectMainController(Controller controller) {
		this.controller = controller;
	}

	public void setupReservation(String id) {
		this.id = id;
		executor.submit(() -> {
			progress.setVisible(true);
			ArrayList<Object> data = dbParser.getGuestAndReservationById(id.trim());

			Guest guest = (Guest) data.get(0);
			Reservation reservation = (Reservation) data.get(1);
			Room resRoom = (Room) data.get(2);

			Platform.runLater(() -> {
				firstname.setText(guest.getFirstName());
				lastname.setText(guest.getLastName());
				passport.setText(guest.getPassportNumber());
				address.setText(guest.getAddress());
				telephone.setText(guest.getTelephoneNumber());
				creditcard.setText(guest.getCreditCard());
				reservationID.setText(Integer.toString(reservation.getId()));
				arrivalDate.setText(Integer.toString(reservation.getArrivalDate()));
				departureDate.setText(Integer.toString(reservation.getDepartureDate()));
				hotel.setText(reservation.getHotel());
				room.setText(Integer.toString(reservation.getRoomNumber()));
				price.setText(Integer.toString(reservation.getPrice()));

				quality.setText(resRoom.getQuality());
			});

			if (reservation.getCheckedIn()) {
				checkedIN.setSelected(true);

			} else if (reservation.getCheckedOut()) {
				checkedOUT.setSelected(true);
			} else {
				cancelReservationButton.setDisable(false);
			}

			progress.setVisible(false);
		});
	}

	@FXML
	void cancelReservation(MouseEvent event) {
		executor.submit(() -> {	
			cancelReservationButton.setDisable(true);
			if (dbParser.cancelReservation(id)) {
				// controller.removeElementFromReservationTable(id);
				controller.reloadReservationTable();
				Platform.runLater(() -> {
					((Node) (event.getSource())).getScene().getWindow().hide();
				});
			}
			cancelReservationButton.setDisable(false);
		});
		
	}

	@FXML
	void closePop(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void initialize() {

	}
}