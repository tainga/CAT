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
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import java.awt.SystemColor;

public class Frame {

	JFrame frame;
	private JTextField textField_1;
	private JCheckBox chckbxToConsole;
	private JCheckBox chckbxToTextFile;
	private JRadioButton rdbtnErrorsOnly;
	private JRadioButton rdbtnAllRecords;

	private JFileChooser fc = new JFileChooser();
    private JTextField textField;
    private JTextField textField_2;
    
    private String inputDirectory;
    private String outputDirectory;
    
    private JLabel warnLabel;
    private JLabel outputWarnLbl;
    private JLabel modeWarnLbl;
    private JTextField textField_3;
    private JTextField textField_4;
	private JComboBox<String> comboBox;
	private JComboBox<Integer> comboBox_1;
	private JComboBox<Integer> comboBox_2;
	private JComboBox<Integer> comboBox_3;
	private JComboBox<Integer> comboBox_4;
	private JComboBox<Integer> comboBox_5;


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
	    
	    outputWarnLbl = new JLabel("");
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
	    
	    modeWarnLbl = new JLabel("");
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
		fc.setCurrentDirectory(new File("C:\\Users\\Anastasia Taing\\Desktop\\CGW project\\example save crash\\chaSYS1_20Jul2016_13_18_17_cgw1f\\CGWLOGS\\control"));
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
	    
	    comboBox = new JComboBox<String>();
	    panel_9.add(comboBox);
	    
	    Component horizontalStrut = Box.createHorizontalStrut(20);
	    panel_9.add(horizontalStrut);
	    
	    JLabel lblEnterSearchTerm = new JLabel("    Search term / error:");
	    panel_9.add(lblEnterSearchTerm);
	    
	    textField_1 = new JTextField();
	    panel_9.add(textField_1);
	    textField_1.setColumns(16);
	    
	    comboBox.addItem("---------------");
	    comboBox.addItem("general");
	    comboBox.addItem("error type");
	    comboBox.addItem("location");
	    comboBox.addItem("sensor");
	    comboBox.addItem("time range");
	    
	    JPanel panel_12 = new JPanel();
	    panel_14.add(panel_12);
	    
	    JLabel lblLocation = new JLabel("Location (rack/module/line):");
	    panel_12.add(lblLocation);
	    
	    comboBox_1 = new JComboBox<Integer>();
	    comboBox_1.addItem(0);
	    comboBox_1.addItem(1);
	    comboBox_1.addItem(2);
	    comboBox_1.addItem(3);
	    panel_12.add(comboBox_1);
	    
	    comboBox_2 = new JComboBox<Integer>();
	    comboBox_2.addItem(0);
	    comboBox_2.addItem(1);
	    comboBox_2.addItem(2);
	    comboBox_2.addItem(3);
	    comboBox_2.addItem(4);
	    comboBox_2.addItem(5);
	    panel_12.add(comboBox_2);
	    
	    comboBox_3 = new JComboBox<Integer>();
	    comboBox_3.addItem(0);
	    comboBox_3.addItem(1);
	    comboBox_3.addItem(2);
	    comboBox_3.addItem(3);
	    panel_12.add(comboBox_3);

	    JLabel lblSensormoduleline = new JLabel("   Sensor (module/line):");
	    panel_12.add(lblSensormoduleline);
	    
	    comboBox_4 = new JComboBox<Integer>();
	    comboBox_4.addItem(0);
	    comboBox_4.addItem(1);
	    comboBox_4.addItem(2);
	    comboBox_4.addItem(3);
	    comboBox_4.addItem(4);
	    comboBox_4.addItem(5);
	    panel_12.add(comboBox_4);
	    
	    comboBox_5 = new JComboBox<Integer>();
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
		
