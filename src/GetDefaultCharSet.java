import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class GetDefaultCharSet {

  public static void main(String[] args) {
    System.out.println("Default charset: " + GetDefaultCharSet.getDefaultCharSet());    
    // Show another way
    System.out.println(Charset.defaultCharset().name());
  }
  
  private static String getDefaultCharSet() {
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new ByteArrayOutputStream());
    return outputStreamWriter.getEncoding();
  }
  

}
