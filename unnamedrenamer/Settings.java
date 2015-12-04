import java.nio.file.Path;
import java.util.List;

public class Settings 
{
	
	//variables :
	private static Path  folderPath ;
	private static Path bibFilePath ;
	private static Path uneditedBibFilePath ;
	private static String defaultRenamingFormat  ; 
	private static String renamingformat ; 
	private static String ignoredFiles ; 
	private static boolean generateBibKey ;
	private static String bibKeyGenerationFormat ; 
	private static String visualizationBibFields ;
	
	/**
	 * No objects are created. Settings files are loaded and written to.
	 */
	private Settings() {
	}
	
	// set and get methods :  
	public static Path getFolderPath()
	{
		return folderPath;
	}
	
	public static boolean setFolderPath(Path folderPath) 
	{
		return false ; 
	}
	
	public static Path getBibFilePath() 
	{
		return bibFilePath;
	}
	
	public static boolean setBibFilePath(Path bibFilePath)
	{
		return false ;
	}
	
	public static Path getUneditedBibFilePath() 
	{
		return uneditedBibFilePath;
	}
	
	public static boolean setUneditedBibFilePath(Path uneditedBibFilePath) 
	{
		return false ;
	}
	
	public static List<String> getRenamingFormat()
	{
		return null;
		
	}
	
	public static boolean setRenamingFormat(String renamingformat) 
	{
		return generateBibKey;
		
	}
	
	public static void resetRenamingFormat  ()   
	{
		
	}
	
	public static List<String> getIgnoredFiles()  
	{
		return null ;
	}
	
	public static void addIgnoredFiles(String ignoredFiles)
	{
		
	}
	
	public static boolean removeIgnoredFile(String ignoredFile )  
	{
		return false ;
	}
	
	public static boolean getGenerateBibKey() 
	{
		return generateBibKey;
	}
	
	public static void setGenerateBibKey(boolean generateBibKey) 
	{
		this.generateBibKey = generateBibKey;
	}
	
	public static List<String> getBibKeyGenerationFormat() 
	{
		return null ;
	}
	
	public static void setBibKeyGenerationFormat(String bibKeyGenerationFormat) 
	{
		this.bibKeyGenerationFormat = bibKeyGenerationFormat;
	}
	
	public static List<String> getVisualizationBibFields() 
	{
		return null ;
	}
	
	public static boolean setVisualizationBibFields(String visualizationBibFields) 
	{
		return false ;	
	} 
	
	
		
}
