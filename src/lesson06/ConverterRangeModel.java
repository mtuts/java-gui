package lesson06;

import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class ConverterRangeModel implements BoundedRangeModel {

  protected ChangeEvent changeEvent = null;
  protected EventListenerList listenerList = new EventListenerList();

  protected int maximum = 10000;
  protected int minimum = 0;
  protected int extent = 0;
  protected double value = 0.0;
  protected double multiplier = 1.0;
  protected boolean isAdjusting = false;

  public ConverterRangeModel() {}

  public double getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(double multiplier) {
    this.multiplier = multiplier;
    fireStateChanged();
  }

  private void fireStateChanged() {
  }

  public double getDoubleValue() {
    return value;
  }

  private void setDoubleValue(double newValue) {
    setRangeProperties(newValue, extent, minimum, maximum, isAdjusting);
  }

  @Override
  public int getMinimum() {
    return minimum;
  }

  @Override
  public void setMinimum(int newMinimum) {
    // Do nothing
  }

  @Override
  public int getMaximum() {
    return maximum;
  }

  @Override
  public void setMaximum(int newMaximum) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int getValue() {
    return (int) getDoubleValue();
  }

  @Override
  public void setValue(int newValue) {
    setDoubleValue((double)newValue);
  }

  @Override
  public void setValueIsAdjusting(boolean b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean getValueIsAdjusting() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int getExtent() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void setExtent(int newExtent) {
    // Do nothing    
  }

  @Override
  public void setRangeProperties(int value, int extent, int min, int max, boolean adjusting) {
    // TODO Auto-generated method stub
    
  }

  public void setRangeProperties(double newValue, int unusedExtent, int unusedMin, int newMax, boolean newAdjusting) {
    if (newMax <= minimum) {
      newMax = minimum + 1;
    }
    if (Math.round(newValue) > newMax) {
      newValue = newMax;
    }

    
  }

  @Override
  public void addChangeListener(ChangeListener x) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void removeChangeListener(ChangeListener x) {
    // TODO Auto-generated method stub
    
  }

  
}
