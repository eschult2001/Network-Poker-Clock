/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
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
	protected String note = null;

	protected transient LevelBean prev = this;
	protected transient LevelBean next = this;

	public LevelBean() {
		super();
	}

	@XmlAttribute()
	@XmlID
	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name = "seconds")
	public final int getTime() { return time; }
	public final void setTime(final int time) {
		this.time = time;
	}

	public abstract int getAnte();
	//public abstract void setAnte(int ante);

	public abstract int getSmallBlind();
	//public abstract void setSmallBlind(int smallBlind);

	public abstract int getBigBlind();
	//public abstract void setBigBlind(int bigBlind);

	@XmlElement(required = false)
	public final String getNote() {return note;}
	public final void setNote(final String note) {
		this.note = note;
	}


	@XmlTransient
	public abstract boolean getAutoNext();


	@XmlTransient
	public final LevelBean getNext() { return next; }
	public final void setNext(LevelBean next) {
		this.next = next;
	}

	@XmlTransient
	public final LevelBean getPrev() { return prev; }
	public final void setPrev(final LevelBean prev) {
		this.prev = prev;
	}

}