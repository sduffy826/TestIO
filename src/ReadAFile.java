import java.util.*;
import java.io.*;

public class ReadAFile {
  static boolean WRITEFILE = true;
  public static int MINTRIMLEN = 1;
  public static void main(String[] args) {
    if (args[0].length() < 1) {
      System.out.println("Must pass input file to program");
      System.exit(999);
    }
    try {      
      System.out.println("Started main...\n will write file indicator: " + ( WRITEFILE ? "Yes" : "No") +
                         "\n minTrimLen: " + Integer.toString(MINTRIMLEN) + "\n inputFile: " + args[0]);
      
      BufferedReader in = new BufferedReader(new FileReader(args[0]));
      String fileOut = args[0] + "_rfm";
      
      FileWriter fw;
      BufferedWriter out = null;
      if (WRITEFILE) {
        fw  = new FileWriter(fileOut, false);
        out = new BufferedWriter(fw);
      }
            
      int lineCnt = 0, minLenCnt = 0, outCnt = 0;
      String str;
      while ((str = in.readLine()) != null) {
        lineCnt++;
        
        if (str.trim().length() >= MINTRIMLEN) {
          if (WRITEFILE) {
            out.write(str); 
            out.newLine();
            outCnt++;
          }
        }
        else {
          System.out.println("MinTrimLen at: " + Integer.toString(lineCnt) + " data: " + str);;
          minLenCnt++;
        }           
      }
      in.close();
      
      if (WRITEFILE) out.close();
     
      System.out.println("read: " + Integer.toString(lineCnt) + 
                        "\nwritten: " + Integer.toString(outCnt) + 
                        "\nminTrimLen Recs: " + Integer.toString(minLenCnt));
    } catch (FileNotFoundException e) {
      System.out.println(e);
      return;
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}

