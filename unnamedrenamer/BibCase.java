import java.util.List;
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
	private static List<Paper> existingPapers;
	private static List<Paper> referencedNonExistingPapers;

	public static void main(String[] args) {
		test();
		//entry point

		//configuration (settings) - copy of settings, copy of bibfile
		//send papers to be renamed
		//populateReferences of existingPapers
		//generate reference visualization
		//keep monitering folder (add->rename, remove->handle)
	}

	public static void test() {
		boolean pathIsSet = Settings.setBibFilePath(Paths.get("samples", "mybib.bib"));
		BibItem item = new BibItem(BibFile.findBibItemByTitle("A small paper", Settings.getBibFilePath()));
		System.out.println(item.getValue(BibTeXEntry.KEY_AUTHOR));
		System.out.println(item.getValue("AUTHOR"));
		System.out.println(item.getValue("author"));
		System.out.println("DOI: " + item.getValue("doi"));
		System.out.println(item.getBibKey());
		System.out.println(item.getType());


		//getValue(key)
		//getValue(string)
		//getBibKey()

		/*try {
			if (pathIsSet) {
				BibTexFinder finder = new BibTexFinder(Settings.getBibFilePath());
				
				//opening multiple instances of bibfile could mean losing data
				finder.findBibItemByTitle("A big paper");
				finder.findBibItemByCitation("Wilson, S. and McDermid, J. (1995), Integrated analysis of complex safety critical systems, The Computer Journal, 1995.");
				finder.close();

				BibTexFinder.findByTitle("2d and 3d visualizations of aspectj programs");
				BibTexFinder.findByCitation("Takang, A. and Grubb, R. (1996), Software maintenance, Concepts and Practice, Thompson, London, 1996.");
			} else {
				System.out.println(pathIsSet);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ParseException pe) {
			pe.printStackTrace();
		}*/
	}

	/**
	 * Add/remove/find paper methods
	 */
}
