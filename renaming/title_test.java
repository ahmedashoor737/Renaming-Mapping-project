import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

class title_test {
public static void main(String [] arg) throws IOException{
		
		PDDocument pdf = PDDocument.load("H:\\Downloads\\1-s2.0-S0166864112002143-main.pdf");
		PDFTextStripper stripper = new PDFTextStripper() {
		    float prevsize = 0;
		    
		    
		    protected void writeString(String text, List<TextPosition> textPositions) throws IOException
		    {
		    	StringBuilder result = new StringBuilder();
		        StringBuilder builder1 = new StringBuilder();
		        StringBuilder builder2 = new StringBuilder();
		        StringBuilder builder3 = new StringBuilder();
		        float size1 =0 ;
		        float size2 =0 ;
		        float size3 =0 ;
		        for (TextPosition position : textPositions)
		        {
		             size1 =position.getFontSizeInPt();
		           
		            if (size1 >= prevsize &&position.getY()>0 && position.getY()<150)
		            {
		            	if (!position.getCharacter().equals("\n"))
		                builder1.append(position.getCharacter());
		                prevsize = size1;
		            }
		        }
		       
		        for (TextPosition position : textPositions)
		        {
		            size2 = position.getFontSizeInPt();
		            
		            if (size2 >= prevsize &&position.getY()>150 && position.getY()<380)
		            {
		            	if (!position.getCharacter().equals("\n"))
		                builder2.append(position.getCharacter());
		                prevsize = size2;
		            }
		        }
		        for (TextPosition position : textPositions)
		        {
		             size3 = position.getFontSizeInPt();
		           
		            if (size3 >= prevsize &&position.getY()>381 && position.getY()<550)
		            {
		            	if (!position.getCharacter().equals("\n"))
		                builder3.append(position.getCharacter());
		                prevsize = size3;
		            }
		        }
		        if (size2>=size1 && size2>=size3)
		        	result = builder2;
		        	
		        else if (size1>size2 && size1>=size3)
		        	result = builder1;
		        else
		        	result = builder3;
		        writeString(result.toString());
		    }
		    
		    
		};
		stripper.setSortByPosition(true);
		stripper.setStartPage(1);
		stripper.setEndPage(1);
		
		String s = stripper.getText(pdf);
		s.replaceAll("\\s+", "");
		
		System.out.println(s.trim());
	}
}