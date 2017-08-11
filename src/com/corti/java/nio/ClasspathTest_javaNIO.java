package com.corti.java.nio;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClasspathTest_javaNIO {
  
    public static void main(String args[])
	{
	  String theDelim = ":";
	  String theOS = System.getProperty("os.name");
	  System.out.println("compare value:" + theOS.substring(0,7) + ":");
	  System.out.println(theOS.substring(0,7).compareToIgnoreCase("WINDOWS"));
	  if (theOS.length() > 5) {
	    if (theOS.substring(0,7).compareToIgnoreCase("WINDOWS") == 0) {
	      theDelim = ";";
	    }
	  }
	  System.out.println("Delimitter: " + theDelim);
	  
	  for (String argLine: args) {
	    System.out.println("args line: " + argLine);
	    String splitLines[] = argLine.split(theDelim);
	    for (String subWord: splitLines) {
	      System.out.println("split/subWord: " + subWord);
	      Path thePath = Paths.get(subWord);  // Convert to path
	      System.out.println(Files.exists(thePath) ? 
	                               "Exists" : "Does not exist");
	    }
	  }	 
	}
}