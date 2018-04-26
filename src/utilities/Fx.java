package utilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.UnaryOperator;

import javafx.animation.PauseTransition;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class Fx {

	public enum Regex {

		ONLY_NUMBERS("^[0-9]+$", "This TextField can only\ncontain numbers!"), NO_NUMBERS("^[a-zA-Z- ',]+$",
				"This TextField can not\ncontain numbers!");
		private String regex;
		private String message;

		Regex(String regex, String message) {
			this.regex = regex;
			this.message = message;
		}

		public String regex() {
			return this.regex;
		}

		public String message() {
			return this.message;
		}

		@Override
		public String toString() {
			return "Regex: " + this.regex + " / Message " + this.message;
		}
	}

	public final static int FIRSTNAME_LENGTH = 30;
	public final static int LASTNAME_LENGTH = 30;
	public final static int PASSPORT_LENGTH = 16;
	public final static int CREDITCARD_LENGTH = 16;
	public final static int TELEPHONE_MIN_LENGTH = 9;
	public final static int TELEPHONE_LENGTH = 20;
	public final static int RESERVATION_ID_LENGTH = 10000;

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
	 * Clear 12 textfields.
	 * 
	 * @param textField1
	 * @param textField2
	 * @param textField3
	 * @param textField4
	 * @param textField5
	 * @param textField6
	 * @param textField7
	 * @param textField8
	 * @param textField9
	 * @param textField10
	 * @param textField11
	 * @param textField12
	 */
	public static void textFieldClear(TextField textField1, TextField textField2, TextField textField3,
			TextField textField4, TextField textField5, TextField textField6, TextField textField7,
			TextField textField8, TextField textField9, TextField textField10, TextField textField11,
			TextField textField12) {
		textField1.clear();
		textField2.clear();
		textField3.clear();
		textField4.clear();
		textField5.clear();
		textField6.clear();
		textField7.clear();
		textField8.clear();
		textField9.clear();
		textField10.clear();
		textField11.clear();
		textField12.clear();
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

	/**
	 * Changes the css-style of a titledpane for X seconds.
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

	/**
	 * Changes the css-style of a titledpane for 3 seconds and disable a button for
	 * the same time.
	 * 
	 * @param titledPane
	 * @param button
	 * @param cssStyle
	 */
	public static void titledPaneColorNotificationButton(TitledPane titledPane, Button button, String cssStyle) {
		button.setDisable(true);
		String oldStyle = trimCssStyle(titledPane.getStyleClass().toString());
		titledPane.getStyleClass().remove(oldStyle);
		titledPane.getStyleClass().add(cssStyle);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> titledPaneColorNotificationButtonHelp(titledPane, button, cssStyle, oldStyle));
		pause.play();
	}

	/**
	 * Changes the css-style of a titledpane for X seconds and disable a button for
	 * the same time.
	 * 
	 * @param titledPane
	 * @param button
	 * @param cssStyle
	 * @param time
	 */
	public static void titledPaneColorNotificationButton(TitledPane titledPane, Button button, String cssStyle,
			int time) {
		button.setDisable(true);
		String oldStyle = trimCssStyle(titledPane.getStyleClass().toString());
		titledPane.getStyleClass().remove(oldStyle);
		titledPane.getStyleClass().add(cssStyle);
		PauseTransition pause = new PauseTransition(Duration.seconds(time));
		pause.setOnFinished(event -> titledPaneColorNotificationButtonHelp(titledPane, button, cssStyle, oldStyle));
		pause.play();
	}

	public static void setTextFormatter(TextField field, int minLength, int maxLength, Fx.Regex regex) {

		final ContextMenu menu = new ContextMenu();
		UnaryOperator<Change> rejectChange = c -> {
			menu.hide();

			// check if the change might effect the validating predicate
			if (c.isContentChange()) {
				// check if change is valid
				if (c.getControlNewText().length() > maxLength) {
					// invalid change
					// sugar: show a context menu with error message
					menu.getItems().clear();
					menu.getItems().add(new MenuItem("This field takes\n" + maxLength + " characters only."));
					menu.show(c.getControl(), Side.BOTTOM, 0, 0);
					// return null to reject the change
					return null;
				}

				if (!c.getControlNewText().matches(regex.regex())) {
					if (!c.getControlNewText().isEmpty()) {
						// invalid change
						// sugar: show a context menu with error message
						menu.getItems().clear();
						menu.getItems().add(new MenuItem(regex.message()));
						menu.show(c.getControl(), Side.BOTTOM, 0, 0);
						// return null to reject the change
						return null;
					}

				}
			}
			// valid change: accept the change by returning it
			return c;
		};
		
		final Tooltip tooltip = new Tooltip();
		tooltip.setText("Must be at least " + minLength + " characters long.");
		field.setTooltip(tooltip);
		field.setTextFormatter(new TextFormatter<TextField>(rejectChange));
	}

	// Helper method to titledPaneColorNotificationButton
	private static void titledPaneColorNotificationButtonHelp(TitledPane titledPane, Button button, String cssStyle,
			String oldStyle) {
		titledPane.getStyleClass().remove(cssStyle);
		titledPane.getStyleClass().add(oldStyle);
		button.setDisable(false);
	}

	// Helper method to titledPaneColorNotification
	private static void titledPaneColorNotificationHelp(TitledPane titledPane, String cssStyle, String oldStyle) {
		titledPane.getStyleClass().remove(cssStyle);
		titledPane.getStyleClass().add(oldStyle);
	}

	// Helper method to trim cssString.
	private static String trimCssStyle(String cssStyle) {
		String result = "";
		result = cssStyle.substring(cssStyle.lastIndexOf(" ") + 1);
		return result;
	}

}
