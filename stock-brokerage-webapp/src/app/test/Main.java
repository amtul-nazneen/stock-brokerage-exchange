package app.test;

import java.util.HashMap;

import app.queue.Producer;
import app.queue.AsynchBuyConsumer;

public class Main {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Main() throws Exception{
		
		
		
		Producer producer = new Producer("queue");
		
		/*for (int i = 0; i < 10; i++) {
			HashMap message = new HashMap();
			message.put("message number", i);
			producer.sendMessage(message);
			System.out.println("Message Number "+ i +" sent.");
		}*/
		producer.sendMessage("hello");
		producer.sendMessage("world");
	}
	
	public static void main(String[] args) throws Exception{
	  new Main();
	}
}