import java.util.Queue;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;

/**
 * This class is reponsible for renaming files. It renames the file and make a Paper object of it.
 * If file fails to rename it adds to a list to be handles later.
 * If user choose to ignore and not handle a file, then it's added to a list of ignored files in settings.
 */
public class PaperFileRenamer {
	private static Queue<String> overlookedFiles;

	/**
	 * No objects are created
	 */
	private PaperFileRenamer() {}

	/**
	 * Iterates over all files in a folder and attempts to rename them
	 *
	 * @param papersFolder path of papers folder
	 * @return a list of Paper objects of renamed files
	 */
	public static List<Paper> batchRenaming(Path papersFolder) {
		List<Paper> renamedPapers = new LinkedList<Paper>;

		//for all .pdf files
		  //rename file
		  //if return null, add to overlooked
		  //else add to list
		//handle overlooked
		  //if renamed add to list
		  //list add to settings.ignored
		//return list
	}

	/**
	 * Renames the file and creates a Paper object
	 *
	 * @param fileName name of file in the papers folder
	 * @return an object of the renamed paper, null if renaming failed
	 */
	public static Paper renameFile(String fileName) {
		/*
		//is PDFReader static or provide static convenience methods?
		//simplest way to pass fileName to PDFReader
		String title = PDFReader.findTitle(fileName);
		Paper paper = new Paper(fileName, title);
		//paper finds the bibitem
		String newFileName = paper.generateFileName();
		//fileName,originalFileName are appropiately changed in paper
		//actual file renaming
		renameTo(fileName, newFileName);
		return paper;
		*/
		try {
			renameTo(fileName, "renamed"+fileName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
		return new Paper("");
	}

	//throws?
	private static void renameTo(String oldName, String newName) throws IOException {
		File papersFolder = Settings.getFolderPath().toFile();
		Path oldPaper = new File(papersFolder, oldName).toPath();
		Path newPaper = new File(papersFolder, newName).toPath();
		Files.move(oldPaper, newPaper);
	}

	/**
	 * Iterates over files whose renaming failed and handles them
	 * If file is chosen to be ignored, add to list of ignored files in settings
	 *
	 * @return A list Paper objects or renamed files
	 */
	public static List<Paper> handleOverlookedPapers() {
		return null;
	}

	/**
	 * Manually rename a file with help of the user
	 *
	 * @param fileName name of file to be renamed
	 * @return A Paper object of the renamed file, null if it wasn't renamed
	 */
	private static Paper manuallyRenameFile(String fileName) {
		return null;
	}

}
