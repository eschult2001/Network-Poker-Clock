package dpclock.schema;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

//@XmlType(name="Level", namespace="##default"
	//,propOrder={"name","ante","smallBlind","bigBlind","autoNext","prev","next","note"}
//)
public class TournamentLevelBean extends LevelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 564501447039972193L;

	public String toString() {
		return String.format("%s: %d/%d/%d",name,getSmallBlind(),getBigBlind(),getAnte());
	}

}
