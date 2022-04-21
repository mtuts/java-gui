package lesson03;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import java.awt.*;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;

public class FormatedTextField extends JPanel implements PropertyChangeListener {

  // Values for the text fields
  private double amount = 100000;
  private double rate = .075; // 7.5 %
  private int numPeriods = 30;
  
  //Labels to identify the fields
  private JLabel amountLabel;
  private JLabel rateLabel;
  private JLabel numPeriodsLabel;
  private JLabel paymentLabel;

  //Strings for the labels
  private static final String AMOUNT = "Loan Amount: ";
  private static final String RATE = "APR (%): ";
  private static final String NUM_PERIODS = "Years: ";
  private static final String PAYMENT = "Monthly Payment: ";
    
  private JFormattedTextField amountField;
  private JFormattedTextField rateField;
  private JFormattedTextField numPeriodsField;
  private JFormattedTextField paymentField;

  // Formates to format and parse numbers
  private NumberFormat amountDisplayFormat;
  private NumberFormat amountEditFormat;
  private NumberFormat percentDisplayFormat;
  private NumberFormat percentEditFormat;
  private NumberFormat paymentFormat;
  

  public FormatedTextField() {
    super(new BorderLayout());
    setUpFormats();
    double payment = computePayment(amount, rate, numPeriods);
    
    //Create the labels.
    amountLabel = new JLabel(AMOUNT);
    rateLabel = new JLabel(RATE);
    numPeriodsLabel = new JLabel(NUM_PERIODS);
    paymentLabel = new JLabel(PAYMENT);

    //Create the text fields and set them up.
    amountField = new JFormattedTextField(
      new DefaultFormatterFactory(
        new NumberFormatter(amountDisplayFormat),
        new NumberFormatter(amountDisplayFormat),
        new NumberFormatter(amountEditFormat)));
    amountField.setValue(amount);
    amountField.setColumns(10);
    amountField.addPropertyChangeListener("value", this);

    NumberFormatter percentEditFormatter =
            new NumberFormatter(percentEditFormat) {
        public String valueToString(Object o)
              throws ParseException {
            Number number = (Number)o;
            if (number != null) {
                double d = number.doubleValue() * 100.0;
                number = d;
            }
            return super.valueToString(number);
        }
        public Object stringToValue(String s)
                throws ParseException {
            Number number = (Number)super.stringToValue(s);
            if (number != null) {
                double d = number.doubleValue() / 100.0;
                number = d;
            }
            return number;
        }
    };
    rateField = new JFormattedTextField(
      new DefaultFormatterFactory(
        new NumberFormatter(percentDisplayFormat),
        new NumberFormatter(percentDisplayFormat),
        percentEditFormatter));
    rateField.setValue(rate);
    rateField.setColumns(10);
    rateField.addPropertyChangeListener("value", this);

    numPeriodsField = new JFormattedTextField();
    numPeriodsField.setValue(numPeriods);
    numPeriodsField.setColumns(10);
    numPeriodsField.addPropertyChangeListener("value", this);

    paymentField = new JFormattedTextField(paymentFormat);
    paymentField.setValue(payment);
    paymentField.setColumns(10);
    paymentField.setEditable(false);
    paymentField.setForeground(Color.red);

    //Tell accessibility tools about label/textfield pairs.
    amountLabel.setLabelFor(amountField);
    rateLabel.setLabelFor(rateField);
    numPeriodsLabel.setLabelFor(numPeriodsField);
    paymentLabel.setLabelFor(paymentField);

    //Lay out the labels in a panel.
    JPanel labelPane = new JPanel(new GridLayout(0,1));
    labelPane.add(amountLabel);
    labelPane.add(rateLabel);
    labelPane.add(numPeriodsLabel);
    labelPane.add(paymentLabel);

    //Layout the text fields in a panel.
    JPanel fieldPane = new JPanel(new GridLayout(0,1));
    fieldPane.add(amountField);
    fieldPane.add(rateField);
    fieldPane.add(numPeriodsField);
    fieldPane.add(paymentField);

    //Put the panels in this panel, labels on left,
    //text fields on right.
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    add(labelPane, BorderLayout.CENTER);
    add(fieldPane, BorderLayout.LINE_END);
  }

  private void setUpFormats() {
    amountDisplayFormat = NumberFormat.getCurrencyInstance();
    amountDisplayFormat.setMinimumFractionDigits(0);
    amountEditFormat = NumberFormat.getNumberInstance();

    percentDisplayFormat = NumberFormat.getPercentInstance();
    percentDisplayFormat.setMinimumFractionDigits(2);
    percentEditFormat = NumberFormat.getNumberInstance();
    percentEditFormat.setMinimumFractionDigits(2);

    paymentFormat = NumberFormat.getCurrencyInstance();
  }

  @Override
  public void propertyChange(PropertyChangeEvent  e) {
    Object source = e.getSource();
    if (source == amountField) {
        amount = ((Number)amountField.getValue()).doubleValue();
    } else if (source == rateField) {
        rate = ((Number)rateField.getValue()).doubleValue();
    } else if (source == numPeriodsField) {
        numPeriods = ((Number)numPeriodsField.getValue()).intValue();
    }

    double payment = computePayment(amount, rate, numPeriods);
    paymentField.setValue(payment);
    
  }

  //Compute the monthly payment based on the loan amount,
  //APR, and length of loan.
  double computePayment(double loanAmt, double rate, int numPeriods) {
    double I, partial1, denominator, answer;

    numPeriods *= 12;        //get number of months
    if (rate > 0.001) {
        I = rate / 12.0;         //get monthly rate from annual
        partial1 = Math.pow((1 + I), (0.0 - numPeriods));
        denominator = (1 - partial1) / I;
    } else { //rate ~= 0
        denominator = numPeriods;
    }

    answer = (-1 * loanAmt) / denominator;
    return answer;
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
          System.err.println("Unable to set look and feel as windows!");
        }
        createAndShowGUI();
      }
    });
  }

  private static void createAndShowGUI() {
    JFrame frame = new JFrame("Formated Text");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    // Add contents to the window
    frame.add(new FormatedTextField());

    // Display the window
    frame.pack();
    frame.setVisible(true);
  }
}
