import java.util.Map;
import java.util.List;

/**
 * Visualizes the existing papers and their references in accordance with the year of publishing
 */
public class ReferencesVisualizer {
	private Map<String, List<Paper>> yearsAndPapers;

	/**
	 * Creates an instance that visualizes the given list of papers and their references
	 *
	 * @param existingPapers list of papers that have files
	 */
	public ReferencesVisualizer(List<Paper> existingPapers) {

	}

	/**
	 * Adds a single paper to the list under its year
	 *
	 * @paper paper the paper to be added
	 */
	public void addPaper(Paper paper) {

	}

	/**
	 * Removes a single paper. If the list under the paper's year become empty, remove it as well.
	 *
	 * @param paper the paper to be removed
	 * @return true if the paper was found and removed, false otherwise
	 */
	public boolean removePaper(Paper paper) {

	}
}
