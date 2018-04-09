package utilities;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;



public class Fx {
	
	private static ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public static void textFieldColorNotification(TextField textField) {
		executor.submit(() -> {
			try {
				//textField.getStyleClass().clear();
				Thread.sleep(3000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Changes the css-style of a titledpane for 3 seconds.
	 * @param titledPane
	 * @param cssStyle
	 */
	public static void titledPaneColorNotification(TitledPane titledPane, String cssStyle) {
		executor.submit(() -> {
			try {
				String old = titledPane.getStyleClass().toString();
				String oldStyle = trimCssStyle(old);
				titledPane.getStyleClass().remove(oldStyle);
				titledPane.getStyleClass().add(cssStyle);
				Thread.sleep(3000);
				titledPane.getStyleClass().remove(cssStyle);
				titledPane.getStyleClass().add(oldStyle);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Changes the css-style of a titlepane for x milliseconds.
	 * @param titledPane
	 * @param cssStyle
	 * @param time
	 */
	public static void titledPaneColorNotification(TitledPane titledPane, String cssStyle, int time) {
		executor.submit(() -> {
			try {
				String old = titledPane.getStyleClass().toString();
				String oldStyle = trimCssStyle(old);
				titledPane.getStyleClass().remove(oldStyle);
				titledPane.getStyleClass().add(cssStyle);
				Thread.sleep(time);
				titledPane.getStyleClass().remove(cssStyle);
				titledPane.getStyleClass().add(oldStyle);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	// Helper method to trim cssString.
	private static String trimCssStyle(String cssStyle) {
		String result = "";
		result = cssStyle.substring(cssStyle.lastIndexOf(" ")+1);
		return result;
	}

}
