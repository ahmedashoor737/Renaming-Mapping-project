class title test{
public static void main(String [] arg) throws IOException{
		
		PDDocument pdf = PDDocument.load(\\put file);
		PDFTextStripper stripper = new PDFTextStripper() {
		    float prevsize = 0;
		    
		    protected void writeLineSeparator() throws IOException
		    {
		        super.writeLineSeparator();
		    }
		    protected void writeString(String text, List<TextPosition> textPositions) throws IOException
		    {
		    	
		        StringBuilder builder = new StringBuilder();

		        for (TextPosition position : textPositions)
		        {
		            float size = position.getFontSizeInPt();
		            if (size >= prevsize &&position.getY()>150)
		            {
		                builder.append(position.getCharacter());
		                prevsize = size;
		            }
		        }
		        
		        writeString(builder.toString());
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