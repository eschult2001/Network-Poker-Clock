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
public class ChannelBeanFactory extends AbstractFactoryBean<JChannel> implements DisposableBean {
    private String jGroupsConfig;
    private String clusterName;
    private Receiver receiver;
    private boolean autoConnect = true;
    
	@Override
	public Class<?> getObjectType() {
		return JChannel.class;
	}

    protected JChannel createInstance() throws ChannelException{

        JChannel jChannel = new JChannel(jGroupsConfig);
        if (receiver != null)
        	jChannel.setReceiver(receiver);
        if (autoConnect && clusterName!=null) {
	        	jChannel.connect(clusterName, true);
        }
        jChannel.setOpt(Channel.LOCAL, Boolean.FALSE);
        return jChannel;
    }

    // SETTERS
    public void setJgroupsConfig(String jgroupsconfig) {
        this.jGroupsConfig = jgroupsconfig;
    }

    public void setClusterName(String cluster_name) {
        this.clusterName = cluster_name;
    }
    
    public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

    protected void destroyInstance(JChannel instance) throws Exception {
        super.destroyInstance(instance);
        instance.close();
    }

    public void destroy() throws Exception {
        super.destroy();    
    }
}
