import java.util.Map;

public class BibItem {
Paper paper ;
String entryType;
String bibKey; 
String originalBibKey;
Map<String, String> bibFields;
String originalEntry;

BibItem(){}

BibItem(Paper paper ,String entryType,String bibKey,String originalBibKey,
Map<String, String> bibFields,String originalEntry){	
	 this.paper = paper ;
	 this.entryType = entryType;
	 this.bibKey = bibKey; 
	 this.originalBibKey = originalBibKey;
	 this.bibFields = bibFields;
	 this.originalEntry = originalEntry;
}

String getValue(String field) {
	
}

String getBibKey(){
	
}

boolean generateBibKey(){
	
}
}
