
import cmps369.OneStringArg;

public class FileAndDirectoryTest {

  /**
   * @param args
   */
  public static void main(String[] args) {
    if (args.length != 0) {
      OneStringArg o = (s) -> System.out.println(s);

      o.outIt("Before aFS instantiation");
      AFileStructure aFS = new AFileStructure();

      o.outIt("calling setDirName");
      aFS.setDirName(args[0]);

      o.outIt("Before build");
      aFS.build();

      o.outIt("Before list");
      aFS.list();

      o.outIt("Before println on result");
      System.out.println(aFS.getResult());
    } else
      System.out.println("Must pass directory to start with");
    // TODO Auto-generated method stub

  }
}
