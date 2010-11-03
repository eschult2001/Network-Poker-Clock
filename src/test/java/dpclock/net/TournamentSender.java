package dpclock.net;

import java.io.Serializable;

import org.jgroups.Channel;
import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.Message;

public class TournamentSender {

	private Channel channel;
	
	public void sendToChannel(Serializable info) {
        Message msg=new Message(null, null, info);
        try {
			channel.send(msg);
		} catch (ChannelNotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChannelClosedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
