import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.*;
import java.util.Map;
import java.util.List;

public class BibTexFinder {
	private String bibItem;

	/**
	* No objects are created
	*/
	private BibTexFinder() {};
	
	String getBibItem(){
		return bibItem;
	}

	/**
	 * this function is given a title then returns a
	 *  bibitem from internet
	 *  @param title paper's title
	 *  @param paper paper to search for
	 */
	public static BibItem findBibItemByTitle(String title, Paper paper) {
		//look in BibFile
		//handle exceptions
		//create BibItem
		//crossref [this is for demonstration purposes only and won't be used in actuality personally or commercially
		//it demonstrates a personal paper manager that retrieves entries for the user's pdf papers as they are added to a certain folder]
		//integrate API (include license?)

		//why does this and bibitem need paper?
		//javac -cp ".;lib/*" BibTexFinder.java
		//java -cp ".;lib/*" BibCase

		//proper handling, and if not found
		String doi;
		String bibEntry;
		try {
			doi = getDOI(title);
			try {
				bibEntry = getBibEntry(doi);
				System.out.println(bibEntry);
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
		} catch (MalformedURLException murle) {
			murle.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (URISyntaxException urise) {
			urise.printStackTrace();
		}

		
		return null;
	}

	/**
	 * this function is given a citation then returns a
	 *  bibitem from internet
	 *  @param citation citation from references
	 *  @param paper paper to search for
	 */
	public static BibItem findBibItemByCitation(String citation, Paper paper) {
		return null;
		
	}

	/**
	 * Finds the DOI using crossref api.
	 *
	 * @param title paper title
	 * @return DOI
	 */
	private static String getDOI(String title) throws URISyntaxException, MalformedURLException, IOException {
		URL url = getCrossrefURL("/works", "rows=1&query=" + title);
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
	private static String getBibEntry(String doi) throws URISyntaxException, MalformedURLException, IOException, ParseException {
		URL url = getCrossrefURL("/works/" + doi + "/transform/application/x-bibtex", null);
		BufferedReader reader = getReader(url);

		//load all reader content onto string
		String line = reader.readLine();
		String bibEntry = "";
		while (line != null) {
			bibEntry = bibEntry + "\n" + line;
			line = reader.readLine();
		}

		return bibEntry;
	}

	/**
	 * Creates a crossref API URL making sure that spaces and irregular characters are encoded properly.
	 *
	 * @param path the path that is after api.crossref.org until the desired file and starting with /
	 * @param query the to be attached to the URL, not starting with ?, multiple parameters are seperated by &
	 * @return a crossref API URL
	 */
	private static URL getCrossrefURL(String path, String query) throws URISyntaxException, MalformedURLException {
		URI uri = new URI("http", "api.crossref.org", path, query, null);
		return new URL(uri.toASCIIString());
	}

	/**
	 * Gets the URL content as JsonObject. The URL is to lead to JSON.
	 *
	 * @param url the desired URL
	 * @return a JsonObject with the JSON contained in the page
	 */
	private static JsonObject getJSON(URL url) throws IOException {
		Reader reader = getReader(url);
		return (new JsonParser().parse(reader)).getAsJsonObject();
	}

	/**
	 * Gets a Reader for the given url.
	 *
	 * @param url the url of the page
	 * @return a reader that contains a stream of the page's content
	 */
	private static BufferedReader getReader(URL url) throws IOException {
		return new BufferedReader(new InputStreamReader(url.openStream()));
	}
}
