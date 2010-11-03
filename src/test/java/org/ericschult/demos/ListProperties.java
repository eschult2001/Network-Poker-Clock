package org.ericschult.demos;

import java.util.Enumeration;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
 
public class ListProperties {
  public static void main(String args[]) throws Exception {
    UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();
 
    for (UIManager.LookAndFeelInfo info : looks) {
      UIManager.setLookAndFeel(info.getClassName());
 
      UIDefaults defaults = UIManager.getDefaults();
      @SuppressWarnings("rawtypes")
	Enumeration newKeys = defaults.keys();
 
      while (newKeys.hasMoreElements()) {
        Object obj = newKeys.nextElement();
        if (obj.toString().toLowerCase().contains("icon")) 
        	System.out.printf("%50s : %s\n", obj, UIManager.get(obj));
      }
    }
  }
}