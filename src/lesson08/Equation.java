package lesson08;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;

public abstract class Equation {
  protected double scale;
  protected Graphics g;
  protected int width;
  protected int height;

  public Equation() {
    scale = 1.0f;
  }

  public Equation(double scale) {
    this.scale = scale;
  }

  public boolean isReady() {
    return g != null;
  }

  public void reset() {
    g = null;
  }

  public void init(Graphics g, JPanel panel) {
    this.g = g;
    width = panel.getWidth();
    height = panel.getHeight();
  }

  protected double minX() {
    return -(width / 2) / scale;
  }

  protected double maxX() {
    return (width / 2) / scale;
  }

  protected double minY() {
    return -(height / 2) / scale;
  }

  protected double maxY() {
    return (height / 2) / scale;
  }

  protected double scaler(double v) {
    return v * scale;
  }

  protected void drawAxis() {
    int min_x = 0;
    int min_y = 0;
    int max_x = width;
    int max_y = height;

    int mid_x = max_x / 2;
    int mid_y = max_y / 2;

    g.setColor(Color.GREEN);
    g.drawLine(min_x, mid_y, max_x, mid_y);

    g.setColor(Color.RED);
    g.drawLine(mid_x, min_y, mid_x, max_y);    
  }

  protected int convertX(double x) {
    x = scaler(x);
    x = x + width / 2;

    return (int) x;
  }

  protected double invertX(int x) {
    double _x = x;
    _x = (_x - width / 2.0) / scale;
    return _x;
  }

  protected int convertY(double y) {
    y = scaler(y);
    y = height / 2 - y;

    return (int) y;
  }

  protected double invertY(int y) {
    double _y = y;
    _y = (height / 2.0 - _y) / scale;

    return _y;
  }

  protected void drawLine(double x1, double y1, double x2, double y2) {
    g.drawLine(convertX(x1), convertY(y1), convertX(x2), convertY(y2));
  }

  protected void drawPoint(double x, double y) {
    drawPoint(convertX(x), convertY(y));
  }

  protected void drawPoint(int x, int y) {
    g.drawLine(x, y, x, y);
  }

  protected void drawCenteredString(String str, double x, double y) {
    FontMetrics metrics = g.getFontMetrics();
    double w = metrics.stringWidth(str) / scale;
    double h = metrics.getHeight() / scale;
    drawCenteredString(str, x - w / 2, y, w, h);
  }

  protected void drawCenteredString(String str, double x, double y, double w, double h) {
    FontMetrics metrics = g.getFontMetrics();
    double _x, _y;
    if (w < metrics.stringWidth(str) / scale) {
      _x = x + (metrics.stringWidth(str) / scale) / 2.0;
    } else {
      _x = x + (w - metrics.stringWidth(str) / scale) / 2.0;
    }
    if (h < metrics.getHeight() / scale) {
      _y = y + (metrics.getHeight() / scale) / 2.0;
    } else {
      _y = y + (h - metrics.getHeight() / scale) / 2.0;
    }

    g.drawString(str, convertX(_x), convertY(_y));
  }

  public abstract void paint();
}
