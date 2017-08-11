import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadFile {
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    try {
      String fileIn, fileOut, dateChk, str;

      int lineCnt, outCnt;

      if (args.length > 0)
        fileIn = args[0];
      else
        fileIn = "c:" + File.separatorChar + "ReadFile.in";

      if (args.length > 1)
        fileOut = args[1];
      else
        fileOut = "c:" + File.separatorChar + "ReadFile.out";

      if (args.length > 2)
        dateChk = args[2];
      else
        dateChk = "10-30-2009";

      FileWriter fw = new FileWriter(fileOut, false);
      BufferedWriter out = new BufferedWriter(fw);
      BufferedReader in = new BufferedReader(new FileReader(fileIn));

      lineCnt = outCnt = 0;
      while ((str = in.readLine()) != null) {
        lineCnt++;
        if (str.trim().length() > 0) {
          if (str.startsWith(dateChk) == false) {
            out.write(str);
            out.newLine();
            outCnt++;
          } else
            System.out.println("Found record with Date, bypassed");
        }
      }
      in.close();
      out.close();
      System.out.print("All Done, read: ");
      System.out.print(lineCnt);
      System.out.println(" records");
      System.out.print("         wrote: ");
      System.out.print(outCnt);
      System.out.println(" records");
    } catch (FileNotFoundException e) {
      System.out.println(e);
      return;
    } catch (IOException e) {
      System.out.println(e);
    }

  }

}
