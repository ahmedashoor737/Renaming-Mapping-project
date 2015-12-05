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
import java.util.Map;
import java.util.List;

/**
 * Use static methods for one-time operations. Create a BibTexFinder for batch operations.
 * When using a BibTeXFinder object, create it, perform needed operations, then close it with close() to save new entries to the file.
 * To avoid data lost: Use one BibTeXFinder per file until you close it. Don't use static methods and an unclosed BibTeXFinder if they use the same file.
 */
public class BibTexFinder {
	//private String bibItem;
	private BibFile bibFile;
	private boolean doLookOnline;

	public BibTexFinder() {
		this.bibFile = null;
		this.doLookOnline = true;
	}

	public BibTexFinder(Path bibFilePath) throws IOException, ParseException {
		this(bibFilePath, true);
	}

	public BibTexFinder(Path bibFilePath, boolean doLookOnline) throws IOException, ParseException {
		this.bibFile = new BibFile(bibFilePath);
		this.bibFile.loadDatabaseFromFile();

		this.doLookOnline = doLookOnline;
	}

	public void close() throws IOException {
		this.bibFile.writeDatabaseToFile();
	}

	//why does this and bibitem need paper?
	//pass Paper?
	public BibItem findBibItemByTitle(String title) {
		//handle exceptions
		//proper handling, and if not found
		//integrate API (include license?)

		//javac -cp ".;lib/*" BibTexFinder.java
		//java -cp ".;lib/*" BibCase

		BibTeXEntry entry = findInFile(title);

		if (entry == null && doLookOnline) {
			try {
				String bibItemString = findOnCrossRef(title);
				entry = BibItem.stringToBibTeXEntry(bibItemString);
				bibFile.addBibTeXEntry(entry);
			} catch (MalformedURLException murle) {
				murle.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (URISyntaxException urise) {
				urise.printStackTrace();
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
		}
		
		return new BibItem(entry);
	}

	/**
	 * this function is given a title then returns a
	 *  bibitem from internet
	 *  @param title paper's title
	 *  @param paper paper to search for
	 */
	public static BibItem findByTitle(String title) throws IOException, ParseException {
		BibTexFinder finder = new BibTexFinder(Settings.getBibFilePath(), true);
		BibItem bibItem = finder.findBibItemByTitle(title);

		finder.close();
		return bibItem;
	}

	public BibItem findBibItemByCitation(String citation) {
		if (doLookOnline) {
			try {
				String bibItemString = findOnCrossRef(citation);
				BibTeXEntry entry = BibItem.stringToBibTeXEntry(bibItemString);
				bibFile.addBibTeXEntry(entry);
				return new BibItem(entry);
			} catch (MalformedURLException murle) {
				murle.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (URISyntaxException urise) {
				urise.printStackTrace();
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * this function is given a citation then returns a
	 *  bibitem from internet
	 *  @param citation citation from references
	 *  @param paper paper to search for
	 */
	//actually same as by citation excpet for local?
	public static BibItem findByCitation(String citation) throws IOException, ParseException{
		BibTexFinder finder = new BibTexFinder(Settings.getBibFilePath(), true);
		BibItem bibItem = finder.findBibItemByCitation(citation);

		finder.close();
		return bibItem;
	}

	private BibTeXEntry findInFile(String title) {
		return bibFile.findBibItemByTitle(title);
	}

	private String findOnCrossRef(String query) throws URISyntaxException, MalformedURLException, IOException {
		String doi = getDOI(query);
		return getBibEntry(doi);
	}

	/**
	 * Finds the DOI using crossref api.
	 *
	 * @param title paper title
	 * @return DOI
	 */
	private String getDOI(String query) throws URISyntaxException, MalformedURLException, IOException {
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
