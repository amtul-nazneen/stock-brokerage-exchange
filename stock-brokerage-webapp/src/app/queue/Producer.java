package app.queue;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;

import app.utils.Logger;

public class Producer extends EndPoint{
	
	public Producer(String endPointName) throws Exception{
		super(endPointName);
	}

	public void sendMessage(Serializable object) throws IOException {
		Logger.info("QUEUE Producer: publishing the object to queue:"+object);
	    channel.basicPublish("",endPointName, null, SerializationUtils.serialize(object));
	}	
}
