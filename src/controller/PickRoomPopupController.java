package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PickRoomPopupController {

	@FXML
	private TextField popupRoomSearch;

	@FXML
	private Button popupRoomSearchButton;

	@FXML
	private TableView<?> roomsResultTable;

	@FXML
	private TableColumn<?, ?> popRoomCol;

	@FXML
	private TableColumn<?, ?> popQualityCol;

	@FXML
	private TableColumn<?, ?> popPriceCol;

	@FXML
	private TableColumn<?, ?> popReservedCol;

	@FXML
	private Button closeRoomsPopUpButton;

	@FXML
	void closeRoomsPopUp(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void popupRoomSearch(ActionEvent event) {

	}

}
