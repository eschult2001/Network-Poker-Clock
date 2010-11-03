/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.schema;

import java.io.Serializable;

public class BreakLevelBean  extends LevelBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8542876885394898925L;

	public BreakLevelBean() {
		setTime(0); // Default time for Breaks
	}
	
	@Override
	public String toString() {
		return name;
	}

	private LevelBean safeNext() {
		if (next==null) {
			return null;
		}

		int maxDepth = 3; //safety for looping graphs
		LevelBean nextLevel = next;
		while(--maxDepth > 0 && nextLevel instanceof BreakLevelBean) {
			nextLevel = nextLevel.getNext();
		}
		return (nextLevel instanceof BreakLevelBean && nextLevel==next) ? null : nextLevel;
	}
	
	@Override
	public final int getAnte() {
		final LevelBean nextLevel = safeNext();
		return nextLevel==null ? 0 : nextLevel.getAnte();
	}
	
	@Override
	public int getSmallBlind() {
		final LevelBean nextLevel = safeNext();
		return nextLevel==null ? 0 : nextLevel.getSmallBlind();
	}
	
	@Override
	public int getBigBlind() {
		final LevelBean nextLevel = safeNext();
		return nextLevel==null ? 0 : nextLevel.getBigBlind();
	}

	@Override
	public final boolean getAutoNext() {
		return false;
	}
}
