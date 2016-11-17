import java.awt.EventQueue;
import javax.swing.JOptionPane;
/**
 * The main class that launches the application.
 * @author Anastasia Taing
 * @version 1.0 November 2016
 */
public class CAT {
		
	/**
	 * Launches the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame window = new Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

		
}