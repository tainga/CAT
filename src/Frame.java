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
import javax.swing.Box;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import java.awt.SystemColor;

/**
 * The Frame class creates the application's main frame and initializes its contents. 
 * 
 */
public class Frame {

	private JFrame frame;
	private JTextField searchTerm_textField;
	private JCheckBox chckbxToConsole;
	private JCheckBox chckbxToTextFile;
	private JRadioButton rdbtnErrorsOnly;
	private JRadioButton rdbtnAllRecords;
	private JFileChooser fcIn;
	private JFileChooser fcOut;
    private JTextField inputFolder_textField;
    private JTextField outputFolder_textField;
    private String inputDirectory;
    private String outputFile;
    private JLabel warnLabel;
    private JLabel outputWarnLbl;
    private JLabel modeWarnLbl;
    private JTextField timeFrameFrom_textField;
    private JTextField timeFrameTo_textField;
	private JComboBox<String> searchBy_comboBox;
	private JComboBox<Integer> locationRack_comboBox;
	private JComboBox<Integer> locationModule_comboBox;
	private JComboBox<Integer> locationLine_comboBox;
	private JComboBox<Integer> sensorModule_comboBox;
	private JComboBox<Integer> sensorLine_comboBox;
	private JComboBox<String> errorType_comboBox;
	private PopUp pop;
	
