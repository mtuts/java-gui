package lesson07;

import java.awt.Color;

public class Square extends Equation {

  public Square() {
    super();
  }

  public Square(double scale) {
    super(scale);
  }

  @Override
  public void paint() {
    drawAxis();
    g.setColor(Color.BLACK);

    for (int y = 0; y < height / 2; y++) {
      double _y = invertY(y);
      double x = Math.sqrt(_y);
      
      drawPoint( x, _y);
      drawPoint(-x, _y);
    }
  }
  
}
