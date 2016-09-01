import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public class Test {
		
		/**
		 * Launch the application.
		 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame window = new Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
