package com.corti.files;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.corti.javalogger.LoggerUtils;
import com.corti.jsonutils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;

public class TestDosAttributes {
  private Logger logger;
  private JsonUtils jsonUtils;
  private Instant startInstant;
  private List<Path> fileList;  
  private List<String> jsonObjectList;
  private String startingPath; 
  
  // Default constructor
  TestDosAttributes() { }
  
  // Mainline
  public static void main(String[] args) throws Exception {
    TestDosAttributes me = new TestDosAttributes();
    me.runIt(args);
  }
  
  public void runIt(String[] args) throws Exception {      
    logger    = (new LoggerUtils()).getLogger("TestDosAttributes", "TestDosAttributesLogger");
    jsonUtils = new JsonUtils();
    jsonUtils.setFailOnUnknowProperties(false);
    
    startInstant = null;
    
    fileList       = new ArrayList<Path>(2000);   // List of files
    jsonObjectList = new ArrayList<String>(2000); // List of Json FileAttributes for the files
    
    startingPath = (args.length > 0 ? args[0] : "c:/seanduff/workspace/TestIO/src");

    // Add all the paths to fileList
    Path path = Paths.get(startingPath);
    addPathsFromPath(path, fileList);
    logger.info("Size of fileList is: " + fileList.size());    
    
    getElapsedTimeInMilliseconds(true);
    
    // Process each file in the list 
    for (Path thePath : fileList) {
      try {         
        FileStore fs = Files.getFileStore(thePath);
        FileAttributes fileAttributes;
        if (fs.supportsFileAttributeView(PosixFileAttributeView.class)) {
          fileAttributes = new UnixFileAttributes(thePath);
        }
        else {
          fileAttributes = new DosFileAttributes(thePath);
        }
        
        // Convert FileAttributes to a json string and add it to the jsonObjectList
        jsonObjectList.add(jsonUtils.getJsonStringFromPojo(fileAttributes));      
      } catch (Exception e) {
        System.out.println("Exception raised with " + thePath.toString());
        e.printStackTrace();
      }
    }
  
    Double elapsedMillis = new Long(getElapsedTimeInMilliseconds(false)).doubleValue();
    logger.info("Elapsed time: " + Double.toString(elapsedMillis/1000.0));
    
    // ------------------------------------------------------------------
    // Down here is for testing, we'll convert the json objects back into
    //   FileAttributes
    // ------------------------------------------------------------------    
    List<FileAttributes> fAttr = new ArrayList<FileAttributes>(10);
    
    // Loop thru jsonObjectList and convert to FileAttributes
    for (int i = 0; i < jsonObjectList.size(); i++) {
      String tempString = jsonObjectList.get(i);      
      JsonNode jsonNode = jsonUtils.getJsonNodeForJsonString(tempString);
      
      logger.info(tempString);;
      logger.info(jsonUtils.prettifyIt(tempString));   
      
      // Get the name of the class that we should use to create the pojo... the
      //   FileAttributes is abstract, the concrete class is a child of it
      String fullClassName = jsonNode.get("className").asText();
      
      Class<?> someClass = Class.forName(fullClassName);
      logger.info("someClass: " + someClass.getName());
      
      /* We don't need to do this but I left it here for reference... it
       * basically just returns the class name without the package prefix
      int stripPos = fullClassName.lastIndexOf(".");
      fullClassName = fullClassName.substring(stripPos+1);
      logger.info("fullClassName:" + fullClassName);
      */
      
      String classNm = fullClassName+".class";
      logger.info("classNm: " + classNm);
      FileAttributes fa = (FileAttributes) jsonUtils.getPojoFromJsonNode(jsonNode, someClass);
      
      // Write the object created
      logger.info("Deserialized obj: " + fa.toString());
      fAttr.add(fa);
    }
        
    /*
    FileStore fs = Files.getFileStore(path);
    
    printDetails(fs, AclFileAttributeView.class);
    printDetails(fs, BasicFileAttributeView.class);
    printDetails(fs, DosFileAttributeView.class);
    printDetails(fs, FileOwnerAttributeView.class);
    printDetails(fs, PosixFileAttributeView.class);
    printDetails(fs, UserDefinedFileAttributeView.class);
      
    System.out.println("fileAttributes: " + fileAttributes.toString());
    */
    
  }

  // Get all the files from the path passed in, will recurse depth
  private void addPathsFromPath(Path _dirPath, List<Path> pathList) {
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(_dirPath)) {
      for (Path entry : stream) {
        if (Files.isDirectory(entry)) {
          addPathsFromPath(entry,pathList);
        }
        else {
          pathList.add(entry);
        }                
      }
    } catch (IOException e) {      
      e.printStackTrace();
    }
  }
    
  public static void printDetails(FileStore fs, Class<? extends FileAttributeView> attribClass) {
    boolean isSupported = fs.supportsFileAttributeView(attribClass);
    System.out.format("%s is supported %s%n", attribClass.getSimpleName(), isSupported);    
  } 
  
  private long getElapsedTimeInMilliseconds(boolean startIt) {
    if (startIt || startInstant == null) {
      startInstant = Instant.now(); 
      return 0;
    }
    else {
      Instant ending = Instant.now();
      return Duration.between(startInstant,  ending).toMillis();
    }
  } 
}
