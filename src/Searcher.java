import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
			    	//searchGeneral(log, writer, searchTerm, errorsOnly, toTextFile, toConsole);
		    		switch (searchType) {
		    			case "general": searchGeneral(log, writer, searchTerm, errorsOnly, toTextFile, toConsole);
		    			break;
		    			case "1": System.out.println("Stuff");
		    			break;
		    			default: System.out.println("Wrong searchType");
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
		  writer.close();
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

	public void searchTimeFrame(String inputDirectory, String term, String startTime, String endTime, boolean errorsOnly) {
		
	}
	
	public void searchLocation(String inputDirectory, String location, boolean errorsOnly) {
		
	}
	
	public void searchSensor(String inputDirectory, String sensor, boolean errorsOnly) {
		
	}

}
