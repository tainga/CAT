import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Searcher {

	private static String brk = System.lineSeparator();
	
	public int search(String searchType, String inputDirectory, String outputDirectory, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole, PopUp pop) throws FileNotFoundException {
		
		File dir = new File(inputDirectory);
		PrintWriter writer = null;					// do something about null pops
		
		if (toTextFile && (outputDirectory == null || outputDirectory.isEmpty())) {
			pop.addText("No output directory!");
			return -3;
		}
		
		if (toTextFile && outputDirectory != null && !outputDirectory.isEmpty()) {
			File output = new File(outputDirectory);
			writer = new PrintWriter(output);
		}
		
		int numberOfRecords = 0;
		
		  File[] directoryListing = dir.listFiles();
		
		  if (directoryListing != null) {
		    for (File log : directoryListing) {
		    	
		    	if (log.isDirectory() || log.getName().length() < 7) continue;
		      
		    	if (log.getName().substring(0, 7).equals("control")) {
		    		String searchTerm = term.trim().toLowerCase();
		    		
		    		Scanner reader = new Scanner(log);
		    		String previous = "";

		    		while (reader.hasNextLine()) {
		    			String line = reader.nextLine();
		    			
		    			
		    			if (!errorsOnly) {
		    				switch (searchType) {
		    				case "general": numberOfRecords += searchGeneralAll(writer, searchTerm, toConsole, pop, line, previous);
		    				break;
		    				case "error type": numberOfRecords += searchTypeAll(writer, searchTerm, toConsole, pop, line, previous);
		    				break;
		    				case "location": numberOfRecords += searchLocationAll(writer, searchTerm, toConsole, pop, line, previous);
		    				break;
		    				case "sensor": numberOfRecords += searchSensorAll(writer, searchTerm, toConsole, pop, line, previous);
		    				break;
		    				case "time range": numberOfRecords += searchTimeAll(writer, searchTerm, toConsole, pop, line, previous);
		    				break;
		    				}
		    				
		    			}
		    			else {
		    				if (line.contains("ERROR")) { 
		    					Error error = Reader.parse(previous, line);
		    					
		    					switch (searchType) {
			    				case "general": numberOfRecords += searchGeneralError(writer, searchTerm, toConsole, pop, error);
			    				break;
			    				case "error type": numberOfRecords += searchTypeError(writer, searchTerm, toConsole, pop, error);
			    				break;
			    				case "location": numberOfRecords += searchLocationError(writer, searchTerm, toConsole, pop, error);
			    				break;
			    				case "sensor": numberOfRecords += searchSensorError(writer, searchTerm, toConsole, pop, error);
			    				break;
			    				case "time range": numberOfRecords += searchTimeError(writer, searchTerm, toConsole, pop, error);
			    				break;
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
	
	public int searchAll(PrintWriter writer, String term, boolean toConsole, PopUp pop, String line, String previous) throws FileNotFoundException {
		int recordCounter = 0;
		if (line.toLowerCase().contains(term)) {
			if (!previous.equals("")) previous += brk;
			if (toConsole) pop.addText(previous + line + brk + brk);
			if (writer != null) writer.println(previous + line + brk); 	
			recordCounter++;
		previous = "";
		}
		else {
			previous = line;
		}
		return recordCounter;
	}
				
	public int searchError(PrintWriter writer, String term, boolean toConsole, PopUp pop, Error error) throws FileNotFoundException {
		int recordCounter = 0;
				
		if (error.contains(term)) {
			if (toConsole) pop.addText(error.toString() + brk);
			if (writer != null) writer.println(error.toString());
			recordCounter++;
		}
			
		return recordCounter;
	}
	
	public int searchGeneralAll(PrintWriter writer, String term, boolean toConsole, PopUp pop, String line, String previous) throws FileNotFoundException {
		// supports several search terms divided by , _ or whitespace
		String[] terms = term.split("\\s*(_|,|\\s)\\s*");

		boolean matchFound = true;
		
		for (int i = 0; i < terms.length && matchFound; i++) {
			if (!line.toLowerCase().contains(terms[i])) matchFound = false;
		}
		
		int recordCounter = 0;
		if (matchFound) {
			if (!previous.equals("")) previous += brk;
			if (toConsole) pop.addText(previous + line + brk + brk);
			if (writer != null) writer.println(previous + line + brk); 	
			recordCounter++;
		previous = "";
		}
		else {
			previous = line;
		}
		return recordCounter;
	}
				
	public int searchGeneralError(PrintWriter writer, String term, boolean toConsole, PopUp pop, Error error) throws FileNotFoundException {
		int recordCounter = 0;
				
		if (error.contains(term)) {
			if (toConsole) pop.addText(error.toString() + brk);
			if (writer != null) writer.println(error.toString());
			recordCounter++;
		}
			
		return recordCounter;
	}
	
	public int searchTypeAll(PrintWriter writer, String term, boolean toConsole, PopUp pop, String line, String previous) throws FileNotFoundException {

		return searchAll(writer, term, toConsole, pop, line, previous);			
	}
	
	public int searchTypeError(PrintWriter writer, String term, boolean toConsole, PopUp pop, Error error) throws FileNotFoundException {

		int recordCounter = 0;
					
		if (error.getType().toLowerCase().equals(term)) {
			if (toConsole) pop.addText(error.toString() + brk);
			if (writer != null) writer.println(error.toString());
			recordCounter++;
		}

		return recordCounter;
	}
	
	public int searchLocationAll(PrintWriter writer, String term, boolean toConsole, PopUp pop, String line, String previous) throws FileNotFoundException {
		return searchAll(writer, term, toConsole, pop, line, previous);
	}
	
	public int searchLocationError(PrintWriter writer, String term, boolean toConsole, PopUp pop, Error error) throws FileNotFoundException {
		
		int recordCounter = 0;
		
		String[] tmp = term.split("/");
		String searchedLocation = "Rack " + tmp[0] + ", Module " + tmp[1] + ", Line " + tmp[2];

		if (error.getLocation().equals(searchedLocation)) {
			if (toConsole) pop.addText(error.toString() + brk);
			if (writer != null) writer.println(error.toString());
			recordCounter++;
		}

		return recordCounter;
	}
		
	public int searchSensorAll(PrintWriter writer, String term, boolean toConsole, PopUp pop, String line, String previous) throws FileNotFoundException {
		return searchAll(writer, term, toConsole, pop, line, previous);
	}
	
	public int searchSensorError(PrintWriter writer, String term, boolean toConsole, PopUp pop, Error error) throws FileNotFoundException {
		String[] tmp = term.split("/");
		int recordCounter = 0;
		try {
			recordCounter = 0;
			
			
			String searchedLocation = "Module " + tmp[0] + ", Line " + tmp[1];

			if (error.getLocation().contains(searchedLocation)) {
				if (toConsole) pop.addText(error.toString() + brk);
				if (writer != null) writer.println(error.toString());
				recordCounter++;
			}
		} catch (Exception e) {
			System.out.println(term);
			System.out.println(Arrays.toString(tmp));
			e.printStackTrace();
		}

		return recordCounter;
	}
	

	public int searchTimeAll(PrintWriter writer, String term, boolean toConsole, PopUp pop, String line, String previous) throws FileNotFoundException {
		
		int recordCounter = 0;
		
		GregorianCalendar start = null;
		GregorianCalendar finish = null;
		
		try {
			String[] tmp = term.split("\n");
			start = parseDateTime(tmp[0]);
			finish = parseDateTime(tmp[1]);
		} catch (Exception e) {
			System.out.println("Error parsing date and time: " + term);
			return 0;
		}
		
		String[] tmp = line.split(" ");
		if (tmp.length < 2) return 0;
		String dateAndTime = tmp[0] + " " + tmp[1];
		GregorianCalendar lineDateTime;
		
		try {
			lineDateTime = parseDateTime(dateAndTime);
		} catch (Exception e) {
			System.out.println("Error parsing date and time: " + dateAndTime);
			return 0;														// maybe handle this better
		}
		
		if (start.compareTo(lineDateTime) <= 0 && finish.compareTo(lineDateTime) >= 0) {
			
				if (!previous.equals("")) previous += brk;
				if (toConsole) pop.addText(previous + line + brk + brk);
				if (writer != null) writer.println(previous + line + brk); 	
				recordCounter++;
				previous = "";
		}
		else {
				previous = line;
		}
		
		return recordCounter;
	}		
	
	public int searchTimeError(PrintWriter writer, String term, boolean toConsole, PopUp pop, Error error) throws FileNotFoundException {
		
		int recordCounter = 0;
		
		GregorianCalendar start = null;
		GregorianCalendar finish = null;
		GregorianCalendar lineDateTime = error.getDateTime();
		
		try {
			String[] tmp = term.split("\n");
			start = parseDateTime(tmp[0]);
			finish = parseDateTime(tmp[1]);
		} catch (Exception e) {
			System.out.println("Error parsing date and time: " + term);
			return 0;
		}
		
		if (start.compareTo(lineDateTime) <= 0 && finish.compareTo(lineDateTime) >= 0) {
			if (toConsole) pop.addText(error.toString() + brk);
			if (writer != null) writer.println(error.toString());
			recordCounter++;
		}

		return recordCounter;
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