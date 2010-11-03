package org.ericschult.demos;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

class ActionChangedListener implements PropertyChangeListener {

  private JButton button;

  public ActionChangedListener(JButton button) {
    this.button = button;
  }

  public void propertyChange(PropertyChangeEvent e) {
    String propertyName = e.getPropertyName();
    System.out.println(propertyName);
    if (e.getPropertyName().equals(Action.NAME)) {
      String text = (String) e.getNewValue();
      button.setText(text);
      button.repaint();
    } else if (propertyName.equals("enabled")) {
      Boolean enabledState = (Boolean) e.getNewValue();
      button.setEnabled(enabledState.booleanValue());
      button.repaint();
    } else if (e.getPropertyName().equals(Action.SMALL_ICON)) {
      Icon icon = (Icon) e.getNewValue();
      button.setIcon(icon);
      button.invalidate();
      button.repaint();
    }
  }
}

