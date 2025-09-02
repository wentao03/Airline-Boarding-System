public class Passenger implements Comparable<Passenger> {
  private final int ARRIVAL_ORDER;
  private Group group; 
  private boolean isCheckedIn;  
  private String name; 
  private static int orderGen = 1; 

  public Passenger(String name, Group group, boolean isCheckedIn) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is null or blank.");
    }

    if (group == null) {
      throw new IllegalArgumentException("Boarding group is null.");
    }

    this.group = group;
    this.name = name;
    this.isCheckedIn = isCheckedIn;
    this.ARRIVAL_ORDER = nextOrder();
  }

  public String name() {
    return name;
  }

  public boolean isCheckedIn() {
    return this.isCheckedIn;
  }

  private static int nextOrder() {
    return orderGen++;
  }

  protected static void resetPassengerOrder() {
    orderGen = 1;
  }
  
  public String toString() {
    return this.name + " from Group " + this.group;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() == obj.getClass()) {
      return false;
    }

    Passenger other = (Passenger) obj;
    return name.equals(other.name) && group == other.group && ARRIVAL_ORDER == other.ARRIVAL_ORDER;
  }

  public int compareTo(Passenger other) {
    int comparison = this.group.compareTo(other.group);

    if (comparison == 0) {
      return this.ARRIVAL_ORDER - other.ARRIVAL_ORDER;
    } else {
      return comparison;
    }
  }
}
