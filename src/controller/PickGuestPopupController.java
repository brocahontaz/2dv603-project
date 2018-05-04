package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Guest;

/**
 * Controller for the Pick Guest popup window
 * 
 * @author Johan Andersson, Fredrik Norrman, David Larsson
 *
 */
public class PickGuestPopupController {

	private ObservableList<Guest> guests;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private DBParser dbParser = new DBParser();
	private Controller controller;
	private TextField textfield;

	@FXML
	private Button popupGuestSearchButton;

	@FXML
	private Button closeGuestsPopUpButton;

	@FXML
	private TableView<model.Guest> guestsResultTable;

	@FXML
	private TextField popupGuestSearch;

	@FXML
	private TableColumn<model.Guest, String> popFirstNameCol;

	@FXML
	private TableColumn<model.Guest, String> popLastNameCol;

	@FXML
	private TableColumn<model.Guest, String> popPassportCol;

	@FXML
	private TableColumn<model.Guest, String> popTelephoneCol;

	@FXML
	private ProgressIndicator progress;

	/**
	 * Close the pop up
	 * 
	 * @param event
	 */
	@FXML
	void closeGuestsPopUp(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	/**
	 * Clear the TableView and return the result of the search.
	 */
	@FXML
	void popupGuestSearch(ActionEvent event) {
		executor.submit(() -> {
			progress.setVisible(true);
			guestsResultTable.setVisible(false);
			guestsResultTable.getItems().clear();
			String searchInput = popupGuestSearch.getText();
			searchInput = searchInput.trim();

			/*
			 * If search-box input is all numeric, search by passport number, else search by
			 * first and last names if input contains a space, else search by only last name
			 */

			if (searchInput.matches("[0-9]+")) {
				guests = FXCollections.observableArrayList(dbParser.searchGuests("", "", "", "", "", searchInput));
			} else if (searchInput.contains(" ")) {
				String[] firstAndLastName = searchInput.split(" ");
				guests = FXCollections.observableArrayList(
						dbParser.searchGuests(firstAndLastName[0], firstAndLastName[1], "", "", "", ""));
			} else {
				guests = FXCollections.observableArrayList(dbParser.searchGuests("", searchInput, "", "", "", ""));
			}
			guestsResultTable.setItems(guests);
			progress.setVisible(false);
			guestsResultTable.setVisible(true);
		});
	}

	/**
	 * Set the TextField to return on selection
	 * 
	 * @param textfield
	 */
	public void setTextField(TextField textfield) {
		this.textfield = textfield;
	}

	/**
	 * Get the Guest when double clicking on row.
	 */
	@FXML
	private void getGuestData(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Guest guest = guestsResultTable.getSelectionModel().getSelectedItem();
			controller.displayPickedGuest(guest, textfield);
			closeGuestsPopUp(event);
		}
	}

	/**
	 * Fire Search-button event when clicking ENTER in TextField.
	 */
	@FXML
	void onEnterClick(ActionEvent event) {
		popupGuestSearchButton.fire();
	}

	/**
	 * Loading all guests from database when opening the window
	 */
	private void getAllGuestsOnPopup() {
		executor.submit(() -> {
			progress.setVisible(true);
			guestsResultTable.setVisible(false);
			guestsResultTable.getItems().clear();
			guests = FXCollections.observableArrayList(dbParser.getAllGuests());
			guestsResultTable.setItems(guests);
			progress.setVisible(false);
			guestsResultTable.setVisible(true);
		});
	}

	/**
	 * Inject the Main Controller to be able to call function on it when picking a
	 * guest
	 * 
	 * @param controller
	 */
	public void injectMainController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Set cell factories for the TableView
	 */
	private void setCellFactories() {
		popFirstNameCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("firstName"));
		popLastNameCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("lastName"));
		popPassportCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("passportNumber"));
		popTelephoneCol.setCellValueFactory(new PropertyValueFactory<model.Guest, String>("telephoneNumber"));
	}

	/**
	 * Load Guest(s) upon opening of the window.
	 */
	@FXML
	void initialize() {
		getAllGuestsOnPopup();
		setCellFactories();
	}
}
