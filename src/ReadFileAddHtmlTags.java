import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class reads in a text file and adds 'ul,li' tags to it; it use '-' as the identifier for lines
 * that should be prefixed with <li>.  It also looks at the nesting to create/remove new lists i.e.
 * - first level
 * - another at level 1
 *   - this is at second level
 *     - this is at third level
 * This would create
 * <ul>
 *   <li>first level</li>
 *   <li>another at level 1</li>
 *   <ul>
 *     <li>this is at second level</li>
 *     <ul>
 *       <li>this is at third level</li>
 *     </ul>
 *   </ul>
 * </ul>
 * 
 * It's a very basic program... only created to prevent me from having to put all the tags in a large
 *   text file... enhance down the road if you find it useful.
 *
 */
public class ReadFileAddHtmlTags {
  public static final int INDENTAMT = 3;
  
  public static void main(String[] args) {
    ReadFileAddHtmlTags me = new ReadFileAddHtmlTags();
    me.runtIt(args);
  }
  
  ReadFileAddHtmlTags() { 
    // Empty constructor
  }
    
  // Return string with the prefix amount for a given level
  private String prefixIt(int level) {
    if (level > 0) {
      return new String(new char[level * INDENTAMT]).replace("\0"," ");
    }
    else 
      return "";
  }
  
  public void runtIt(String[] args) {
    // TODO Auto-generated method stub
    try {      
      boolean done = false;
      String fileIn, fileOut, str, tmpStr;

      int lineCnt, outCnt, currPos, lastPos, currLevel;
            
      if (args.length > 0)
        fileIn = args[0];
      else
        fileIn = "c:" + File.separatorChar + "ReadFile.in";

      if (args.length > 1)
        fileOut = args[1];
      else
        fileOut = "c:" + File.separatorChar + "ReadFile.out";
      
      System.out.println("Input: " + fileIn + "  output: " + fileOut);

      FileWriter fw = new FileWriter(fileOut, false);
      BufferedWriter out = new BufferedWriter(fw);
      BufferedReader in = new BufferedReader(new FileReader(fileIn));

      lineCnt = outCnt = currLevel = 0; 
      lastPos = -1;      
      
      while (!done) {
        str = in.readLine();
        if (str == null) {
          str = "";
          done = true;
        }
        lineCnt++;
        tmpStr = str.trim();
        currPos = -1;
        
        if (tmpStr.length() > 2 && tmpStr.startsWith("-")) {
          currPos = str.indexOf("-");
          tmpStr = tmpStr.substring(1).trim(); // Strip off - and any spaces after
        }
        else if (currLevel == 0 && tmpStr.length() > 0) {
          tmpStr = "<h3>" + tmpStr + "</h3>";
        }
        
        if (currPos > lastPos) {
          // We need to go one level deeper for this item
          out.write(prefixIt(currLevel)+"<ul>");
          out.newLine();
          currLevel++;
        }
        else
          if (currPos < lastPos ) {
            // We need to go back one level
            System.out.print("Currlevel went down, from: " + currLevel);
            currLevel = (currLevel > 0 ? currLevel-1 : 0);
            System.out.println(" to: " + currLevel);
            out.write(prefixIt(currLevel)+"</ul>");
            out.newLine();
            
            if (currPos < 0) {  // Close all the open lists
              while (currLevel > 0) {
                currLevel--;
                out.write(prefixIt(currLevel)+"</ul>");
                out.newLine();
              }
            }
            
          }
        
        lastPos = currPos;             

        // Write out the current line
        if (currLevel > 0) {          
          out.write(prefixIt(currLevel)+"<li>"+tmpStr+"</li>");
        }
        else out.write(tmpStr);
        
        out.newLine();               
      }
      in.close();
      out.close();
      System.out.print("All Done, processed: ");
      System.out.print(lineCnt);
      System.out.println(" records");
    } catch (FileNotFoundException e) {
      System.out.println(e);
      return;
    } catch (IOException e) {
      System.out.println(e);
    }

  }

}
