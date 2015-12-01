import java.util.Map;

public class BibItem {
private Paper paper ;
private String entryType;
private String bibKey; 
private String originalBibKey;
private Map<String, String> bibFields;
private String originalEntry;
/**
 * an empty constructor
 */
BibItem(){}

/**
 * 
 * @param paper paper related to this bib item
 * @param entryType a field for entrytype
 * @param bibKey a field for bibkey
 * @param originalBibKey a field to save original bibkey(dose not change)
 * @param bibFields list of rest of bib fields
 * @param originalEntry a field to save original Entry(dose not change)
 */
BibItem(Paper paper ,String entryType,String bibKey,String originalBibKey,
Map<String, String> bibFields,String originalEntry){	
	 this.paper = paper ;
	 this.entryType = entryType;
	 this.bibKey = bibKey; 
	 this.originalBibKey = originalBibKey;
	 this.bibFields = bibFields;
	 this.originalEntry = originalEntry;
}
/**
 * 
 * @param field a bib item field
 * @return this field
 */
String getValue(String field) {
	return field;
	
}
/**
 * @return this bibkey 
 */
String getBibKey(){
	return bibKey;
	
}
/**
 * @return if to generate bibkey or not
 */
boolean generateBibKey(){
	return false;
	
}
}
