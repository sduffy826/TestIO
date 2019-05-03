package com.corti.files.testJsonSerializationDeserialization;

public class Car extends Vehicle {  
  private int passengers;
  Car(String color, int passengers) {    
    super(4, color, "Diesel");
    this.passengers = passengers;
    if (DEBUGIT) System.out.println("Car constructor with args");
  }
  
  Car() {
    super(4, "Unknown", "Diesel");
    this.passengers = 0;
    if (DEBUGIT) System.out.println("Car default constructor");
  }
   public void setPassengers(int passengers) {
    this.passengers = passengers;
    if (DEBUGIT) System.out.println("Car->setPassengers(), passengers: " + Integer.toString(passengers));
  } 
  
  @Override
  public String toString() {
    return "Car: passengers=" + passengers + " " + super.toString();
  }

}
