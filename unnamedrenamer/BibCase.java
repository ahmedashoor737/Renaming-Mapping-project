import java.util.Collection;
import java.util.Set;
import java.util.LinkedHashSet;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jbibtex.*;

/**
 * The entry class of BibCase. Initializes the system parts including settings and GUI.
 * Keeps monitering the papers folder for changes and keeps a list of Paper objects.
 */
public class BibCase {
	//might change List since we need to search
	//integrate API?
	//packages

	//existing papers are papers with a pdf file
	public static int numberOfRenamedPapers = 0;
	private static Set<Paper> existingPapers = new LinkedHashSet<Paper>();
	//referenced non existing papers are papers that were references but have no pdf file
	private static Set<Paper> referencedNonExistingPapers = new LinkedHashSet<Paper>();
	//using hash set for more efficent remove(Paper) method?
	
	public static void main(String[] args) {
		configure();

		new BibCaseGUI();
		
		initialize();

		try {
			WatchDir.monitorFolder(Settings.getFolderPath());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			//do something
		}

		//gui > Settings > Notes > Visualization
	}

	public static void initialize() {
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
		File workingDirectory = new File(System.getProperty("user.dir"));
		
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setCurrentDirectory(workingDirectory);
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int folderValue = folderChooser.showOpenDialog(null);
		if (folderValue == JFileChooser.APPROVE_OPTION) {
			File folder = folderChooser.getSelectedFile();
			Settings.setFolderPath(folder.toPath());
		} else {
			//fallback
		}


		JFileChooser bibChooser = new JFileChooser();
		bibChooser.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("BibTeX database", "bib");
		bibChooser.setFileFilter(filter);
		int fileValue = bibChooser.showOpenDialog(null);
		if (fileValue == JFileChooser.APPROVE_OPTION) {
    		File bibFile = bibChooser.getSelectedFile();
			Settings.setBibFilePath(bibFile.toPath());
		}

		//Settings.setBibFilePath(Paths.get("samples", "mybib.bib"));
		//Settings.setFolderPath(Paths.get("samples"));
		//load settings
		//choose folder and .bib file
	}

	/**
	 * Add/remove/find paper methods
	 */
	public static void addToExistingPapers(Paper newPaper) {
		existingPapers.add(newPaper);
	}

	public static Paper findPaperinExistingPapers(BibItem bibItem) {
		return searchCollectionByBibItem(existingPapers, bibItem);
	}

	public static Paper findPaperInReferences(BibItem bibItem) {
		return searchCollectionByBibItem(referencedNonExistingPapers, bibItem);
	}

	public static Paper removeFromExistingPapers(BibItem bibItem) {
		return removeFromCollection(existingPapers, bibItem);
	}

	public static Paper removeFromReferences(BibItem bibItem) {
		return removeFromCollection(referencedNonExistingPapers, bibItem);
	}

	/**
	 * I can't just remove(new Paper(BibItem)). I need a reference to the original Paper object because that's what is referenced by other Paper objects.
	 */
	public static Paper removeFromCollection(Collection<Paper> paperCollection, BibItem bibItem) {
		Paper paper = searchCollectionByBibItem(paperCollection, bibItem);
		if (paper != null) {
			paperCollection.remove(paper);
		}
		return paper;
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

	public static int lengthOfExistingPapers() {
		//return existingPapers.size();
		return numberOfRenamedPapers;
	}
}
