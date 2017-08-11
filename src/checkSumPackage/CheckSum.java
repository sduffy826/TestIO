package checkSumPackage;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

import com.corti.java.nio.TestNIO;

import java.io.File;
import java.nio.file.Paths;

public class CheckSum {

  public static void main(String[] args) {

    try {
      //Create checksum for this file    
      File file = new File("c:/seanduff/putty/pscp.exe");
      String[] theList = { "MD5", "SHA-1", "SHA-256" };

      for (String algoType : theList) {
        System.out.println("Trying: " + algoType);
        long startTime = System.currentTimeMillis();

        //Use MD5 algorithm
        MessageDigest md5Digest = MessageDigest.getInstance(algoType);

        //Get the checksum
        String checksum = CheckSum.getFileCheckSum(md5Digest, file);

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

  public static String getFileCheckSum(MessageDigest digest, File file) throws IOException{
    //Get file input stream for reading the file content
    FileInputStream fis = new FileInputStream(file);

    //Create byte array to read data in chunks
    byte[] byteArray = new byte[1024];
    int bytesCount = 0; 

    //Read file data and update in message digest
    while ((bytesCount = fis.read(byteArray)) != -1) {
      digest.update(byteArray, 0, bytesCount);
    };

    //close the stream; We don't need it now.
    fis.close();

    //Get the hash's bytes
    byte[] bytes = digest.digest();

    //This bytes[] has bytes in decimal format;
    //Convert it to hexadecimal format
    StringBuilder sb = new StringBuilder();
    for(int i=0; i< bytes.length ;i++)
    {
      sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
    }

    //return complete hash
    return sb.toString();
  }
}