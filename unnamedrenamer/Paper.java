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
	public String generateFileName() {
		return "";
	}

	/**
	 * Find the creation date of the file linked with the paper.
	 *
	 * @return creation date of file, or null if Paper has no file
	 */
	private String getCreationDate() {
		return "";
	}

	/**
	 * Shows whether the Paper is linked with an existing file or not
	 *
	 * @return whether Paper has file of not
	 */
	public boolean isExistingPaper() {
		return true;
	}
}
