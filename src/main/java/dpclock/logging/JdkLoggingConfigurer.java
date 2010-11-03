package dpclock.logging;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.springframework.util.CollectionUtils;

/**
 * Configures logging by being constructed. 
 * Especially useful when created as a bean from a Spring configuration file.
 */
public class JdkLoggingConfigurer {

  public JdkLoggingConfigurer(Properties props) {
    @SuppressWarnings("rawtypes")
	Iterator i = props.keySet().iterator();

    System.out.println("JdkLoggingConfigurer:");
    LogManager mgr = LogManager.getLogManager();
    System.out.println("LogManager:"+mgr);
    
    Iterator<String> loggerNames = CollectionUtils.toIterator(mgr.getLoggerNames());
    while (loggerNames.hasNext()){
    	System.out.println(" Logger: "+loggerNames.next());
    }
    
    while (i.hasNext()) {
      String loggerName = (String) i.next();
      String levelName = props.getProperty(loggerName);
      try {
        Level level = Level.parse(levelName);
        Logger l = Logger.getLogger(loggerName);
        l.setLevel(level);
      }
      catch ( IllegalArgumentException e ) {
        System.err.println( "WARNING: Unable to parse '" 
           + levelName + "' as a java.util.Level for logger " 
           + loggerName + "; ignoring..." );
      }
    }
  }
}