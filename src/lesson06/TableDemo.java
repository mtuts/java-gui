package lesson06;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableDemo extends JFrame implements ActionListener {
  private DataSet dataSet;
  private JPanel panel;
  private JPanel tablePanel;
  private JTable table;

  private GridBagConstraints gbc;
  private GridBagLayout layout;

  private JButton addColumnButton;
  private JButton updateColumnButton;
  private JButton deleteColumnButton;
  private JButton addRowButton;
  private JButton removeRowButton;

  public TableDemo(String title) {
    super(title);
    
    gbc = new GridBagConstraints();
    gbc.insets = new Insets(3,3,3,3);
    layout = new GridBagLayout();

    panel = new JPanel();
    panel.setLayout(layout);
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));

    Container pane = getContentPane();
    pane.add(panel, BorderLayout.CENTER);

    tablePanel = new JPanel(new GridLayout(1,0));
    tablePanel.setOpaque(true);
    dataSet = new DataSet();
    TableDemo.sampleData(dataSet);

    table = new JTable(dataSet);
    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
    table.setFillsViewportHeight(true);

    //Create the scroll pane and add the table to it.
    JScrollPane scrollPane = new JScrollPane(table);

    //Add the scroll pane to this panel.
    tablePanel.add(scrollPane);

    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    addComponent(tablePanel, 0, 0, 5, 1);

    addColumnButton = new JButton("Add Column");
    addColumnButton.addActionListener(this);
    gbc.weightx = 1;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    addComponent(addColumnButton, 1, 0, 1, 1);

    updateColumnButton = new JButton("Edit Column");
    updateColumnButton.addActionListener(this);
    addComponent(updateColumnButton, 1, 1, 1, 1);

    deleteColumnButton = new JButton("Delete Column");
    deleteColumnButton.addActionListener(this);
    addComponent(deleteColumnButton, 1, 2, 1, 1);

    addRowButton = new JButton("Add Row");
    addRowButton.addActionListener(this);
    addComponent(addRowButton, 1, 3, 1, 1);

    removeRowButton = new JButton("Remove Selected Row");
    removeRowButton.addActionListener(this);
    addComponent(removeRowButton, 1, 4, 1, 1);
  }

  private void addComponent(Component component, int row, int column, int width, int height) {
    gbc.gridx = column;
    gbc.gridy = row;
    gbc.gridwidth = width;
    gbc.gridheight = height;
    layout.setConstraints(component, gbc);
    panel.add(component);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == addColumnButton) {
      // show input dialog
      ColumnInfo info = ColumnDialog.showInputDialog(this);
      if (info != null) {
        dataSet.addColumn(info);
        table.getColumnModel().addColumn(new TableColumn(dataSet.getColumnCount() - 1));
        table.getColumnModel().getColumn(dataSet.getColumnCount() - 1).setHeaderValue(info.toString());
        table.updateUI();
      }
    } else if (e.getSource() == updateColumnButton) {
      // show input dialog
      ColumnUpdate info = ColumnDialog.showInputDialog(this, dataSet.getColumns());
      if (info != null) {
        dataSet.updateColumn(info);
        table.getColumnModel().getColumn(info.getIndex()).setHeaderValue(info.getTitle());
        table.updateUI();
      }
    } else if (e.getSource() == deleteColumnButton) {
      // show input dialog
      ColumnUpdate info = ColumnDialog.showDeleteDialog(this, dataSet.getColumns());
      if (info != null) {
        dataSet.deleteColumn(info);
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(info.getIndex()));
        table.updateUI();
      }
    } else if (e.getSource() == addRowButton) {
      dataSet.addRow();
      table.updateUI();
    } else if (e.getSource() == removeRowButton) {

      if (dataSet.removeRow(table.getSelectedRow()))
        table.updateUI();
    }
  }

  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event-dispatching thread.
   */
  private static void createAndShowGUI() {
    TableDemo frame = new TableDemo("TableDemo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            createAndShowGUI();
        }
    });
  }
  
  public static void sampleData(DataSet data) {
    data.addColumn(new ColumnInfo("#", ColumnType.Integer));
    data.addColumn(new ColumnInfo("Item", ColumnType.String));
    data.addColumn(new ColumnInfo("Checked", ColumnType.Boolean));
    data.addColumn(new ColumnInfo("Quantity", ColumnType.Integer));
    data.addColumn(new ColumnInfo("Unit Price", ColumnType.Double));
    data.addColumn(new ColumnInfo("Total", ColumnType.Double));

    data.addRow();
    data.addRow();
    data.addRow();
    data.addRow();
    data.addRow();

    int row = 0, col = 0;
    data.setValueAt(1, row, col++);
    data.setValueAt("Apple", row, col++);
    data.setValueAt(true, row, col++);
    data.setValueAt(3, row, col++);
    data.setValueAt(5.9, row, col++);
    data.setValueAt(((int)data.getValueAt(row, 3)) * ((double)data.getValueAt(row, 4)), row, col++);

    row++; col = 0;
    data.setValueAt(2, row, col++);
    data.setValueAt("Banana", row, col++);
    data.setValueAt(false, row, col++);
    data.setValueAt(1, row, col++);
    data.setValueAt(3.59, row, col++);
    data.setValueAt(((int)data.getValueAt(row, 3)) * ((double)data.getValueAt(row, 4)), row, col++);

    row++; col = 0;
    data.setValueAt(3, row, col++);
    data.setValueAt("Orange", row, col++);
    data.setValueAt(true, row, col++);
    data.setValueAt(10, row, col++);
    data.setValueAt(2.95, row, col++);
    data.setValueAt(((int)data.getValueAt(row, 3)) * ((double)data.getValueAt(row, 4)), row, col++);

    row++; col = 0;
    data.setValueAt(4, row, col++);
    data.setValueAt("Avocado", row, col++);
    data.setValueAt(true, row, col++);
    data.setValueAt(2, row, col++);
    data.setValueAt(16.23, row, col++);
    data.setValueAt(((int)data.getValueAt(row, 3)) * ((double)data.getValueAt(row, 4)), row, col++);

    row++; col = 0;
    data.setValueAt(3, row, col++);
    data.setValueAt("Strowberry", row, col++);
    data.setValueAt(false, row, col++);
    data.setValueAt(15, row, col++);
    data.setValueAt(4.99, row, col++);
    data.setValueAt(((int)data.getValueAt(row, 3)) * ((double)data.getValueAt(row, 4)), row, col++);
  }
}