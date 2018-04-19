package utilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.PauseTransition;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.Duration;

public class Fx {

	/**
	 * Clear 6 textfields.
	 * 
	 * @param textField1
	 * @param textField2
	 * @param textField3
	 * @param textField4
	 * @param textField5
	 * @param textField6
	 */
	public static void textFieldClear(TextField textField1, TextField textField2, TextField textField3,
			TextField textField4, TextField textField5, TextField textField6) {
		textField1.clear();
		textField2.clear();
		textField3.clear();
		textField4.clear();
		textField5.clear();
		textField6.clear();
	}

	/**
	 * Clear 8 textfields.
	 * 
	 * @param textField1
	 * @param textField2
	 * @param textField3
	 * @param textField4
	 * @param textField5
	 * @param textField6
	 * @param textField7
	 * @param textField8
	 */
	public static void textFieldClear(TextField textField1, TextField textField2, TextField textField3,
			TextField textField4, TextField textField5, TextField textField6, TextField textField7,
			TextField textField8) {
		textField1.clear();
		textField2.clear();
		textField3.clear();
		textField4.clear();
		textField5.clear();
		textField6.clear();
		textField7.clear();
		textField8.clear();
	}

	/**
	 * Changes the css-style of a textfield for 3 seconds.
	 * 
	 * @param textField
	 * @param cssStyle
	 */
	public static void textFieldColorNotification(TextField textField, String cssStyle) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(() -> {
			try {
				String old = textField.getStyleClass().toString();
				String oldStyle = trimCssStyle(old);
				textField.getStyleClass().remove(oldStyle);
				textField.getStyleClass().add(cssStyle);
				Thread.sleep(3000);
				textField.getStyleClass().remove(cssStyle);
				textField.getStyleClass().add(oldStyle);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Exception from FX textFieldColorNotification");
			}
		});
		executor.shutdown();
	}

	/**
	 * Changes the css-style of a titledpane for 3 seconds.
	 * 
	 * @param titledPane
	 * @param cssStyle
	 */
	public static void titledPaneColorNotification(TitledPane titledPane, String cssStyle) {
		String oldStyle = trimCssStyle(titledPane.getStyleClass().toString());
		titledPane.getStyleClass().remove(oldStyle);
		titledPane.getStyleClass().add(cssStyle);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> titledPaneColorNotificationHelp(titledPane, cssStyle, oldStyle));
		pause.play();
	}
	// Helper method to titledPaneColorNotification
	private static void titledPaneColorNotificationHelp(TitledPane titledPane, String cssStyle, String oldStyle) {
		titledPane.getStyleClass().remove(cssStyle);
		titledPane.getStyleClass().add(oldStyle);
	}

	/**
	 * Changes the css-style of a titlepane for x seconds.
	 * 
	 * @param titledPane
	 * @param cssStyle
	 * @param time
	 */
	public static void titledPaneColorNotification(TitledPane titledPane, String cssStyle, int time) {
		String oldStyle = trimCssStyle(titledPane.getStyleClass().toString());
		titledPane.getStyleClass().remove(oldStyle);
		titledPane.getStyleClass().add(cssStyle);
		PauseTransition pause = new PauseTransition(Duration.seconds(time));
		pause.setOnFinished(event -> titledPaneColorNotificationHelp(titledPane, cssStyle, oldStyle));
		pause.play();
	}

	// Helper method to trim cssString.
	private static String trimCssStyle(String cssStyle) {
		String result = "";
		result = cssStyle.substring(cssStyle.lastIndexOf(" ") + 1);
		return result;
	}

}
