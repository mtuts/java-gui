package lesson04;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.EventQueue;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyList extends JPanel implements ActionListener, ListSelectionListener {

  private JList<String> list;
  private DefaultListModel<String> list_model;
  private JScrollPane scrollPane;

  private JLabel listLabel;
  private JLabel editLabel;
  private JTextField editField;
  private JButton addButton;
  private JButton updateButton;
  private JButton deleteButton;

  private GridBagConstraints gbc;
  private GridBagLayout layout;

  public MyList(String[] data) {
    gbc = new GridBagConstraints();
    layout = new GridBagLayout();
    setLayout(layout);
    setBorder(new EmptyBorder(10, 10, 10, 10));
    
    list_model = new DefaultListModel<String>();
    list = new JList<String>(list_model);

    ListSelectionModel listSelectionModel = list.getSelectionModel();
    listSelectionModel.addListSelectionListener(this);

    scrollPane = new JScrollPane(list);
    listLabel = new JLabel("Fruits Basket:");
    editLabel = new JLabel("Fruit Name:");

    editField = new JTextField();
    addButton = new JButton("Insert");
    addButton.addActionListener(this);

    updateButton = new JButton("Save");
    updateButton.addActionListener(this);

    deleteButton = new JButton("Delete");
    deleteButton.addActionListener(this);

    listLabel.setLabelFor(list);
    editLabel.setLabelFor(editField);

    gbc.fill = GridBagConstraints.HORIZONTAL;
    addComponent(listLabel, 0, 0, 3, 1);

    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    addComponent(scrollPane, 1, 0, 3, 1);

    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    addComponent(editLabel, 2, 0, 1, 1);

    gbc.weightx = 1;
    addComponent(editField, 2, 1, 2, 1);

    JPanel buttonsPanel = new JPanel(new FlowLayout());
    buttonsPanel.add(addButton);
    buttonsPanel.add(updateButton);
    buttonsPanel.add(deleteButton);
    
    addComponent(buttonsPanel, 3, 0, 3, 1);

    for (String item : data) {
      list_model.addElement(item);
    }    
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == addButton) {
      if (editField.getText().length() > 0) {
        list_model.addElement(editField.getText());
        list.ensureIndexIsVisible(list_model.size() - 1);
      }
    } else if (e.getSource() == updateButton) {
      int index = list.getSelectedIndex();
      if (editField.getText().length() > 0 && index >= 0) {
        list_model.set(index, editField.getText());
      }
    } else if (e.getSource() == deleteButton) {
      int index = list.getSelectedIndex();
      if (index >= 0) {
        String item = list.getSelectedValue().toString();
        int choice = JOptionPane.showConfirmDialog(this, String.format("Are you sure want remove (%s)?", item), "Confirm Remove", 
          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
          list_model.remove(index);
        }
      }
    }    
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      int index = lsm.getMaxSelectionIndex();
      if (index >= 0) {
        editField.setText(list_model.get(index));
      }
    }
  }

  private void addComponent(Component component, int row, int column, int width, int height) {
    gbc.gridx = column;
    gbc.gridy = row;
    gbc.gridwidth = width;
    gbc.gridheight = height;
    layout.setConstraints(component, gbc);
    add(component);
  }
  

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
          System.err.println("Unable to set look and feel as windows!");
          try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
          } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException x) {
            System.err.println("Unable to set look and feel as windows!");
          }  
        }
        createAndShowGUI();
      }
    });
  }

  private static void createAndShowGUI() {
    String[] data = {"Cherry", "Apple", "Apricots", "Avocado", "Banana", "Blackberries", 
      "Blueberries", "Clementine", "Banana"};
    JFrame frame = new JFrame("Custom List");
    MyList myList = new MyList(data);
    frame.add(myList);
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    // Display the window
    frame.pack();
    frame.setVisible(true);
  }
}
