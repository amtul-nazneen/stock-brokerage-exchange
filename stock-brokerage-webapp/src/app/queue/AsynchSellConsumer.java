package app.queue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.google.gson.Gson;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import app.dao.Stocks;
import app.db.QueryHandler;
import app.utils.Logger;

public class AsynchSellConsumer extends EndPoint implements Runnable, Consumer {

	public AsynchSellConsumer(String endPointName) throws Exception {
		super(endPointName);
	}

	public void run() {
		try {
				channel.basicConsume(endPointName, true, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleConsumeOk(String consumerTag) {
		System.out.println("Asynch Sell Consumer " + consumerTag + " registered");
	}

	public void handleDelivery(String consumerTag, Envelope env, BasicProperties props, byte[] body)
			throws IOException {
		try {
			Thread.sleep(15000);
			String message = (String) SerializationUtils.deserialize(body);
			Logger.info("Retrieved the message from the queue:" + message);
			String recurringplan=message.split(":")[0];
			String stockids=message.split(":")[1];
			Logger.info("Calling the SellStocks Recurring");
			QueryHandler.sellStocksRecurring(stockids, recurringplan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleCancel(String consumerTag) {
	}

	public void handleCancelOk(String consumerTag) {
	}

	public void handleRecoverOk(String consumerTag) {
	}

	public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {
	}
}
