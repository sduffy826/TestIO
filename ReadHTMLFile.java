
//import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class ReadHTMLFile {
	
  public static void main(String[] args) {
    String fileIn;
    if (args.length > 0) 
      fileIn = args[0];
	else {
	  System.out.println("Must pass in file to process");
	  return;
	}  
	  
	ParserGetter kit = new ParserGetter();
	HTMLEditorKit.Parser parser = kit.getParser();
	HTMLEditorKit.ParserCallback callback = new ReportAttributes();

	try {
	  BufferedReader inFile = new BufferedReader(new FileReader(fileIn));
	  parser.parse(inFile, callback, true);
	  inFile.close();
	} catch (IOException e) {
	    System.err.println(e);
	}
  }
}

class ReportAttributes extends HTMLEditorKit.ParserCallback {

  int indent = 0;
  boolean showAttributes, showTag = false;
  
  public String space() {
	  String theString = "";
	  for (int i = 0; i < indent; i++) {
		 theString += "  ";		  
	  }
	  return theString;
  }
  
  public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes, int position) {
	    if (showTag) 
	      System.out.println(space() + tag);
	    indent++;
	    if (showAttributes)
	      this.listAttributes(attributes);
  }

  private void listAttributes(AttributeSet attributes) {
    Enumeration e = attributes.getAttributeNames();
    while (e.hasMoreElements()) {
      Object name = e.nextElement();
      Object value = attributes.getAttribute(name);
      System.out.println(space() + "name: " + name.toString() + " value:" + value.toString());
      if (!attributes.containsAttribute(name.toString(), value)) {
        System.out.println("containsAttribute() fails");
      }
      if (!attributes.isDefined(name.toString())) {
        System.out.println("isDefined() fails");
      }
      System.out.println(space() + name + "=" + value);
    }
  }
  
  public void handleText(char[] chArray, int thePos) {
	  System.out.print(space());
	  
	  System.out.println(chArray);
	  // System.out.println(" pos: " + thePos);  // Pos maybe pos in file, useless
  }

  public void handleEndTag(HTML.Tag tag, int position) {
	    // System.out.println(space() + "end " + tag + " pos: " + position);
	    indent--;
  }

  public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, int position) {
    this.listAttributes(attributes);
  }
}

class ParserGetter extends HTMLEditorKit {
  public HTMLEditorKit.Parser getParser() {
    return super.getParser();
  }
}
	
/*
	-----------
	 
	 public static void main(String[] args) {
	 
	  try {
	 
	     SAXParserFactory factory = SAXParserFactory.newInstance();
	     SAXParser saxParser = factory.newSAXParser();
	     String fileIn;   	 
	     
	     DefaultHandler handler = new DefaultHandler() {
	       boolean bfname = false;     
	 
	       public void startElement(String uri, String localName,
	          String qName, Attributes attributes)
	          throws SAXException {
	 
	          System.out.println("Start Element :" + qName);
              if (attributes != null) {
	            for (int i = 0; i < attributes.getLength(); i++) {
	              System.out.println(attributes.getLocalName(i) + "=\"" + attributes.getValue(i) + "\"");
                 }
	          }
	          
	          System.out.println(uri + "/" + localName + "/");
	 
	          if (qName.equalsIgnoreCase("FIRSTNAME")) {
	             bfname = true;
	          }	 
	       }
	 
	       public void endElement(String uri, String localName, String qName)
	            throws SAXException {
	 
	            System.out.println("End Element :" + qName);	 
	       }
	 
	       public void characters(char ch[], int start, int length)
	           throws SAXException {
	 
	           if (bfname) {
	              System.out.println("First Name : " + new String(ch, start, length));
	              bfname = false;
	           }
	           else
	        	   System.out.println("Value " + new String(ch, start, length));
	        }
	 
	      };
	 
	      if (args.length > 0) 
		    fileIn = args[0];
		  else
			fileIn = "c:" + File.separatorChar + "ReadFile.in";
	      saxParser.parse(fileIn, handler);
	 
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	 
	}
*/
//
//
//public class ReadXxMLFile {
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		try {
//			  String fileIn, fileOut, dateChk, str;
//			  
//		      int lineCnt, outCnt;
//		      
//			  if (args.length > 0) 
//				fileIn = args[0];
//			  else
//				fileIn = "c:" + File.separatorChar + "ReadFile.in";
//			  
//			  if (args.length > 1)
//				fileOut = args[1];
//			  else
//				fileOut = "c:" + File.separatorChar + "ReadFile.out";
//			
//			  if (args.length > 2)
//				dateChk = args[2];
//			  else
//				dateChk = "10-30-2009";
//		      
//		      FileWriter fw = new FileWriter(fileOut,false);
//	          BufferedWriter out = new BufferedWriter(fw);
//		      BufferedReader in = new BufferedReader(new FileReader(fileIn));
//	        
//	          lineCnt = outCnt = 0;
//	          while ((str = in.readLine()) != null)
//	          {
//	        	  lineCnt++;
//	        	  if ( str.trim().length() > 0 ) {
//	        		if ( str.startsWith(dateChk) == false) {
//	        			out.write(str);
//	        			out.newLine();
//	        			outCnt++;
//	        		}
//	        		else
//	        	      System.out.println("Found record with Date, bypassed");
//	        	  }
//	          }
//	          in.close();
//	          out.close();
//	          System.out.print("All Done, read: ");
//	          System.out.print(lineCnt);
//	          System.out.println(" records");
//	          System.out.print("         wrote: ");
//	          System.out.print(outCnt);
//	          System.out.println(" records");
//			}
//			catch (FileNotFoundException e) {
//				System.out.println(e);
//				return;
//			}
//			catch (IOException e) {
//				System.out.println(e);
//			}
//			
//
//	}
//
//}
