package dpclock;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {
	public void launch() {
		String[] contextPaths = new String[] { 
				"dpclock/conf/app-context.xml"
				,"dpclock/conf/app-logging.xml"
				//,"dpclock/conf/theme-halo.xml"
				,"dpclock/conf/theme-amy.xml"
		};
		ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext(contextPaths);

	}
}