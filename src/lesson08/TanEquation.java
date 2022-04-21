package lesson08;

import java.awt.Color;

public class TanEquation extends Equation {

  public TanEquation() {
    super();
  }

  public TanEquation(double scale) {
    super(scale);
  }

  @Override
  public void paint() {
    drawAxis();
    g.setColor(Color.BLACK);

    for (int x = 0; x <= width; x++) {
      double _x = invertX(x);
      double y = Math.tan(_x);
      drawPoint(_x, y);
    }
    for (int y = 0; y <= height; y++) {
      double _y = invertY(y);
      double x = Math.atan(_y);

      drawPoint(x, _y);
    }
  }
  
}
