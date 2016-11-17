import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * The error class is used for storing information about a single error in an object.
 *
 */
public class Error {
	
	private String originalMessage;
	private String originalStatus;
	private String date;
	private String time;
	private double BLR;
	private double thresh;
	private String type;
	private String location;
	private boolean blrThreshLocationIsSet;
	private String brk = System.lineSeparator();
	
	/**
	 * Constructor. Used for error messages with a preceding status line.
	 * @param statusLine a line containing a status message
	 * @param errorLine a line containing an error message
	 */
	public Error (String statusLine, String errorLine) {
		originalMessage = errorLine;
		originalStatus = statusLine;
		// initializes date and time
		parseDatetime(errorLine);
		// initializes BLR, thresh, location, blrThreshLocationIsSet
		parseOriginalMessage(statusLine, errorLine);
		// initializes type
		parseType(errorLine);			
	}
	
	/**
	 * Constructor. Used for error messages without a preceding status line.
	 * @param errorLine a line containing an error message
	 */
	public Error (String errorLine) {
		parseType(errorLine);
		parseDatetime(errorLine);	
	}
	
	/**
	 * Parses date and time information from a string that contains an error message.
	 * @param errorLine a string that contains an error message
	 */
	private void parseDatetime(String errorLine) {
		try {
			originalMessage = errorLine;
			String[] split = errorLine.split(" ");
			this.date = split[0];
			this.time = split[1];

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error parsing date and time in line:" + brk + errorLine, "Error", JOptionPane.ERROR_MESSAGE);
		}	
	}

	/**
	 * Parses information about the error's BLR, BLR threshold and location and stores it in instance variables.
	 * @param statusLine a line containing a status message
	 * @param errorLine a line containing an error message
	 */
	private void parseOriginalMessage(String statusLine, String errorLine) {
		
		String[] splitLine2 = errorLine.split(" ");
		
		try {
			this.date = splitLine2[0];
			this.time = splitLine2[1];
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error parsing date and time in line:" + brk + errorLine, "Error", JOptionPane.ERROR_MESSAGE);
		}
	
		int blrLocation = statusLine.indexOf("BLR=");
		if (blrLocation != -1) {
			String sub = statusLine.substring(blrLocation + 4);
			this.BLR = Double.parseDouble(sub.split(" ")[0]);
		}
		else {
			this.BLR = -1;
		}
		
		int thrLocation = statusLine.indexOf("BLR_THRESH=");
		if (thrLocation != -1) {
			String sub = statusLine.substring(thrLocation + 11);
			this.thresh = Double.parseDouble(sub.split(" ")[0]);
		}
		else {
			this.thresh = -1;
		}
		
		int locationIndex = -1;
		Pattern p = Pattern.compile("[0-9]/[0-9]/[0-9]");
		Matcher m = p.matcher(statusLine);
		if (m.find()) {
			locationIndex = m.start();
		}
		if (locationIndex != -1) {
			String sub = statusLine.substring(locationIndex, locationIndex + 5);
			String[] tmp = sub.split("/");
			this.location = "Rack " + tmp[0] + ", Module " + tmp[1] + ", Line " + tmp[2];
		}
		
		if (blrLocation != -1 && thrLocation != -1 && locationIndex != -1) {
			blrThreshLocationIsSet = true;
		}
		else { blrThreshLocationIsSet = false; }
	}

	/**
	 * Parses information about the type of the error from the original error message.
	 * @param errorMsg a string containing an error message
	 */
	private void parseType(String errorMsg) {
		String msg = errorMsg.toLowerCase();
		if (msg.contains("sync_rx_idle_misses")) {
			type = "Idle miss";
		}
		else if (msg.contains("sync_rx_data_losses")) {
			type = "Data loss";
		}
		else if (msg.contains("sync_rx_parity_errs")) {
			type = "Parity";
		}
		else {type = "0";}
	}	
	
	/**
	 * Extracts the error message from the error line. Returns the error message only, without the date, time and message source info.
	 * @param errorLine a line containing an error message
	 * @return a string containing the error message only
	 */
	private String parseErrorMsg(String errorLine) {
		int indexOfMsgStart = errorLine.indexOf("): ");
		return errorLine.substring(indexOfMsgStart +3);
	}
	
	/**
	 * Returns a formatted string representation of the error. Includes information about BLR, BLR threshold and location if
	 * the error belongs to one of the recognized types (parity, idle miss, data loss). BLR information is omitted if BLR does not exceed the threshold.
	 * For all other types of errors, includes the whole error message followed by the preceding status line, if such exists.
	 * @return a string representation of the error
	 */
	public String toString() {
		
		String output = "";
		
		if (date != null) {
			output += date + " ";
		}
		if (time !=null) {
			output += time + " ";
		}
		if (!type.equals("0") && type != null) {
			output += type + " error";
		}
		if (location != null) {
			output += " in " + location;
		}
		if (Double.compare(BLR, thresh) > 0) {
			if (BLR > -1) {
				output += brk + "Bit loss rate: " + BLR + " ";
			}
			if (thresh > -1) {
				output += "BLR threshold: " + thresh;
			}
		}
		if (!blrThreshLocationIsSet) {
			if (type.equals("Idle miss") || type.equals("Data loss") || type.equals("Parity")) {
				if (originalStatus != null) {
					output +=  brk + originalStatus;
				}
				else {
					output +=  brk + originalMessage;
				}
			}
			else {
				output += "Error: " + parseErrorMsg(originalMessage);
					if (originalStatus != null) {
						output +=  brk + originalStatus;
					}
			}
		}
		output += brk;
		return output;	
	}
	
	/**
	 * Searches through the string representation of the error to determine whether it contains the given search term.
	 * @param searchTerm a string containing the search term
	 * @return true if the error contains the search term, false otherwise
	 */
	public boolean contains(String searchTerm) {
		if (this.toString().contains(searchTerm)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the location where the error occurred in the form 'Rack X, Module X, Line X';
	 * @return
	 */
	public String getLocation() {
		if (location != null) return location;
		return "";
	}
	
	/**
	 * Parses the string containing the error date and time and constructs an appropriate Calendar object
	 * @return a GregorianCalendar object matching the specified date and time
	 */
	public GregorianCalendar getDateTime() {
		try {
			String[] tmpDate = date.split("/");
			String[] tmpTime = time.split(":");
			GregorianCalendar dateTime = new GregorianCalendar(Integer.parseInt(tmpDate[2]), Integer.parseInt(tmpDate[0]), Integer.parseInt(tmpDate[1]), Integer.parseInt(tmpTime[0]), Integer.parseInt(tmpTime[1]), Integer.parseInt(tmpTime[2]));
			return dateTime;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error parsing date and time: " + date + " " + time, "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	/**
	 * Returns the error type. The type is set to 'Parity', 'Idle miss' or 'Data loss' if the error belongs to one of the three suppported types, and to '-1' otherwise.
	 * @return error type
	 */
	public String getType() {
		return type;
	}
	

}