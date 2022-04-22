package lesson05;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JFileChooser;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MenuDemo extends JFrame implements ActionListener, DocumentListener, WindowListener {

  private static final String APP_TITLE = "Basic Editor";
  private static final String DOCUMENT_TITLE = "Untitled";

  private int number_of_document = 1;

  private boolean document_has_changed = false;

  private JMenuBar menuBar;
  private JMenu menu, recently_menu;
  private JMenuItem menuItemCreateNew;
  private JMenuItem menuItemSave;
  private JMenuItem menuItemSaveAs;
  private JMenuItem menuItemOpen;
  private JMenuItem menuItemExit;
  private JMenuItem menuItemStatus;
  private JMenuItem menuClearRecentFile;
  private JRadioButtonMenuItem[] rbMenuItems;
  private JCheckBoxMenuItem[] cbMenuItems;

  private GridBagConstraints constraints;
  private GridBagLayout layout;

  private JScrollPane scrollPane;
  private JTextArea textArea;

  private File edit_file = null;
  private File recently_files_data = null;

  private RecentFileMenuItem[] recently_files;

  public MenuDemo() {
    super(APP_TITLE);
    constraints = new GridBagConstraints();
    layout = new GridBagLayout();
    
    setLayout(layout);

    prepareMainMenu();

    textArea = new JTextArea();
    textArea.getDocument().addDocumentListener(this);

    scrollPane = new JScrollPane(textArea);
    
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    addComponent(scrollPane, 0, 0, 1, 1);

    updateTitle();

    addWindowListener(this);
  }

  private void prepareMainMenu() {
    menuBar = new JMenuBar();

    menu = new JMenu("File");
    menu.setMnemonic(KeyEvent.VK_F);
    menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
    menuBar.add(menu);

    menuItemCreateNew = new JMenuItem("New Document", KeyEvent.VK_N);
    menuItemCreateNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    menuItemCreateNew.getAccessibleContext().setAccessibleDescription("Create new Text document");
    menuItemCreateNew.addActionListener(this);
    menu.add(menuItemCreateNew);

    menu.addSeparator();

    menuItemSave = new JMenuItem("Save", new ImageIcon("images/floppy-disk.png"));
    menuItemSave.setMnemonic(KeyEvent.VK_S);
    menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    menuItemSave.getAccessibleContext().setAccessibleDescription("Save current changes");
    menuItemSave.addActionListener(this);
    menu.add(menuItemSave);

    menuItemSaveAs = new JMenuItem("Save As", KeyEvent.VK_A);
    menuItemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK));
    menuItemSaveAs.getAccessibleContext().setAccessibleDescription("Save copy from current document");
    menuItemSaveAs.addActionListener(this);
    menu.add(menuItemSaveAs);
    menu.addSeparator();

    menuItemOpen = new JMenuItem("Open", new ImageIcon("images/open.png"));
    menuItemOpen.setMnemonic(KeyEvent.VK_O);
    menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    menuItemOpen.getAccessibleContext().setAccessibleDescription("Open a document");
    menuItemOpen.addActionListener(this);
    menu.add(menuItemOpen);

    loadRecentlyOpenedMenu();

    menu.addSeparator();

    menuItemExit = new JMenuItem("Exit", KeyEvent.VK_X);
    menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
    menuItemExit.getAccessibleContext().setAccessibleDescription("Exit program");
    menuItemExit.addActionListener(this);
    menu.add(menuItemExit);

    menu = new JMenu("Setting");
    menuBar.add(menu);

    rbMenuItems = new JRadioButtonMenuItem[2];

    ButtonGroup group = new ButtonGroup();
    rbMenuItems[0] = new JRadioButtonMenuItem("Option 1");
    rbMenuItems[0].setSelected(true);
    rbMenuItems[0].setMnemonic(KeyEvent.VK_1);
    group.add(rbMenuItems[0]);
    menu.add(rbMenuItems[0]);
    
    rbMenuItems[1] = new JRadioButtonMenuItem("Option 2");
    rbMenuItems[1].setMnemonic(KeyEvent.VK_2);
    group.add(rbMenuItems[1]);
    menu.add(rbMenuItems[1]);

    cbMenuItems = new JCheckBoxMenuItem[2];

    menu.addSeparator();
    cbMenuItems[0] = new JCheckBoxMenuItem("Check box 1");
    cbMenuItems[0].setMnemonic(KeyEvent.VK_C);
    cbMenuItems[0].setState(true);
    menu.add(cbMenuItems[0]);

    cbMenuItems[1] = new JCheckBoxMenuItem("Check box 2");
    cbMenuItems[1].setMnemonic(KeyEvent.VK_B);
    menu.add(cbMenuItems[1]);

    menu.addSeparator();
    menuItemStatus = new JMenuItem("Print Status");
    menuItemStatus.addActionListener(this);
    menu.add(menuItemStatus);

    setJMenuBar(menuBar);
  }

  public void updateTitle() {
    if (edit_file != null) {
      super.setTitle(APP_TITLE + " - " + edit_file.getName());
    } else {
      super.setTitle(APP_TITLE + " - " + DOCUMENT_TITLE + " " + number_of_document);
    }
  }

  private void loadRecentlyOpenedMenu() {
    recently_files_data = new File("settings/lesson05.dat");
    recently_menu = new JMenu("Open Recent");
    recently_menu.setMnemonic(KeyEvent.VK_R);

    menuClearRecentFile = new JMenuItem("Clear Recently Opened");
    menuClearRecentFile.addActionListener(this);
    recently_menu.add(menuClearRecentFile);

    recently_menu.addSeparator();
    
    Scanner scan = null;
    recently_files = new RecentFileMenuItem[10];
    try {
      scan = new Scanner(recently_files_data);
      int i = 0;
      while (scan.hasNextLine()) {
        String text = scan.nextLine();
        File file = new File(text);
        if (file.exists() && file.isFile()) {
          RecentFileMenuItem item = new RecentFileMenuItem(file);
          item.addActionListener(this);
          recently_files[i] = item;
          recently_menu.add(item);
          i++;
        }
      }
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }
    scan.close();
    
    menu.add(recently_menu);
  }

  private void clearRecentFiles() {
    for (int i = 0; i < recently_files.length; i++) {
      if (recently_files[i] != null)
        recently_menu.remove(recently_files[i]);
      recently_files[i] = null;
    }
  }

  private void insertRecentlyFile(File file) {
    // put last opened file into the first of the list
    boolean flag = false;
    int i;
    for (i = 0; i < recently_files.length - 1; i++) {
      if (recently_files[i] == null) break;
      recently_menu.remove(recently_files[i]);
      if (recently_files[i].getFilePath().equals(file.getAbsolutePath())) {
        flag = true;
      }
      if (flag) {
        recently_files[i] = recently_files[i + 1];
      }
    }
    if (recently_files[i] != null) {
      recently_menu.remove(recently_files[i]);
      if (recently_files[i].getFilePath().equals(file.getAbsolutePath())) {
        recently_files[i] = null;
      }
    }

    for (i = recently_files.length - 1; i > 0; i--) {
      recently_files[i] = recently_files[i - 1];
      if (recently_files[i] != null) {
        recently_menu.insert(recently_files[i], 2);
      }
    }
    recently_files[0] = new RecentFileMenuItem(file);
    recently_menu.insert(recently_files[0], 2);
  }

  private void storeRecentlyOpenedFiles() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(new BufferedWriter(new FileWriter(recently_files_data)));
      for (RecentFileMenuItem item : recently_files) {
        if (item != null && item.isExist()) {
          writer.println(item.getFilePath());
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } finally {
      if (writer != null) {
        writer.close();
      }
    }
  }

  private void openRecentlyFile(RecentFileMenuItem item) {
    openEditFile(new File(item.getFilePath()));
  }

  private void openEditFile(File file) {
    edit_file = file;
    FileReader reader = null;
    Scanner scan = null;
    try {
      reader = new FileReader(edit_file);
      scan = new Scanner(reader);
      StringBuilder builder = new StringBuilder();
      while (scan.hasNextLine()) {
        builder.append(scan.nextLine() + (scan.hasNextLine() ? "\n":""));
      }
      textArea.setText(builder.toString());
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
      if (scan != null) {
        scan.close();

        insertRecentlyFile(edit_file);
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    document_has_changed = false;
    updateTitle();
  }

  private void addComponent(Component component, int row, int column, int width, int height) {
    constraints.gridx = column;
    constraints.gridy = row;
    constraints.gridwidth = width;
    constraints.gridheight = height;
    layout.setConstraints(component, constraints);
    add(component);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == menuItemCreateNew) {
      performNew();
    } else if (e.getSource() == menuItemSave) {
      performSave();
    } else if (e.getSource() == menuItemSaveAs) {
      performSave(true);
    } else if (e.getSource() == menuItemOpen) {
      performOpen();
    } else if (e.getSource() == menuItemExit) {
      storeRecentlyOpenedFiles();
      System.exit(0);
    } else if (e.getSource() == menuItemStatus) {
      printStatus();
    } else if (e.getSource() == menuClearRecentFile) {
      clearRecentFiles();
    } else if (e.getSource() instanceof RecentFileMenuItem) {
      openRecentlyFile((RecentFileMenuItem) e.getSource());
    }
  }

  private void printStatus() {
    System.out.print("\nRadio button: ");
    for (JRadioButtonMenuItem item : rbMenuItems) {
      if (item.isSelected()) {
        System.out.println(item.getText());
        break;
      }
    }
    System.out.println("\nChecked buttons: ");
    for (JCheckBoxMenuItem item : cbMenuItems) {
      System.out.printf("%s: %s\n", item.getText(), item.getState());
    }
  }

  private void performNew() {
    if (document_has_changed) {
      String file_name = edit_file != null ? edit_file.getName() : DOCUMENT_TITLE + " " + number_of_document;
      int n = JOptionPane.showConfirmDialog(this, String.format("Do you want save changes to %s?", file_name), "Confirm Save", JOptionPane.YES_NO_CANCEL_OPTION);
      if (n == JOptionPane.YES_OPTION) {
        performSave();
      } else if (n == JOptionPane.CANCEL_OPTION) {
        return;
      }
    }
    edit_file = null;
    textArea.setText("");
    document_has_changed = false;
    number_of_document++;
    updateTitle();
  }

  private void performOpen() {
    if (document_has_changed) {
      performSave();
    }
    JFileChooser openChooser = new JFileChooser("Open Text File");
    int i = openChooser.showOpenDialog(this);
    if (i == JFileChooser.APPROVE_OPTION) {
      textArea.setText("");
      openEditFile(openChooser.getSelectedFile());
    }
    
    updateTitle();
  }

  private void performSave() {
    performSave(false);
  }

  private void performSave(boolean save_as) {
    if (edit_file != null && !save_as) {
      writeOnDisk();
    } else {
      JFileChooser saveChooser = new JFileChooser("Save as Text File");
      int i = saveChooser.showSaveDialog(this);
      if (i == JFileChooser.APPROVE_OPTION) {
        edit_file = saveChooser.getSelectedFile();
        if (edit_file.exists()) {
          int n = JOptionPane.showConfirmDialog(this, String.format("Are you sure want override (%s)?", edit_file.getName()), "Confirm Override", JOptionPane.OK_CANCEL_OPTION);
          if (n == JOptionPane.CANCEL_OPTION) {
            return;
          }
        }
        writeOnDisk();
        insertRecentlyFile(edit_file);
        document_has_changed = false;
      }
    }
    updateTitle();
  }

  private void writeOnDisk() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(edit_file);
      writer.print(textArea.getText());
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
      if (writer != null) {
        writer.close();
      }
    }
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    document_has_changed = true;
  }
  
  @Override
  public void removeUpdate(DocumentEvent e) {
    document_has_changed = true;
  }
  
  @Override
  public void changedUpdate(DocumentEvent e) {
    document_has_changed = true;
  }

  @Override
  public void windowOpened(WindowEvent e) {
  }

  @Override
  public void windowClosing(WindowEvent e) {
    storeRecentlyOpenedFiles();
  }

  @Override
  public void windowClosed(WindowEvent e) {

  }

  @Override
  public void windowIconified(WindowEvent e) {    
  }

  @Override
  public void windowDeiconified(WindowEvent e) {
  }

  @Override
  public void windowActivated(WindowEvent e) {
  }

  @Override
  public void windowDeactivated(WindowEvent e) {
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
    MenuDemo menuDemo = new MenuDemo();
    menuDemo.setMinimumSize(new Dimension(400, 300));
    menuDemo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    menuDemo.setLocationRelativeTo(null);
    menuDemo.pack();
    menuDemo.setVisible(true);
  }
}
