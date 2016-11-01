import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class Reader {

	private static String brk = System.lineSeparator();
		
	public int parseLog(String inputDirectory, PopUp pop, String outputPath, boolean errorsOnly, boolean toConsole, boolean toTextFile) {

		if (toTextFile && (outputPath == null || outputPath.trim().isEmpty())) {
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
				writer = new PrintWriter(outputPath);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unable to create output file in directory: " + outputPath, "Error", JOptionPane.ERROR_MESSAGE);
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
	
	
	
	private static boolean isErrorLine(String line) { 
		if (line.contains("ERROR")) {
			return true;
		}
		return false;
	}
	
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