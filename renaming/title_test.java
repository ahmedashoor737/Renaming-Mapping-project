import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

class title_test {
public static void main(String [] arg) throws IOException{
		
		PDDocument pdf = PDDocument.load("H:\\Downloads\\00794787 (1).pdf");
		PDFTextStripper stripper = new PDFTextStripper() {
		    float prevsize = 0;
		    
		    
		    protected void writeString(String text, List<TextPosition> textPositions) throws IOException
		    {
		        StringBuilder builder1 = new StringBuilder();
		        StringBuilder builder2 = new StringBuilder();
		        float size1 =0 ;
		        float size2 =0 ;
		     
		        for (TextPosition position : textPositions)
		        {
		             size1 = position.getFontSizeInPt();
		             size2 = position.getFontSizeInPt();
		            if (size1 >= prevsize &&position.getY()>0 && position.getY()<150)
		            {
		                builder1.append(position.getCharacter());
		                prevsize = size1;
		            }
		             if (size2 >= prevsize &&position.getY()>150 && position.getY()<400)
		            {
		                builder2.append(position.getCharacter());
		                prevsize = size2;
		            }
		         
		        }
		       String s = null;
		        
		       if (size1>=size2)
		        	s =builder1.toString();
		        	
		         if (size2>size1)
		        	s = builder2.toString();
		       
		        writeString(s);
		        
		    }
		    
		    
		};
		stripper.setSortByPosition(true);
		stripper.setStartPage(1);
		stripper.setEndPage(1);
		String s = stripper.getText(pdf);
		   StringTokenizer tk =new StringTokenizer(s);
		   while(tk.hasMoreTokens() ){
			   
		System.out.print(tk.nextToken() + " ");
		   }
		   
	}
}