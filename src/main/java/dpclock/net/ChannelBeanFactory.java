/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.net;
import org.jgroups.Channel;
import org.jgroups.ChannelException;
import org.jgroups.JChannel;
import org.jgroups.Receiver;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * @author 
 */
public class ChannelBeanFactory extends AbstractFactoryBean<JChannel> {
    private String jGroupsConfig;
    private String clusterName;
    private Receiver receiver;
    private boolean autoConnect = true;
    
	@Override
	public final Class<?> getObjectType() {
		return JChannel.class;
	}

    protected final JChannel createInstance() throws ChannelException{

        JChannel jChannel = new JChannel(jGroupsConfig);
        if (receiver != null) {
        	jChannel.setReceiver(receiver);
        }
        if (autoConnect && clusterName!=null) {
	        	jChannel.connect(clusterName, true);
        }
        jChannel.setOpt(Channel.LOCAL, Boolean.FALSE);
        return jChannel;
    }

    protected final void destroyInstance(final JChannel instance) throws Exception {
        instance.close();
        super.destroyInstance(instance);
    }

    
    // SETTERS
    public final void setJgroupsConfig(final String jgroupsconfig) {
        this.jGroupsConfig = jgroupsconfig;
    }

    public final void setClusterName(final String cluster_name) {
        this.clusterName = cluster_name;
    }
    
    public final void setReceiver(final Receiver receiver) {
		this.receiver = receiver;
	}

}
