import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.io.StringWriter;
import java.io.IOException;
import java.util.List;
import org.jbibtex.*;

/**
 * Represents a BibTeX entry. Most methods are adaptations of BibTeXEntry in JBibTeX.
 * Can be created by BibTeXEntry or a String. Methods to transform one to the other.
 */
public class BibItem {
	private BibTeXEntry entry;

	/**
	 * an empty constructor
	 */
	public BibItem(){}

	public BibItem(BibTeXEntry entry) {
		this.entry = entry;
	}

	/**
	 * Parses the given String as BibTeXEntry and calls BibItem(BibTeXEntry)
	 *
	 * @param entryAsString a String representation of the BibTeX entry
	 */
	public BibItem(String entryAsString) throws ParseException {
		this(BibItem.stringToBibTeXEntry(entryAsString));
	}

	/**
	 * Returns the value of the field indicated by the given Key object
	 * Key objects are public static variables of BibTeXEntry
	 * 
	 * @param field a Key representign a BibTeX field such as title and author
	 * @return null if value not found, value otherwise 
	 */
	public String getValue(Key field) {
		try {
			return entry.getField(field).toUserString();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the value of a field whose key has the given name
	 * 
	 * @param field a String representation of a BibTeX field such as title and author
	 * @return null if value not found, value otherwise
	 */
	public String getValue(String field) {
		return getValue(new Key(field.toLowerCase()));
	}

	/**
	 * 
	 * @return a String representation of the BibKey
	 */
	public String getBibKey(){
		return entry.getKey().toString();
	}

	/**
	 *
	 * @return a String representation of the type of this entry
	 */
	public String getType() {
		return entry.getType().toString();
	}

	/**
	 * Transforms a string representation of a BibTeX entry into an object of BibTeXEntry
	 *
	 * @param bibTeXString the entry as String
	 * @return the entry as BibTeXEntry
	 */
	public static BibTeXEntry stringToBibTeXEntry(String bibTeXString) throws ParseException {
		BibTeXParser parser = new BibTeXParser();
		Reader reader = new StringReader(bibTeXString);
		BibTeXDatabase dummyDatabase = parser.parse(reader);
		List<BibTeXObject> objects = dummyDatabase.getObjects();
		return (BibTeXEntry)objects.get(0);
	}

	/**
	 * Transforms a BibTeXEntry representation of a BibTeX entry into a String
	 *
	 * @param bibTeXSEntry to be transformed
	 * @return the entry as String
	 */
	public static String bibTeXEntryToString(BibTeXEntry bibTeXEntry) throws IOException {
		Writer writer = new StringWriter();
		BibTeXFormatter formatter = new BibTeXFormatter();
		BibTeXDatabase dummyDatabase = new BibTeXDatabase();
		dummyDatabase.addObject(bibTeXEntry);
		formatter.format(dummyDatabase, writer);
		String entryAsString = writer.toString();
		writer.close();
		return entryAsString;
	}

	/**
	 * Allow remove/add field?
	 * getCrossReference
	 * getFields()
	 */
}
