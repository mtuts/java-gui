package lesson07;

public class Process {

  private String name;
  private int begin;
  private int end;
  private int length;

  public Process(String name, int begin, int end) {
    this.name = name;
    this.begin = begin;
    this.end = end;
    this.length = end - begin;
  }

  public String getName() {
    return name;
  }

  public int getBegin() {
    return begin;
  }

  public int getEnd() {
    return end;
  }

  public int getLength() {
    return length;
  }  
}
