import java.util.*;
public class ADir {
  private String path;
  private String name;
  private Vector files = new Vector();
  private Vector dirs  = new Vector();
  
  public ADir(String pathName, String fileName)
  {
	  path = pathName;
	  name = fileName;
  }
  public String getName()
  {
	  return name;
  }
  public String getPath()
  {
	  return path;
  }
  public void addFile(AFile f)
  {
	  files.addElement(f);
  }
  public void addDir(ADir d)
  {
	  dirs.addElement(d);	  
  }
  public Vector getFiles()
  {
	  return files;
  }
  public Vector getDirs()
  {
	  return dirs;
  }
}
