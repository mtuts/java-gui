package lesson07;

public class ColumnInfo {
  private String title;
  private ColumnType type;

  public ColumnInfo(String title, ColumnType type) {
    this.title = title;
    this.type = type;
  }

  @Override
  public String toString() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ColumnType getColumnType() {
    return type;
  }

  public Object getInitValue() {
    if (type == ColumnType.String) {
      return "";
    } else if (type == ColumnType.Integer) {
      return 0;
    } else if (type == ColumnType.Double) {
      return 0.0;
    } else if (type == ColumnType.Boolean) {
      return false;
    }
    return "";
  }

  public Object getTypedValue(Object value) {
    if (type == ColumnType.String) {
      return value;
    } else if (type == ColumnType.Integer) {
      return Integer.parseInt(value.toString());
    } else if (type == ColumnType.Double) {
      return Double.parseDouble(value.toString());
    } else if (type == ColumnType.Boolean) {
      return value.toString().equalsIgnoreCase("true");
    }
    return "";
  }
}