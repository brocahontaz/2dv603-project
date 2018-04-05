package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Room;

public class PickRoomPopupController {
	
	private ObservableList<Room> rooms;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private DBParser dbParser = new DBParser();
	private Controller controller;

	@FXML
	private TextField popupRoomSearch;

	@FXML
	private Button popupRoomSearchButton;

	@FXML
	private TableView<model.Room> roomsResultTable;

	@FXML
	private TableColumn<model.Room, Integer> popRoomCol;

	@FXML
	private TableColumn<model.Room, Integer> popBedsCol;

	@FXML
	private TableColumn<model.Room, Boolean> popReservedCol;

	@FXML
	private Button closeRoomsPopUpButton;

	@FXML
	void closeRoomsPopUp(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	/*
	 * No filtering or selection at the moment, no query for getting rooms in
	 * place yet so this is temporary.
	 */
	@FXML
	void popupRoomSearch(ActionEvent event) {
		executor.submit(() -> {
			//String searchInput = popupRoomSearch.getText();
			rooms = FXCollections.observableArrayList();
			rooms.add(new Room(2, 2, false));
			roomsResultTable.setItems(rooms);
		});
	}
	
	/**
	 * Get the Guest when double clicking on row.
	 */
	@FXML
	private void getRoomData(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Room room = roomsResultTable.getSelectionModel().getSelectedItem();
			controller.displayPickedRoom(room);
			closeRoomsPopUp(event);
		}
	}
	
	/**
	 * Fire Search-button event when clicking ENTER in TextField.
	 */
    @FXML
    void onEnterClick(ActionEvent event) {
    	popupRoomSearchButton.fire();
    }

	
	/**
	 * Load Room(s) upon opening of the window.
	 */
	@FXML
	void initialize() {
		popRoomCol.setCellValueFactory(new PropertyValueFactory<model.Room, Integer>("roomNumber"));
		popBedsCol.setCellValueFactory(new PropertyValueFactory<model.Room, Integer>("numberOfBeds"));
		popReservedCol.setCellValueFactory(new PropertyValueFactory<model.Room, Boolean>("available"));		
	}
	
	public void injectMainController(Controller controller) {
		this.controller = controller;
	}

}
