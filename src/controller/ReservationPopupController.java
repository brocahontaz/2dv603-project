package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import model.Guest;
import model.Hotel;
import model.Room;
import model.RoomQuality;

public class ReservationPopupController {
	
	private Controller controller;
	private RoomQuality roomQualityChoice;
	private Hotel hotelChoice;
	private int discountChoice;
	private String departureDate;
	private String arrivalDate;
	private Room room;
	private Guest guest;

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
	private TextField quality;

	@FXML
	private Button close;

	@FXML
	void close(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	public void acceptValues(Guest guest, Room room, String arrivalDate, String departureDate, Hotel hotelChoice,
			RoomQuality roomQualityChoice, int discountChoice) {

		this.guest = guest;
		this.room = room;
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
		arrival.setText(arrivalDate);
		departure.setText(departureDate);
		hotel.setText(hotelChoice.getName());
		quality.setText(roomQualityChoice.getQuality());
		
	}

	@FXML
	void initialize() {

	}

	public void injectMainController(Controller controller) {
		this.controller = controller;
	}
}
