
import java.util.List;


public class Category 
{
       public static List <Category>category;
       public List <Note>notes;
       private String name;
       
       public Category (String name)
       {
           this.name = name;
           
       }
       
       public void setCategoryName (String name)
       {
           this.name = name;
       }
       
}
