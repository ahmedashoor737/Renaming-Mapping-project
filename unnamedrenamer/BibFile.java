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

	public void loadDatabaseFromFile() throws IOException, ParseException {
		if (!loadedDatabase) {
			Reader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
			BibTeXParser bibTeXParser = new BibTeXParser();
			this.bibTexDatabase = bibTeXParser.parse(reader);
			loadedDatabase = true;
			reader.close();
		}
	}

	public void writeDatabaseToFile() throws IOException {
		Writer writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8);
		BibTeXFormatter formatter = new BibTeXFormatter();
		formatter.format(bibTexDatabase, writer);
		writer.close();
	}

	//find by other
	public BibTeXEntry findBibItemByTitle(String title) {
		List<BibTeXObject> objects = bibTexDatabase.getObjects();
		
		for (BibTeXObject object : objects) {
			if (object instanceof BibTeXEntry) {
				BibTeXEntry entry = (BibTeXEntry)object;
				if (entry.getField(BibTeXEntry.KEY_TITLE).toUserString().equals(title)) {
					return entry;
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param title title of paper
	 * @param bibFilePath path to the bib file
	 * @return bib item for this paper
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

	public void addBibTeXEntry(BibTeXEntry entry) {
		bibTexDatabase.addObject(entry);
	}

	public void addBibTeXEntry(String bibItemString) throws ParseException {
		addBibTeXEntry(BibItem.stringToBibTeXEntry(bibItemString));
	}

	public static void addBibItem(BibTeXEntry entry, Path bibFilePath) throws IOException, ParseException {
		BibFile bibFile = new BibFile(bibFilePath);
		bibFile.loadDatabaseFromFile();
		bibFile.addBibTeXEntry(entry);
		bibFile.writeDatabaseToFile();
	}

	/**
	 * 
	 * @param bibItem bib item of paper
	 * @param bibFilePath path to bib file
	 * @return bib item 
	 */
	public static void addBibItem(String bibItemString, Path bibFilePath) throws IOException, ParseException {
		BibFile.addBibItem(BibItem.stringToBibTeXEntry(bibItemString), bibFilePath);
	}

	//what parameter (BibItem, BibTexEntry, String of bibitem or a field/key)
	//exchange (old, new)
	public boolean removeBibItem() {
		return false;
	}

	public static boolean removeBibItem(Path bibFilePath) {
		//create, load, write
		return false;
	}
}
