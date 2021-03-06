//check if they are used
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import com.google.gson.*;
import org.jbibtex.*;

/**
 * This class finds a BibTeX entry in a BibFile or using CrossRef. The query can be the title or the citation (in which case it only searches CrossRef).
 * If an entry was found online, the class adds it to the BibFile.
 *
 * Use static methods for one-time operations. Create a BibTexFinder for batch operations. The static methods use Settings.getBibFilePath() as the .bib file.
 * When using a BibTeXFinder object, create it, perform needed operations, then close it with close() to save new entries to the file.
 * To avoid data lost: Use one BibTeXFinder per file until you close it. Don't use static methods and an unclosed BibTeXFinder if they use the same file.
 */
public class BibTexFinder {
	private BibFile bibFile;
	private boolean doLookOnline;
	//given by CrossRef
	private int maximumQueryLength = 4096;

	/**
	 * Same as BibTexFinder(bibFilePath, true)
	 */
	public BibTexFinder(Path bibFilePath) throws IOException, ParseException {
		this(bibFilePath, true);
	}

	/**
	 * Creates a BibFile and calls loadDatabaseFromFile() on it
	 *
	 * @param bibFilePath for the .bib file acting as database
	 * @param doLookOnline whether to look online or not
	 */
	public BibTexFinder(Path bibFilePath, boolean doLookOnline) throws IOException, ParseException {
		this.bibFile = new BibFile(bibFilePath);
		this.bibFile.loadDatabaseFromFile();

		this.doLookOnline = doLookOnline;
	}

	/**
	 * Calls writeDatabaseToFile() on the BibFile
	 */
	public void close() throws IOException {
		this.bibFile.writeDatabaseToFile();
	}

	//why does this and bibitem need paper?
	//actually same as by citation excpet for local?
	/**
	 * Looks for the item in the BibFile and on CrossRef. Adds the item to BibFile if found on CrossRef.
	 *
	 * @param title of paper
	 * @return BibItem containing the found entry, null if not found.
	 */
	public BibItem findBibItemByTitle(String title) throws MalformedURLException, IOException, URISyntaxException, ParseException, IllegalStateException {
		BibTeXEntry entry = findInFile(title);

		if (title.length() > maximumQueryLength) {
			throw new IOException("Query is too long");
		}

		if (entry == null && doLookOnline) {
			String bibItemString = findOnCrossRef(title);
			entry = BibItem.stringToBibTeXEntry(bibItemString);
			bibFile.addBibTeXEntry(entry);
		}
		
		return new BibItem(entry);
	}

	/**
	 * Creates an instance of BibTeXFinder with Settings.getBibFilePath() and doLookOnline = true.
	 * Calls close() after searching.
	 *
	 * @param title of paper
	 * @return BibItem containing the found entry, null if not found.
	 */
	public static BibItem findByTitle(String title) throws IOException, ParseException, URISyntaxException {
		BibTexFinder finder = new BibTexFinder(Settings.getBibFilePath(), true);
		BibItem bibItem = finder.findBibItemByTitle(title);

		finder.close();
		return bibItem;
	}

	/**
	 * Searches on CrossRef using the given citation.
	 *
	 * @param citation with paper information
	 * @return BibItem containing the found entry, null if not found.
	 */
	public BibItem findBibItemByCitation(String citation) throws MalformedURLException, IOException, URISyntaxException, ParseException, IllegalStateException {
		if (citation.length() > maximumQueryLength) {
			throw new IOException("Query is too long");
		}

		if (doLookOnline) {
			String bibItemString = findOnCrossRef(citation);
			BibTeXEntry entry = BibItem.stringToBibTeXEntry(bibItemString);
			bibFile.addBibTeXEntry(entry);
			return new BibItem(entry);
		}

		return null;
	}

