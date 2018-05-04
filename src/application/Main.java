package application;

import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		setupMainStage(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void setupMainStage(Stage primaryStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewConfig.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("/view/bootstrap3.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(800);
			primaryStage.setMaxHeight(1080);
			primaryStage.setMaxWidth(1920);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/view/apeemoji.png")));
			primaryStage.setTitle("HotelFX");
			loader.<Controller>getController().setStage(primaryStage);

			// Completely closes the application and all threads.
			primaryStage.setOnCloseRequest(e -> {
				Platform.exit();
				System.exit(0);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
