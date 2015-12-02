import java.util.List;

/**
 * The entry class of BibCase. Initializes the system parts including settings and GUI.
 * Keeps monitering the papers folder for changes and keeps a list of Paper objects.
 */
public class BibCase {
	//might change List since we need to search
	private static List<Paper> existingPapers;
	private static List<Paper> referencedNonExistingPapers;

	public static void main(String[] args) {
		test();
		//entry point

		//configuration (settings)
		//send papers to be renamed
		//populateReferences of existingPapers
		//generate reference visualization
		//keep monitering folder (add->rename, remove->handle)
	}

	public static void test() {
		BibTexFinder.findBibItemByTitle("2d and 3d visualizations of aspectj programs", new Paper(""));
	}

	/**
	 * Add/remove/find paper methods
	 */
}
