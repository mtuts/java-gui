package lesson02;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {

  private JPanel basePanel;
  private JPanel toolPanel;
  private JButton openButton;
  private JButton saveButton;
  private JTextArea fileContentArea;

  public TextEditor() {
    super();

    basePanel = new JPanel();
    basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
    add(basePanel);

    toolPanel = new JPanel();
    toolPanel.setLayout(new FlowLayout());
    toolPanel.setMaximumSize(new Dimension(600, 50));
    basePanel.add(toolPanel);
    
    openButton = new JButton("Open a file");
    openButton.addActionListener(this);
    openButton.setMnemonic(KeyEvent.VK_O);
    toolPanel.add(openButton);

    saveButton = new JButton("Save as file");
    saveButton.addActionListener(this);
    saveButton.setMnemonic(KeyEvent.VK_S);
    toolPanel.add(saveButton);

    
    fileContentArea = new JTextArea();
    JScrollPane textScrollPane = new JScrollPane(fileContentArea);
    textScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    basePanel.add(textScrollPane);

    //pane.add(toolContainer, BorderLayout.PAGE_START);
    
    setTitle("Basic Text Editor");
    setSize(600, 480);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //setLayout(new BorderLayout());
    //pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == openButton) {
      performOpen();
    } else if (e.getSource() == saveButton) {
      performSave();
    }
  }

  private void performOpen() {
    JFileChooser openChooser = new JFileChooser("Open Text File");
    int i = openChooser.showOpenDialog(this);
    if (i == JFileChooser.APPROVE_OPTION) {
      fileContentArea.setText("");
      FileReader reader = null;
      Scanner scan = null;
      try {
        reader = new FileReader(openChooser.getSelectedFile());
        scan = new Scanner(reader);
        StringBuilder builder = new StringBuilder();
        while (scan.hasNextLine()) {
          builder.append(scan.nextLine() + (scan.hasNextLine() ? "\n":""));
        }
        fileContentArea.setText(builder.toString());
      } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      } finally {
        if (scan != null) {
          scan.close();
        }
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    }
  }

  private void performSave() {
    JFileChooser saveChooser = new JFileChooser("Save as Text File");
    int i = saveChooser.showSaveDialog(this);
    if (i == JFileChooser.APPROVE_OPTION) {
      PrintWriter writer = null;
      File file = saveChooser.getSelectedFile();
      if (file.exists()) {
        int n = JOptionPane.showConfirmDialog(this, String.format("Are you sure want override (%s)?", file.getName()), "Confirm Override", JOptionPane.OK_CANCEL_OPTION);
        if (n == JOptionPane.CANCEL_OPTION) {
          return;
        }
      }
      try {
        writer = new PrintWriter(file);
        writer.print(fileContentArea.getText());
      } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      } finally {
        if (writer != null) {
          writer.close();
        }
      }
    }
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      System.err.println("Unable to set look and feel as windows!");
    }
    // JFrame.setDefaultLookAndFeelDecorated(true);
    new TextEditor();
  }
}
