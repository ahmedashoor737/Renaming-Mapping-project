import java.util.Queue;
import java.util.List;
import java.nio.file.Path;

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
	public List<Paper> batchRenaming(Path papersFolder) {

	}

	/**
	 * Renames the file and creates a Paper object
	 *
	 * @param fileName name of file in the papers folder
	 * @return an object of the renamed paper, null if renaming failed
	 */
	public static Paper renameFile(String fileName) {

	}

	/**
	 * Iterates over files whose renaming failed and handles them
	 * If file is chosen to be ignored, add to list of ignored files in settings
	 *
	 * @return A list Paper objects or renamed files
	 */
	public static List<Paper> handleOverlookedPapers() {

	}

	/**
	 * Manually rename a file with help of the user
	 *
	 * @param fileName name of file to be renamed
	 * @return A Paper object of the renamed file, null if it wasn't renamed
	 */
	private static Paper manuallyRenameFile(String fileName) {

	}

}
