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

public class AsynchBuyConsumer extends EndPoint implements Runnable, Consumer {

	public AsynchBuyConsumer(String endPointName) throws Exception {
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
		Logger.info("Asynch Buy Consumer " + consumerTag + " registered");
	}

	public void handleDelivery(String consumerTag, Envelope env, BasicProperties props, byte[] body)
			throws IOException {
		try {
			Thread.sleep(15000);
			String message = (String) SerializationUtils.deserialize(body);
			Logger.info("Retrieved the message from the queue:" + message);
			Gson gson = new Gson();
			Object object = gson.fromJson(message, Stocks.class);
			Stocks stks = (Stocks) object;
			String code = stks.getCode();
			String company = stks.getCompany();
			String username = stks.getUsername();
			for (int counter = 0; counter < stks.getStocks().size(); counter++) {
				String stck_price = stks.getStocks().get(counter);
				String cost = stck_price.split("-")[0];
				String qty = stck_price.split("-")[1];
				boolean resultfromquery = QueryHandler.buyStocks(username, company, qty, code, cost);
				if (resultfromquery == false) {
					Logger.info("Error in adding values");
				}
			}
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
