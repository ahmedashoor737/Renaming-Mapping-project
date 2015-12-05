
import java.util.List;
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



public class PDFReader 
{
	List<String> fileAttributes ;
	List<String> refernces ;
	
	PDDocument paper;
	PDFTextStripper strip = new PDFTextStripper();
	PDDocumentInformation info ;
	
	
	
	public String findTitle ( File fileLocation )
	{
		paper = PDDocument.load(fileLocation);
		info = 	paper.getDocumentInformation();

		if (info.getTitle() == null) //still working on algorithm to handle not founding title
		else	
		return info.getTitle().trim(); //function provided by api to get title
	}
	
	public String getFileAttributes (File fileLocation)
	{
		BasicFileAttributes attr = Files.readAttributes(fileLocation.getPath(), BasicFileAttributes.class);
		
		paper = PDDocument.load(fileLocation);
		info = 	paper.getDocumentInformation();
		return attr.creationTime().toString().substring(0, attr.creationTime().toString().indexOf('T')); //this return creation time 
		//return info.getCreationDate().getTime().toString().substring(0, 10); this is provided by pdfbox api(different format) 
	}
	
	
	public List<Paper> extractReferences (File fileLocation)
	{
		return null ;//still working on a way to read pdf file without changing format and a way to strip the region of references
	}

}
