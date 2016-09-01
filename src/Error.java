import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Error {
	
	private String originalMessage;
	private String originalStatus;
	private String date;  //change to Date, maybe
	private String time;
	private double BLR;
	private double thresh;
	private String type;
	private String location;
	private boolean blrThreshLocationIsSet;
	
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
			System.out.println("Error parsing date and time in line:\n" + errorLine);
		}	
	}
		
	private void parseOriginalMessage(String statusLine, String errorLine) {
		String[] splitLine1 = statusLine.split(" ");
		String[] splitLine2 = errorLine.split(" ");
		
		
		try {
			this.date = splitLine2[0];
			this.time = splitLine2[1];
		} 
		catch (Exception e) {

			System.out.println("Error parsing date and time in line:\n" + errorLine);
		}
		
			int blrLocation = statusLine.indexOf("BLR=");
			if (blrLocation != -1) {
				//System.out.println(statusLine);
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
		/*
		else if (msg.contains("guilded lilly:")) {
			type = "Guilded Lilly";
		}
		else if (msg.contains("time out error")) {
			type = "Time out";
		}
		*/
		else {type = "0";}
	}	
	
	private String parseErrorMsg(String errorLine) {
		int indexOfMsgStart = errorLine.indexOf("): ");
		return errorLine.substring(indexOfMsgStart +3);
	}
	
	public String toString() {
		
		String output = "";
		
		/*
		// no status line
		if (type.equals("0") && originalMessage != null && originalStatus == null) {
			return originalMessage + "\n";
		}
		
		// atypical status or error
		if ((!blrThreshLocationIsSet || type.equals("0")) && originalMessage != null && originalStatus != null) {
			return originalStatus + "\n" + originalMessage + "\n";
		}
		
		if (type.equals("idle miss") || type.equals("data loss") || type.equals("parity")) {
			String aORan = "A";
			if (type.equals("idle miss")) {aORan += "n";}
			return aORan + " " + type + " error occurred in " + location + "\nBit loss rate: " + BLR + ", BLR threshold: " + thresh + 
					"\nDate and time: " + date + " " + time + "\n";
		}
		if (type.equals("time out")) {
			String status = originalStatus.substring(20);
			int errLocation = originalMessage.indexOf("Time out error.") + 16;
			String errMsg = originalMessage.substring(errLocation);
			return "A " + type + " error occurred at " + date + " " + time + "\n" + errMsg + "\n" + status + "\n";
		}
		if (type.equals("Guilded Lilly")) {   
			String status = originalStatus.substring(20);
			int errLocation = originalMessage.indexOf("Guilded Lilly:") + 15;
			String errMsg = originalMessage.substring(errLocation);
			return "A " + type + " error occurred at " + date + " " + time + "\n" + errMsg + "\n" + status + "\n";
		}
		
		else {
			return ("\n\nAchtung!!!\n\n" + originalStatus + "\n" + originalMessage);  
		}
		*/
		
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
				output += "\nBit loss rate: " + BLR + " ";
			}
			if (thresh > -1) {
				output += "BLR threshold: " + thresh;
			}
		}
		if (!blrThreshLocationIsSet) {
			if (type.equals("Idle miss") || type.equals("Data loss") || type.equals("Parity")) {
				if (originalStatus != null) {
					output +=  "\n" + originalStatus;
				}
				else {
					output +=  "\n" + originalMessage;
				}
			}
			else {
				output += "Error: " + parseErrorMsg(originalMessage);
					if (originalStatus != null) {
						output +=  "\n" + originalStatus;
					}
			}
		}
		
		output += "\n";
		
		return output;
		
	}
	
	public boolean contains(String searchTerm) {
		if ((originalMessage != null && originalMessage.toLowerCase().contains(searchTerm.toLowerCase()) || (originalStatus != null && originalStatus.toLowerCase().contains(searchTerm.toLowerCase())))) {
			return true;
		}
		return false;
	}
	

}
