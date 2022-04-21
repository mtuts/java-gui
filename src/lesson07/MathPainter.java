package lesson07;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MathPainter extends JFrame implements ActionListener {

  private JPanel panel;
  private JButton x_squareButton;
  private JButton sinButton;
  private JButton cosButton;
  private JButton tanButton;
  private JButton clearButton;
  private JButton ganttChartButton;

  private Canvas canvas;

  private GridBagConstraints gbc;
  private GridBagLayout layout;

  private GanttChartEquation gantt_chart;

  public MathPainter(String title) {
    super(title);
    gbc = new GridBagConstraints();
    gbc.insets = new Insets(3,3,3,3);
    layout = new GridBagLayout();
    
    panel = new JPanel();
    panel.setLayout(layout);
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));

    Container pane = getContentPane();
    pane.add(panel, BorderLayout.CENTER);

    canvas = new Canvas();
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    addComponent(canvas, 0, 0, 6, 1);

    x_squareButton = new JButton("X^2");
    x_squareButton.addActionListener(this);
    gbc.weightx = 1;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    addComponent(x_squareButton, 1, 0, 1, 1);

    sinButton = new JButton("sin(x)");
    sinButton.addActionListener(this);
    addComponent(sinButton, 1, 1, 1, 1);
    
    cosButton = new JButton("cos(x)");
    cosButton.addActionListener(this);
    addComponent(cosButton, 1, 2, 1, 1);

    tanButton = new JButton("tan(x)");
    tanButton.addActionListener(this);
    addComponent(tanButton, 1, 3, 1, 1);

    ganttChartButton = new JButton("Gantt Chart");
    ganttChartButton.addActionListener(this);
    addComponent(ganttChartButton, 1, 4, 1, 1);

    clearButton = new JButton("Clear");
    clearButton.addActionListener(this);
    addComponent(clearButton, 1, 5, 1, 1);

    gantt_chart = new GanttChartEquation(10);
    gantt_chart.add(new Process("P0", 0, 8));
    gantt_chart.add(new Process("P1", 8, 12));
    gantt_chart.add(new Process("P2", 12, 15));
    gantt_chart.add(new Process("P3", 15, 21));
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
    if (e.getSource() == x_squareButton) {
      canvas.setEquation(new Square(10));
    } else if (e.getSource() == sinButton) {
      canvas.setEquation(new SineEquation(10));
    } else if (e.getSource() == cosButton) {
      canvas.setEquation(new CosineEquation(10));
    } else if (e.getSource() == tanButton) {
      canvas.setEquation(new TanEquation(10));
    } else if (e.getSource() == ganttChartButton) {
      canvas.setEquation(gantt_chart);
    } else if (e.getSource() == clearButton) {
      canvas.setEquation(null);
    }
  }

  public static void main(String[] args) {
    MathPainter painter = new MathPainter("Math Painter");
    painter.setSize(600, 400);
    painter.setVisible(true);
    painter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          
  }
}
