package lesson08;

import java.awt.Color;

public class CosineEquation extends Equation {

  public CosineEquation() {
    super();
  }

  public CosineEquation(double scale) {
    super(scale);
  }

  @Override
  public void paint() {
    drawAxis();
    g.setColor(Color.BLACK);

    for (int x = 0; x <= width; x++) {
      double _x = invertX(x);
      double y = Math.cos(_x);
      drawPoint(_x, y);
    }
  }
  
}
