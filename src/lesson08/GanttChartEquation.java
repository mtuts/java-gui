package lesson08;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;

public class GanttChartEquation extends Equation {

  private List<Process> items;

  public GanttChartEquation() {
    super();
    items = new ArrayList<>();
  }

  public GanttChartEquation(double scale) {
    super(scale);
    items = new ArrayList<>();
  }

  public void add(Process process) {
    items.add(process);
  }

  @Override
  public void paint() {
    g.setColor(Color.BLACK);

    double init_x = minX() + 1;
    double max_x = init_x;
    drawLine(init_x, 0, init_x, -1);
    drawCenteredString("0", init_x, -2.5);

    for (Process item : items) {
      double x_b = init_x + item.getBegin();
      double x_e = init_x + item.getEnd();
      max_x = Math.max(init_x, x_e);
      drawLine(x_b, 0, x_b, -1);
      drawCenteredString(item.getBegin() + "", x_b, -2.5);
      drawLine(x_e, 0, x_e, -1);
      drawCenteredString(item.getEnd() + "", x_e, -2.5);
      drawCenteredString(item.getName(), x_b, 0.0, x_e - x_b, 1);
    }

    drawLine(init_x, 0, max_x, 0);
  }
  
}
