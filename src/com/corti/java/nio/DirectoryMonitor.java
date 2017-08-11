package com.corti.java.nio;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import static java.nio.file.StandardWatchEventKinds.*;

public class DirectoryMonitor {
  private Path path = null;
  private WatchService watchService = null;

  private void init() {
    path = Paths.get("c:\\temp\\temp2\\");
    try {
      watchService = FileSystems.getDefault().newWatchService();
      path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
    } catch (IOException e) {
      System.out.println("IOException"+ e.getMessage());
    }
  }
  /**
   * The police will start making rounds 
   */
  private void doRounds() {
    WatchKey key = null;
    while(true) {
      try {
        key = watchService.take();
        for (WatchEvent<?> event : key.pollEvents()) {
          Kind<?> kind = event.kind();
          Path pathModified = (Path)event.context();
          System.out.println("Event on " + pathModified.toString() + " is " + kind);
        }
      } catch (InterruptedException e) {
        System.out.println("InterruptedException: "+e.getMessage());
      }
      System.out.println("Loop...");
      boolean reset = key.reset();  // Resets watch service back to ready state
      if(!reset)
        break;
    }
  }

  public static void main(String[] args) {
    DirectoryMonitor monitor = new DirectoryMonitor();
    monitor.init();
    monitor.doRounds();
  }
}
