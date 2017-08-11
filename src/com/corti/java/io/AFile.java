package com.corti.java.io;

public class AFile {
  private String path;
  private String name;

  public AFile(String thePath, String theName) {
    path = thePath;
    name = theName;
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  @Override
  public String toString() {
    return "AFile [path=" + path + ", name=" + name + "]";
  } 
  
}
