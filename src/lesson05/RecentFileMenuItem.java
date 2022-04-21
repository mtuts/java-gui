package lesson05;

import java.io.File;

import javax.swing.JMenuItem;

public class RecentFileMenuItem extends JMenuItem {

  private File file;
  
  public RecentFileMenuItem(File file) {
    super(file.getName());

    this.file = file;
  }

  public String getFilePath() {
    return file.getAbsolutePath();
  }

  public boolean isExist() {
    return file.exists() && file.isFile();
  }
}
