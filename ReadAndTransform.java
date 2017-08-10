import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadAndTransform {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    // TODO Auto-generated method stub
    try {
          String fileIn, fileOut, str;
          
          int lineCnt, outCnt;
          
          if (args.length > 0) 
            fileIn = args[0];
          else
            fileIn = "c:" + File.separatorChar + "ReadFile.in";
          
          if (args.length > 1)
            fileOut = args[1];
          else
            fileOut = "c:" + File.separatorChar + "ReadFile.out";
        
          FileWriter fw = new FileWriter(fileOut,false);
          BufferedWriter out = new BufferedWriter(fw);
          BufferedReader in = new BufferedReader(new FileReader(fileIn));
        
          lineCnt = outCnt = 0;
          while ((str = in.readLine()) != null)
          {
              lineCnt++;
              if ( str.trim().length() > 0 ) {
                String encString = java.net.URLEncoder.encode(str,"UTF-8");
                out.write(encString);                
              }
              out.newLine();
              outCnt++;
          }
          in.close();
          out.close();
          System.out.print("All Done, read: ");
          System.out.print(lineCnt);
          System.out.println(" records");
          System.out.print("         wrote: ");
          System.out.print(outCnt);
          System.out.println(" records");
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        }
        catch (IOException e) {
            System.out.println(e);
        }
  }
}
