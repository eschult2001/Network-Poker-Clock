package dpclock.schema;

import java.io.Serializable;

public class BreakLevelBean  extends LevelBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8542876885394898925L;

	public BreakLevelBean() {
		setAutoNext(false);
		setTime(0);
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int getAnte() {
		if (getNext()==this || getNext()==null)
			return 0;
		return next.getAnte();
	}

	@Override
	public int getSmallBlind() {
		if (next==this || next==null)
			return 0;
		return next.getSmallBlind();
	}

	@Override
	public int getBigBlind() {
		if (next==this || next==null)
			return 0;
		return next.getBigBlind();
	}

}
