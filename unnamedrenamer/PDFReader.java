
import java.util.List;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;



public class PDFReader extends PDFTextStripper
{
	public PDFReader() throws IOException {
		super();
		
	}


	List<String> fileAttributes ;
	List<String> refernces ;
	
	PDDocument paper;
	PDFTextStripper strip ;
	PDDocumentInformation info ;
	
	
	
	public String findTitle ( File fileLocation ) throws IOException //changed string filename to File filelocation
	{
		
		strip.setSortByPosition(true);
		strip.setStartPage(1);
		strip.setEndPage(1);
		
		paper = PDDocument.load(fileLocation);
		info = 	paper.getDocumentInformation();

		String titleWithWhiteSpaces = strip.getText(paper);
		StringBuilder title = null;
		StringTokenizer tk =new StringTokenizer(titleWithWhiteSpaces);
		while(tk.hasMoreTokens() ){
			   
			title.append(tk.nextToken() + " ");
			   }
		
		if (title.toString().isEmpty() || title.toString() == null)
			title.append(info.getTitle());
		
		
		
		
		return title.toString();
		
		
		
			
		
	}
	
	public String getFileAttributes (File fileLocation) throws IOException//changed string filename to File 
	{
		BasicFileAttributes attr = Files.readAttributes(fileLocation.toPath(), BasicFileAttributes.class);
		
		paper = PDDocument.load(fileLocation);
		info = 	paper.getDocumentInformation();
		return attr.creationTime().toString().substring(0, attr.creationTime().toString().indexOf('T')); //this return creation time 
		//return info.getCreationDate().getTime().toString().substring(0, 10); this is provided by pdfbox api(different format) 
	}
	
	
	public List<Paper> extractReferences (File fileLocation)//changed string filename to File filelocation
	{
		return null ;//leaving this to cermine
	}
	
	
	
	
	
	
	
	//override the text extracting method of PDFTextStripper 
	 protected void writeString(String text, List<TextPosition> textPositions) throws IOException
	    {
	        StringBuilder builder = new StringBuilder();

	        for (TextPosition position : textPositions)
	        {
	            
	             
	            if (position.getFontSizeInPt() >=13 &&position.getY()>0 && position.getY()<500)
	            {
	                builder.append(position.getCharacter());
	            }
	             
	            
	        }
	        
	       writeString(builder.toString());
	        
	    }

}
