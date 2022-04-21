package lesson06;

public class Unit {
  String description;
  double multiplier;

  Unit(String description, double multiplier) {
    super();
    this.description = description;
    this.multiplier = multiplier;
  }

  @Override
  public String toString() {
    String s = "Meters/" + description + " = " + multiplier;
    return s;
  }
  
}
