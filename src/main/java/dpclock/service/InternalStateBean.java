/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.service;

import java.io.Serializable;

public interface InternalStateBean {

	public Serializable saveInternalState();

	public void restoreInternalState(Serializable s);

}