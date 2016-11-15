import java.awt.BorderLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * The PopUp class creates an output window.
 *
 */
public class PopUp extends JFrame {

	private static final long serialVersionUID = -6559673435200505582L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JScrollPane jsp;

	/**
	 * Class constructor
	 */
	public PopUp() {

		setBounds(700, 110, 700, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textArea = new JTextArea();
		jsp = new JScrollPane(textArea);
		
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setMargin(new Insets(10,10,10,10));
		
		contentPane.add(jsp);
		contentPane.revalidate();
		contentPane.repaint();
		
	}
	
	/**
	 * Adds text to the output window
	 * @param text a string to be added to the output window
	 */
	public void addText(String text) {
		textArea.append(text);
	}

}
