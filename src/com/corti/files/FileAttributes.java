package com.corti.files;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import checkSumPackage.CheckSum;

// We don't want instantiationMethod to be deserialized, but it can be serialized, you
//  can add other attributes to array if needed
@JsonIgnoreProperties(value={ "instantiationMethod" }, allowGetters=true)
public abstract class FileAttributes {
  // These statics are used to represent how the object was instantiated; when instantiated
  //   from DEFAULT it's probably from deserialization so the path could reflect 
  private static final int DEFAULT = 0;
  private static final int FROMFILENAMEANDPATH = 1;
  private static final int FROMPATH = 2;
  
  protected static final String CHECKSUMALGORITHM = "SHA-256";
  private int instantiationMethod;
  
  protected String className;
  protected Path path;
  protected String absolutePath;  // Needed when instantiated from json object
  
  @JsonSerialize(using = com.corti.jsonutils.FileTimeSerializer.class)
  @JsonDeserialize(using = com.corti.jsonutils.FileTimeDeSerializer.class)
  protected FileTime creationTime;
  
  @JsonSerialize(using = com.corti.jsonutils.FileTimeSerializer.class)
  @JsonDeserialize(using = com.corti.jsonutils.FileTimeDeSerializer.class)
  protected FileTime lastAccessTime;
  
  @JsonSerialize(using = com.corti.jsonutils.FileTimeSerializer.class)
  @JsonDeserialize(using = com.corti.jsonutils.FileTimeDeSerializer.class)
  protected FileTime lastModifiedTime;
  protected boolean isDirectory;
  protected boolean isRegularFile;
  protected boolean isSymbolicLink;
  protected boolean isOther;
  protected long sizeInBytes;
  protected String checkSumValue;

  protected String exceptionMessage;
  
  // This one is really for deserialization; that's the only time objects should be
  // instantiated without knowing path or filename
  FileAttributes() {
    this.path = null;
    this.instantiationMethod = DEFAULT;
    init();        
  }
  
  FileAttributes(Path path) throws Exception {
    this.path = path;
    this.instantiationMethod = FROMPATH;
    init();
  }
  
  FileAttributes(String fileNameAndPath) throws Exception {
    this.path = Paths.get(fileNameAndPath);
    this.instantiationMethod = FROMFILENAMEANDPATH;
    init();
  }
  
  private void init() {    
    this.className = this.getClass().getName();
    this.exceptionMessage = "";
    if (this.path != null) {
      try {
        BasicFileAttributes basicFileAttributes = Files.readAttributes(this.path, BasicFileAttributes.class);    
        
        this.creationTime     = basicFileAttributes.creationTime();
        this.lastAccessTime   = basicFileAttributes.lastAccessTime();
        this.lastModifiedTime = basicFileAttributes.lastModifiedTime();
        this.isDirectory      = basicFileAttributes.isDirectory();
        this.isRegularFile    = basicFileAttributes.isRegularFile();
        this.isSymbolicLink   = basicFileAttributes.isSymbolicLink();
        this.isOther          = basicFileAttributes.isOther();
        this.sizeInBytes      = basicFileAttributes.size();
        this.checkSumValue    = "";
        if (this.isRegularFile) { 
          try {
            this.checkSumValue = CheckSum.getFileCheckSumValue(path, CHECKSUMALGORITHM);
          }
          catch (Exception e) {
            System.out.println("CheckSum raised error on:" + path.toString() );
          }
        }
        
        this.absolutePath = "";
        Path absolutePath = path.toAbsolutePath();
        if (absolutePath != null ) {
          this.absolutePath = absolutePath.toString();
        }
      
      } catch (Exception e) {
        exceptionMessage = e.getMessage();
      }      
    }
  }

  // All the getters
  public String getChecksumAlgorithm() {
    return CHECKSUMALGORITHM;
  }   
  public Path getPath() {
    return path;
  }
  public String getFileName() {
    return path.getFileName().toString();
  }
  public String getPathRoot() {
    Path root = path.getRoot();
    return root != null ? root.toString() : null;
  }
  public String getPathParent() {
    Path parent = path.getParent();
    return parent != null ? parent.toString() : null;
  }
  public String getAbsolutePath() {
    return absolutePath;
  }   
  public FileTime getCreationTime() {
    return creationTime;
  }
  public FileTime getLastAccessTime() {
    return lastAccessTime;
  }
  public FileTime getLastModifiedTime() {
    return lastModifiedTime;
  }
  public boolean isDirectory() {
    return isDirectory;
  }
  public boolean isRegularFile() {
    return isRegularFile;
  }
  public boolean isSymbolicLink() {
    return isSymbolicLink;
  }
  public boolean isOther() {
    return isOther;
  }
  public long getSizeInBytes() {
    return sizeInBytes;
  }
  public String getCheckSumValue() {
    return checkSumValue;
  }
  public int getInstantiationMethod() {
    return instantiationMethod;
  }
  public String getClassName() {
    return className;
  }
  public String getExceptionMessage() {
    return exceptionMessage;
  }

  // return boolean if an exception was raised with this object
  public boolean hasException() {
    return (exceptionMessage.length() > 0);
  }

  // Setters; these are called when object is deserialized (i.e. from json object)  
  public void setPath(Path path) {
    this.path = path;
  }
  public void setCreationTime(FileTime creationTime) {
    this.creationTime = creationTime;
  }
  public void setLastAccessTime(FileTime lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }
  public void setLastModifiedTime(FileTime lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }
  public void setDirectory(boolean isDirectory) {
    this.isDirectory = isDirectory;
  }
  public void setRegularFile(boolean isRegularFile) {
    this.isRegularFile = isRegularFile;
  }
  public void setSymbolicLink(boolean isSymbolicLink) {
    this.isSymbolicLink = isSymbolicLink;
  }
  public void setOther(boolean isOther) {
    this.isOther = isOther;
  }
  public void setSizeInBytes(long sizeInBytes) {
    this.sizeInBytes = sizeInBytes;
  }
  public void setCheckSumValue(String checkSumValue) {
    this.checkSumValue = checkSumValue;
  }  
  public void setAbsolutePath(String absolutePath) {
    this.absolutePath = absolutePath;
  }

  @Override
  public String toString() {
    return "FileAttributes [instantiationMethod=" + instantiationMethod
        + ", path=" + path + ", fileName=" + getFileName() 
        + ", pathRoot=" + getPathRoot() + ", getPathParent=" + getPathParent() 
        + ", getAbsolutePath=" + absolutePath        
        + ", creationTime=" + creationTime
        + ", lastAccessTime=" + lastAccessTime + ", lastModifiedTime="
        + lastModifiedTime + ", isDirectory=" + isDirectory + ", isRegularFile="
        + isRegularFile + ", isSymbolicLink=" + isSymbolicLink + ", isOther="
        + isOther + ", sizeInBytes=" + sizeInBytes + ", checkSumValue="
        + checkSumValue + ", exceptionMessage=" + exceptionMessage + "]";
  }

}
