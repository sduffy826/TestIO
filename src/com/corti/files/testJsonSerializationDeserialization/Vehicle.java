package com.corti.files.testJsonSerializationDeserialization;

public abstract class Vehicle {
  protected static final boolean DEBUGIT = true;
  private String className;
  private int wheels;
  private String engineType;
  private String color;
  Vehicle() { 
    className = engineType = color = "Unknown";
    wheels = 0;
    this.className = this.getClass().getName();
    if (DEBUGIT) System.out.println("Vehicle default constructor");
  }
  Vehicle(int wheels, String color, String engineType) {
    this.wheels = wheels;
    this.color = color;
    this.engineType = engineType;
    this.className = this.getClass().getName();
    if (DEBUGIT) System.out.println("Vehicle constructor wth args" + this.toString());
  }
  
  public String getClassName() {
    if (DEBUGIT) System.out.println("Vehicle->getClassName()");
    return className;
  }
  public int getWheels() {
    if (DEBUGIT) System.out.println("Vehicle->getWheels()");
    return wheels;
  }
  public String getEngineType() {
    if (DEBUGIT) System.out.println("Vehicle->getEngineType()");
    return engineType;
  }
  public String getColor() {
    if (DEBUGIT) System.out.println("Vehicle->getColor()");
    return color;
  }
  public void setClassName(String className) {
    this.className = className;
    if (DEBUGIT) System.out.println("Vehicle->setClassName(), className: " + className);
  }
  public void setWheels(int wheels) {
    this.wheels = wheels;
    if (DEBUGIT) System.out.println("Vehicle->setWheels(), wheels: " + Integer.toString(wheels));
  }
  public void setEngineType(String engineType) {
    this.engineType = engineType;
    if (DEBUGIT) System.out.println("Vehicle->setEngineType(), engineType: " + engineType);
  }  
  public void setColor(String color) {
    this.color = color;
     if (DEBUGIT) System.out.println("Vehicle->setColor(), color: " + color);
  }
  @Override
  public String toString() {
    return "(Vehicle: className=" + className + ", wheels=" + wheels + 
            ", engineType=" + engineType + ", color=" + color + ")";
  }
  
}
