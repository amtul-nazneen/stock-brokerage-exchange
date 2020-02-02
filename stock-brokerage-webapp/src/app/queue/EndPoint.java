package app.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import app.config.Config;

public abstract class EndPoint{
	
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;
	
    public EndPoint(String endpointName) throws Exception{
         this.endPointName = endpointName;
         ConnectionFactory factory = new ConnectionFactory();
         factory.setHost(Config.RABBITMQ_HOST);
         connection = factory.newConnection();
         channel = connection.createChannel();
         channel.queueDeclare(endpointName, false, false, false, null);
    }
	
     public void close() throws Exception{
         this.channel.close();
         this.connection.close();
     }
}