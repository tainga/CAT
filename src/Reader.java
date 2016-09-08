import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

import org.omg.Messaging.SyncScopeHelper;


public class Reader {

		
	// prints error report to console
	public void parseLog(String inputDirectory, boolean errorsOnly) throws FileNotFoundException {
		
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
						
						if (errorsOnly) {
							if (isErrorLine(line)) { 
								Error error = parse(previous, line);
								System.out.println(error.toString());
								previous = "";
							}
							else {
								previous = line;
							}
						}
						else {
							System.out.println(line);
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
	
	// prints error report to file
	public void parseLog(String inputDirectory, String outputPath, boolean errorsOnly) throws FileNotFoundException {
		
		PrintWriter writer = new PrintWriter(outputPath);
		
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
						
						if (errorsOnly) {
							if (isErrorLine(line)) {
								Error error = parse(previous, line);
								writer.println(error.toString());
								previous = "";
							} 
							else {
								previous = line;
							} 
						}
						else {
							writer.println(line);
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
		  writer.close();
		  
	}
	
	private static boolean isErrorLine(String line) { 
		if (line.contains("ERROR")) {
			return true;
		}
		return false;
	}
	
	public static Error parse(String statLine, String errorLine) {

		Error err;
				
		if (errorLine.contains("ERROR") && statLine.contains("STATUS")) {
			
			err = new Error (statLine, errorLine);
		}
		else if (statLine == null || statLine == "" || statLine == " ") {
			err = new Error (errorLine);
		}
		else {
			System.out.println();
			throw new IllegalArgumentException("Input error");
			
		}
	
		return err;
	}

}
