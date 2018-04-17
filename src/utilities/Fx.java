package utilities;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;



public class Fx {
	
	private static ExecutorService executor = Executors.newFixedThreadPool(1);
	
	/**
	 * Clear 6 textfields.
	 * @param textField1
	 * @param textField2
	 * @param textField3
	 * @param textField4
	 * @param textField5
	 * @param textField6
	 */
	public static void textFieldClear(TextField textField1, TextField textField2, TextField textField3, TextField textField4,TextField textField5 ,TextField textField6) {
		textField1.clear();
		textField2.clear();
		textField3.clear();
		textField4.clear();
		textField5.clear();
		textField6.clear();
	}
	
	/**
	 * Clear 8 textfields.
	 * @param textField1
	 * @param textField2
	 * @param textField3
	 * @param textField4
	 * @param textField5
	 * @param textField6
	 * @param textField7
	 * @param textField8
	 */
	public static void textFieldClear(TextField textField1, TextField textField2, TextField textField3, TextField textField4,TextField textField5 ,TextField textField6, TextField textField7, TextField textField8) {
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
				System.out.println("Exception from FX titledPaneColorNotification 3sec");
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
				System.out.println("Exception from FX titledPaneColorNotification x ms");
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
