
import java.util.*;
import java.io.*;

public class ClasspathTest {

  public static void main(String args[]) {
    String theDelim = ":";
    String theOS = System.getProperty("os.name");
    if (theOS.length() > 5) {
      if (theOS.substring(0, 7).compareToIgnoreCase("WINDOWS") == 0) {
        theDelim = ";";
      }
    }
    System.out.println("Delimitter: " + theDelim);

    for (String argLine : args) {
      System.out.println("args line: " + argLine);
      String splitLines[] = argLine.split(theDelim);
      for (String subWord : splitLines) {
        File f = new File(subWord);
        System.out.println(
            (f.exists() ? "Exists" : "Does not exist") + " file: " + subWord);
      }
    }
  }
}
