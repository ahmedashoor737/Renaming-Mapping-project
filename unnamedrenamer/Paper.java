import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

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
	 * @param originalFileName name of file
	 * @param title paper's title
	 */
	public Paper(String originalFileName, String title) {
            this.originalFileName = originalFileName;
            this.title = title;
            year = ; // the published year!?
	}

	/**
	 * Creates a paper that doesn't have a corresponding file and finds its information.
	 *
	 * @param citation the citation containing the paper information
	 */
	public Paper(String citation) {

	}

	/**
	 * Find references of paper in PDF file then finds or creates Paper objects.
	 * Adds Paper objects to list of references and adds this Paper to list of referencing papers of that object.
	 *
	 * @return false if paper doesn't have a file or has already been populated, true otherwise
	 */
	public boolean populateReferences() {
		return true;
	}

	/**
	 * Adds a Paper that references this Paper to its list of referencingPapers.
	 *
	 * @param referencingPaper a paper that references this paper
	 */
	private void addReferencingPaper(Paper referencingPaper) {

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
	public String generateFileName() throws IOException {
            String theCounter = String.format("%04d",BibCase.lengthOfExistingPapers()); 
            String creationYear = getCreationDate();
            String theGenerateFileName = String.format("%s    %s %s %s",theCounter,creationYear,title,year);
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

	/**
	 * Shows whether the Paper is linked with an existing file or not
	 *
	 * @return whether Paper has file of not
	 */
	public boolean isExistingPaper() {
		return true;
	}
        /**
	 * Shows whether the Paper is renamed or not
	 *
	 * @return whether Paper has file of not
	 */
        public boolean isRenamed() {
            
            if(!fileName.isEmpty())
                return true;
            return false;
        }
        
}
