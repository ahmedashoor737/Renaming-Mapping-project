import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;



public class PDFReader extends PDFTextStripper
{
	static float max = 0;
    static float size =0;

	List<String> fileAttributes ;
	List<String> refernces ;

	 PDDocument paper;
	 PDFTextStripper strip ;
	 PDDocumentInformation info ;
	LinkedList<Position> lPositions ;

	PDFReader()throws IOException
	{
		this.lPositions = new LinkedList<Position>();
	}

	public  String title (File fileLocation) throws IOException //changed string filename to File filelocation
	{
		strip =new PDFTextStripper(){protected void writeString(String text, List<TextPosition> textPositions) throws IOException
	    {

	        //find font size of first position
	        Position p = new Position(textPositions);
	        //store list with its font size

	        lPositions.add(p);

	        }};
		strip.setSortByPosition(true);
		strip.setStartPage(1);
		strip.setEndPage(1);

		paper = PDDocument.load(fileLocation);
		info = 	paper.getDocumentInformation();

		strip.getText(paper);

		String title =findmax(lPositions);


		paper.close();

		return title;





	}

	public String getFileAttributes (File fileLocation) throws IOException//changed string filename to File
	{
		BasicFileAttributes attr = Files.readAttributes(fileLocation.toPath(), BasicFileAttributes.class);

		paper = PDDocument.load(fileLocation);
		info = 	paper.getDocumentInformation();
		return attr.creationTime().toString().substring(0, attr.creationTime().toString().indexOf('T')); //this return creation time
	}




    String findmax(LinkedList<Position> lPositions )
    {
    	
    	Position max = lPositions.element();
    	for (Position lposition : lPositions)
    	{
    		if (lposition.compareTo(max)>0)
    			max = lposition;
    	}


    	return max.toString();
    }


    private class Position implements Comparable<Position> {

    	List<TextPosition> position;
    	private float size;



    	Position(List<TextPosition> position)
    	{
    		this.position =position;

    	}


    	public float getSize() {
    		return this.position.get(0).getFontSizeInPt();
    	}


    	public void setSize(float size) {
    		this.size = position.get(0).getFontSizeInPt();
    	}


    	@Override
    	public int compareTo(Position other) {
    		if (this.getSize() < other.getSize())
    			return -1;
    		else if(this.getSize() == other.getSize())
    			return 0;
    		else
    			return 1;

    	}
    	public String toString()
    	{
    		StringBuilder title = new StringBuilder();
    		StringBuilder builder = new StringBuilder();

    		for (TextPosition pos : position)
    			builder.append(pos.getCharacter());

    		String titleWithWhiteSpaces = builder.toString();
    		StringTokenizer tk =new StringTokenizer(titleWithWhiteSpaces);

    		while(tk.hasMoreTokens() ){

    			title.append(tk.nextToken() + " ");

    		}

    		return title.toString();
    	}
    }


	public static String findTitle(File fileLocation)throws IOException
	{

			PDFReader title = new PDFReader();
			String titlef = title.title(fileLocation);

			return titlef;


	}

	static List<String> findReferences(File fileLocation) throws IOException
	{
		PDFTextStripper strip = new PDFTextStripper();
		PDDocument paper = PDDocument.load(new File(fileLocation));

		for(int i = paper.getNumberOfPages();i!=0;i--)
		{
			strip.setStartPage(i);
			strip.setEndPage(i);
			if (strip.getText(paper).toLowerCase().contains("references"))
				break;

		}
		String refPage = strip.getText(paper).toLowerCase();
		String references = refPage.substring(refPage.indexOf("references")+10);
		Scanner sc = new Scanner(references);
		LinkedList<String> lRef = new LinkedList<String>() ;
		int i =0;
		while(sc.hasNextLine())
		{
				lRef.add(sc.nextLine());
				i++;
		}
		return lRef;
	}


}
