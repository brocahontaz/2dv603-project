package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private Room roomChoice;
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
	private TextField room;

	@FXML
	private TextField quality;

	@FXML
	private Button close;
	
	@FXML
    private TableView<?> roomResultsTable;

    @FXML
    private TableColumn<?, ?> colHotel;

    @FXML
    private TableColumn<?, ?> colQuality;

    @FXML
    private TableColumn<?, ?> colRoomNumber;

    @FXML
    private Button makeResButton;

	@FXML
	void close(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	public void acceptValues(Guest guest, Room roomChoice, String arrivalDate, String departureDate, Hotel hotelChoice,
			RoomQuality roomQualityChoice, int discountChoice) {

		this.guest = guest;
		this.roomChoice = roomChoice;
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

		if (hotelChoice.getName() != null && hotelChoice.getName() != ""
				&& !(hotelChoice.getName() == Controller.DEFAULT_HOTEL_CHOICE)) {
			hotel.setText(hotelChoice.getName());
		} else {
			hotel.clear();
		}
		if (roomQualityChoice.getQuality() != null && roomQualityChoice.getQuality() != ""
				&& !(roomQualityChoice.getQuality() == Controller.DEFAULT_QUALITY_CHOICE)) {
			quality.setText(roomQualityChoice.getQuality());
		} else {
			quality.clear();
		}
		if (roomChoice.getRoomNumber() != 0) {
			room.setText(Integer.toString(roomChoice.getRoomNumber()));
			roomResultsTable.setVisible(false);
		} else {
			room.clear();
			roomResultsTable.setVisible(true);
		}

	}

	@FXML
	void initialize() {

	}

	public void injectMainController(Controller controller) {
		this.controller = controller;
	}
}
