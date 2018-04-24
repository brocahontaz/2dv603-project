package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Guest;
import utilities.Fx;

public class GuestInfoPopupController {

	private DBParser dbParser = new DBParser();
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private String tempPassportnumber; // There to make it able to change the current passport number aswell
	private ObservableList<model.Reservation> reservations;

	@FXML
	private TitledPane guestInfoBox;

	@FXML
	private TextField guestInfoFirstname;

	@FXML
	private TextField guestInfoLastname;

	@FXML
	private TextField guestInfoAddress;

	@FXML
	private TextField guestInfoTelephone;

	@FXML
	private TextField guestInfoCreditCard;

	@FXML
	private TextField guestInfoPassport;

	@FXML
	private TableColumn<model.Reservation, String> reservationIdCol;

	@FXML
	private TableColumn<model.Reservation, String> reservationHotelCol;

	@FXML
	private TableColumn<model.Reservation, String> reservationRoomCol;

	@FXML
	private TableView<model.Reservation> guestInfoPopupReservationsTable;

	@FXML
	private Button closeGuestInfoPopup;

	@FXML
	private Button saveGuestInfoPopup;

	@FXML
	private ProgressIndicator progress;

	@FXML
	void closeGuestInfoPopup(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	/*
	 * Updates the guests info in the database
	 */
	@FXML
	void updateGuest(MouseEvent event) {
		if (guestInfoFirstname.getText().isEmpty()) {
			Fx.titledPaneColorNotification(guestInfoBox, "danger");
			return;
		}
		if (guestInfoLastname.getText().isEmpty()) {
			Fx.titledPaneColorNotification(guestInfoBox, "danger");
			return;
		}
		if (guestInfoAddress.getText().isEmpty()) {
			Fx.titledPaneColorNotification(guestInfoBox, "danger");
			return;
		}
		if (guestInfoTelephone.getText().isEmpty()) {
			Fx.titledPaneColorNotification(guestInfoBox, "danger");
			return;
		}
		if (guestInfoCreditCard.getText().isEmpty()) {
			Fx.titledPaneColorNotification(guestInfoBox, "danger");
			return;
		}
		if (guestInfoPassport.getText().isEmpty()) {
			Fx.titledPaneColorNotification(guestInfoBox, "danger");
			return;
		}

		executor.submit(() -> {
			String firstName = guestInfoFirstname.getText();
			String lastName = guestInfoLastname.getText();
			String address = guestInfoAddress.getText();
			String telephone = guestInfoTelephone.getText();
			String creditCard = guestInfoCreditCard.getText();
			String passport = guestInfoPassport.getText();

			if (dbParser.updateGuest(firstName, lastName, address, telephone, creditCard, passport,
					tempPassportnumber) == true) {
				Fx.titledPaneColorNotification(guestInfoBox, "success");
			} else {
				Fx.titledPaneColorNotification(guestInfoBox, "danger");
			}
		});

	}

	/*
	 * Setting the textfields contents to the guests current information
	 */
	void setupGuestInfoPopup(Guest guest) {
		guestInfoFirstname.setText(guest.getFirstName());
		guestInfoLastname.setText(guest.getLastName());
		guestInfoAddress.setText(guest.getAddress());
		guestInfoTelephone.setText(guest.getTelephoneNumber());
		guestInfoCreditCard.setText(guest.getCreditCard());
		guestInfoPassport.setText(guest.getPassportNumber());
		tempPassportnumber = guest.getPassportNumber();

		executor.submit(() -> {
			guestInfoPopupReservationsTable.setVisible(false);
			progress.setVisible(true);
			reservations = FXCollections.observableArrayList(dbParser.getReservationByPassport(tempPassportnumber));
			guestInfoPopupReservationsTable.setItems(reservations);
			guestInfoPopupReservationsTable.setVisible(true);
			progress.setVisible(false);

		});
	}

	@FXML
	void keyReleasedProperty(KeyEvent event) {
		boolean isDisabled = (guestInfoFirstname.getText().isEmpty() || guestInfoLastname.getText().isEmpty()
				|| guestInfoAddress.getText().isEmpty() || guestInfoTelephone.getText().isEmpty()
				|| guestInfoCreditCard.getText().isEmpty() || guestInfoPassport.getText().isEmpty())
				|| guestInfoPassport.getText().isEmpty();
		saveGuestInfoPopup.setDisable(isDisabled);
	}

	@FXML
	void initialize() {
		reservationIdCol.setCellValueFactory(new PropertyValueFactory<model.Reservation, String>("id"));
		reservationHotelCol.setCellValueFactory(new PropertyValueFactory<model.Reservation, String>("hotel"));
		reservationRoomCol.setCellValueFactory(new PropertyValueFactory<model.Reservation, String>("roomNumber"));
	}
}
