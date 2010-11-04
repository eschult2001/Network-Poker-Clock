/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */

package dpclock;

public class DPClock {

	private DPClock() {}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.setProperty("java.net.preferIPv4Stack", "true");
        Launcher launcher = new Launcher();
        launcher.launch();
	}

}
