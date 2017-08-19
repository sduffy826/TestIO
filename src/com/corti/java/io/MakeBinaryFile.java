package com.corti.java.io;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.Random;

public class MakeBinaryFile {

  public static void main(String[] args) throws Exception {
    String outFile = "/RandomBinaryFile.bin";
    DataOutputStream dos = new DataOutputStream(new FileOutputStream(outFile));

    int sizeInMegabytes = 10;
    int nbDesiredBytes = sizeInMegabytes * 1024 * 1024;
    int bufferSize = 1024;
    byte[] buffer = new byte[bufferSize];
    Random r = new Random();

    System.out.println("Patience creating file with: " +
        NumberFormat.getIntegerInstance().format(nbDesiredBytes));
    int nbBytes = 0;
    while (nbBytes < nbDesiredBytes) {
      int nbBytesToWrite = Math.min(nbDesiredBytes - nbBytes, bufferSize);
      byte[] bytes = new byte[nbBytesToWrite];
      r.nextBytes(bytes);
      dos.write(bytes);
      nbBytes += nbBytesToWrite;
    }
    dos.flush();
    dos.close();
    System.out.println("Done writing: " + outFile);
  }
}