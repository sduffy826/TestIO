package checkSumPackage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class TestIt {
    
  public static void main(String[] args) {
    
    String file2Check = "c:/seanduff/putty/pscp.exe";
    if (args.length > 0) {
      file2Check = args[0];
    }
    
    CheckSum checkSum = new CheckSum(file2Check);
   
    try {
      // We're measuring performance so don't want file creation time to pollute numbers :)
      File file = new File(file2Check);;
      
      String[] theList = { "MD5", "SHA-1", "SHA-256" };
      for (String algoType : theList) {
        System.out.println("Trying: " + algoType);
        long startTime = System.currentTimeMillis();

        //Get the checksum
        String checksum = checkSum.getFileCheckSum(file, algoType);

        long endTime = System.currentTimeMillis();
        long elapsedMilliSeconds = endTime - startTime;
        double elapsedSeconds = elapsedMilliSeconds / 1000.0;
        System.out.println(elapsedSeconds);

        //see checksum
        System.out.println(Integer.toString(checksum.length()) + " " + checksum);
      }
    }
    catch(Exception e) { e.printStackTrace(); }       
  }
  
  
  
  

}
