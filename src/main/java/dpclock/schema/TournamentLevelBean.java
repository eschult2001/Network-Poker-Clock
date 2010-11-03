/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.schema;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

//@XmlType(name="Level", namespace="##default"
	//,propOrder={"name","ante","smallBlind","bigBlind","autoNext","prev","next","note"}
//)
public class TournamentLevelBean extends LevelBean implements Serializable {

	protected int ante = 0;
	protected int smallBlind = 25;
	protected int bigBlind = 50;

	private static final long serialVersionUID = 564501447039972193L;

	public String toString() {
		return String.format("%s: %d/%d/%d",name,getAnte(),getSmallBlind(),getBigBlind());
	}

	/* (non-Javadoc)
	 * @see dpclock.schema.LevelBean#getAutoNext()
	 */
	@Override
	public final boolean getAutoNext() {
		return true;
	}

	@XmlAttribute
	@Override 
	public final int  getAnte() { return ante; }
	public final void setAnte(final int ante) {
		this.ante = ante;
	}

	@XmlAttribute
	@Override 
	public final int  getBigBlind() { return bigBlind; }
	public final void setBigBlind(final int bigBlind) {
		this.bigBlind = bigBlind;
	}
	
	@XmlAttribute
	@Override 
	public final int  getSmallBlind() { return smallBlind;	}
	public final void setSmallBlind(final int smallBlind) {
		this.smallBlind = smallBlind;
	}
}

