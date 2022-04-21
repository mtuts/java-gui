package lesson07;

import java.awt.Color;

public class SineEquation extends Equation {

  public SineEquation() {
    super();
  }

  public SineEquation(double scale) {
    super(scale);
  }

  @Override
  public void paint() {
    drawAxis();
    g.setColor(Color.BLACK);

    for (int x = 0; x <= width; x++) {
      double _x = invertX(x);
      double y = Math.sin(_x);
      drawPoint(_x, y);
    }
  }
  
}
