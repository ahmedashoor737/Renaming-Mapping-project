import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import org.jbibtex.*;

/**
 * This class is reponsible for renaming files. It renames the file and make a Paper object with a BibItem of it.
 * If file fails to rename in batch operations it's added to a queue to be handled later. Currently those files are added to Settings.ignoredFiles.
 *
 * Create an instance for batch operations. Use static methods for one-time operations. Check for null when using static methods.
 * To avoid data loss: BibTexFinder should be closed as described in its documentation.
 */
public class PaperFileRenamer {
	private Queue<String> overlookedFiles;
	private BibTexFinder finder;
	private File papersFolder;

	public PaperFileRenamer(Path papersFolder, BibTexFinder finder) {
		this.papersFolder = papersFolder.toFile();
		this.finder = finder;
		overlookedFiles = new LinkedList<String>();
	}

	/**
	 * Iterates over all files in a folder and attempts to rename them
	 *
	 * @param papersFolder path of papers folder
	 * @return a list of Paper objects of renamed files
	 */
	public List<Paper> batchRenaming() {
		List<Paper> renamedPapers = new LinkedList<Paper>();
		String[] pdfFilesNames = papersFolder.list(new PDFFilesFilter());

		for (String fileName : pdfFilesNames) {
			Paper renamedPaper = renameFile(fileName);
			if (renamedPaper != null) {
				renamedPapers.add(renamedPaper);
			} else {
				overlookedFiles.add(fileName);
			}
		}

		//files in the queue are handled, some remain unhandled
		renamedPapers.addAll(handleOverlookedFiles());
		Settings.addIgnoredFiles(overlookedFiles);

		return renamedPapers;
	}

	/**
	 * Finds the title and the bibItem of the file, checks if the paper exists among references, generates the new file name, then return the matching Paper object.
	 *
	 * @param fileName name of file in the papers folder
	 * @return an object of the renamed paper, null if renaming failed
	 */
	public Paper renameFile(String fileName) {
		//is PDFReader static or provide static convenience methods?
		//simplest way to pass fileName to PDFReader
		String title = "2D and 3D Visualization of AspectJ Programs"; //PDFReader.findTitle(fileName);
		BibItem bibItem;	
		try {
			bibItem = finder.findByTitle(title);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ParseException pe) {
			pe.printStackTrace();
			return null;
		} catch (URISyntaxException urie) {
			urie.printStackTrace();
			return null;
		}

		Paper paper;
		paper = BibCase.removeFromReferences(bibItem);
		if (paper != null) {
			paper.setOriginalFileName(fileName);
		} else {
			paper = new Paper(bibItem, fileName);
		}

		try {
			String newFileName = paper.generateFileName();
			renameTo(fileName, newFileName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}

		return paper;
	}

	/**
	 * Static convenience method to rename a single file without having to deal with instances of PaperFileRenamer and BibTexFinder.
	 * Uses Settings.getFolderPath() and Settings.getBibFilePath().
	 * 
	 * @param fileName file to be renamed
	 * @return null if renaming failed, a Paper object otherwise
	 */
	public static Paper renameSingleFile(String fileName) {
		Path papersFolder = Settings.getFolderPath();
		BibTexFinder finder;
		try {
			finder = new BibTexFinder(Settings.getBibFilePath());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ParseException pe) {
			pe.printStackTrace();
			return null;
		}
		PaperFileRenamer renamer = new PaperFileRenamer(papersFolder, finder);

		Paper paper = renamer.renameFile(fileName);
		try {
			finder.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return paper;
	}

	private void renameTo(String oldName, String newName) throws IOException {
		Path oldPaper = new File(papersFolder, oldName).toPath();
		Path newPaper = new File(papersFolder, newName).toPath();
		Files.move(oldPaper, newPaper);
	}

	/**
	 * Doesn't do anything right now. Supposed to be:
	 * Iterates over files whose renaming failed and handles them
	 *
	 * @return A list Paper objects of renamed files
	 */
	private List<Paper> handleOverlookedFiles() {
		return new LinkedList<Paper>();
	}

	/**
	 * Doesn't do anything right now. Supposed to be:
	 * Manually rename a file with help of the user
	 *
	 * @param fileName name of file to be renamed
	 * @return A Paper object of the renamed file, null if it wasn't renamed
	 */
	private Paper manuallyRenameFile(String fileName) {
		return null;
	}

	/**
	 * A filter used in File.list(FilenameFilter) to select files ending with .pdf or .PDF
	 */
	public class PDFFilesFilter implements FilenameFilter {
		public boolean accept(File dir, String fileName) {
			return fileName.endsWith(".pdf") || fileName.endsWith(".PDF");
		}
	}
}
