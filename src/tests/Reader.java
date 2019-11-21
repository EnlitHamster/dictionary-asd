package tests;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Reader {
  private File csv;
  // private ArrayList<String[]> content;
  private ArrayList<Record> content;

  public Reader(String path) {
    this.csv = new File(path);
    // this.content = new ArrayList<String[]>();
    this.content = new ArrayList<Record>();
  }

  // public ArrayList<String[]> scan() {
  public ArrayList<Record> scan() {
    try {
      FileReader fr = new FileReader(this.csv);
      BufferedReader br = new BufferedReader(fr);
      String line = null;

      while ((line = br.readLine()) != null) {
        Record record = new Record(line.trim().split(","));
        this.content.add(record);
      }

      return this.content;
    } catch (FileNotFoundException ex) {
      System.err.println("[!] File not found: " + this.csv.getPath());

      return null;
    } catch (IOException ex) {
      ex.printStackTrace();

      return null;
    }
  }

  public static void main(String[] args) {
    Reader reader = new Reader("italian_dist_graph.csv.bak");
    ArrayList<Record> arr = reader.scan();
    for (Record elem : arr) {
      System.out.println(elem);
    }
  }
}
