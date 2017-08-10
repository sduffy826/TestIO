import java.io.*;
import java.util.*;
public class ShowProperties {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	  Properties listOfProps = System.getProperties();
      int stringLen, dumVar;
      for (Enumeration e = listOfProps.propertyNames() ; e.hasMoreElements() ;)
      {
    	 String elementName = (String)e.nextElement();
    	 stringLen = elementName.length();
	     System.out.print(elementName);
	     for (dumVar = stringLen; dumVar < 30; dumVar++)
	    	System.out.print(" ");
	     System.out.println(System.getProperty(elementName));
      }
	}

}
