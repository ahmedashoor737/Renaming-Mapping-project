import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.Writer;
import java.io.IOException;
import org.jbibtex.*;
import java.util.List;

/**
 * Represents a .bib file and provides methods to handle entries in the file.
 *
 * Use static methods for one-time operations on file. Create a BibFile for batch operations.
 * To use a BibFile object after passing the .bib file path: loadDatabaseFromFile(), perform operations, then writeDatabaseToFile()
 */
public class BibFile {
	private Path filePath;
	private BibTeXDatabase bibTexDatabase;
	private boolean loadedDatabase = false;

	public BibFile(Path filePath) throws IOException {
		this.filePath = filePath;
	}

	/**
	 * Reads the content of the file and puts it in a BibTeXDatabase
	 *
	 * The method can only be called once per instance and has no effect if called multiple times.
	 */
	public void loadDatabaseFromFile() throws IOException, ParseException {
		if (!loadedDatabase) {
			Reader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
			BibTeXParser bibTeXParser = new BibTeXParser();
			this.bibTexDatabase = bibTeXParser.parse(reader);
			loadedDatabase = true;
			reader.close();
		}
	}

	/**
	 * Writes the BibTeXDatabase to the file.
	 */
	public void writeDatabaseToFile() throws IOException {
		Writer writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8);
		BibTeXFormatter formatter = new BibTeXFormatter();
		formatter.format(bibTexDatabase, writer);
		writer.close();
	}

	//find by other
	/**
	 * Looks for an entry in the database using the given title.
	 *
	 * @param title of the paper
	 * @return null if not found, a matching BibTeXEntry otherwise
	 */
	public BibTeXEntry findBibItemByTitle(String title) {
		List<BibTeXObject> objects = bibTexDatabase.getObjects();
		
		for (BibTeXObject object : objects) {
			if (object instanceof BibTeXEntry) {
				BibTeXEntry entry = (BibTeXEntry)object;
				if (entry.getField(BibTeXEntry.KEY_TITLE).toUserString().equalsIgnoreCase(title)) {
					return entry;
				}
			}
		}

		return null;
	}

	/**
	 * Creates a BibFile, loadDatabaseFromFile(), then findBibItemByTitle(String).
	 *
	 * @param title of the paper
	 * @param bibFilePath the .bib file to search in
	 * @return null if not found, a matching BibTeXEntry otherwise
	 */
	public static BibTeXEntry findBibItemByTitle(String title, Path bibFilePath) {
		BibTeXEntry entry = null;

		try {
			BibFile bibFile = new BibFile(bibFilePath);
			bibFile.loadDatabaseFromFile();
			entry = bibFile.findBibItemByTitle(title);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ParseException pe) {
			pe.printStackTrace();
		}

		return entry;
	}

	/**
	 * Adds an entry to the database
	 *
	 * @param entry to be added
	 */
	public void addBibTeXEntry(BibTeXEntry entry) {
		bibTexDatabase.addObject(entry);
	}

	/**
	 * Transform the given String to BibTeXEntry and adds it to the database by addBibTeXEntry(BibTeXEntry)
	 *
	 * @param bibItemString representation of a BibTeX entry
	 */
	public void addBibTeXEntry(String bibItemString) throws ParseException {
		addBibTeXEntry(BibItem.stringToBibTeXEntry(bibItemString));
	}

	/**
	 * Creates a BibFile, loads its content, adds the entry, then write changes to file
	 *
	 * @entry to be added
	 * @bibFilePath to be added to
	 */
	public static void addBibItem(BibTeXEntry entry, Path bibFilePath) throws IOException, ParseException {
		BibFile bibFile = new BibFile(bibFilePath);
		bibFile.loadDatabaseFromFile();
		bibFile.addBibTeXEntry(entry);
		bibFile.writeDatabaseToFile();
	}

	/**
	 * Transform the given String to BibTeXEntry and adds it to the database by addBibItem(BibTeXEntry, Path)
	 *
	 * @param bibItemString representation of the entry to be added
	 * @param bibFilePath to add to
	 */
	public static void addBibItem(String bibItemString, Path bibFilePath) throws IOException, ParseException {
		BibFile.addBibItem(BibItem.stringToBibTeXEntry(bibItemString), bibFilePath);
	}

	/**
	 * Remove/Change
	 */
}
