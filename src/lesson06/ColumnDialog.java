package lesson06;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ColumnDialog extends JDialog {

  private JTextField titleField;
  private JComboBox<Object> typeComboBox;
  private JLabel titleLabel;
  private JLabel typeLabel;
  private JButton updateColumnButton;
  private JButton btnCancel;
  private boolean succeeded;

  private static ColumnDialog instance = null;

  private ColumnDialog(JFrame parent) {
    super(parent, "Adding New Column", true);
    //
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    GridBagConstraints cs = new GridBagConstraints();

    cs.fill = GridBagConstraints.HORIZONTAL;

    titleLabel = new JLabel("title: ");
    cs.gridx = 0;
    cs.gridy = 0;
    cs.gridwidth = 1;
    panel.add(titleLabel, cs);

    titleField = new JTextField(40);
    cs.gridx = 1;
    cs.gridy = 0;
    cs.gridwidth = 2;
    panel.add(titleField, cs);

    typeLabel = new JLabel("Type: ");
    cs.gridx = 0;
    cs.gridy = 1;
    cs.gridwidth = 1;
    panel.add(typeLabel, cs);

    typeComboBox = new JComboBox<>();
    typeComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (typeComboBox.getSelectedItem() instanceof String) {
          titleField.setText(typeComboBox.getSelectedItem().toString());
        }
      }

    });
    cs.gridx = 1;
    cs.gridy = 1;
    cs.gridwidth = 2;
    panel.add(typeComboBox, cs);

    updateColumnButton = new JButton("Add Column");

    updateColumnButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        succeeded = true;
        dispose();
      }
    });
    btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        succeeded = false;
        dispose();
      }
    });
    JPanel bp = new JPanel();
    bp.add(updateColumnButton);
    bp.add(btnCancel);

    getContentPane().add(panel, BorderLayout.CENTER);
    getContentPane().add(bp, BorderLayout.PAGE_END);

    pack();
    setResizable(false);
    setLocationRelativeTo(parent);
  }

  private Object getColumnInfo() {
    if (typeComboBox.getSelectedItem() instanceof ColumnType) {
      return new ColumnInfo(titleField.getText().trim(), (ColumnType)typeComboBox.getSelectedItem());
    } else {
      return new ColumnUpdate(titleField.getText().trim(), typeComboBox.getSelectedIndex());
    }
  }

  public boolean isSucceeded() {
    return succeeded;
  }

  private void prepareAddColumn() {
    setTitle("Add New Column");
    updateColumnButton.setText("Add Column");
    typeLabel.setText("Type:");
    typeComboBox.removeAllItems();
    typeComboBox.addItem(ColumnType.String);
    typeComboBox.addItem(ColumnType.Integer);
    typeComboBox.addItem(ColumnType.Double);
    typeComboBox.addItem(ColumnType.Boolean);
    titleField.setVisible(true);
    titleLabel.setVisible(true);
  }

  private void prepareEditColumn(ColumnInfo[] columns) {
    setTitle("Edit Column");
    updateColumnButton.setText("Update");
    typeLabel.setText("Select Column:");
    typeComboBox.removeAllItems();
    for (ColumnInfo item : columns) {
      typeComboBox.addItem(item.toString());
    }
    titleField.setVisible(true);
    titleLabel.setVisible(true);
  }

  private void prepareDeleteColumn(ColumnInfo[] columns) {
    setTitle("Confirm Delete Column");
    updateColumnButton.setText("Delete");
    typeLabel.setText("Select Column:");
    typeComboBox.removeAllItems();
    for (ColumnInfo item : columns) {
      typeComboBox.addItem(item.toString());
    }
    titleField.setVisible(false);
    titleLabel.setVisible(false);
  }

  private static void init(JFrame parent) {
    if (instance == null) {
      instance = new ColumnDialog(parent);
    }
    instance.succeeded = false;
    
  }

  public static ColumnInfo showInputDialog(JFrame parent) {
    init(parent);
    instance.prepareAddColumn();
    instance.setVisible(true);  // since it is modal it will wait until dialog close
    if (instance.succeeded && instance.getColumnInfo().toString().length() > 0) {
      return (ColumnInfo) instance.getColumnInfo();
    }
    return null;
  }

  public static ColumnUpdate showInputDialog(JFrame parent, ColumnInfo[] columns) {
    init(parent);
    instance.prepareEditColumn(columns);
    instance.setVisible(true);  // since it is modal it will wait until dialog close
    if (instance.succeeded && instance.getColumnInfo().toString().length() > 0) {
      return (ColumnUpdate)instance.getColumnInfo();
    }
    return null;
  }

  public static ColumnUpdate showDeleteDialog(JFrame parent, ColumnInfo[] columns) {
    init(parent);
    instance.prepareDeleteColumn(columns);
    instance.setVisible(true);  // since it is modal it will wait until dialog close
    if (instance.succeeded) {
      return (ColumnUpdate)instance.getColumnInfo();
    }
    return null;
  }
}