	    comboBox.addActionListener(new SearchOptionListener());
		lockFields();
	}
	
	class OutputGenerator implements ActionListener {
	      public void actionPerformed(ActionEvent e) {

	    	  String warning = "";
	    	  warnLabel.setText(warning);
	    	  outputWarnLbl.setText("");
			  modeWarnLbl.setText("");
	    	  
	    	  if (!chckbxToConsole.isSelected() && !chckbxToTextFile.isSelected()) {
	    		  outputWarnLbl.setText("Select output");
	    	  }
	    	  if (!rdbtnErrorsOnly.isSelected() && !rdbtnAllRecords.isSelected()) {
	    		  modeWarnLbl.setText("Select mode");
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
	    	  
	    	  if (chckbxToTextFile.isSelected()) {
		    	String dateTime = LocalDateTime.now().toString().replace(':', '\'').replace('T', ' ');
		    	outputDirectory = fc.getSelectedFile().getAbsolutePath();
				outputDirectory += "\\CAT " + dateTime + ".txt";
	    	  }
			
	    	  Reader reader = new Reader();

	    		if (chckbxToConsole.isSelected()) {
	    			PopUp pop = popup();
	    			try {
						reader.parseLog(inputDirectory, pop, rdbtnErrorsOnly.isSelected());
					} catch (FileNotFoundException e1) {
						System.out.println("Something went terrribly wrong. Sorry about that.");
					}
		    	}
		    	  
		    	if (chckbxToTextFile.isSelected()) {
		    		try {
						reader.parseLog(inputDirectory, outputDirectory, rdbtnErrorsOnly.isSelected());
					} catch (FileNotFoundException e1) {
						System.out.println("Something went terrribly wrong. Sorry about that.");
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
	    		  outputWarnLbl.setText("Select output");
	    	  }
	    	  if (!rdbtnErrorsOnly.isSelected() && !rdbtnAllRecords.isSelected()) {
	    		  modeWarnLbl.setText("Select mode");
	    	  }
	    	  if (chckbxToTextFile.isSelected() && outputDirectory == null) {
	    		  warning += "Please select output directory<br>";
	    	  }
	    	  if (inputDirectory == null) {
	    		  warning += "Please select input directory<br>";
	    	  }
	    	  
	    	  String option = (String) comboBox.getSelectedItem();
	    	  
	    	  switch (option) {
	    	  	case "---------------":
	    	  		warning += "Please select search option<br>";
	    	  		break;
				case "general": 
					if (textField_1.getText() == null || textField_1.getText().trim().isEmpty()) {
						warning += "Please enter search term<br>";
					}
					break;
				case "error type": 
					if (textField_1.getText() == null || textField_1.getText().trim().isEmpty()) {
						warning += "Please enter error type<br>";
					}; 
				break;
				case "time range": 
					if (textField_3.getText() == null || textField_4.getText() == null) {
						warning += "Please enter time range<br>";
						break;
					}
					String from = textField_3.getText();
					String till = textField_4.getText();
					if (!(checkDateTimeFormat(from) && checkDateTimeFormat(till))) {
						warning += "Incorrect format of time range<br>";
					}
					break;
				}
			
	    	  if (!warning.equals("")) {
	    		  warning = "<html><div style='text-align:center'>" + warning + "</div></html>";
	    		  warnLabel.setText(warning);
	    		  return;
	    	  }
	    	  
	    	if (chckbxToTextFile.isSelected()) {
			    String dateTime = LocalDateTime.now().toString().replace(':', '\'').replace('T', ' ');
			    outputDirectory = fc.getSelectedFile().getAbsolutePath();
				outputDirectory += "\\CAT " + dateTime + ".txt";
		    }
	    	PopUp pop = null;
			if (chckbxToConsole.isSelected()) {
				pop = popup();
			}
	    	
			Searcher finder = new Searcher();
			String term = "";
			
			switch (option) {
			case "general": term = textField_1.getText(); break;
			case "error type": term = textField_1.getText(); break;
			case "location": term = comboBox_1.getSelectedItem() + "/" + comboBox_2.getSelectedItem() + "/" + comboBox_3.getSelectedItem(); break;
			case "sensor": term = comboBox_4.getSelectedItem() + "/" + comboBox_5.getSelectedItem(); break;
			case "time range": 
				String from = textField_3.getText().trim();
				String till = textField_4.getText().trim();
				term = from + "\n" + till; 
				break;
			default: System.out.println("Wrong 'search by' option");
			}
			
			try {
				finder.search(option, inputDirectory, outputDirectory, term, rdbtnErrorsOnly.isSelected(), chckbxToTextFile.isSelected(), chckbxToConsole.isSelected(), pop);
			} catch (FileNotFoundException e1) {
				System.out.println("Something went terrribly wrong. Sorry about that.");
			}
		}
	}
	
	class SearchOptionListener implements ActionListener {
	      public void actionPerformed(ActionEvent e) {
	    	  
	    	  lockFields();
	    	  
	    	  @SuppressWarnings("unchecked")
	    	  JComboBox<String> optionBox = (JComboBox<String>)e.getSource();
              String option = (String)optionBox.getSelectedItem();
              
              switch (option) {
  				case "general": 
  					textField_1.setEditable(true); 
  					break;
  				case "error type": 
  					textField_1.setEditable(true); 
  					break;
  				case "location":
  					comboBox_1.setEnabled(true);
  					comboBox_2.setEnabled(true);
  					comboBox_3.setEnabled(true);
  					break;
  				case "sensor": 
  					comboBox_4.setEnabled(true);
  					comboBox_5.setEnabled(true);
  					break;
  				case "time range": 
	  				textField_3.setEditable(true);
	  				textField_4.setEditable(true);
	  				break;
  			  }
	      }
	}

	   private boolean checkDateTimeFormat(String dateTime) {

		    String re1="(\\d+)";	// Integer Number 1
		    String re2="(\\/)";	// Any Single Character 1
		    String re3="((?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])";	// Day 1
		    String re4="(\\/)";	// Any Single Character 2
		    String re5="((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])";	// Year 1
		    String re6="( )";	// White Space 1
		    String re7="((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:\\s?(?:am|AM|pm|PM))?)";	// HourMinuteSec 1

		    Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		    Matcher m = p.matcher(dateTime);

		    return m.matches();
	   }
	   
	   private void lockFields() {
		   	textField_1.setText("");
		    textField_1.setEditable(false);
		    textField_3.setText("");
		    textField_3.setEditable(false);
		    textField_4.setText("");
		    textField_4.setEditable(false);
		    comboBox_1.setEnabled(false);
		    comboBox_2.setEnabled(false);
		    comboBox_3.setEnabled(false);
		    comboBox_4.setEnabled(false);
		    comboBox_5.setEnabled(false); 
	   }
	   
	   private PopUp popup() {
		   PopUp frame = null;
					try {
						frame = new PopUp();
						frame.setVisible(true);
						java.net.URL iconURL = getClass().getResource("cat-paw.png");
						ImageIcon icon = new ImageIcon(iconURL);
						frame.setIconImage(icon.getImage());
					
					} catch (Exception e) {
						e.printStackTrace();
					}
			return frame;
	   }

}