
import java.util.*;
import java.io.*;
import org.apache.commons.lang3.*;

public class ReadAFileCheckIsAscii {
  public static void main(String args[]) {
    String theFile = "/zivcis.txt";
    if (args.length == 0) {
      System.out.println("Must pass the name of the file to check, going to use " + theFile);
    }
    else theFile = args[0];
    
    try {      
      BufferedReader in = new BufferedReader(new FileReader(theFile));
      int lineCnt = 0, badCnt = 0;
      String str;
      while ((str = in.readLine()) != null) {
        lineCnt++;
        if (StringUtils.isAsciiPrintable(str) == false) {
          System.out
              .println("Bad data at: " + Integer.toString(lineCnt) + " " + str);
          badCnt++;
        }
      }
      in.close();
      System.out.println("read: " + Integer.toString(lineCnt) + " lines, " + 
                         badCnt + " lines had non-ascii characters");
    } catch (FileNotFoundException e) {
      System.out.println(e);
      return;
    } catch (IOException e) {
      System.out.println(e);
    }

  }
}
