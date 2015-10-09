package renaming;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Title {
	public static  void  main(String[] arg) throws IOException {
		System.out.println(getTitle("")); // just for testing
	}
 	public static String getTitle(String path) throws FileNotFoundException{//
	 	File bib = new File(path);
	 	
	 	Scanner scbib = new Scanner(bib);
	 	
	 	String se=null;
	 	
	 	String s=null;
	 	
	 	String title = null;
	 	
	 	while (scbib.hasNextLine()){
	 			
	 			s = scbib.nextLine();
	 	
	 		if (s.startsWith("title"))
	 			se = s;
	 	}
	 	int end = se.lastIndexOf('"');
	 	int start = se.indexOf('"');
	 	title = se.substring(start+1, end);
	 	scbib.close();
	 	return title;
	 	}
 	
 	
}