	/**
	 * Creates an instance of BibTeXFinder with Settings.getBibFilePath() and doLookOnline = true.
	 * Calls close() after searching.
	 *
	 * @param citation with paper information
	 * @return BibItem containing the found entry, null if not found.
	 */
	public static BibItem findByCitation(String citation) throws IOException, ParseException, URISyntaxException {
		BibTexFinder finder = new BibTexFinder(Settings.getBibFilePath(), true);
		BibItem bibItem = finder.findBibItemByCitation(citation);

		finder.close();
		return bibItem;
	}

	/**
	 * Searches for entry in .bib file
	 * 
	 * @param title of paper
	 * @return BibTeXEntry as it appears in the file database
	 */
	private BibTeXEntry findInFile(String title) {
		return bibFile.findBibItemByTitle(title);
	}

	/**
	 * Searches CrossRef using the given query. Finds the DOI then uses it to find the entry.
	 *
	 * @param query to search with
	 * @return a string containing the entry as found on CrossRef, empty string if not found
	 */
	private String findOnCrossRef(String query) throws URISyntaxException, MalformedURLException, IOException, IllegalStateException {
		String doi = getDOI(query);
		return getBibEntry(doi);
	}

	/**
	 * Finds the DOI using crossref api.
	 *
	 * @param title paper title
	 * @return DOI
	 */
	private String getDOI(String query) throws URISyntaxException, MalformedURLException, IOException, IllegalStateException {
		URL url = getCrossrefURL("/works", "rows=1&query=" + query);
		JsonObject crossrefJson = getJSON(url);

		//navigate through crossref json
		JsonObject messegeJson = crossrefJson.get("message").getAsJsonObject();
		JsonElement itemsValue = messegeJson.get("items");
		String itemsString = itemsValue.toString();
		itemsString = itemsString.substring(1, itemsString.length()-1);
		JsonObject itemsJson = (new JsonParser().parse(itemsString)).getAsJsonObject();
		String doi = itemsJson.get("DOI").toString();
		doi = doi.substring(1, doi.length()-1);
		
		return doi;
	}

	/**
	 * Looks for the bib entry on CrossRef using the DOI.
	 *
	 * @param doi the DOI of the publication. Can be found using getDOI(title)
	 * @return a sting representation of the bib entry
	 */
	private String getBibEntry(String doi) throws URISyntaxException, MalformedURLException, IOException {
		URL url = getCrossrefURL("/works/" + doi + "/transform/application/x-bibtex", null);
		BufferedReader reader = getReader(url);

		//load all reader content onto string
		String line = reader.readLine();
		String bibEntry = "";
		while (line != null) {
			bibEntry = bibEntry + "\n" + line;
			line = reader.readLine();
		}

		reader.close();
		return bibEntry;
	}

	/**
	 * Creates a crossref API URL making sure that spaces and irregular characters are encoded properly.
	 *
	 * @param path the path that is after api.crossref.org until the desired file and starting with /
	 * @param query the to be attached to the URL, not starting with ?, multiple parameters are seperated by &
	 * @return a crossref API URL
	 */
	private URL getCrossrefURL(String path, String query) throws URISyntaxException, MalformedURLException {
		URI uri = new URI("http", "api.crossref.org", path, query, null);
		return new URL(uri.toASCIIString());
	}

	/**
	 * Gets the URL content as JsonObject. The URL is to lead to JSON.
	 *
	 * @param url the desired URL
	 * @return a JsonObject with the JSON contained in the page
	 */
	private JsonObject getJSON(URL url) throws IOException {
		Reader reader = getReader(url);
		JsonObject jsonObject = (new JsonParser().parse(reader)).getAsJsonObject();

		reader.close();
		return jsonObject;
	}

	/**
	 * Gets a Reader for the given url.
	 *
	 * @param url the url of the page
	 * @return a reader that contains a stream of the page's content
	 */
	private BufferedReader getReader(URL url) throws IOException {
		return new BufferedReader(new InputStreamReader(url.openStream()));
	}
}
