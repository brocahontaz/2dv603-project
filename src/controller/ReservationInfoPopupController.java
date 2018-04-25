package controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Guest;
import model.Reservation;
import model.Room;

public class ReservationInfoPopupController {

	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private DBParser dbParser = new DBParser();

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

	public void setupReservation(String id) {
		// this.id = id;

		executor.submit(() -> {
			System.out.println("hej???");
			progress.setVisible(true);
			ArrayList<Object> data = dbParser.getGuestAndReservationById(id.trim());

			Guest guest = (Guest) data.get(0);
			Reservation reservation = (Reservation) data.get(1);
			Room resRoom = (Room) data.get(2);

			System.out.println(guest);
			System.out.println(reservation);
			System.out.println(resRoom);

			reservationID.setText(Integer.toString(reservation.getId()));
			arrivalDate.setText(Integer.toString(reservation.getArrivalDate()));
			departureDate.setText(Integer.toString(reservation.getDepartureDate()));
			hotel.setText(reservation.getHotel());
			quality.setText(resRoom.getQuality());
			room.setText(Integer.toString(reservation.getRoomNumber()));
			firstname.setText(guest.getFirstName());
			lastname.setText(guest.getLastName());
			passport.setText(guest.getPassportNumber());
			address.setText(guest.getAddress());
			telephone.setText(guest.getTelephoneNumber());
			creditcard.setText(guest.getCreditCard());
			price.setText(Integer.toString(reservation.getPrice()));

			/*
			 * arrivalDate.setText("TEST"); departureDate.setText("TEST");
			 * hotel.setText("TEST"); quality.setText("TEST"); room.setText("TEST");
			 * firstname.setText("TEST"); lastname.setText("TEST");
			 * passport.setText("TEST"); address.setText("TEST"); telephone.setText("TEST");
			 * creditcard.setText("TEST"); price.setText("TEST");
			 */

			
			if (reservation.getCheckedIn()) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						checkedIN.setSelected(true);
						cancelReservationButton.setDisable(true);
					}
				});
			}
			if (reservation.getCheckedOut()) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						checkedOUT.setSelected(true);
						cancelReservationButton.setDisable(true);
					}
				});
			}
			
			progress.setVisible(false);
		});
	}

	@FXML
	void cancelReservation(MouseEvent event) {

		if (!checkedIN.isSelected() && !checkedOUT.isSelected()) {
			if (dbParser.cancelReservation(id)) {
				executor.submit(() -> {
					((Node) (event.getSource())).getScene().getWindow().hide();
				});
			}
		}

	}

	@FXML
	void closePop(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void initialize() {

	}
}