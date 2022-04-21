package lesson07;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class DataSet extends AbstractTableModel {
  private Vector<ColumnInfo> columnNames;
  private List<List<Object>> data;
  private final boolean DEBUG;

  public DataSet() {
    DEBUG = false;
    columnNames = new Vector<>();
    data = new ArrayList<>();
  }
  
  public DataSet(boolean debug) {
    DEBUG = debug;
    columnNames = new Vector<>();
    data = new ArrayList<>();
  }

  public ColumnInfo[] getColumns() {
    ColumnInfo[] columns = new ColumnInfo[columnNames.size()];
    int i = 0;
    for (ColumnInfo item: columnNames) {
      columns[i] = item;
      i++;
    }
    return columns;
  }

  public String[] getColumnNames() {
    String[] names = new String[columnNames.size()];
    int i = 0;
    for (ColumnInfo item: columnNames) {
      names[i] = item.toString();
      i++;
    }
    return names;
  }

  public String getColumnName(int index) {
    return columnNames.get(index).toString();
  }

  @Override
  public int getRowCount() {
    return data.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.size();
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if (rowIndex < data.size() && columnIndex < data.get(rowIndex).size()) {
      return data.get(rowIndex).get(columnIndex);
    }
    return null;
  }

  /*
    * Don't need to implement this method unless your table's
    * editable.
    */
  @Override
  public boolean isCellEditable(int row, int col) {
    return true;
  }

  /*
    * Don't need to implement this method unless your table's
    * data can change.
    */
  @Override
  public void setValueAt(Object value, int row, int col) {
    value = columnNames.get(col).getTypedValue(value);
    if (DEBUG) {
      System.out.println("Setting value at " + row + "," + col
                          + " to " + value
                          + " (an instance of "
                          + value.getClass() + ")");
    }

    data.get(row).set(col, value);
    fireTableCellUpdated(row, col);

    if (DEBUG) {
      System.out.println("New value of data:");
      printDebugData();
    }
  }

  public void addColumn(ColumnInfo column) {
    columnNames.add(column);
    updateRows();
  }

  public void updateColumn(ColumnUpdate column) {
    columnNames.get(column.getIndex()).setTitle(column.getTitle());
  }

  public void deleteColumn(ColumnUpdate column) {
    columnNames.remove(column.getIndex());
    for (List<Object> row : data) {
      row.remove(column.getIndex());
    }
  }

  public void addRow() {
    data.add(new ArrayList<Object>());
    updateRow(data.get(data.size() - 1));
  }

  private void updateRows() {
    int i = 0;
    for (List<Object> row : data) {
      if (row == null) {
        data.set(i, new ArrayList<Object>());
      }
      updateRow(row);
      i++;
    }
  }

  private void updateRow(List<Object> row) {
    while (row.size() < columnNames.size()) {
      int i = row.size();
      row.add(columnNames.get(i).getInitValue());
    }
  }

  private void printDebugData() {
    int numRows = getRowCount();
    int numCols = getColumnCount();

    for (int i=0; i < numRows; i++) {
      System.out.print("    row " + i + ":");
      for (int j=0; j < numCols; j++) {
          System.out.print("  " + data.get(i).get(j));
      }
      System.out.println();
    }
    System.out.println("--------------------------");
  }

  public boolean removeRow(int selectedRow) {
    if (selectedRow >= 0 && selectedRow < data.size()) {
      data.remove(selectedRow);
      return true;
    }
    return false;
  }
}
