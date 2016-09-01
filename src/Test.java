import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		//"C:\\Users\\anastasia taing\\Desktop\\CGW project\\example save crash\\vpsSYS2_27Jun2016_12_27_25_cgw1e\\CGWLOGS\\control"
		String inputDirectory = "C:\\Users\\anastasia taing\\Desktop\\CGW project\\example save crash\\chaSYS1_20Jul2016_13_18_17_cgw1f\\CGWLOGS\\control";
		
		String dateTime = LocalDateTime.now().toString().replace(':', '\'').replace('T', ' ');
		String output = "C:\\Users\\anastasia taing\\Desktop\\CGW project\\output\\run " + dateTime + ".txt";
		
		Reader test = new Reader();
		//test.reportToTextFile(inputDirectory, output);

	}

}
