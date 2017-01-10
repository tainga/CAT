import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 * The Reader class compiles a unified report based on CGW control log files.
 *
 */
public class Reader {

	private static String brk = System.lineSeparator();
	
	/**
	 * Compiles a report by going through all files inside the specified directory whose name start with 'control'; all other files are ignored.
	 * Depending on the value of the errorsOnly argument, the report will contain either all raw data (if errorsOnly is false),
	 * or all error-related records only (if errorsOnly is true). Prints all found data to a text file and/or a text window as specified by the user.	
	 * @param inputDirectory a path to the directory containing the log files
	 * @param pop an output window
	 * @param outputFile a path to the directory in which an output text file is to be created
	 * @param errorsOnly a boolean specifying whether the report should contain error messages only
	 * @param toConsole a boolean specifying whether the output should be directed to an output window
	 * @param toTextFile a boolean specifying whether the output should be directed to a text file
	 * @return the number of records found
	 */
	public int parseLog(String inputDirectory, PopUp pop, String outputFile, boolean errorsOnly, boolean toConsole, boolean toTextFile) {

		if (toTextFile && (outputFile == null || outputFile.trim().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Output destination not specified", "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		if (inputDirectory == null || inputDirectory.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Input directory not specified", "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		File dir = null;
		File[] directoryListing = null;
		try {
			dir = new File(inputDirectory);
			directoryListing = dir.listFiles();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		if (pop == null && toConsole) {
			JOptionPane.showMessageDialog(null, "Unable to find output window", "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}

		PrintWriter writer = null;
		if (toTextFile) {
			try {
				writer = new PrintWriter(outputFile);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unable to create output file: " + outputFile, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		int recordCounter = 0;

			for (File log : directoryListing) {

				if (log.isDirectory() || log.getName().length() < 7)
					continue;

				if (log.getName().substring(0, 7).equals("control")) {
					
					Scanner reader = null;
					try {
						reader = new Scanner(log);
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						continue;
					}
					
					String previous = "";

					while (reader.hasNextLine()) {
						String line = reader.nextLine();

						if (errorsOnly) {
							if (isErrorLine(line)) {
								Error error = parse(previous, line);
								if (toConsole)
									pop.addText(error.toString() + brk);
								if (toTextFile)
									writer.println(error.toString() + brk);
								recordCounter++;
								previous = "";
							} 
							else {
								previous = line;
							}
						} 
						else {
							if (toConsole) pop.addText(line + brk);
							if (toTextFile) writer.println(line + brk);
							recordCounter++;
						}
					}

					reader.close();	
				} 
			}

		if (writer != null) writer.close();
		
		return recordCounter;
	}
	
	/**
	 * Determines whether the given line should be processed as an error
	 * @param line the string to be analyzed
	 * @return true if the record pertains to an error, false otherwise
	 */
	private static boolean isErrorLine(String line) { 
		if (line.contains("ERROR")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Constructs an error object based on the error information contained in the lines passed as the arguments
	 * @param statLine a string containing the status message preceding the error message
	 * @param errorLine a string containing the error message
	 * @return an Error object based on the information from the error and status lines
	 */
	public static Error parse(String statLine, String errorLine) {

		Error err = null;
				
		if (errorLine.contains("ERROR") && statLine.contains("STATUS")) {
			
			err = new Error (statLine, errorLine);
		}
		else if (statLine == null || statLine.trim().equals("")) {
			err = new Error (errorLine);
		}
		else {
			JOptionPane.showMessageDialog(null, "Error parsing lines:\n" + statLine + "\n" + errorLine, "Error", JOptionPane.ERROR_MESSAGE);
		}
	
		return err;
	}

}