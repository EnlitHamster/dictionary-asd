package tests;

public class Record {
  private String start;
  private String end;
  private double distance;

  public Record(String s, String e, double d) {
    this.start = s.trim();
    this.end = e.trim();
    this.distance = d;
  }

  public Record(String[] arr) {
    if (arr.length == 3) {
      this.start = arr[0].trim();
      this.end = arr[1].trim();
      this.distance = Double.valueOf(arr[2].trim());
    } else {
      this.start = null;
      this.end = null;
      this.distance = -1.;
    }
  }

  public String getStart() {
    return this.start;
  }

  public String getEnd() {
    return this.end;
  }

  public double getDistance() {
    return this.distance;
  }

  public boolean equals(Record record) {
    return this.start.equals(record.start) && this.end.equals(record.end) && this.distance == record.distance;
  }

  public String toString() {
    return "<" + this.start + ", " + this.end + ", " + this.distance + ">";
  }
}