	/**
	 * Class constructor
	 */
	public Frame() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 680, 480);
		frame.setTitle("CAT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		ImageIcon icon = new ImageIcon(getClass().getResource("cat-paw.png"));
		frame.setIconImage(icon.getImage());
		
		JPanel titlePanel = new JPanel();
		frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
		
		JLabel lblCgwAnalysisTool = new JLabel("CGW Analysis Tool");
		titlePanel.add(lblCgwAnalysisTool);
		
		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel mainSubPanel = new JPanel();
		mainSubPanel.setBorder(null);
		mainPanel.add(mainSubPanel, BorderLayout.CENTER);
	    mainSubPanel.setLayout(new BoxLayout(mainSubPanel, BoxLayout.Y_AXIS));
	    
	    initOptionsPanel(mainSubPanel);
	    
	    initIOPanel(mainSubPanel);
	    
	    initSearchAndReportPanel(mainSubPanel);
	    
	    JPanel warningsPanel = new JPanel();
	    warningsPanel.setPreferredSize(new Dimension(640, 60));
	    mainSubPanel.add(warningsPanel);
	    
	    warnLabel = new JLabel("");
	    warningsPanel.add(warnLabel);
		
		JPanel copyrightPanel = new JPanel();
		frame.getContentPane().add(copyrightPanel, BorderLayout.SOUTH);
		
		JLabel lblcopy = new JLabel("\u00a9 2016");
		copyrightPanel.add(lblcopy);	
		
		frame.setVisible(true);
		

	}

	/**
	 * @param mainSubPanel
	 */
	private void initSearchAndReportPanel(JPanel mainSubPanel) {
		JPanel searchAndReport_panel = new JPanel();
	    mainSubPanel.add(searchAndReport_panel);
	    searchAndReport_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    JPanel reportPanel = new JPanel();
	    reportPanel.setBorder(new LineBorder(new Color(153, 180, 209), 2));
	    searchAndReport_panel.add(reportPanel);
	    reportPanel.setLayout(new BorderLayout(0, 0));
	    reportPanel.setPreferredSize(new Dimension(150, 170));
	    
	    JLabel lblGenerateReport = new JLabel("Generate Report");
	    lblGenerateReport.setHorizontalAlignment(SwingConstants.CENTER);
	    reportPanel.add(lblGenerateReport);
	    
	    JPanel generateButtonPanel = new JPanel();
	    reportPanel.add(generateButtonPanel, BorderLayout.SOUTH);
	    
	    JButton btnGenerate = new JButton("");
	    JLabel generateLbl = new JLabel("Generate");
	    btnGenerate.add(generateLbl);
	    btnGenerate.addActionListener(new OutputGenerator());
	    generateButtonPanel.add(btnGenerate);
	    
	    JPanel searchPanel = new JPanel();
	    searchPanel.setPreferredSize(new Dimension(510, 170));
	    searchPanel.setBorder(new LineBorder(SystemColor.activeCaption, 2));
	    searchAndReport_panel.add(searchPanel);
	    searchPanel.setLayout(new BorderLayout(0, 0));
	    
	    JPanel searchOptionsPanel = new JPanel();
	    searchPanel.add(searchOptionsPanel, BorderLayout.CENTER);
	    searchOptionsPanel.setLayout(new BoxLayout(searchOptionsPanel, BoxLayout.Y_AXIS));
	    
	    JPanel searchByPanel = new JPanel();
	    searchOptionsPanel.add(searchByPanel);
	    
	    JLabel lblSearchBy = new JLabel("Search by ");
	    searchByPanel.add(lblSearchBy);
	    
	    searchBy_comboBox = new JComboBox<String>();
	    searchByPanel.add(searchBy_comboBox);
	    
	    searchBy_comboBox.addItem("---------------");
	    searchBy_comboBox.addItem("general");
	    searchBy_comboBox.addItem("error type");
	    searchBy_comboBox.addItem("location");
	    searchBy_comboBox.addItem("sensor");
	    searchBy_comboBox.addItem("time range");
	    
	    JPanel searchTermError_panel = new JPanel();
	    FlowLayout flowLayout = (FlowLayout) searchTermError_panel.getLayout();
	    flowLayout.setAlignment(FlowLayout.LEFT);
	    searchOptionsPanel.add(searchTermError_panel);
	    
	    JLabel lblEnterSearchTerm = new JLabel("Search term:");
	    searchTermError_panel.add(lblEnterSearchTerm);
	    
	    searchTerm_textField = new JTextField();
	    searchTerm_textField.setColumns(16);
	    searchTerm_textField.addActionListener(new SearchListener());
	    searchTermError_panel.add(searchTerm_textField);
	    
	    Component horizontalStrut = Box.createHorizontalStrut(20);
	    searchTermError_panel.add(horizontalStrut);
	    
	    JLabel lblError = new JLabel("Error:");
	    searchTermError_panel.add(lblError);
	    
	    errorType_comboBox = new JComboBox<String>();
	    errorType_comboBox.addItem("data loss");
	    errorType_comboBox.addItem("idle miss");
	    errorType_comboBox.addItem("parity");
	    searchTermError_panel.add(errorType_comboBox);
	    
	    JPanel locationSensorPanel = new JPanel();
	    searchOptionsPanel.add(locationSensorPanel);
	    
	    JLabel lblLocation = new JLabel("Location (rack/module/line):");
	    locationSensorPanel.add(lblLocation);
	    
	    locationRack_comboBox = new JComboBox<Integer>();
	    locationRack_comboBox.addItem(0);
	    locationRack_comboBox.addItem(1);
	    locationRack_comboBox.addItem(2);
	    locationRack_comboBox.addItem(3);
	    locationSensorPanel.add(locationRack_comboBox);
	    
	    locationModule_comboBox = new JComboBox<Integer>();
	    locationModule_comboBox.addItem(0);
	    locationModule_comboBox.addItem(1);
	    locationModule_comboBox.addItem(2);
	    locationModule_comboBox.addItem(3);
	    locationModule_comboBox.addItem(4);
	    locationModule_comboBox.addItem(5);
	    locationSensorPanel.add(locationModule_comboBox);
	    
	    locationLine_comboBox = new JComboBox<Integer>();
	    locationLine_comboBox.addItem(0);
	    locationLine_comboBox.addItem(1);
	    locationLine_comboBox.addItem(2);
	    locationLine_comboBox.addItem(3);
	    locationSensorPanel.add(locationLine_comboBox);

	    JLabel lblSensorModuleLine = new JLabel("   Sensor (module/line):");
	    locationSensorPanel.add(lblSensorModuleLine);
	    
	    sensorModule_comboBox = new JComboBox<Integer>();
	    sensorModule_comboBox.addItem(0);
	    sensorModule_comboBox.addItem(1);
	    sensorModule_comboBox.addItem(2);
	    sensorModule_comboBox.addItem(3);
	    sensorModule_comboBox.addItem(4);
	    sensorModule_comboBox.addItem(5);
	    locationSensorPanel.add(sensorModule_comboBox);
	    
	    sensorLine_comboBox = new JComboBox<Integer>();
	    sensorLine_comboBox.addItem(0);
	    sensorLine_comboBox.addItem(1);
	    sensorLine_comboBox.addItem(2);
	    sensorLine_comboBox.addItem(3);
	    locationSensorPanel.add(sensorLine_comboBox);
	    
	    JPanel timeFramePanel = new JPanel();
	    searchOptionsPanel.add(timeFramePanel);
	    timeFramePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    JLabel lblTimeFrameFrom = new JLabel("Time Frame:   from");
	    timeFramePanel.add(lblTimeFrameFrom);
	    
	    timeFrameFrom_textField = new JTextField();
	    timeFramePanel.add(timeFrameFrom_textField);
	    timeFrameFrom_textField.setColumns(10);
	    
	    JLabel lblTo = new JLabel("to");
	    timeFramePanel.add(lblTo);
	    
	    timeFrameTo_textField = new JTextField();
	    timeFrameTo_textField.setColumns(10);

	    timeFramePanel.add(timeFrameTo_textField);
	    
	    JLabel lblEx = new JLabel("ex.: 6/27/2016 13:40:46");
	    lblEx.setForeground(SystemColor.windowBorder);
	    timeFramePanel.add(lblEx);
	    
	    Component horizontalGlue = Box.createHorizontalGlue();
	    timeFramePanel.add(horizontalGlue);
	    
	    JPanel SearchButtonPanel = new JPanel();
	    searchPanel.add(SearchButtonPanel, BorderLayout.SOUTH);
	    
	    JButton btnSearch = new JButton("");
	    JLabel searchLbl = new JLabel("Search");
	    btnSearch.add(searchLbl);

	    SearchButtonPanel.add(btnSearch);	    
	    
	    searchTerm_textField.addActionListener(new SearchListener());
	    timeFrameTo_textField.addActionListener(new SearchListener());
	    btnSearch.addActionListener(new SearchListener());
	    searchBy_comboBox.addActionListener(new SearchOptionListener());
		lockFields();
	}

	private void initIOPanel(JPanel mainSubPanel) {
		JPanel inputOutputPanel = new JPanel();
	    inputOutputPanel.setBorder(null);
	    mainSubPanel.add(inputOutputPanel);
	    
	    inputOutputPanel.setLayout(new GridLayout(2,0));
	    
	    JPanel inputPanel = new JPanel();
	    inputOutputPanel.add(inputPanel);
	    inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

	    JButton btnInputFolder = new JButton("Input folder");
	    btnInputFolder.setPreferredSize(new Dimension(150, 23));
	    inputPanel.add(btnInputFolder);
	    
	    inputFolder_textField = new JTextField();
	    inputFolder_textField.setBorder(new LineBorder(SystemColor.activeCaption));
	    inputFolder_textField.setBackground(new Color(234,240,246));
	    inputFolder_textField.setPreferredSize(new Dimension(510, 25));
	    inputPanel.add(inputFolder_textField);
	    inputFolder_textField.setEditable(false);
	    fcIn = new JFileChooser();
	    fcIn.setCurrentDirectory(new File("C:\\Users\\Anastasia Taing\\Desktop\\CGW project\\example save crash\\vpsSYS2_27Jun2016_12_27_25_cgw1e\\CGWLOGS\\control"));
	    fcIn.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
	    btnInputFolder.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		
	    	    int returnValue = fcIn.showOpenDialog(new JFrame());
	    	    
	    	    if(returnValue == JFileChooser.APPROVE_OPTION)
	    	    {
	    	    	inputDirectory = fcIn.getSelectedFile().getAbsolutePath();
	    	    	inputFolder_textField.setText(inputDirectory);
	    	    }
	    	}
	    });
	    
	    JPanel outputPanel = new JPanel();
	    inputOutputPanel.add(outputPanel);
	    outputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	     
	    JButton btnOutputFolder = new JButton("Output folder");
	    btnOutputFolder.setPreferredSize(new Dimension(150, 23));
	    outputPanel.add(btnOutputFolder);
	    
	    outputFolder_textField = new JTextField();
	    outputFolder_textField.setBorder(new LineBorder(SystemColor.activeCaption));
	    outputFolder_textField.setBackground(new Color(234,240,246));
	    outputFolder_textField.setPreferredSize(new Dimension(510, 25));
	    outputPanel.add(outputFolder_textField);
	    outputFolder_textField.setEditable(false);
	    fcOut = new JFileChooser();
	    fcOut.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
	    btnOutputFolder.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		
	    	    int returnValue = fcOut.showOpenDialog(new JFrame());
	    	    
	    	    if(returnValue == JFileChooser.APPROVE_OPTION && fcOut.getSelectedFile() != null)
	    	    {
	    	    	outputFile = fcOut.getSelectedFile().getAbsolutePath();
	    	    	outputFolder_textField.setText(outputFile);
	    	    }
	    	}
	    });
	}

	private void initOptionsPanel(JPanel mainSubPanel) {
		ButtonGroup group1 = new ButtonGroup();
		JPanel optionsPanel = new JPanel();
	    mainSubPanel.add(optionsPanel);
	    optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    JPanel optionsSubPanel = new JPanel();
	    optionsPanel.add(optionsSubPanel);
	    optionsSubPanel.setPreferredSize(new Dimension(664, 80));
	    optionsSubPanel.setBorder(new LineBorder(SystemColor.activeCaption, 2));
	    optionsSubPanel.setLayout(new GridLayout(0, 6, 0, 0));
	    
	    Component horizontalGlue_1 = Box.createHorizontalGlue();
	    optionsSubPanel.add(horizontalGlue_1);
	    
	    Component horizontalGlue_9 = Box.createHorizontalGlue();
	    optionsSubPanel.add(horizontalGlue_9);
	    
	    JLabel lblOutput = new JLabel("Output:");
	    optionsSubPanel.add(lblOutput);
	    
	    Component horizontalGlue_2 = Box.createHorizontalGlue();
	    optionsSubPanel.add(horizontalGlue_2);
	    
	    JLabel lblInclude = new JLabel("Include:");
	    optionsSubPanel.add(lblInclude);
	    
	    Component horizontalGlue_3 = Box.createHorizontalGlue();
	    optionsSubPanel.add(horizontalGlue_3);
	    
	    outputWarnLbl = new JLabel("");
	    outputWarnLbl.setForeground(Color.red);
	    optionsSubPanel.add(outputWarnLbl);
	    
	    Component horizontalGlue_10 = Box.createHorizontalGlue();
	    optionsSubPanel.add(horizontalGlue_10);
	    
	    chckbxToConsole = new JCheckBox("to console");
	    chckbxToConsole.setSelected(true);
	    optionsSubPanel.add(chckbxToConsole); 
	    
	    Component horizontalGlue_5 = Box.createHorizontalGlue();
	    optionsSubPanel.add(horizontalGlue_5);
	    
	    rdbtnErrorsOnly = new JRadioButton("errors only");
	    optionsSubPanel.add(rdbtnErrorsOnly);
	    group1.add(rdbtnErrorsOnly);
	    
	    Component horizontalGlue_6 = Box.createHorizontalGlue();
	    optionsSubPanel.add(horizontalGlue_6);
	    
	    modeWarnLbl = new JLabel("");
	    modeWarnLbl.setForeground(Color.red);
	    optionsSubPanel.add(modeWarnLbl);
	    
	    Component horizontalGlue_11 = Box.createHorizontalGlue();
	    optionsSubPanel.add(horizontalGlue_11);
	    
	    chckbxToTextFile = new JCheckBox("to text file");
	    optionsSubPanel.add(chckbxToTextFile);
	    
	    Component horizontalGlue_8 = Box.createHorizontalGlue();
	    optionsSubPanel.add(horizontalGlue_8);
	    
	    rdbtnAllRecords = new JRadioButton("all records");
	    rdbtnAllRecords.setSelected(true);
	    optionsSubPanel.add(rdbtnAllRecords);
	    group1.add(rdbtnAllRecords);
	}

	
	/**
	 * Inner class to initiate generation of a report when the 'generate' button is pressed.
	 *
	 */
	class OutputGenerator implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {

			if (!validateUserSelections()) return;

			initializeOutput();

			Reader reader = new Reader();
			int numberOfEntries = 0;

			numberOfEntries = reader.parseLog(inputDirectory, pop, outputFile, rdbtnErrorsOnly.isSelected(),
					chckbxToConsole.isSelected(), chckbxToTextFile.isSelected());

			if (numberOfEntries == 0) {
				if (chckbxToConsole.isSelected() && pop != null) {
					pop.dispose();
				}
				if (chckbxToTextFile.isSelected()) {
					File fl = new File(outputFile);
					try {
						fl.delete();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			
				warnLabel.setText("<html><div style='text-align:center; color:red'>No records found</div></html>");
			}

			else {
				if (chckbxToConsole.isSelected() && pop != null) {
					pop.setVisible(true);
				}
				
				warnLabel.setText("<html><div style='text-align:center; color:green'>" + numberOfEntries
						+ " records found</div></html>");
			}

		}
	}
	
	/**
	 * Inner class to initialize search when the 'search' button is pressed.
	 *
	 */
	class SearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (!validateUserSelections() || !validateSearchOption()) return;
			
			String term = "";
			
			String option = (String) searchBy_comboBox.getSelectedItem();
			
			switch (option) {
			case "general": term = searchTerm_textField.getText(); break;
			case "error type": term = errorType_comboBox.getSelectedItem() + ""; break;
			case "location": term = locationRack_comboBox.getSelectedItem() + "/" + locationModule_comboBox.getSelectedItem() + "/" + locationLine_comboBox.getSelectedItem(); break;
			case "sensor": term = sensorModule_comboBox.getSelectedItem() + "/" + sensorLine_comboBox.getSelectedItem(); break;
			case "time range": 
				String from = timeFrameFrom_textField.getText().trim();
				String till = timeFrameTo_textField.getText().trim();
				term = from + "\n" + till; 
				break;
			default: JOptionPane.showMessageDialog(null, "'Search by' option does not match any of the supported options", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			int numberOfEntries = 0;
	    	
			initializeOutput();
			Searcher finder = new Searcher();
			numberOfEntries = finder.search(option, inputDirectory, outputFile, term, rdbtnErrorsOnly.isSelected(), chckbxToTextFile.isSelected(), chckbxToConsole.isSelected(), pop);
			
			if (numberOfEntries == 0) {
				if (chckbxToConsole.isSelected() && pop != null) {
					pop.dispose();
				}
				if (chckbxToTextFile.isSelected()) {
					File fl = new File(outputFile);
					try {
						if (fl.exists()) fl.delete();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
	    		warnLabel.setText("<html><div style='text-align:center; color:red'>No records found</div></html>");
			}
			else {
				if (pop != null && chckbxToConsole.isSelected()) {
					pop.setVisible(true);
				}
				
	    		warnLabel.setText("<html><div style='text-align:center; color:green'>" + numberOfEntries + " records found</div></html>");
			}
		}

	}
	
	/**
	 * Inner class to monitor the user's choice in the 'search type' drop-down menu and lock the fields that will not be used.
	 *
	 */
	class SearchOptionListener implements ActionListener {
	      public void actionPerformed(ActionEvent e) {
	    	  
	    	  lockFields();
	    	  
	    	  @SuppressWarnings("unchecked")
	    	  JComboBox<String> optionBox = (JComboBox<String>)e.getSource();
              String option = (String)optionBox.getSelectedItem();
              
              switch (option) {
  				case "general": 
  					searchTerm_textField.setEditable(true); 
  					break;
  				case "error type": 
  					errorType_comboBox.setEnabled(true); 
  					break;
  				case "location":
  					locationRack_comboBox.setEnabled(true);
  					locationModule_comboBox.setEnabled(true);
  					locationLine_comboBox.setEnabled(true);
  					break;
  				case "sensor": 
  					sensorModule_comboBox.setEnabled(true);
  					sensorLine_comboBox.setEnabled(true);
  					break;
  				case "time range": 
	  				timeFrameFrom_textField.setEditable(true);
	  				timeFrameTo_textField.setEditable(true);
	  				break;
  			  }
	      }
	}
	
	/**
	 * A helper method to check if a string contains valid date and time that match the format 'MM/DD/YYYY HH:MM:SS'.
	 * @param dateTime a string containing date and time
	 * @return true if date and time are valid, false otherwise
	 */
   private boolean checkDateTimeFormat(String dateTime) {

	    String re1="(\\d+)";	// Integer
	    String re2="(\\/)";	// slash
	    String re3="((?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])";	// Day
	    String re4="(\\/)";	// slash
	    String re5="((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])";	// Year
	    String re6="( )";	// White Space 1
	    String re7="((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:\\s?(?:am|AM|pm|PM))?)";	// HourMinuteSec

	    Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(dateTime);
	    
	    return m.matches();
   }
   
   /**
    * A helper method that sets makes all fields in the search area uneditable; used by the SearchOptionListener inner class.
    */
   private void lockFields() {
	   	searchTerm_textField.setText("");
	    searchTerm_textField.setEditable(false);
	    timeFrameFrom_textField.setText("");
	    timeFrameFrom_textField.setEditable(false);
	    timeFrameTo_textField.setText("");
	    timeFrameTo_textField.setEditable(false);
	    locationRack_comboBox.setEnabled(false);
	    locationModule_comboBox.setEnabled(false);
	    locationLine_comboBox.setEnabled(false);
	    sensorModule_comboBox.setEnabled(false);
	    sensorLine_comboBox.setEnabled(false); 
	    errorType_comboBox.setEnabled(false);
   }
	   
   /**
    * A helper method to check if the user has made valid selections in the upper box of the GUI; used by the SearchListener and OutputGenerator classes.
    * @return true if the user has made all necessary selections and they are valid, false otherwise
    */
	private boolean validateUserSelections() {

		String warning = "";
		boolean modeAndDestinationChosen = true;

		warnLabel.setText(warning);
		outputWarnLbl.setText("");
		modeWarnLbl.setText("");

		if (!chckbxToConsole.isSelected() && !chckbxToTextFile.isSelected()) {
			outputWarnLbl.setText("Select output");
			modeAndDestinationChosen = false;
		}
		if (!rdbtnErrorsOnly.isSelected() && !rdbtnAllRecords.isSelected()) {
			modeWarnLbl.setText("Select mode");
			modeAndDestinationChosen = false;
		}
		if (chckbxToTextFile.isSelected() && outputFile == null) {
			warning += "Please select output directory<br>";
		}
		if (inputDirectory == null) {
			warning += "Please select input directory<br>";
		}

		if (!warning.equals("")) {
			warning = "<html><div style='text-align:center; color:red'>" + warning + "</div></html>";
			warnLabel.setText(warning);
			return false;
		}
		if (!modeAndDestinationChosen) {return false;}

		return true;
	}
	
   /**
    * A helper method to check if the user has made valid selections in the search area of the GUI; used by the SearchListener class.
    * @return true if the user has made all necessary selections and they are valid, false otherwise
    */
	private boolean validateSearchOption() {

		String warning = "";
		String option = (String) searchBy_comboBox.getSelectedItem();

		switch (option) {

		case "---------------":
			warning += "Please select search option<br>";
			break;

		case "general":
			if (searchTerm_textField.getText() == null || searchTerm_textField.getText().trim().isEmpty()) {
				warning += "Please enter search term<br>";
			}
			break;

		case "error type":
			break;

		case "time range":
			if (timeFrameFrom_textField.getText() == null || timeFrameTo_textField.getText() == null) {
				warning += "Please enter time range<br>";
				break;
			}

			String from = timeFrameFrom_textField.getText().trim();
			String till = timeFrameTo_textField.getText().trim();
			if (!(checkDateTimeFormat(from) && checkDateTimeFormat(till))) {
				warning += "Incorrect format of time range<br>";
			}
			break;
		}
		
		if (!warning.equals("")) {
			warning = "<html><div style='text-align:center; color:red'>" + warning + "</div></html>";
			warnLabel.setText(warning);
			return false;
		}
		
		return true;
	}
	   
	/**
	 * A helper method to initialize an output object.
	 * Creates a text file if the user has chosen to direct the output to a text file, 
	 * and an object of the PopUp class if the user has chosen to direct the output to console.
	 */
	private void initializeOutput() {
		if (chckbxToTextFile.isSelected()) {
		    String dateTime = LocalDateTime.now().toString().replace(':', '\'').replace('T', ' ');
		    outputFile = fcOut.getSelectedFile().getAbsolutePath();
			outputFile += "\\CAT " + dateTime + ".txt";
	    }
    	
		if (chckbxToConsole.isSelected()) {
			pop = popup();
		}
	}
	
	/**
	 * A helper method to create and initialize an object of the PopUp class.
	 * @return a frame created by the PopUp class
	 */
	private PopUp popup() {
		return new PopUp();
	}

}