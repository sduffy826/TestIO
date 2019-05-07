package com.corti.files;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GetDirectoriesFromPath {
  private static final int num2Process = -1;  // -1 to do all records
  private String startingPath          = null;
  private String outputName            = "getDirectoriesFromPath.csv";
  private List<Path> fileList;
  List<PathMatcher> pathMatchers;
  
  // Default constructor
  GetDirectoriesFromPath() { }
  
  // Mainline
  public static void main(String[] args) throws Exception {
    GetDirectoriesFromPath me = new GetDirectoriesFromPath();                      
    me.runIt(args);
  }
  
  // Main processing
  public void runIt(String[] args) throws Exception {    
    fileList     = new ArrayList<Path>(500);            // List of directories
    startingPath = (args.length > 0 ? args[0] : "c:/seanduff/workspace");
    if (args.length > 1) outputName = args[1];
    
    Path basePath               = Paths.get(startingPath);
    String startingAbsolutePath =  basePath.toAbsolutePath().toString();
    System.out.println("startingAbsolutePath: " + startingAbsolutePath);
        
    FileSystem fileSystem = FileSystems.getDefault();
    pathMatchers = new ArrayList<PathMatcher>(5);
    pathMatchers.add(fileSystem.getPathMatcher("glob:**/target*"));
    pathMatchers.add(fileSystem.getPathMatcher("glob:**/workspace/.metadata*"));
    pathMatchers.add(fileSystem.getPathMatcher("glob:**/workspace/.recommenders*"));
             
    fileList.add(basePath);  
    addPathsFromPath(fileList);
    
    // If it has more data than we want to process then trim list to size we want
    if (num2Process > 0 && fileList.size() > num2Process)  
      fileList = fileList.subList(0, num2Process);
            
    Path outputPath = Paths.get(outputName);
    System.out.println("Writing directories to " + outputPath.getFileName());
    try(BufferedWriter writer = Files.newBufferedWriter(outputPath))  {
      for (Path thePath : fileList) {
        writer.write("\"" + thePath.toAbsolutePath().toString() + "\"");
        writer.newLine();
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
    System.out.println("Done");    
  }
      
  // Get all the files from the path passed in, will recurse depth
  private void addPathsFromPath(List<Path> pathList) {
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("skippyList")))  {
    for (int idx = 0; idx < pathList.size(); idx++) {
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(pathList.get(idx))) {
        for (Path entry : stream) {
          // if (DEBUGIT) System.out.println(entry.toString());
          if (Files.isDirectory(entry)) {            
            boolean skipIt = false;
            
            for (PathMatcher pathMatcher: pathMatchers) {
              if (pathMatcher.matches(entry.toAbsolutePath()) == true ) {
                skipIt = true;
                writer.write(entry.toAbsolutePath().toString());
                writer.newLine();                             
              }
            }
            if (skipIt == false) pathList.add(entry);
          }
        }
      } catch (IOException e) {      
        e.printStackTrace();
      }
    }
    }
    catch (IOException e) { }
  }

}  