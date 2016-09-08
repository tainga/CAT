import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Searcher {

	
	public void search(String searchType, String inputDirectory, String outputDirectory, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole) throws FileNotFoundException {
		
		File dir = new File(inputDirectory);
		PrintWriter writer = null;
		
		if (toTextFile && (outputDirectory == null || outputDirectory.isEmpty())) {
			System.out.println("No output directory!");
			return;
		}
		
		if (toTextFile && outputDirectory != null && !outputDirectory.isEmpty()) {
			File output = new File(outputDirectory);
			writer = new PrintWriter(output);
		}
		
		  File[] directoryListing = dir.listFiles();
		
		  if (directoryListing != null) {
		    for (File log : directoryListing) {
		    	
		    	if (log.isDirectory() || log.getName().length() < 7) continue;
		      
		    	if (log.getName().substring(0, 7).equals("control")) {
		    		String searchTerm = term.trim().toLowerCase();
		    		switch (searchType) {
		    			case "general": searchGeneral(log, writer, searchTerm, errorsOnly, toTextFile, toConsole);
		    			break;
		    			case "error type": searchType(log, writer, searchTerm, errorsOnly, toTextFile, toConsole);
		    			break;
		    			case "location": searchLocation(log, writer, searchTerm, errorsOnly, toTextFile, toConsole);
		    			break;
		    			case "sensor": searchSensor(log, writer, searchTerm, errorsOnly, toTextFile, toConsole);
		    			break;
		    			case "time range": searchTime(log, writer, searchTerm, errorsOnly, toTextFile, toConsole);
		    			break;
		    			default: System.out.println("Wrong searchType: " + searchType);
		    			break;
		    		}
		    	}
		    	else continue;
		    	
		    }
		    
		  } else {
		    // Handle the case where dir is not really a directory.
		    // Checking dir.isDirectory() above would not be sufficient
		    // to avoid race conditions with another process that deletes
		    // directories.
		  }  
		  
		  if (writer != null) writer.close();
	}
	
	public void searchGeneral(File log, PrintWriter writer, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole) throws FileNotFoundException {
		Scanner reader = new Scanner(log);
		String previous = "";

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String lowerCaseLine = line.toLowerCase();
			
			if (!errorsOnly) {
				if (lowerCaseLine.contains(term)) {
					if (toConsole) System.out.println(line);
					if (writer != null) writer.println(line); 
				}
			}
			else {
				if (line.contains("ERROR")) { 
					Error error = Reader.parse(previous, line);
					if (error.contains(term)) {
						if (toConsole) System.out.println(error.toString());
						if (writer != null) writer.println(error.toString());
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
	
	public void searchType(File log, PrintWriter writer, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole) throws FileNotFoundException {
		if (!(term.equals("parity") || term.equals("data loss") || term.equals("idle miss"))) {
			searchGeneral(log, writer, term, errorsOnly, toTextFile, toConsole);
			return;
		}
		
		Scanner reader = new Scanner(log);
		String previous = "";

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String lowerCaseLine = line.toLowerCase();
			
			if (!errorsOnly) {
				if (lowerCaseLine.contains(term)) {
					if (toConsole) System.out.println(line);
					if (writer != null) writer.println(line); 
				}
			}
			else {
				if (line.contains("ERROR")) { 
					Error error = Reader.parse(previous, line);
					
					if (error.getType().toLowerCase().equals(term)) {
						if (toConsole) System.out.println(error.toString());
						if (writer != null) writer.println(error.toString());
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
	
	public void searchLocation(File log, PrintWriter writer, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole) throws FileNotFoundException {
		Scanner reader = new Scanner(log);
		String previous = "";

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String lowerCaseLine = line.toLowerCase();
			
			if (!errorsOnly) {
				if (lowerCaseLine.contains(term)) {
					if (toConsole) System.out.println(line);
					if (writer != null) writer.println(line); 
				}
			}
			else {
				if (line.contains("ERROR")) { 
					Error error = Reader.parse(previous, line);
					String[] tmp = term.split("/");
					String searchedLocation = "Rack " + tmp[0] + ", Module " + tmp[1] + ", Line " + tmp[2];
					if (error.getLocation().equals(searchedLocation)) {
						if (toConsole) System.out.println(error.toString());
						if (writer != null) writer.println(error.toString());
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
	
	public void searchSensor(File log, PrintWriter writer, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole) throws FileNotFoundException {
		Scanner reader = new Scanner(log);
		String previous = "";

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String lowerCaseLine = line.toLowerCase();
			
			if (!errorsOnly) {
				if (lowerCaseLine.contains(term)) {
					if (toConsole) System.out.println(line);
					if (writer != null) writer.println(line); 
				}
			}
			else {
				if (line.contains("ERROR")) { 
					Error error = Reader.parse(previous, line);
					String[] tmp = term.split("/");
					String searchedLocation = "Module " + tmp[0] + ", Line " + tmp[1];
					if (error.getLocation().contains(searchedLocation)) {
						if (toConsole) System.out.println(error.toString());
						if (writer != null) writer.println(error.toString());
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
	

	public void searchTime(File log, PrintWriter writer, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole) throws FileNotFoundException {
		GregorianCalendar start = null;
		GregorianCalendar finish = null;
		try {
			String[] tmp = term.split("\n");
			start = parseDateTime(tmp[0]);
			finish = parseDateTime(tmp[1]);
		} catch (Exception e) {
			System.out.println("Error parsing date and time: " + term);
			return;
		}
		
		Scanner reader = new Scanner(log);
		String previous = "";

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			
			if (!errorsOnly) {
				String[] tmp = line.split(" ");
				if (tmp.length < 2) continue;
				String dateAndTime = tmp[0] + " " + tmp[1];
				GregorianCalendar lineDateTime;
				try {
					lineDateTime = parseDateTime(dateAndTime);
				} catch (Exception e) {
					continue;														// maybe handle this better
				}
				if (start.compareTo(lineDateTime) <= 0 && finish.compareTo(lineDateTime) >= 0) {
					if (toConsole) System.out.println(line);
					if (writer != null) writer.println(line); 
				}
			}
			else {
				if (line.contains("ERROR")) { 
					Error error = Reader.parse(previous, line);
					GregorianCalendar lineDateTime = error.getDateTime();
					if (start.compareTo(lineDateTime) <= 0 && finish.compareTo(lineDateTime) >= 0) {
						if (toConsole) System.out.println(error.toString());
						if (writer != null) writer.println(error.toString());
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
