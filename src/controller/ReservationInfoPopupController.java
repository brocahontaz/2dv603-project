package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
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

	private String id;
	
	public void setupReservation(String id) {
		this.id = id;
		
		reservationID.setText(id);
		
		executor.submit(() -> {
			ObservableList<Object> data = FXCollections
					.observableArrayList(dbParser.getGuestAndReservationById(id));
			Guest guest = (Guest) data.get(0);
			Reservation reservation = (Reservation) data.get(1);
			Room resRoom = (Room) data.get(2);
			
			arrivalDate.setText(reservation.getArrivalDate() + "");
			departureDate.setText(reservation.getDepartureDate() + "");
			hotel.setText(reservation.getHotel());
			quality.setText(resRoom.getQuality());
			room.setText(reservation.getRoomNumber() + "");
			firstname.setText(guest.getFirstName());
			lastname.setText(guest.getLastName());
			passport.setText(guest.getPassportNumber());
			address.setText(guest.getAddress());
			telephone.setText(guest.getTelephoneNumber());
			creditcard.setText(guest.getCreditCard());
			price.setText(reservation.getPrice() + "");
			if (reservation.getCheckedIn()) {
				checkedIN.setSelected(true);
			}
			if (reservation.getCheckedOut()) {
				checkedOUT.setSelected(true);
			}
			
		});
	}

	@FXML
	void cancelReservation(MouseEvent event) {
		
	}

	@FXML
	void closePop(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}
}