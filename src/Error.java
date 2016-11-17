import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

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
	
		// for error messages without a preceding status line
	public Error (String errorLine) {
		parseType(errorLine);
		parseDatetime(errorLine);	
	}
	
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
	
	private String parseErrorMsg(String errorLine) {
		int indexOfMsgStart = errorLine.indexOf("): ");
		return errorLine.substring(indexOfMsgStart +3);
	}
	
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
	
	public boolean contains(String searchTerm) {
		if (this.toString().contains(searchTerm)) {
			return true;
		}
		return false;
	}
	
	public String getLocation() {
		if (location != null) return location;
		return "";
	}
	
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
	
	public String getType() {
		return type;
	}
	

}