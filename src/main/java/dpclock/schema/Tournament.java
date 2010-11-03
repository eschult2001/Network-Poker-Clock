package dpclock.schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(
		propOrder={"tournamentName","currentLevel",
				"levels"}
)
public class Tournament implements Serializable {

	private static final long serialVersionUID = 2L;

	protected String tournamentName;
	protected LevelBean currentLevel;
	protected List<LevelBean> levels = new ArrayList<LevelBean>();


	
	
	@XmlElement(name="name", required = true)
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	@XmlIDREF
	@XmlElement(name="startingLevel",required = true)
	public LevelBean getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(LevelBean currentLevel) {
		this.currentLevel = currentLevel;
	}
		
	@XmlElementWrapper(name = "levels", required=true)
    @XmlElements({
        @XmlElement(name="break", type=BreakLevelBean.class),
        @XmlElement(name="level", type=TournamentLevelBean.class)
    })
	public List<LevelBean> getLevels() {
		return levels;
	}
	public void setLevels(List<LevelBean> levels) {
		this.levels = levels;
	}
}
