/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {
	public final void launch() {
		String[] contextPaths = new String[] {
				"dpclock/conf/app-context.xml",
				// "dpclock/conf/app-logging.xml",
				// "dpclock/conf/theme-halo.xml",
				"dpclock/conf/theme-amy.xml" };
		new ClassPathXmlApplicationContext(contextPaths);

	}
}