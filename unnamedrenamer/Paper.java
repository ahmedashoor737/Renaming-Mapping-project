import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.ListIterator;

/**
 * The class defines a research paper. The paper could or could not exist as a file in the system.
 */
public class Paper {
	private String title;
	private String year;
	private String fileName;
	private String originalFileName;
	private BibItem bibItem;
	private List<Paper> references;
	private List<Paper> referencingPapers;
	private List<Note> notes;

	/**
	 * Creates a paper that has a corresponding file.
	 *
         * @param bibItem 
	 * @param originalFileName name of file
	 * 
	 */
	public Paper(BibItem bibItem,String originalFileName) {
            this.bibItem = bibItem;
            this.originalFileName = originalFileName;
            year = bibItem.getValue("Year");
            
            
	}

	
        
	public Paper(BibItem bibItem) {
            this.bibItem = bibItem;
            year = bibItem.getValue("Year");

	}

	/**
	 * Find references of paper in PDF file then finds or creates Paper objects.
	 * Adds Paper objects to list of references and adds this Paper to list of referencing papers of that object.
	 *
	 * @return false if paper doesn't have a file or has already been populated, true otherwise
	 */
	public boolean populateReferences() {
		return true;
		/*
		List<String> citations = pdfreader.getReferences()
		BibTexFinder finder = ..
		//finds bibitems for each citation (try-catch exceptions)
		//looks for a matching Paper object in BibCase's referencedNonExistingPapers or existingPapers
		//Paper reference;
		  if found in existingPapers
		    reference = found paper
		  if found in referencedNonExistingPapers
		    reference = found paper
		  if not found
		    pass bibitem to new Paper object
		//add reference to this.references
		//add this paper to reference's referencingPapers
		*/
	}

	/**
	 * Adds a Paper that references this Paper to its list of referencingPapers.
	 *
	 * @param referencingPaper a paper that references this paper
	 */
	private void addReferencingPaper(Paper referencingPaper) {
            boolean found = false;
            ListIterator<Paper> searchIterator = referencingPapers.listIterator();
            while (searchIterator.hasNext() && !found) {
                 Paper  paper = searchIterator.next();
                 if(paper == referencingPaper){
                    found =true;
                }
            }
            if(!found){
            referencingPapers.add(referencingPaper);
            }

	}

	/**
	 * Shows whether references were populated before or not
	 *
	 * @return whether references were populated
	 */
	private boolean referencesArePopulated() {
		return true;
	}

	/**
	 * Generates a new file name depending on chosen naming format. Finds needed information for renaming.
	 * Stores the generated name in fileName.
	 * 
	 * @return the new file name, or null if Paper has no file
	 */
	public String generateFileName() throws IOException  {
            String theCounter = String.format("%04d",BibCase.lengthOfExistingPapers()); 
            String creationYear = getCreationDate();
            String theGenerateFileName = String.format("%s    %s %s %s.pdf",theCounter,creationYear,title,year);
            fileName = theGenerateFileName;
            return theGenerateFileName;
	}

	/**
	 * Find the creation date of the file linked with the paper.
	 *
	 * @return creation date of file, or null if Paper has no file
	 */
	private String getCreationDate() throws IOException {
           
            Path file = Paths.get(originalFileName);
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            String creationTime = attr.creationTime()+"";
            String creationDate = creationTime.substring(0,10);

             return creationDate;

	}
        public void setoriginalFileName (String originalFileName){
            this.originalFileName=originalFileName;
        }

	/**
	 * Shows whether the Paper is linked with an existing file or not
	 *
	 * @return whether Paper has file of not
	 */
        
	public boolean isExistingPaper() {
		return originalFileName!=null;
	}
        
        /**
	 * Shows whether the Paper is renamed or not
	 *
	 * @return whether Paper has file of not
	 */
        public boolean isRenamed() {
            
            return !fileName.isEmpty();
        }
        
}