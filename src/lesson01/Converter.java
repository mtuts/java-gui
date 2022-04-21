package lesson01;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.*;

public class Converter extends JFrame implements ActionListener {

  private JTextField celsiusField;
  private JLabel celsiusLabel;
  private JButton convertButton;
  private JLabel fahrenheitLabel;

  public Converter() {
    super();
    celsiusField = new JTextField();
    celsiusField.setBounds(10, 10, 120, 25);
    add(celsiusField);

    celsiusLabel = new JLabel("Celsius");
    celsiusLabel.setBounds(140, 10, 120, 25);
    add(celsiusLabel);

    convertButton = new JButton("Convert");
    convertButton.setBounds(10, 50, 120, 25);
    convertButton.addActionListener(this);
    add(convertButton);

    fahrenheitLabel = new JLabel("Fahrenheit");
    fahrenheitLabel.setBounds(140, 50, 150, 25);
    add(fahrenheitLabel);

    setTitle("Celsius Converter");
    setSize(300, 150);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == convertButton) {
      convert();
    }
  }

  private void convert() {
    String celsiusStr = celsiusField.getText();
    float celsius = 0.0f, fahrenheit = 0.0f;
    try {
      fahrenheitLabel.setText("Fahrenheit");
      celsius = Float.parseFloat(celsiusStr);
      fahrenheit = celsius * 9 / 5 + 32;
      fahrenheitLabel.setText(String.format("%.2f Fahrenheit", fahrenheit));
    } catch (NumberFormatException e) {
      String error_message;
      if (celsiusStr.length() == 0) {
        error_message = "You must enter a number in celsius field!";
      } else {
        error_message = "Celsius filed must be number!";
      }
      JOptionPane.showMessageDialog(this, error_message, "Wrong Input", JOptionPane.ERROR_MESSAGE);
    }

  }

  public static void main(String[] args) {
    new Converter();
  }
}
