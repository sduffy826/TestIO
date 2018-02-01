import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Base64;

public class ReadBinaryFile {
  private String file2Process;

  public static void main(String[] args) throws IOException, InterruptedException {
    if (args.length < 1) {
      System.out.println("Need to pass filename in");
      Thread.sleep(5000);
      System.exit(999);
    }
    System.out.println("Input file: " + args[0]);
    ReadBinaryFile readBinaryFile = new ReadBinaryFile(args[0]);
    readBinaryFile.runTest();
  }

  private ReadBinaryFile() { }
  
  public ReadBinaryFile(String filename) {
    file2Process = filename;
  }
    
  public void runTest() throws IOException {
    byte[] bytes = readSmallFile(file2Process);
    System.out.println("binary below, size of array: " + bytes.length + "\n");
    for (int i = 0; i < bytes.length; i++) System.out.print(String.format("%02X", bytes[i]));
    
    System.out.println("\n\nbase64 below");
    
    String base64String = convertBin2Base64(bytes);
    System.out.println(base64String);
    
    // Want to show all the base64 bytes in the file and # of times it appears
    char[] char64Bytes = base64String.toCharArray();
    java.util.Arrays.sort(char64Bytes);
    char lastChar = char64Bytes[0];
    int theCount = 0;
    for (int i = 0; i < char64Bytes.length; i++) {
      if (char64Bytes[i] == lastChar) {
        theCount++;
      }
      else {
        System.out.println("Char: " + lastChar + " Count: " + Integer.toString(theCount));
        theCount = 1;
        lastChar = char64Bytes[i];
      }
    }
    System.out.println("Char: " + lastChar + " Count: " + Integer.toString(theCount));
    
    
    System.out.println("\n\nDecoded base64 back to original below :)");
    byte[] decodedBytes = decodeBase64String(base64String);
    
    // Compare the decoded bytes back with original, just for fun :)
    int allMatch = (bytes.length - decodedBytes.length) - 1;  // Lens match value will be -1
    for (int i = 0; i < decodedBytes.length; i++) {
      System.out.print(String.format("%02X", decodedBytes[i]));
      // if match so far check this byte otherwise return it's value (which would be false)
      if (allMatch == -1) { // Everthing matches so far continue.
         if ( bytes[i] != decodedBytes[i] ) {
           allMatch = i;
         }
      }
    }
    // If decoded byte array differs from original mark the place it's different
    System.out.println("");
    if (allMatch == -1) {
      System.out.println("\n\nEverthing matches");
    }
    else {      
      System.out.println(String.format("%1$"+(2*allMatch+1)+"s", "|")+"\nBytes Differ");
    }    
  }
  
  
  private byte[] readSmallFile(String filename) throws IOException {
    Path path = Paths.get(filename);
    return Files.readAllBytes(path);
  }
  
  private String convertBin2Base64(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }  
  
  private byte[] decodeBase64String(String theString) {
    return Base64.getDecoder().decode(theString);
  }
}
