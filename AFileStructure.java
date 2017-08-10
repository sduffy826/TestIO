import java.io.*;
import java.util.*;
public class AFileStructure {
  private String aDirName;
  private ADir myDir;
  private String result;
  public void setDirName(String argDirName)
  {
	  aDirName = argDirName;
  }
  public String getResult()
  {
	  return result;
  }
  public void build() 
  {
	//  File fileObj = new File(aDirName);
	  myDir = build(new File(aDirName));
  }
  public ADir build(File dirObj)
  {
	  if (!dirObj.exists())
		return null;
	  if (!dirObj.isDirectory())
		return null;
	  String tempPath = dirObj.getPath();
	  String tempName = dirObj.getName();
	  ADir tempDir = new ADir(tempPath,tempName);
	  
	  String[] allFiles = dirObj.list();
	  for (int i = 0; i < allFiles.length; i++)
	  {
		File anotherFile = new File(tempPath,allFiles[i]);
		if (anotherFile.isFile())
		{
		  tempDir.addFile(new AFile(tempPath, allFiles[i]));
		}
		else 
		  if (anotherFile.isDirectory())
		  {
			//File dirFile = new File(tempPath, allFiles[i]);
			//ADir tempDir2 = build(dirFile);
			ADir tempDir2 = build(anotherFile);
			if (tempDir2 != null)
			{
			  tempDir.addDir(tempDir2);
			}
		  }
	  }
	  return tempDir;
  }
	
  protected void outFile(AFile fObj, int theLevel)
  {
	String tempName = fObj.getName();
	result += repeat(" ",2*theLevel) + "File: " + tempName + "\n";
  }
  protected void outDir(ADir dObj, int theLevel)
  {
	String tempDir = dObj.getName();
	result += repeat(" ",2*theLevel) + "Dir: " + tempDir + "\n";
  }
  protected void outEndDir()
  {	  
  }
  public void list()
  {
	  if (myDir == null)
	  {
		  System.out.println("Not a valid directory");
		  return;
	  }
	  result = "";
	  outDir(myDir,0);
	  list(myDir,0);
	  outEndDir();
  }
  public void list(ADir holdDir, int theLevel)
  {
	  theLevel++;
	  Vector vecDir = holdDir.getDirs();
	  for (int i = 0; i < vecDir.size(); i++)
	  {
		  ADir dirTemp = (ADir)vecDir.elementAt(i);
		  outDir(dirTemp,theLevel);
		  list(dirTemp,theLevel);
		  outEndDir();
	  }
	  Vector vecFile = holdDir.getFiles();
	  for (int i = 0; i < vecFile.size(); i++)
	  {
		AFile fileTemp = (AFile)vecFile.elementAt(i);
		outFile(fileTemp,theLevel);
	  }
  }
  
  private String repeat(String tempString, int repCnt)
  {
	  StringBuffer strBuff = new StringBuffer(repCnt);
	  for (int i = 0; i < repCnt; i++)
	  {
		strBuff.append(tempString);
	  }
	  return strBuff.toString();
  }
}
