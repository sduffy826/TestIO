import java.util.*;
import java.io.*;

public class SplitFileAtRecordCount {
  public static final int MINTRIMLEN = 1;
  public static final int RECORDS2WRITE = 60000;
  public static int fileSuffix = 200;
  public static int totFiles = 0;
  
  public static BufferedWriter getFile(String inFile, BufferedWriter outFile, boolean endOfJob) {
    String fileOut = inFile + "_" + Integer.toString(++fileSuffix);
    try {
      if (outFile != null) {
        outFile.flush();
        outFile.close();
      }
      if (!endOfJob) {
        // If not at end of job then return a buffered writer, otherwise our task was to just flush/close
        totFiles++;
        FileWriter fw = new FileWriter(fileOut,false);
        return new BufferedWriter(fw);
      }
    }
    catch (IOException e) {
      System.out.println(e);
    }
    return null;
  }
  
  
  public static void main(String[] args) {
    if (args[0].length() < 1) {
      System.out.println("Must pass input file to program");
      System.exit(999);
    }
    try {
      System.out.println("Started main...\n minTrimLen: " + Integer.toString(MINTRIMLEN) + "\n inputFile: " + args[0]);
      
      BufferedReader in = new BufferedReader(new FileReader(args[0]));
      BufferedWriter out = getFile(args[0], null, false);
          
      int lineCnt = 0, minLenCnt = 0, outCnt = 0, totCnt = 0;
      String str;
      while ((str = in.readLine()) != null) {
        lineCnt++;
        
        if (str.trim().length() >= MINTRIMLEN) {
          outCnt++;
          totCnt++;
          if (outCnt > RECORDS2WRITE) {
            out = getFile(args[0],out,false);
            outCnt = 1;
          }
          out.write(str); 
          out.newLine();     
        }
        else {
          System.out.println("MinTrimLen at: " + Integer.toString(lineCnt) + " data: " + str);;
          minLenCnt++;
        }           
      }
      in.close();
      out = getFile(args[0],out,true);
     
      System.out.println("read: " + Integer.toString(lineCnt) + 
                        "\ntotalWritten: " + Integer.toString(totCnt) +
                        "\nfilesCreated: " + Integer.toString(totFiles) +
                        "\nminTrimLen Recs: " + Integer.toString(minLenCnt));
    } catch (FileNotFoundException e) {
      System.out.println(e);
      return;
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}