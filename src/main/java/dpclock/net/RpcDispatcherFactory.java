/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.net;

import org.jgroups.Channel;
import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.MembershipListener;
import org.jgroups.MessageListener;
import org.jgroups.blocks.RpcDispatcher;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class RpcDispatcherFactory extends AbstractFactoryBean<RpcDispatcher> {

	private Channel channel;
	private MessageListener messageListener = null;
	private MembershipListener membershipListener =null;
	private Object server;
	
	
	@Override
	protected final RpcDispatcher createInstance() throws ChannelClosedException, ChannelNotConnectedException{
		RpcDispatcher disp = new RpcDispatcher(channel, messageListener, membershipListener, server);
		//disp.setConcurrentProcessing(false);
		//disp.setDeadlockDetection(false);
		disp.getChannel().getState(null, 0);
		return disp;
	}
	
	@Override
	public final Class<?> getObjectType() {
		return RpcDispatcher.class;
	}

	public final void setChannel(Channel channel) {
		this.channel = channel;
	}
	public final void setMembershipListener(MembershipListener membershipListener) {
		this.membershipListener = membershipListener;
	}
	public final void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}
	public final void setServer(Object server) {
		this.server = server;
	}
}
