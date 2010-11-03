package dpclock.schema;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;

public abstract class LevelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String name = "Unknown Level";
	protected int time = 15*60;
	protected int ante = 0;
	protected int smallBlind = 25;
	protected int bigBlind = 50;
	protected String note = null;
	protected boolean autoNext = true;
	protected transient LevelBean prev = this;
	protected transient LevelBean next = this;

	public LevelBean() {
		super();
	}

	@XmlAttribute()
	@XmlID
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name = "seconds")
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@XmlAttribute
	public int getAnte() {
		return ante;
	}

	public void setAnte(int ante) {
		this.ante = ante;
	}

	@XmlAttribute
	public int getSmallBlind() {
		return smallBlind;
	}

	public void setSmallBlind(int smallBlind) {
		this.smallBlind = smallBlind;
	}

	@XmlAttribute
	public int getBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(int bigBlind) {
		this.bigBlind = bigBlind;
	}

	@XmlElement(required = false)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@XmlAttribute()
	public boolean getAutoNext() {
		return this.autoNext;
	}

	public void setAutoNext(boolean autoNext) {
		this.autoNext = autoNext;
	}

	@XmlTransient
	public LevelBean getNext() {
		return next;
	}

	public void setNext(LevelBean next) {
		this.next = next;
	}

	@XmlTransient
	public LevelBean getPrev() {
		return prev;
	}

	public void setPrev(LevelBean prev) {
		this.prev = prev;
	}

}