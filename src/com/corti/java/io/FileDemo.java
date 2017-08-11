package com.corti.java.io;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class FileDemo {

  BufferedReader consoleReader;
  private final File demoFile = new File("demo.txt");

  public FileDemo() {
    InputStreamReader isr = new InputStreamReader(System.in);
    consoleReader = new BufferedReader(isr);
  }

  public void dumpFileInfo() {
    try {
      System.out.println("getAbsolutePath  " + demoFile.getAbsolutePath());
      System.out.println("lastModified     " + demoFile.lastModified());
      System.out.println("toString         " + demoFile.toString());
      System.out.println(
          "getCanonicalPath " + demoFile.getCanonicalPath().toString());
      System.out.println("getName          " + demoFile.getName());
      System.out.println("getPath          " + demoFile.getPath());
      System.out.println("getParent        " + demoFile.getParent());
      System.out.println("isDirectory      " + demoFile.isDirectory());
      System.out.println("isFile           " + demoFile.isFile());
      System.out.println("canRead          " + demoFile.canRead());
      System.out.println("canWrite         " + demoFile.canWrite());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public String readContentsFromUser() throws IOException {
    System.out.println("Enter a string/sentance:  ");
    return consoleReader.readLine();
  }

  public void writeLowerCaseToFile(String contents) throws IOException {
    FileOutputStream fos = new FileOutputStream(demoFile);
    PrintStream fileStream = new PrintStream(fos);
    fileStream.println(contents.toLowerCase());
    fileStream.close();
  }

  public String readLowerCaseFromFile() throws IOException {
    FileReader fr = new FileReader(this.demoFile);
    BufferedReader fileReader = new BufferedReader(fr);
    String contents = fileReader.readLine();
    fileReader.close();
    return contents;
  }

  public static void main(String[] args) {
    FileDemo fd = new FileDemo();
    String contents;
    try {
      contents = fd.readContentsFromUser();
      fd.writeLowerCaseToFile(contents);
      contents = fd.readLowerCaseFromFile();
      System.out.println(contents);
      fd.dumpFileInfo();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
