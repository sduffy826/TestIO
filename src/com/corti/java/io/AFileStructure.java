package com.corti.java.io;
import java.io.*;
import java.util.*;

import com.corti.lambda.OneStringArg;

public class AFileStructure {
  private static final boolean DEBUG = true;
  private static final int MAXLEVEL = 3;  // Maximum directory level to go
  
  private String aDirName;
  private ADir myDir;
  private String result;
  private OneStringArg o;

  public AFileStructure() {
    // Define the lambda expression to output a string (s), done this way for compactness :)
    o = (s) -> System.out.println(s);
  }

  public void setDirName(String argDirName) {
    aDirName = argDirName;
  }

  public String getResult() {
    return result;
  }

  public void build() {
    // File fileObj = new File(aDirName);
    myDir = build(new File(aDirName),1);
  }

  // method builds the 
  private ADir build(File dirObj, int currentDepth) {
    if (!dirObj.exists())
      return null;
    if (!dirObj.isDirectory())
      return null;
   
    String tempPath = dirObj.getPath();
    String tempName = dirObj.getName();
    
    // Create new ADir object based on path and name
    ADir tempDir = new ADir(tempPath, tempName);

    // We'll loop thru all the files in the directory, we'll add files or directories to the
    // associated vector
    String[] allFiles = dirObj.list();
    for (int i = 0; i < allFiles.length; i++) {
      File anotherFile = new File(tempPath, allFiles[i]);
      if (anotherFile.isFile()) {
        tempDir.addFile(new AFile(tempPath, allFiles[i]));
      } else if (anotherFile.isDirectory()) {
        // Call build to return the contents of this directory, we then add that to the vector
        // of directories
        if (currentDepth <= MAXLEVEL) {          
          ADir tempDir2 = build(anotherFile,currentDepth+1);
          if (tempDir2 != null) {
            tempDir.addDir(tempDir2);
          }          
        }
        else o.outIt("** MAX Directory depth level hit, didn't process: " + anotherFile.getAbsolutePath());
      }
    }
    if (DEBUG) o.outIt("Leaving build, detail: " + tempDir.toString());
    return tempDir;
  }

  protected void outFile(AFile fObj, int theLevel) {
    String tempName = fObj.getName();
    result += repeat(" ", 2 * theLevel) + "File: " + tempName + "\n";
  }

  protected void outDir(ADir dObj, int theLevel) {
    String tempDir = dObj.getName();
    result += repeat(" ", 2 * theLevel) + "Dir: " + tempDir + "\n";
  }

  protected void outEndDir() {
  }

  public void list() {
    if (myDir == null) {
      System.out.println("Not a valid directory");
      return;
    }
    result = "";
    outDir(myDir, 0);
    list(myDir, 0);
    outEndDir();
  }

  public void list(ADir holdDir, int theLevel) {
    theLevel++;
    Vector vecDir = holdDir.getDirs();
    for (int i = 0; i < vecDir.size(); i++) {
      ADir dirTemp = (ADir) vecDir.elementAt(i);
      outDir(dirTemp, theLevel);
      list(dirTemp, theLevel);
      outEndDir();
    }
    Vector vecFile = holdDir.getFiles();
    for (int i = 0; i < vecFile.size(); i++) {
      AFile fileTemp = (AFile) vecFile.elementAt(i);
      outFile(fileTemp, theLevel);
    }
  }

  private String repeat(String tempString, int repCnt) {
    StringBuffer strBuff = new StringBuffer(repCnt);
    for (int i = 0; i < repCnt; i++) {
      strBuff.append(tempString);
    }
    return strBuff.toString();
  }
}
