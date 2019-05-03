package com.corti.files.testJsonSerializationDeserialization;

public class Motorcycle extends Vehicle {
  private int passengers;
  Motorcycle() { 
    super(1, "Unknown", "Gas");
    this.passengers = 1;
    if (DEBUGIT) System.out.println("Motorcycle default constructor");
  }
  Motorcycle(String color) {
    super(2, color, "Gas");
    this.passengers = 1;
    if (DEBUGIT) System.out.println("Motorcycle constructor with color=" + color);
  }
  public void setPassengers(int passengers) {
    this.passengers = passengers;
    if (DEBUGIT) System.out.println("Motorcycle->setPassengers(), passengers: " + Integer.toString(passengers));
  }  
  @Override
  public String toString() {
    return "Motorcycle: passengers=" + passengers + " " + super.toString();
  }

}
