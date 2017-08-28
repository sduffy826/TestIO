package com.corti.java.nio;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class SimpleFileVisitorImpl extends SimpleFileVisitor<Path> {
  @Override
  public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) {
    if (basicFileAttributes.isRegularFile()) {
      System.out.println(path.toString() + " is a regular file, size: " +
        Long.toString(basicFileAttributes.size()));
    }
    else if (basicFileAttributes.isSymbolicLink()) {
      System.out.println(path.toString() + " is a symbolic link");
    }
    else {
      System.out.println(path.toString() + " is not sym link or regular file");
    }
    return java.nio.file.FileVisitResult.CONTINUE;
  }
  
  @Override
  public FileVisitResult postVisitDirectory(Path path, IOException ioException) {
    System.out.println(path.toString() + " postVisitDirectory");
    return java.nio.file.FileVisitResult.CONTINUE;
  }
  

  @Override
  public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) {
    String theDir = path.getFileName().toString();
    if (theDir.startsWith(".")) {
      return java.nio.file.FileVisitResult.SKIP_SUBTREE;
    }
    System.out.println(path.toString() + " preVisitDirectory, dir: " + theDir);
    return java.nio.file.FileVisitResult.CONTINUE;
  }
  
  
  @Override
  public FileVisitResult visitFileFailed(Path path, IOException ioException) {
    System.out.println(path.toString() + " visitFileFailed");
    return java.nio.file.FileVisitResult.CONTINUE;
  }
  
  
}
