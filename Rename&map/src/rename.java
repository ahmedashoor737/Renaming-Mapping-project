import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class rename {
	
	public static void main(String[] arg) throws IOException
	{
		System.out.println(getCreationDate("")); // this is for testing only
	}
	public static  String  getCreationDate(String location) throws IOException { 
// This code is to get the creation date of a file
			
			Path path = Paths.get(location);
		    
			BasicFileAttributes attr;
		    
		    try {
		    
		    attr = Files.readAttributes(path, BasicFileAttributes.class);
		    
		    FileTime date = attr.creationTime();
		    
		    String getridofT = date.toString();
		    
		    int k = getridofT.indexOf("T");
		    
		    String creationdate = getridofT.substring(0, k);
		    
		    return (creationdate);
		    } catch (IOException e) {
		    
		    	System.out.println("oops un error! " + e.getMessage());
		    }
			return null;

		  }
}
