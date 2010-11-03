package dpclock.net;

import org.jgroups.Channel;
import org.jgroups.MembershipListener;
import org.jgroups.MessageListener;
import org.jgroups.blocks.RpcDispatcher;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class RpcDispatcherFactory extends AbstractFactoryBean<RpcDispatcher> {

	Channel channel;
	MessageListener messageListener = null;
	MembershipListener membershipListener =null;
	Object server;
	
	
	@Override
	protected RpcDispatcher createInstance() throws Exception {
		RpcDispatcher disp = new RpcDispatcher(channel, messageListener, membershipListener, server);
		//disp.setConcurrentProcessing(false);
		//disp.setDeadlockDetection(false);
		disp.getChannel().getState(null, 0);
		return disp;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return RpcDispatcher.class;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public void setMembershipListener(MembershipListener membershipListener) {
		this.membershipListener = membershipListener;
	}
	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}
	public void setServer(Object server) {
		this.server = server;
	}
}
