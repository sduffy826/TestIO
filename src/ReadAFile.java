
import java.util.*;
import java.io.*;

public class ReadAFile {
  public static void main(String args[]) {
    try {
      BufferedReader in = new BufferedReader(new FileReader(args[0]));
      int lineCnt = 0;
      String str;
      while ((str = in.readLine()) != null) {
        lineCnt++;
      }
      in.close();
      System.out.println("read: " + Integer.toString(lineCnt));
    } catch (FileNotFoundException e) {
      System.out.println(e);
      return;
    } catch (IOException e) {
      System.out.println(e);
    }

  }
}
