import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Base64;

public class CompareStrings {
  private static String uploadString;
  private static String downloadString;
  
  public static void main(String[] args) {
    uploadString = "UEsDBBQAAAAAAI10JUtiGg/5EQAAABEAAAAIAAAAdGVzdC50eHRUaGlzIGlzIGEgdGVzdC4NClBLAQIUABQAAAAAAI10JUtiGg/5EQAAABEAAAAIAAAAAAAAAAEAIAAAAAAAAAB0ZXN0LnR4dFBLBQYAAAAAAQABADYAAAA3AAAAAAA=";
    downloadString = "UEsDBBQAAAAAAI10JUtiGg/5EQAAABEAAAAIAAAAdGVzdC50eHRUaGlzIGlzIGEgdGVzdC4NClBLAQIUABQAAAAAAI10JUtiGg/5EQAAABEAAAAIAAAAAAAAAAEAIAAAAAAAAAB0ZXN0LnR4dFBLBQYAAAAAAQABADYAAAA3AAAAAAA=";
    
    uploadString = "UEsDBBQACAAIAAt9JUsAAAAAAAAAAAAAAAAIAAAAdGVzdC50eHQLycgsVgCiRIWS1OISPV4uAFBLBwhiGg/5EQAAABEAAABQSwECFAAUAAgACAALfSVLYhoP+REAAAARAAAACAAAAAAAAAAAAAAAAAAAAAAAdGVzdC50eHRQSwUGAAAAAAEAAQA2AAAARwAAAAAA";
    downloadString = "UEsDBBQACAAIAAt9JUsAAAAAAAAAAAAAAAAIAAAAdGVzdC50eHQLycgsVgCiRIWS1OISPV4uAFBLBwhiGg\\5EQAAABEAAABQSwECFAAUAAgACAALfSVLYhoP+REAAAARAAAACAAAAAAAAAAAAAAAAAAAAAAAdGVzdC50eHRQSwUGAAAAAAEAAQA2AAAARwAAAAAA";
    
    
    char[] downChars = downloadString.toCharArray();
    char[] upChars = uploadString.toCharArray();
    
    int allMatch = (downChars.length - upChars.length) - 1;  // Lens match value will be -1
    for (int i = 0; i < upChars.length; i++) {
      if (upChars[i] != downChars[i]) {
        System.out.println("Diff at: " + Integer.toString(i) + " up: " + upChars[i] +
                          " down: " + downChars[i]);
        if (allMatch == -1) allMatch = i;
      }
    }
    // If decoded byte array differs from original mark the place it's different
    System.out.println("");
    if (allMatch == -1) {
      System.out.println("\n\nEverthing matches");
    }
    else {
      System.out.println(String.format("%1$"+(1*allMatch+1)+"s", "|")+"\nBytes Differ");
    }    
  }
}
