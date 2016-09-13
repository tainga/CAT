import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.JTextPane;

public class PopUp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6559673435200505582L;
	private JPanel contentPane;
	private JTextPane textPane;

	/**
	 * Create the frame.
	 */
	public PopUp() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(110, 110, 700, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		contentPane.add(textPane, BorderLayout.CENTER);
	}
	
	public void addText(String text) {
		try {
			textPane.getStyledDocument().insertString(0, text + "\n", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
