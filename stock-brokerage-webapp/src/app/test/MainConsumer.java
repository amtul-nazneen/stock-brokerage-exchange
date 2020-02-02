package app.test;

import app.queue.AsynchBuyConsumer;

public class MainConsumer {
public static void main(String[] args) throws Exception {
	AsynchBuyConsumer consumer = new AsynchBuyConsumer("queue");
	Thread consumerThread = new Thread(consumer);
	consumerThread.start();
}
}
