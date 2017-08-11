import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class ReadAndTransform {
  public static void main(String[] args) {
    try {
      
      // Little program to read file and write it out in different encoding, the encoding
      // args are passable 
      
      String fileIn, charSetIn, fileOut, charSetOut, str;
      int lineCnt, outCnt;

      // ------------ c a n    i g n o r e    t h i s    b l o c k ----------------
      // Little stub as quickndirty way to get a file with all the ascii characters.
      if (true) {
        // Just simple to create output file with all characters
        String allChars = "";
        for (int i = 0; i < 256; i++) allChars += (char)i;
        System.out.println(allChars);
        FileWriter fileWriter = new FileWriter("/allAsciis.txt");
        BufferedWriter buffWriter = new BufferedWriter(fileWriter);
        buffWriter.write(allChars);;
        buffWriter.flush();
        buffWriter.close();
      }
      
      
      // Set default character sets (will override later if passed in)
      charSetIn = charSetOut = Charset.defaultCharset().name();
      
      if (args.length > 0) {
        fileIn = args[0];
        if (args.length > 1) charSetIn = args[1];
      }
      else
        fileIn = "c:" + File.separatorChar + "ReadFile.in";

      if (args.length > 2) {
        fileOut = args[2];
        if (args.length > 3) charSetOut = args[3];
      }
      else
        fileOut = "c:" + File.separatorChar + "ReadFile.out";
          
      // Setup output, we specify the character set on the outputstreamwriter
      FileOutputStream fileOutputStream = new FileOutputStream(fileOut);
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,charSetOut);
      BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
      
      // Setup input 
      File inputFile = new File(fileIn);
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(inputFile), charSetIn);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      
      lineCnt = outCnt = 0;
      while ((str = bufferedReader.readLine()) != null) {
        lineCnt++;
        /* 
        if (str.trim().length() > 0) {
          String encString = java.net.URLEncoder.encode(str, "UTF-16");
          out.write(encString);
        }
        */
        bufferedWriter.write(str);
        bufferedWriter.newLine();
        outCnt++;
      }
      bufferedReader.close();
      bufferedWriter.flush();
      bufferedWriter.close();
      System.out.println("Input: " + fileIn + " character set: " + charSetIn);
      System.out.println("Output: " + fileOut + " character set: " + charSetOut);
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
