package app.utils;

import app.config.Constants;
import app.queue.Producer;
import app.queue.AsynchBuyConsumer;
import app.queue.AsynchSellConsumer;

public class ServiceUtil {
	public static void asynchBuyConsumer() {
		Logger.info("In asynchronous buy consumer");
		
		AsynchBuyConsumer consumer=null;
		try {
			consumer = new AsynchBuyConsumer(Constants.ASYNCH_BUY_QUEUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();
		Logger.info("Buy Queue Consumer Thread Started");
	} 
	

	public static void asynchBuyProducer(String purchase) {
		Logger.info("In Asynchronous Buy Producer");
		Producer producer = null;
		try {
			producer = new Producer(Constants.ASYNCH_BUY_QUEUE);
			Logger.info("Added the buy message to the queue");
			producer.sendMessage(purchase);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void asynchSellConsumer() {
		Logger.info("In asynchronous sell consumer");
		AsynchSellConsumer consumer=null;
		try {
			consumer = new AsynchSellConsumer(Constants.ASYNCH_SELL_QUEUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();
		Logger.info("Sell Queue Consumer Thread Started");
	} 
	

	public static void asynchSellProducer(String sell) {
		Logger.info("In Asynchronous Sell Producer");
		Producer producer = null;
		try {
			producer = new Producer(Constants.ASYNCH_SELL_QUEUE);
			Logger.info("Added the sell message to the queue");
			producer.sendMessage(sell);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
