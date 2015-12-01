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
	String ignoredFiles ; 
	boolean generateBibKey ;
	String bibKeyGenerationFormat ; 
	String visualizationBibFields ;
	
	
	
	// set and get methods :  
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
	public List<String> getRenamingFormat()
	{
		return null;
		
	}
	
	public boolean setRenamingFormat(String renamingformat) 
	{
		return generateBibKey;
		
	}
	
	public void resetRenamingFormat  ()   
	{
		
	}
	
	public List<String> getIgnoredFiles()  
	{
		return null ;
	}
	
	public void addIgnoredFiles(String ignoredFiles)
	{
		
	}
	
	public boolean removeIgnoredFile(String ignoredFile )  
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
	
	public List<String> getBibKeyGenerationFormat() 
	{
		return null ;
	}
	
	public void setBibKeyGenerationFormat(String bibKeyGenerationFormat) 
	{
		this.bibKeyGenerationFormat = bibKeyGenerationFormat;
	}
	
	public List<String> getVisualizationBibFields() 
	{
		return null ;
	}
	
	public boolean setVisualizationBibFields(String visualizationBibFields) 
	{
		return false ;	
	} 
	
	
		
}
