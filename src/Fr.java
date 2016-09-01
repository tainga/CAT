import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;

import javax.print.DocFlavor.URL;
import javax.swing.Box;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

import net.miginfocom.swing.MigLayout;
import javax.swing.SpringLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class Fr {

	private JFrame frame;
	private JTextField textField_1;
	private JCheckBox chckbxToConsole;
	private JCheckBox chckbxToTextFile;
	private JRadioButton rdbtnErrorsOnly;
	private JRadioButton rdbtnAllRecords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fr window = new Fr();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Fr() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setTitle("CAT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		java.net.URL iconURL = getClass().getResource("cat-paw.png");
		ImageIcon icon = new ImageIcon(iconURL);
		frame.setIconImage(icon.getImage());
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblCgwAnalysisTool = new JLabel("CGW Analysis Tool");
		panel.add(lblCgwAnalysisTool);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_2.add(panel_1, BorderLayout.CENTER);
	    
	    //ButtonGroup group = new ButtonGroup();
		
		ButtonGroup group1 = new ButtonGroup();
	    panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
	    
	    JPanel panel_4 = new JPanel();
	    panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
	    panel_1.add(panel_4);
	    panel_4.setLayout(new GridLayout(0, 2, 0, 0));
	    
	    JLabel lblOutput = new JLabel("Output:");
	    panel_4.add(lblOutput);
	    
	    JLabel lblInclude = new JLabel("Include:");
	    panel_4.add(lblInclude);
	    
	    chckbxToConsole = new JCheckBox("to console");
	    panel_4.add(chckbxToConsole); 
	    
	    rdbtnErrorsOnly = new JRadioButton("errors only");
	    panel_4.add(rdbtnErrorsOnly);
	    group1.add(rdbtnErrorsOnly);
	    
	    chckbxToTextFile = new JCheckBox("to text file");
	    panel_4.add(chckbxToTextFile);
	    
	    rdbtnAllRecords = new JRadioButton("all records");
	    panel_4.add(rdbtnAllRecords);
	    group1.add(rdbtnAllRecords);
	    
	    JPanel panel_5 = new JPanel();
	    panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
	    panel_1.add(panel_5);
	    panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    JButton btnInputFolder = new JButton("Input folder");
	    panel_5.add(btnInputFolder);
	    
	    JButton btnOutputFolder = new JButton("Output folder");
	    panel_5.add(btnOutputFolder);
	    JFileChooser fileChooser = new JFileChooser();
	    
	    JPanel panel_6 = new JPanel();
	    panel_1.add(panel_6);
	    panel_6.setLayout(new GridLayout(0, 2, 0, 0));
	    
	    JPanel panel_7 = new JPanel();
	    panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
	    panel_6.add(panel_7);
	    panel_7.setLayout(new BorderLayout(0, 0));
	    
	    JLabel lblGenerateReport = new JLabel("Generate Report");
	    lblGenerateReport.setHorizontalAlignment(SwingConstants.CENTER);
	    panel_7.add(lblGenerateReport);
	    
	    JPanel panel_11 = new JPanel();
	    panel_7.add(panel_11, BorderLayout.SOUTH);
	    
	    JButton btnGenerate = new JButton("");
	    JLabel generateLbl = new JLabel("Generate");
	    btnGenerate.add(generateLbl);
	    btnGenerate.addActionListener(new OutputGenerator());
	    panel_11.add(btnGenerate);
	    
	    JPanel panel_8 = new JPanel();
	    panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
	    panel_6.add(panel_8);
	    panel_8.setLayout(new BorderLayout(0, 0));
	    
	    JPanel panel_14 = new JPanel();
	    panel_8.add(panel_14, BorderLayout.CENTER);
	    panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.Y_AXIS));
	    
	    JPanel panel_9 = new JPanel();
	    panel_14.add(panel_9);
	    
	    JLabel lblSearchBy = new JLabel("Search by ");
	    panel_9.add(lblSearchBy);
	    
	    JComboBox comboBox = new JComboBox();
	    panel_9.add(comboBox);
	    comboBox.addItem("error type");
	    comboBox.addItem("location");
	    comboBox.addItem("time range");
	    
	    JPanel panel_12 = new JPanel();
	    panel_14.add(panel_12);
	    
	    JLabel lblhint = new JLabel("");
	    panel_12.add(lblhint);
	    lblhint.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    JPanel panel_10 = new JPanel();
	    panel_14.add(panel_10);
	    panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    JLabel lblEnterSearchTerm = new JLabel("Search term:");
	    panel_10.add(lblEnterSearchTerm);
	    
	    textField_1 = new JTextField();
	    panel_10.add(textField_1);
	    textField_1.setColumns(16);
	    
	    JPanel panel_13 = new JPanel();
	    panel_8.add(panel_13, BorderLayout.SOUTH);
	    
	    JButton btnSearch = new JButton("");
	    JLabel searchLbl = new JLabel("Search");
	    btnSearch.add(searchLbl);
	    panel_13.add(btnSearch);
		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.SOUTH);
		
		JLabel lblcopy = new JLabel("\u00a9 2016");
		panel_3.add(lblcopy);
		
	}
	
	class OutputGenerator implements ActionListener {
	      public void actionPerformed(ActionEvent e) {
	    	  if (!chckbxToConsole.isSelected() && !chckbxToTextFile.isSelected()) {
	    		  //error message to label
	    		  return;
	    	  }
	    	  if (!rdbtnErrorsOnly.isSelected() && !rdbtnAllRecords.isSelected()) {
	    		  //same
	    		  return;
	    	  }
	    	  
	    	  //tmp
	    	String inputDirectory = "C:\\Users\\anastasia taing\\Desktop\\CGW project\\example save crash\\vpsSYS2_27Jun2016_12_27_25_cgw1e\\CGWLOGS\\control";
  			
	    	//chaSYS1_20Jul2016_13_18_17_cgw1f
	    	String dateTime = LocalDateTime.now().toString().replace(':', '\'').replace('T', ' ');

  			String outputDirectory = "C:\\Users\\anastasia taing\\Desktop\\CGW project\\output\\CAT " + dateTime + ".txt";
  			
  			Reader reader = new Reader();
	    	//String output = "";

	    
	    		if (chckbxToConsole.isSelected()) {
	    			try {
						reader.parseLog(inputDirectory, rdbtnErrorsOnly.isSelected());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    	}
		    	  
		    	if (chckbxToTextFile.isSelected()) {
		    		try {
						reader.parseLog(inputDirectory, outputDirectory, rdbtnErrorsOnly.isSelected());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    	}
	      }
	}
	
	class SearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
		}
		
	}
	
	   

}
