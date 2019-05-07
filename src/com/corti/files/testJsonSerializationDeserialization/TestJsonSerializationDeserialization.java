package com.corti.files.testJsonSerializationDeserialization;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.corti.javalogger.LoggerUtils;
import com.corti.jsonutils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;

public class TestJsonSerializationDeserialization {

  public static void main(String[] args) throws Exception {  
    Logger logger = (new LoggerUtils()).getLogger("myLogger", "testJsonSerialization");
    JsonUtils jsonUtils = new JsonUtils();
  
    List<Vehicle> vehicles = new ArrayList<Vehicle>(2);
    List<String> jsonObjectList = new ArrayList<String>(2);
    List<Vehicle> serializedVehicles = new ArrayList<Vehicle>(2);
    
    vehicles.add(new Car("Silver", 4));
    vehicles.add(new Motorcycle("Red"));
    logger.info("Size of fileList is: " + vehicles.size());
        
    // Add serialized versions to jsonObjectList
    for (Vehicle aVehicle : vehicles) {
      try {
        jsonObjectList.add(jsonUtils.getJsonStringFromPojo(aVehicle));      
      } catch (Exception e) {
        System.out.println("Exception raised with " + aVehicle.toString());
        e.printStackTrace();
      }
    }
    
    //  Down here the jsonObjectList has elements to process
    for (int i = 0; i < jsonObjectList.size(); i++) {
      String tempString = jsonObjectList.get(i);      
      JsonNode jsonNode = jsonUtils.getJsonNodeForJsonString(tempString);
      
      logger.info(tempString);;
      logger.info(jsonUtils.prettifyIt(tempString));   
      
      String fullClassName = jsonNode.get("className").asText();
      
      Class<?> someClass = Class.forName(fullClassName);
      logger.info("someClass: " + someClass.getName());
      
      Vehicle vehicleObj = (Vehicle) jsonUtils.getPojoFromJsonNode(jsonNode, someClass);
      serializedVehicles.add(vehicleObj);
    }
  }
}
