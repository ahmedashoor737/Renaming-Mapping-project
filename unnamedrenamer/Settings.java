import java.nio.file.Path;
import java.util.List;

public class Settings 
{
	
	//variables :
	Path  folderPath ;
	Path bibFilePath ;
	Path undeditedBibFilePath ;
	String defaultRenamingFormat  ; 
	String renamingformat ; 
	String ignoredFiles ; // Collcetion ???
	boolean generateBibKey ;
	String bibKeyGenerationFormat ; 
	String visualizationBibFields ;
	
	
	
	// set and get methodes :  
	public Path getFolderPath()
	{
		return folderPath;
	}
	
	public boolean setFolderPath(Path folderPath) 
	{
		return false ; 
	}
	
	public Path getBibFilePath() 
	{
		return bibFilePath;
	}
	
	public boolean setBibFilePath(Path bibFilePath)
	{
		return false ;
	}
	
	public Path getUndeditedBibFilePath() 
	{
		return undeditedBibFilePath;
	}
	
	public boolean setUndeditedBibFilePath(Path undeditedBibFilePath) 
	{
		return false ;
	}
	/*
	public String getDefaultRenamingFormat() // List <Strind>
	{
		return defaultRenamingFormat;
	}
	
	public void setDefaultRenamingFormat(String defaultRenamingFormat) 
	{
		this.defaultRenamingFormat = defaultRenamingFormat;
	}
	*/
	public List<String> getRenamingFormat() // List <String> ?? 
	{
		return null;
		
	}
	
	public boolean setRenamingFormat(String renamingformat) 
	{
		return generateBibKey;
		
	}
	
	public void resetRenamingFormat  ()  // Parameter ? 
	{
		
	}
	
	public String getIgnoredFiles()  //String Collection ??
	{
		return ignoredFiles;
	}
	
	public void addIgnoredFiles(String ignoredFiles)
	{
		
	}
	
	public boolean removeIgnoredFile() //Parameter 
	{
		return false ;
	}
	
	public boolean getGenerateBibKey() 
	{
		return generateBibKey;
	}
	
	public void setGenerateBibKey(boolean generateBibKey) 
	{
		this.generateBibKey = generateBibKey;
	}
	
	public List<String> getBibKeyGenerationFormat() //List <String> ?? 
	{
		return null ;
	}
	
	public void setBibKeyGenerationFormat(String bibKeyGenerationFormat) 
	{
		this.bibKeyGenerationFormat = bibKeyGenerationFormat;
	}
	
	public String getVisualizationBibFields() // List <String> ?? 
	{
		return visualizationBibFields;
	}
	
	public boolean setVisualizationBibFields(String visualizationBibFields) 
	{
		return false ;	
	} 
	
	
		
}
