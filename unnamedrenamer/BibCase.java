import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.nio.file.Paths;
import java.io.IOException;
import org.jbibtex.*;

/**
 * The entry class of BibCase. Initializes the system parts including settings and GUI.
 * Keeps monitering the papers folder for changes and keeps a list of Paper objects.
 */
public class BibCase {
	//might change List since we need to search
	//integrate API?
	//packages

	private static List<Paper> existingPapers = new LinkedList<Paper>();
	private static List<Paper> referencedNonExistingPapers = new LinkedList<Paper>();

	public static void main(String[] args) {
		initialize();
		
		//monitorFolder(Settings.getFolderPath());

		//gui > Settings > Notes > Visualization
		//keep monitering folder (add->rename-visualize, remove->handle)
	}

	public static void initialize() {
		configure();

		try {
			BibTexFinder finder = new BibTexFinder(Settings.getBibFilePath());
			PaperFileRenamer renamer = new PaperFileRenamer(Settings.getFolderPath(), finder);
			existingPapers.addAll(renamer.batchRenaming());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			//do something
		} catch (ParseException pe) {
			pe.printStackTrace();
			//do something
		}

		for (Paper paper : existingPapers) {
			paper.populateReferences();
		}

		//generate references visualization
	}

	public static void configure() {
		Settings.setBibFilePath(Paths.get("samples", "mybib.bib"));
		Settings.setFolderPath(Paths.get("samples"));
		//load settings
		//choose folder and .bib file
	}

	/**
	 * Add/remove/find paper methods
	 */
	public static Paper findPaperinExistingPaper(String title) {
		return searchCollectionByTitle(existingPapers, title);
	}

	public static Paper findPaperInReferences(String title) {
		return searchCollectionByTitle(referencedNonExistingPapers, title);
	}

	public static Paper findPaperinExistingPaper(BibItem bibItem) {
		return searchCollectionByBibItem(existingPapers, bibItem);
	}

	public static Paper findPaperInReferences(BibItem bibItem) {
		return searchCollectionByBibItem(referencedNonExistingPapers, bibItem);
	}

	private static Paper searchCollectionByBibItem(Collection<Paper> paperCollection, BibItem bibItem) {
		Paper target = new Paper(bibItem);
		for (Paper paper : paperCollection) {
			if (paper.equals(target)) {
				return paper;
			}
		}

		return null;
	}

	private static Paper searchCollectionByTitle(Collection<Paper> paperCollection, String title) {
		for (Paper paper : paperCollection) {
			if (paper.getTitle().equalsIgnoreCase(title)) {
				return paper;
			}
		}

		return null;	
	}

	public static int lengthOfExistingPapers() {
		return existingPapers.size();
	}
}
