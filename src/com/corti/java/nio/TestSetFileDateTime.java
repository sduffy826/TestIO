package com.corti.java.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.IIOException;

public class TestSetFileDateTime {

  public static void main(String[] args) {
    TestSetFileDateTime testSetFileDateTime = new TestSetFileDateTime();
    
    Path path = testSetFileDateTime.getPath("c:\\seanduff\\test.001");
    testSetFileDateTime.changeFileDateTime(path,  null);
    testSetFileDateTime.showFileAttributes(path);
  }

  public String getISODateTime(Long millis) {
    ZonedDateTime zoneDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("America/New_York"));
    LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("Z"));
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    //formatter.for
    System.out.println("ZonedDateTime: " + zoneDateTime.toString());
      
    return formatter.format(localDateTime);    
  }
  
  
  public void showFileAttributes(Path path) {
    BasicFileAttributeView basicFileAttributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class);
    try {
      System.out.println("lastAccesstime(): " + 
         getISODateTime(basicFileAttributeView.readAttributes().lastAccessTime().toMillis()));

      // will return the last access time.
      System.out.println("basicFileAttributeView.readAttributes().lastAccessTime().toMillis():" +
          getISODateTime(basicFileAttributeView.readAttributes().lastAccessTime().toMillis()));

      // will return the last time the file was changed.
      System.out.println("basicFileAttributeView.readAttributes().lastModifiedTime().toMillis():" +
          getISODateTime(basicFileAttributeView.readAttributes().lastModifiedTime().toMillis()));
     
      // will return the creation time.
      System.out.println("basicFileAttributeView.readAttributes().creationTime().toMillis():" +
          getISODateTime(basicFileAttributeView.readAttributes().creationTime().toMillis()));  
    } catch (IOException e) {
      e.printStackTrace();
    };

  }
  
  public Path getPath(String fileNameAndPath) {
    Path p = Paths.get(fileNameAndPath);
    return p;
  }
  
  public boolean changeFileDateTime(Path path, LocalDateTime localDateTime) {
    Long anHour = 60 * 60 * 1000L;
    try {    
     File file = path.toFile();
     System.out.println("lastModified: " + file.lastModified());
     System.out.println("getParentFile: " + file.getParentFile());
     System.out.println("getAbsolutePath: " + file.getAbsolutePath());
     System.out.println("getName: " + file.getName());
     System.out.println("getPath: " + file.getPath());
     System.out.println("getCanonicalPath: " + file.getCanonicalPath());
     System.out.println("isDirectory: " + file.isDirectory());
     
     //file.setLastModified(file.lastModified() + anHour );
     //System.out.println("new Date/Time: " + file.lastModified());
     
    } catch (IOException e) {
      System.err.println("Exception thrown: " + e);
    }
    return false;
    
//    try {
//        Calendar c = Calendar.getInstance();
//        c.set(2010, Calendar.MARCH, 20);
//        Files.setAttribute(p, "creationTime", FileTime.fromMillis(c.getTimeInMillis()));
//    } catch (IOException e) {
//        System.err.println("Cannot change the creation time. " + e);
//    }
  }
}
