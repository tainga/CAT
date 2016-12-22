import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * @author Anastasia Taing, John Bucknam
 * 
 */

/**
 * The PopUp class creates an output window.
 *
 */
public class PopUp extends JFrame {

	private static final long serialVersionUID = -6559673435200505582L;
	private Consumer consumer;
	private JPanel contentPane;
	private JTextArea textArea;
	private JScrollPane jsp;
	private LinkedList<String> queue;
	private Semaphore sem;
	private Semaphore mutex;
	private WindowListener exitListener;

	/**
	 * Class constructor
	 */
	public PopUp() {

		sem = new Semaphore(0);
		mutex = new Semaphore(1);
		
		setBounds(700, 110, 700, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textArea = new JTextArea();
		
		jsp = new JScrollPane(textArea);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		queue = new LinkedList<>();
		
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setMargin(new Insets(10,10,10,10));
		
		contentPane.add(jsp);
		contentPane.revalidate();
		contentPane.repaint();
		
		exitListener = new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				consumer.terminate();
				textArea.setText("");
				contentPane.revalidate();
				contentPane.repaint();
				setVisible(false);
			}
		};
		
		if(consumer == null)
		{
			consumer = new Consumer();
		}
		consumer.start();
		
		this.addWindowListener(exitListener);
	}

	/**
	 * Adds text to the output window
	 * @param text a string to be added to the output window
	 */
	public void addText(String text) {
		try {
			mutex.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		queue.add(text);
		mutex.release();
		sem.release(1);

	}
	
	private class Consumer extends Thread {
		private boolean running;
		
		public void run()
		{
			running = true;
			while (running) {
				try {
					sem.acquire(1);
					mutex.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					textArea.append(queue.pop());
					contentPane.revalidate();
					contentPane.repaint();
					mutex.release();
	        		try {
	        			Thread.sleep(1);
	        		} catch (InterruptedException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
				} catch (NoSuchElementException e) {
        			break;
				}
			}
		}
		
		public void terminate()
		{
			running = false;
			sem.release(1);
		}
	}
	
}
