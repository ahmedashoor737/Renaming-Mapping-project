import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import org.jbibtex.*;

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
	 * 
	 * @param originalEntry a field to save original Entry(dose not change)
	 */
	public BibItem(String entryAsString) throws ParseException {
		this(BibItem.stringToBibTeXEntry(entryAsString));
	}

	/**
	 * 
	 * @param field a bib item field
	 * @return this field
	 */
	public String getValue(String field) {
		return field;
		
	}
	/**
	 * @return this bibkey 
	 */
	public String getBibKey(){
		return null;
		
	}

	public static BibTeXEntry stringToBibTeXEntry(String bibTeXString) throws ParseException {
		BibTeXParser parser = new BibTeXParser();
		Reader reader = new StringReader(bibTeXString);
		BibTeXDatabase database = parser.parse(reader);
		List<BibTeXObject> objects = database.getObjects();
		return (BibTeXEntry)objects.get(0);
	}

	public static String bibTeXEntryToString(BibTeXEntry bibTeXEntry) {
		return "";
	}
}
