package org.ericschult.demos;

import java.lang.reflect.Method;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.blocks.MessageListenerAdapter;
import org.jgroups.blocks.MethodCall;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

public class RpcDispatcherSample extends MessageListenerAdapter {
    Channel            channel;
    RpcDispatcher      disp;
    RspList            rsp_list;
    String             props; // set by application
    Method			   printMethod;
    
    public RpcDispatcherSample() {
    	
	}
    
    public int print(int number) throws Exception {
        return number * 2;
    }

    public void start() throws Exception {
        channel=new JChannel(props);
        disp=new RpcDispatcher(channel, null, null, this);
        
        channel.connect("RpcDispatcherTestGroup");

        Method method = RpcDispatcherSample.class.getMethod("print", int.class);
        
        for(int i=0; i < 10; i++) {
        	System.out.println("i="+i);
            Util.sleep(1000);
            rsp_list=disp.callRemoteMethods(null, 
            		new MethodCall(method,new Object[]{i}),
            		RequestOptions.SYNC);
            System.out.println("Responses: " +rsp_list);
        }
        channel.close();
        disp.stop();
     }

    public static void main(String[] args) {
        try {
            new RpcDispatcherSample().start();
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
}
  
