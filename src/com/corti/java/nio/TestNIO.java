package com.corti.java.nio;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class TestNIO {
 
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    TestNIO myTest = new TestNIO();
    if (true) myTest.testPath();
    if (false) myTest.testFiles();
    if (true) myTest.testAttributes();
    if (false)  myTest.listDirs(Paths.get("/seanduff"));
    if (false)  myTest.listFiles(Paths.get("/seanduff"));
    if (false)  myTest.listFiles(Paths.get("/seanduff"),"*.txt");
    if (false)  myTest.listDirs(Paths.get("/home/dev/thisIsADummyFile.txt"));
    if (false) {
      List<String> aList = myTest.getDirsString("/seanduff/root");
      for (String aStr : aList) System.out.println(aStr);
    }
    if (false) myTest.testTree("c:/seanduff/workspace/TestIO"); ///home/dev/workspace/TestIO");
  }

  public void testAttributes() {
    String path = "demo.txt";

    try {
      Path file = Paths.get(path);
      System.out.println("getFileName: " + file.getFileName());
      
      BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
      System.out.println("creationTime     = " + attr.creationTime());
      System.out.println("lastAccessTime   = " + attr.lastAccessTime());
      System.out.println("lastModifiedTime = " + attr.lastModifiedTime());
      
      java.sql.Date theDate = new java.sql.Date(attr.lastModifiedTime().toMillis());
      java.sql.Time theTime = new java.sql.Time(attr.lastModifiedTime().toMillis());
      java.sql.Timestamp theTimeStamp = new java.sql.Timestamp(attr.lastModifiedTime().toMillis());
      System.out.println("sqlDate: " + theDate.toString());
      System.out.println("sqlTime: " + theTime.toString());
      System.out.println("sqlTimestamp: " + theTimeStamp.toString());

      System.out.println("isDirectory      = " + attr.isDirectory());
      System.out.println("isOther          = " + attr.isOther());
      System.out.println("isRegularFile    = " + attr.isRegularFile());
      System.out.println("isSymbolicLink   = " + attr.isSymbolicLink());
      System.out.println("size             = " + attr.size());
     
    }
    catch(IOException e){ e.printStackTrace(); }
  }
  public void testPath() {
    // A path is a reference to a file path, it is equivalent to 
    // java.io.File
    Path path = Paths.get("/home/dev/thisIsADummyFile.txt");
    path = Paths.get("~/thisIsADummyFile.txt");
    System.out.println("Number of name elements in the path (# nodes): " + path.getNameCount());
    System.out.println("File name:   " + path.getFileName());
    System.out.println("File root:   " + path.getRoot());
    System.out.println("File parent: " + path.getParent());
    System.out.println("toString:    " + path.toString());
    
    try {
      // Delete file if it exists, won't throw an exception if file not existing
      System.out.println(Files.deleteIfExists(Paths.get("c:\\temp\\temp2\\testdel.txt")));
      // This one throws an exception if not found
      Files.delete(Paths.get("c:\\temp\\temp2\\testdel.txt"));      
    } catch (IOException e) {
     // e.printStackTrace();
    }    
  }
  
  public void testFiles() {
    Path from = Paths.get("C:\\temp\\from.txt");
    Path to = Paths.get("C:\\temp\\temp2\\to.txt");
    //overwrite existing file, if exists
    CopyOption[] options = new CopyOption[]{
      // StandardCopyOption.REPLACE_EXISTING,
      StandardCopyOption.COPY_ATTRIBUTES
    }; 
    try {
      Files.copy(from, to, options);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  List<String> getDirsString(String aPath) {
    List<Path> pathList = getAllDirs(aPath);
    List<String> rtnList = new ArrayList<String>(pathList.size());
    for (Path aDir : pathList) {
      rtnList.add(aDir.toString());
    }
    return rtnList;
  }
  
  List<Path> getAllDirs(String aPath) {
    try {
      return Files.walk(Paths.get(aPath)).collect(Collectors.toList());
    }
    catch(Exception e) { e.printStackTrace(); }
    return null;
  }
  
  
  void listDirs(Path path) {
    try {
       DirectoryStream<Path> stream = Files.newDirectoryStream(path);        
       for (Path entry : stream) {
         if (Files.isDirectory(entry)) {
           System.out.println(entry.toString());
           listDirs(entry);
         }
         // files.add(entry);
    }
    } catch(Exception e) { e.printStackTrace(); }
  }
  
  void listFiles(Path path) {
    try {
       DirectoryStream<Path> stream = Files.newDirectoryStream(path);        
       for (Path entry : stream) {
         if (Files.isDirectory(entry) == false) {
           System.out.println(entry.toString());           
         }
    }
    } catch(Exception e) { e.printStackTrace(); }
  }
 
  void listFiles(Path path, String wildCard) {
    try {
       DirectoryStream<Path> stream = Files.newDirectoryStream(path, wildCard);        
       for (Path entry : stream) {
         if (Files.isDirectory(entry) == false) {
           System.out.println(entry.toString());           
         }
    }
    } catch(Exception e) { e.printStackTrace(); }
  }
 
  
  
  void listTree(Path path, String prefix) {
    try {
       DirectoryStream<Path> stream = Files.newDirectoryStream(path);        
       for (Path entry : stream) {
         if (Files.isDirectory(entry)) {
           System.out.println(entry.toString());
           listDirs(entry);
         }
         // files.add(entry);
    }
    } catch(Exception e) { }
  }
  
  private void testTree(String theDirectory) {
    Path directory2Process = Paths.get(theDirectory);
    
    SimpleFileVisitorImpl simpleFileVisitorImpl = new SimpleFileVisitorImpl();
    try {
      Files.walkFileTree(directory2Process, simpleFileVisitorImpl);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}
