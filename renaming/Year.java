

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Year {
	public static  void  main(String[] arg) throws IOException {
		System.out.println(getYear(".bib")); // just for testing
	}
 	public static String getYear(String path) throws FileNotFoundException{
	 	File bib = new File(path);
	 	
	 	Scanner scbib = new Scanner(bib);
	 	
	 	String se=null;
	 	
	 	String s=null;
	 	
	 	String year = null;
	 	
	 	while (scbib.hasNextLine()){
	 			
	 			s = scbib.nextLine();
	 	
	 		if (s.startsWith("year"))
	 			se = s;
	 	}
	 	int end = se.lastIndexOf('"');
	 	int start = se.indexOf('"');
	 	year = se.substring(start+1, end);
	 	scbib.close();
	 	return year;
	 	}
}
