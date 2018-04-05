package application;
	
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/view/ViewConfig.fxml"));
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("/view/bootstrap3.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(800);
			primaryStage.setMaxHeight(1080);
			primaryStage.setMaxWidth(1920);
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
			//primaryStage.getIcons().add(new Image("/apeemoji.png"));
			//primaryStage.getIcons().add(new Image(getClass().getResource("/apeemoji.png").toExternalForm()));
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/view/apeemoji.png")));
			primaryStage.setTitle("HotelFX");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
