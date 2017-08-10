
public class FileAndDirectoryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
	  if (args.length != 0)
	  {
		AFileStructure aFS = new AFileStructure();
		aFS.setDirName(args[0]);
		aFS.build();
		aFS.list();
		System.out.println(aFS.getResult());
	  }
	  else
		System.out.println("Must pass directory to start with");
		// TODO Auto-generated method stub

	}

}
