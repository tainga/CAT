import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Searcher {

	private static String brk = System.lineSeparator();
	
	public void search(String searchType, String inputDirectory, String outputDirectory, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole, PopUp pop) throws FileNotFoundException {
		
		File dir = new File(inputDirectory);
		PrintWriter writer = null;
		
		if (toTextFile && (outputDirectory == null || outputDirectory.isEmpty())) {
			pop.addText("No output directory!");
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
		    			case "general": searchGeneral(log, writer, searchTerm, errorsOnly, toTextFile, toConsole, pop);
		    			break;
		    			case "error type": searchType(log, writer, searchTerm, errorsOnly, toTextFile, toConsole, pop);
		    			break;
		    			case "location": searchLocation(log, writer, searchTerm, errorsOnly, toTextFile, toConsole, pop);
		    			break;
		    			case "sensor": searchSensor(log, writer, searchTerm, errorsOnly, toTextFile, toConsole, pop);
		    			break;
		    			case "time range": searchTime(log, writer, searchTerm, errorsOnly, toTextFile, toConsole, pop);
		    			break;
		    			default: pop.addText("Wrong searchType: " + searchType);
		    			break;
		    		}
		    	}
		    	else continue;
		    	
		    }
		    
		  } else {
			  pop.addText("Directory not found. Please make sure that the directory you specified is really a directory.");
		  }  
		  
		  if (writer != null) writer.close();
	}
	
	public void searchGeneral(File log, PrintWriter writer, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole, PopUp pop) throws FileNotFoundException {
		Scanner reader = new Scanner(log);
		String previous = "";

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String lowerCaseLine = line.toLowerCase();
			
			if (!errorsOnly) {
				if (lowerCaseLine.contains(term)) {
					if (!previous.equals("")) previous += brk;
					if (toConsole) pop.addText(previous + line + brk + brk);
					if (writer != null) writer.println(previous + line + brk); 				
				previous = "";
				}
				else {
					previous = line;
				}
			}
			else {
				if (line.contains("ERROR")) { 
					Error error = Reader.parse(previous, line);
					if (error.contains(term)) {
						if (toConsole) pop.addText(error.toString() + brk);
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
	
	public void searchType(File log, PrintWriter writer, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole, PopUp pop) throws FileNotFoundException {
		if (!(term.equals("parity") || term.equals("data loss") || term.equals("idle miss"))) {
			searchGeneral(log, writer, term, errorsOnly, toTextFile, toConsole, pop);
			return;
		}
		
		Scanner reader = new Scanner(log);
		String previous = "";

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String lowerCaseLine = line.toLowerCase();
			
			if (!errorsOnly) {
				if (lowerCaseLine.contains(term)) {
					if (toConsole) pop.addText(line + brk);
					if (writer != null) writer.println(line + brk); 
				}
			}
			else {
				if (line.contains("ERROR")) { 
					Error error = Reader.parse(previous, line);
					
					if (error.getType().toLowerCase().equals(term)) {
						if (toConsole) pop.addText(error.toString() + brk);
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
	
	public void searchLocation(File log, PrintWriter writer, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole, PopUp pop) throws FileNotFoundException {
		Scanner reader = new Scanner(log);
		String previous = "";

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String lowerCaseLine = line.toLowerCase();
			
			if (!errorsOnly) {
				if (lowerCaseLine.contains(term)) {
					if (toConsole) pop.addText(line + brk);
					if (writer != null) writer.println(line + brk); 
				}
			}
			else {
				if (line.contains("ERROR")) { 
					Error error = Reader.parse(previous, line);
					String[] tmp = term.split("/");
					String searchedLocation = "Rack " + tmp[0] + ", Module " + tmp[1] + ", Line " + tmp[2];
					if (error.getLocation().equals(searchedLocation)) {
						if (toConsole) pop.addText(error.toString() + brk);
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
	
	public void searchSensor(File log, PrintWriter writer, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole, PopUp pop) throws FileNotFoundException {
		Scanner reader = new Scanner(log);
		String previous = "";

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String lowerCaseLine = line.toLowerCase();
			
			if (!errorsOnly) {
				if (lowerCaseLine.contains(term)) {
					if (toConsole) pop.addText(line + brk);
					if (writer != null) writer.println(line + brk); 
				}
			}
			else {
				if (line.contains("ERROR")) { 
					Error error = Reader.parse(previous, line);
					String[] tmp = term.split("/");
					String searchedLocation = "Module " + tmp[0] + ", Line " + tmp[1];
					if (error.getLocation().contains(searchedLocation)) {
						if (toConsole) pop.addText(error.toString() + brk);
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
	

	public void searchTime(File log, PrintWriter writer, String term, boolean errorsOnly, boolean toTextFile, boolean toConsole, PopUp pop) throws FileNotFoundException {
		GregorianCalendar start = null;
		GregorianCalendar finish = null;
		try {
			String[] tmp = term.split("\n");
			start = parseDateTime(tmp[0]);
			finish = parseDateTime(tmp[1]);
		} catch (Exception e) {
			pop.addText("Error parsing date and time: " + term);
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
					if (toConsole) pop.addText(line + brk);
					if (writer != null) writer.println(line + brk); 
				}
			}
			else {
				if (line.contains("ERROR")) { 
					Error error = Reader.parse(previous, line);
					GregorianCalendar lineDateTime = error.getDateTime();
					if (start.compareTo(lineDateTime) <= 0 && finish.compareTo(lineDateTime) >= 0) {
						if (toConsole) pop.addText(error.toString() + brk);
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