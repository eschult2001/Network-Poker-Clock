/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.net;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import dpclock.service.TournamentControllerImpl;

public class TournamentReceiver extends ReceiverAdapter implements Receiver {

	private static final Log log = LogFactory.getLog(TournamentReceiver.class);

	@Resource
	protected TournamentControllerImpl tournamentControllerImpl;
	
	@Override
	public byte[] getState() {
		Serializable state = tournamentControllerImpl.saveInternalState();
        log.info("** getState: " + state);

		try {
            return Util.objectToByteBuffer(state);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void setState(byte[] arg0) {
        try {
            Serializable state = (Serializable) Util.objectFromByteBuffer(arg0);
            log.info("** setState: " + state);
            tournamentControllerImpl.restoreInternalState(state);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
	}

//	@PostConstruct
//	public void afterPropertiesSet() throws Exception {
//	}

	@Override
	public void receive(Message msg) {
		log.debug("Message: "+msg);
	}

	@Override
	public void viewAccepted(View new_view) {
		log.info("View Accepted: " + new_view);
		super.viewAccepted(new_view);
	}
	
}
