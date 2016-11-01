import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Searcher {

	private static String brk = System.lineSeparator();
	
	public int search(String searchType, String inputDirectory, String outputDirectory, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole, PopUp pop) {
		
		File dir = new File(inputDirectory);
		File[] directoryListing = dir.listFiles();
		PrintWriter writer = null;				
		
		if (toTextFile) {
			File output = new File(outputDirectory);
			try {
				writer = new PrintWriter(output);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return 0;
			}
		}
		
		int numberOfRecords = 0;

		  if (directoryListing != null) {
		    for (File log : directoryListing) {
		    	
		    	if (log.isDirectory() || log.getName().length() < 7) continue;
		      
		    	if (log.getName().substring(0, 7).equals("control")) {
		    		String searchTerm = term.trim().toLowerCase();
		    		
		    		Scanner reader = null;
					try {
						reader = new Scanner(log);
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						return 0;
					}
					
		    		String previous = "";

		    		while (reader.hasNextLine()) {
		    			String line = reader.nextLine();
		    			
		    			//all records mode
		    			if (!errorsOnly) {  
		    				
		    				if (lineContainsTerm(searchType, searchTerm, line)) {
		    					if (!previous.equals("")) previous += brk;
		    					if (toConsole) pop.addText(previous + line + brk + brk);
		    					if (writer != null) writer.println(previous + line + brk); 	
		    					numberOfRecords++;
		    				previous = "";
		    				}
		    				
		    				else {
		    					previous = line;
		    				}
		    			}
		    			
		    			//errors only mode
		    			else {
		    				if (line.contains("ERROR")) { 
		    					Error error = Reader.parse(previous, line);

		    					if (errorContainsTerm(searchType, searchTerm, error)) {
		    						if (toConsole) pop.addText(error.toString() + brk);
		    						if (writer != null) writer.println(error.toString());
		    						numberOfRecords++;
		    					}

		    					previous = "";
		    				}
		    				else {
		    					previous = line;
		    				}
		    			}
		    		}
		    		reader.close();	
		    	}
		    	else continue;
		    	
		    }
		    
		  } else {
			  pop.addText("Directory not found. Please make sure that the directory you specified is really a directory.");
		  }  
		  
		  if (writer != null) writer.close();
		  
		  return numberOfRecords;
	}

	public boolean lineContainsTerm(String searchType, String searchTerm, String line) {
		
		switch (searchType) {
		
			case "general": {
				// supports several search terms divided by , _ or whitespace
				String[] terms = searchTerm.split("\\s*(_|,|\\s)\\s*");
	
				boolean matchFound = true;
				
				for (int i = 0; i < terms.length && matchFound; i++) {
					if (!line.toLowerCase().contains(terms[i])) matchFound = false;
				}
				return matchFound;
			}
			
			case "error type": {
				return line.toLowerCase().contains(searchTerm);
			}
			
			case "location": {
				return line.toLowerCase().contains(searchTerm);
			}
			
			case "sensor": {
				return line.toLowerCase().contains(searchTerm);
			}
			
			case "time range":{
				GregorianCalendar start = null;
				GregorianCalendar finish = null;
				
				try {
					String[] tmp = searchTerm.split("\n");
					start = parseDateTime(tmp[0]);
					finish = parseDateTime(tmp[1]);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				String[] tmp = line.split(" ");
				if (tmp.length < 2) return false; 

				String dateAndTime = tmp[0] + " " + tmp[1];
				GregorianCalendar lineDateTime;
				
				try {
					lineDateTime = parseDateTime(dateAndTime);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return false;														
				}
				
				return (start.compareTo(lineDateTime) <= 0 && finish.compareTo(lineDateTime) >= 0);
			}
			
			default: {
				throw new IllegalArgumentException("searchType must be equal to one of the following: 'general', 'error type', 'location', 'sensor', 'time range'");
			}
	
		}
	}
	
	public boolean errorContainsTerm(String searchType, String searchTerm, Error error) {
		
		switch (searchType) {
			
			case "general": {
				return error.contains(searchTerm);
			}
		
			case "error type": {
				return error.getType().toLowerCase().equals(searchTerm);
			}
			
			case "location": {
				String[] tmp = searchTerm.split("/");
				String searchedLocation = "Rack " + tmp[0] + ", Module " + tmp[1] + ", Line " + tmp[2];
	
				return error.getLocation().equals(searchedLocation);
			}
			
			case "sensor": {
				String[] tmp = searchTerm.split("/");
				String searchedSensor = "Module " + tmp[0] + ", Line " + tmp[1];
	
				return error.getLocation().contains(searchedSensor);
			}
			
			case "time range": {
				GregorianCalendar start = null;
				GregorianCalendar finish = null;
				GregorianCalendar lineDateTime = error.getDateTime();
	
				String[] tmp = searchTerm.split("\n");
				start = parseDateTime(tmp[0]);
				finish = parseDateTime(tmp[1]);
	
				return start.compareTo(lineDateTime) <= 0 && finish.compareTo(lineDateTime) >= 0;
			}
			
			default: {
				throw new IllegalArgumentException("searchType must be equal to one of the following: 'general', 'error type', 'location', 'sensor', 'time range'");
			}
		}
	}
	
	private GregorianCalendar parseDateTime(String dateTime) {
		
		String[] tmp = dateTime.split(" ");
		String date = tmp[0];
		String time = tmp[1];
		String[] tmpDate = date.split("/");
		String[] tmpTime = time.split(":");
		GregorianCalendar result = new GregorianCalendar(Integer.parseInt(tmpDate[2]), Integer.parseInt(tmpDate[0]), Integer.parseInt(tmpDate[1]), Integer.parseInt(tmpTime[0]), Integer.parseInt(tmpTime[1]), Integer.parseInt(tmpTime[2]));
		return result;
	}

}