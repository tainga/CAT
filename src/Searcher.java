import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Searcher {

	
	public void searchGeneral(String inputDirectory, String term, boolean errorsOnly) throws FileNotFoundException {
		
		
		File dir = new File(inputDirectory);
		  
		  File[] directoryListing = dir.listFiles();
		
		  if (directoryListing != null) {
		    for (File log : directoryListing) {
		    	
		    	if (log.isDirectory() || log.getName().length() < 7) continue;
		      
		    	if (log.getName().substring(0, 7).equals("control")) {
			    	Scanner reader = new Scanner(log);
					String previous = "";
	
					while (reader.hasNextLine()) {
						String line = reader.nextLine();
						String lowerCaseLine = line.toLowerCase();
						
						if (!errorsOnly) {
							if (lowerCaseLine.contains(term)) {
								System.out.println(line);
							}
						}
						else {
							if (line.contains("ERROR")) { 
								Error error = Reader.parse(previous, line);
								if (error.contains(term)) {
									System.out.println(error.toString());
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
		    // Handle the case where dir is not really a directory.
		    // Checking dir.isDirectory() above would not be sufficient
		    // to avoid race conditions with another process that deletes
		    // directories.
		  }  
	}
	
	

	public void searchTimeFrame(String inputDirectory, String term, String startTime, String endTime, boolean errorsOnly) {
		
	}
	
	public void searchLocation(String inputDirectory, String location, boolean errorsOnly) {
		
	}
	
	public void searchSensor(String inputDirectory, String sensor, boolean errorsOnly) {
		
	}

}
