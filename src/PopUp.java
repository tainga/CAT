import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class PopUp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6559673435200505582L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JScrollPane jsp;

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
		
		textArea = new JTextArea();
		jsp = new JScrollPane(textArea);
		
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		
		//contentPane.add(textArea, BorderLayout.CENTER);
		contentPane.add(jsp);
		
		contentPane.revalidate();
		contentPane.repaint();
		
	}
	
	public void addText(String text) {
		//StyledDocument doc = textPane.getStyledDocument();
		//doc.insertString(0, text + "\n", null);
		textArea.append(text);
	}

}
