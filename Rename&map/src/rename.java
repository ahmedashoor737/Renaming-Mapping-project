import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class rename {
	
	
	public  void  getCreationDate() throws IOException { 
// This code is to get the creation date of a file
			
			Path path = Paths.get("");
		    
			BasicFileAttributes attr;
		    
		    try {
		    
		    attr = Files.readAttributes(path, BasicFileAttributes.class);
		    
		    FileTime date = attr.creationTime();
		    
		    String getridofT = date.toString();
		    
		    int k = getridofT.indexOf("T");
		    
		    String creationdate = getridofT.substring(0, k);
		    
		    System.out.println("Creation date: " + creationdate);

		    } catch (IOException e) {
		    
		    	System.out.println("oops un error! " + e.getMessage());
		    }
		  }
}
