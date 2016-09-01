import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
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
import java.awt.SystemColor;

public class Frame {

	private JFrame frame;
	private JTextField textField_1;
	private JCheckBox chckbxToConsole;
	private JCheckBox chckbxToTextFile;
	private JRadioButton rdbtnErrorsOnly;
	private JRadioButton rdbtnAllRecords;

	private JFileChooser fc = new JFileChooser();
    //private File inputFile = null;
    //private File outputDirectory = null;
    private JTextField textField;
    private JTextField textField_2;
    
    private String inputDirectory;
    private String outputDirectory;
    
    private JLabel warnLabel;
    private JLabel outputWarnLbl;
    private JLabel modeWarnLbl;
    private JTextField textField_3;
    private JTextField textField_4;
	
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

	/**
	 * Create the application.
	 */
	public Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 680, 480);
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
	    
	    JPanel panel_18 = new JPanel();
	    panel_1.add(panel_18);
	    panel_18.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    JPanel panel_4 = new JPanel();
	    panel_18.add(panel_4);
	    panel_4.setPreferredSize(new Dimension(664, 80));
	    panel_4.setBorder(new LineBorder(SystemColor.activeCaption, 2));
	    panel_4.setLayout(new GridLayout(0, 6, 0, 0));
	    
	    Component horizontalGlue_1 = Box.createHorizontalGlue();
	    panel_4.add(horizontalGlue_1);
	    
	    Component horizontalGlue_9 = Box.createHorizontalGlue();
	    panel_4.add(horizontalGlue_9);
	    
	    JLabel lblOutput = new JLabel("Output:");
	    panel_4.add(lblOutput);
	    
	    Component horizontalGlue_2 = Box.createHorizontalGlue();
	    panel_4.add(horizontalGlue_2);
	    
	    JLabel lblInclude = new JLabel("Include:");
	    panel_4.add(lblInclude);
	    
	    Component horizontalGlue_3 = Box.createHorizontalGlue();
	    panel_4.add(horizontalGlue_3);
	    
	    outputWarnLbl = new JLabel("New label");
	    outputWarnLbl.setForeground(Color.red);
	    panel_4.add(outputWarnLbl);
	    
	    Component horizontalGlue_10 = Box.createHorizontalGlue();
	    panel_4.add(horizontalGlue_10);
	    
	    chckbxToConsole = new JCheckBox("to console");
	    panel_4.add(chckbxToConsole); 
	    
	    Component horizontalGlue_5 = Box.createHorizontalGlue();
	    panel_4.add(horizontalGlue_5);
	    
	    rdbtnErrorsOnly = new JRadioButton("errors only");
	    panel_4.add(rdbtnErrorsOnly);
	    group1.add(rdbtnErrorsOnly);
	    
	    Component horizontalGlue_6 = Box.createHorizontalGlue();
	    panel_4.add(horizontalGlue_6);
	    
	    modeWarnLbl = new JLabel("New label");
	    modeWarnLbl.setForeground(Color.red);
	    panel_4.add(modeWarnLbl);
	    
	    Component horizontalGlue_11 = Box.createHorizontalGlue();
	    panel_4.add(horizontalGlue_11);
	    
	    chckbxToTextFile = new JCheckBox("to text file");
	    panel_4.add(chckbxToTextFile);
	    
	    Component horizontalGlue_8 = Box.createHorizontalGlue();
	    panel_4.add(horizontalGlue_8);
	    
	    rdbtnAllRecords = new JRadioButton("all records");
	    panel_4.add(rdbtnAllRecords);
	    group1.add(rdbtnAllRecords);
	    
	    JPanel panel_5 = new JPanel();
	    panel_5.setBorder(null);
	    panel_1.add(panel_5);
	    
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File("C:\\Users\\anastasia taing\\Desktop\\CGW project\\example save crash\\vpsSYS2_27Jun2016_12_27_25_cgw1e\\CGWLOGS\\control"));
	    panel_5.setLayout(new GridLayout(2,0));
	    
	    JPanel panel_15 = new JPanel();
	    panel_5.add(panel_15);
	    panel_15.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    // Input Folder - Button & JFileChooser
	    JButton btnInputFolder = new JButton("Input folder");
	    btnInputFolder.setPreferredSize(new Dimension(150, 23));
	    panel_15.add(btnInputFolder);
	    
	    textField = new JTextField();
	    textField.setBorder(new LineBorder(SystemColor.activeCaption));
	    textField.setBackground(new Color(234,240,246));
	    textField.setPreferredSize(new Dimension(510, 25));
	    panel_15.add(textField);
	    textField.setEditable(false);
	    
	    
	    btnInputFolder.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e)
	    	{
	    	    int returnValue = fc.showOpenDialog(new JFrame());
	    	    
	    	    if(returnValue == JFileChooser.APPROVE_OPTION)
	    	    {
	    	    	inputDirectory = fc.getSelectedFile().getAbsolutePath();
	    	    	textField.setText(inputDirectory);
	    	    }
	    	}
	    });
	    
	    JPanel panel_16 = new JPanel();
	    panel_5.add(panel_16);
	    panel_16.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    
	    // Output Folder - Button & JFileChooser
	    JButton btnOutputFolder = new JButton("Output folder");
	    btnOutputFolder.setPreferredSize(new Dimension(150, 23));
	    panel_16.add(btnOutputFolder);
	    
	    textField_2 = new JTextField();
	    textField_2.setBorder(new LineBorder(SystemColor.activeCaption));
	    textField_2.setBackground(new Color(234,240,246));
	    textField_2.setPreferredSize(new Dimension(510, 25));
	    panel_16.add(textField_2);
	    textField_2.setEditable(false);
	    
	    btnOutputFolder.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e)
	    	{
	    	    int returnValue = fc.showOpenDialog(new JFrame());
	    	    
	    	    if(returnValue == JFileChooser.APPROVE_OPTION && fc.getSelectedFile() != null)
	    	    {
	    	    	outputDirectory = fc.getSelectedFile().getAbsolutePath();
	    	    	textField_2.setText(outputDirectory);
	    	    }
	    	}
	    });
	    
	    JPanel panel_6 = new JPanel();
	    panel_1.add(panel_6);
	    panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    JPanel panel_7 = new JPanel();
	    panel_7.setBorder(new LineBorder(new Color(153, 180, 209), 2));
	    panel_6.add(panel_7);
	    panel_7.setLayout(new BorderLayout(0, 0));
	    panel_7.setPreferredSize(new Dimension(150, 170));
	    
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
	    panel_8.setPreferredSize(new Dimension(510, 170));
	    panel_8.setBorder(new LineBorder(SystemColor.activeCaption, 2));
	    panel_6.add(panel_8);
	    panel_8.setLayout(new BorderLayout(0, 0));
	    
	    JPanel panel_14 = new JPanel();
	    panel_8.add(panel_14, BorderLayout.CENTER);
	    panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.Y_AXIS));
	    
	    JPanel panel_9 = new JPanel();
	    panel_14.add(panel_9);
	    
	    JLabel lblSearchBy = new JLabel("Search by ");
	    panel_9.add(lblSearchBy);
	    
	    JComboBox<String> comboBox = new JComboBox<String>();
	    panel_9.add(comboBox);
	    
	    Component horizontalStrut = Box.createHorizontalStrut(20);
	    panel_9.add(horizontalStrut);
	    
	    Component horizontalStrut_2 = Box.createHorizontalStrut(20);
	    panel_9.add(horizontalStrut_2);
	    
	    Component horizontalStrut_3 = Box.createHorizontalStrut(20);
	    panel_9.add(horizontalStrut_3);
	    
	    JLabel lblEnterSearchTerm = new JLabel("Search term:");
	    panel_9.add(lblEnterSearchTerm);
	    
	    textField_1 = new JTextField();
	    panel_9.add(textField_1);
	    textField_1.setColumns(16);
	    comboBox.addItem("error type");
	    comboBox.addItem("location");
	    comboBox.addItem("time range");
	    
	    JPanel panel_12 = new JPanel();
	    panel_14.add(panel_12);
	    
	    JLabel lblLocation = new JLabel("Location (rack/module/line):");
	    panel_12.add(lblLocation);
	    
	    JComboBox<Integer> comboBox_1 = new JComboBox<Integer>();
	    comboBox_1.addItem(0);
	    comboBox_1.addItem(1);
	    comboBox_1.addItem(2);
	    comboBox_1.addItem(3);
	    panel_12.add(comboBox_1);
	    
	    JComboBox<Integer> comboBox_2 = new JComboBox<Integer>();
	    comboBox_2.addItem(0);
	    comboBox_2.addItem(1);
	    comboBox_2.addItem(2);
	    comboBox_2.addItem(3);
	    comboBox_2.addItem(4);
	    comboBox_2.addItem(5);
	    panel_12.add(comboBox_2);
	    
	    JComboBox<Integer> comboBox_3 = new JComboBox<Integer>();
	    comboBox_3.addItem(0);
	    comboBox_3.addItem(1);
	    comboBox_3.addItem(2);
	    comboBox_3.addItem(3);
	    panel_12.add(comboBox_3);

	    
	    JLabel lblSensormoduleline = new JLabel("   Sensor (module/line):");
	    panel_12.add(lblSensormoduleline);
	    
	    JComboBox<Integer> comboBox_4 = new JComboBox<Integer>();
	    comboBox_4.addItem(0);
	    comboBox_4.addItem(1);
	    comboBox_4.addItem(2);
	    comboBox_4.addItem(3);
	    comboBox_4.addItem(4);
	    comboBox_4.addItem(5);
	    panel_12.add(comboBox_4);
	    
	    JComboBox<Integer> comboBox_5 = new JComboBox<Integer>();
	    comboBox_5.addItem(0);
	    comboBox_5.addItem(1);
	    comboBox_5.addItem(2);
	    comboBox_5.addItem(3);
	    panel_12.add(comboBox_5);
	    
	    JPanel panel_10 = new JPanel();
	    panel_14.add(panel_10);
	    panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    JLabel lblTimeFrameFrom = new JLabel("Time Frame:   from");
	    panel_10.add(lblTimeFrameFrom);
	    
	    textField_3 = new JTextField();
	    panel_10.add(textField_3);
	    textField_3.setColumns(10);
	    
	    JLabel lblTo = new JLabel("to");
	    panel_10.add(lblTo);
	    
	    textField_4 = new JTextField();
	    panel_10.add(textField_4);
	    textField_4.setColumns(10);
	    
	    JLabel lblEx = new JLabel("ex.: 6/27/2016 13:40:46");
	    lblEx.setForeground(SystemColor.windowBorder);
	    panel_10.add(lblEx);
	    
	    Component horizontalGlue = Box.createHorizontalGlue();
	    panel_10.add(horizontalGlue);
	    //textField_1.setEditable(false);
	    
	    JPanel panel_13 = new JPanel();
	    panel_8.add(panel_13, BorderLayout.SOUTH);
	    
	    JButton btnSearch = new JButton("");
	    JLabel searchLbl = new JLabel("Search");
	    btnSearch.add(searchLbl);
	    btnSearch.addActionListener(new SearchListener());
	    panel_13.add(btnSearch);
	    
	    JPanel panel_17 = new JPanel();
	    panel_17.setPreferredSize(new Dimension(640, 60));
	    panel_1.add(panel_17);
	    
	    warnLabel = new JLabel("");
	    warnLabel.setForeground(Color.red);
	    panel_17.add(warnLabel);
		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.SOUTH);
		
		JLabel lblcopy = new JLabel("\u00a9 2016");
		panel_3.add(lblcopy);
		
	}
	
	class OutputGenerator implements ActionListener {
	      public void actionPerformed(ActionEvent e) {

	    	  String warning = "";
	    	  warnLabel.setText(warning);
	    	  outputWarnLbl.setText("");
			  modeWarnLbl.setText("");
	    	  
	    	  if (!chckbxToConsole.isSelected() && !chckbxToTextFile.isSelected()) {
	    		  outputWarnLbl.setText("Select output method");
	    	  }
	    	  if (!rdbtnErrorsOnly.isSelected() && !rdbtnAllRecords.isSelected()) {
	    		  modeWarnLbl.setText("Please select mode");
	    	  }
	    	  if (chckbxToTextFile.isSelected() && outputDirectory == null) {
	    		  warning += "Please select output directory<br>";
	    	  }
	    	  if (inputDirectory == null) {
	    		  warning += "Please select input directory<br>";
	    	  }
			
	    	  if (!warning.equals("")) {
	    		  warning = "<html><div style='text-align:center'>" + warning + "</div></html>";
	    		  warnLabel.setText(warning);
	    		  return;
	    	  }
	    	  
	    	String dateTime = LocalDateTime.now().toString().replace(':', '\'').replace('T', ' ');

			outputDirectory += "\\CAT " + dateTime + ".txt";
			//System.out.println(outputDirectory);
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
			
			String warning = "";
			warnLabel.setText(warning);
			outputWarnLbl.setText("");
			modeWarnLbl.setText("");
	    	  
			  if (!chckbxToConsole.isSelected() && !chckbxToTextFile.isSelected()) {
	    		  outputWarnLbl.setText("Select output method");
	    	  }
	    	  if (!rdbtnErrorsOnly.isSelected() && !rdbtnAllRecords.isSelected()) {
	    		  modeWarnLbl.setText("Please select mode");
	    	  }
	    	  if (chckbxToTextFile.isSelected() && outputDirectory == null) {
	    		  warning += "Please choose output directory<br>";
	    	  }
	    	  if (inputDirectory == null) {
	    		  warning += "Please select input directory<br>";
	    	  }
	    	  if (textField_1.getText().isEmpty()) {
	    		  warning += "Please enter a search term<br>";
	    	  }
			
	    	  if (!warning.equals("")) {
	    		  warning = "<html><div style='text-align:center'>" + warning + "</div></html>";
	    		  warnLabel.setText(warning);
	    		  return;
	    	  }
			
			Searcher search = new Searcher();
			String term = textField_1.getText().trim().toLowerCase();
			//System.out.println(term);
			try {
				search.searchGeneral(inputDirectory, term, true);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}

	   

}
