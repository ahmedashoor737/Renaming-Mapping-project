import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class Settings 
{
	
	//variables :
	private static Path  folderPath ;
	private static Path bibFilePath ;
	private static String defaultRenamingFormat  ; 
	private static String renamingformat ; 
	private static List<String> ignoredFiles ; 
	private static String visualizationBibFields ;
	
	/**
	 * No objects are created. Settings files are loaded and written to.
	 */
	private Settings() {
	}
	
	// set and get methods :  
	public static Path getFolderPath()
	{
		return Settings.folderPath;
	}
	
	public static boolean setFolderPath(Path folderPath) 
	{
		if (folderPath != null) {
			Settings.folderPath = folderPath;
			return true;
		} else {
			return false;
		}
	}
	
	public static Path getBibFilePath() 
	{
		return Settings.bibFilePath;
	}
	
	public static boolean setBibFilePath(Path bibFilePath)
	{
		if (bibFilePath != null) {
			Settings.bibFilePath = bibFilePath;
			return true;
		} else {
			return false;
		}
	}
	
	public static List<String> getRenamingFormat()
	{
		return null;
		
	}
	
	public static boolean setRenamingFormat(String renamingformat) 
	{
		boolean validFormat = false;
		return validFormat;
		
	}
	
	public static void resetRenamingFormat  ()   
	{
		
	}
	
	public static List<String> getIgnoredFiles()  
	{
		return ignoredFiles;
	}
	
	public static void addIgnoredFiles(Collection<String> newIgnoredFiles)
	{
		ignoredFiles.addAll(newIgnoredFiles);
	}
	
	public static boolean removeIgnoredFile(String ignoredFile )  
	{
		return false ;
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
