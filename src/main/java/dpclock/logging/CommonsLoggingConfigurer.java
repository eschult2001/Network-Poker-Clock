package dpclock.logging;

import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Configures logging by being constructed. 
 * Especially useful when created as a bean from a Spring configuration file.
 */
public class CommonsLoggingConfigurer {

  public CommonsLoggingConfigurer(Properties props) {
    @SuppressWarnings("rawtypes")
	Iterator i = props.keySet().iterator();

    LogFactory factory = LogFactory.getFactory();

    System.out.println("LogFactory: " + factory);
    for(String attr : factory.getAttributeNames()) {
  	  System.out.println("\t" + attr + "=" + factory.getAttribute(attr));
    }
    System.out.println("LogFactory: End configuration");

//    while (i.hasNext()) {
//      String loggerName = (String) i.next();
//      String levelName = props.getProperty(loggerName);
      
      
//      try {
//          Log l = LogFactory.getLog(levelName);
//          l.
//        Level level = Level.parse(levelName);
//        l.setLevel(level);
//      }
//      catch ( IllegalArgumentException e ) {
//        System.err.println( "WARNING: Unable to parse '" 
//           + levelName + "' as a java.util.Level for logger " 
//           + loggerName + "; ignoring..." );
//      }
//    }
  }
}