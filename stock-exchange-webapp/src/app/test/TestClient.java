package app.test;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestClient {
	static final String REST_URI = "http://localhost:7080/stock-exchange-webapp";
	static final String EXCHANGE_SERVICE = "/ExchangeService/companyPrice/";

	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(REST_URI);
		WebResource addService = service.path("rest").path(EXCHANGE_SERVICE + "currentDay");
		System.out.println("Response: " + getResponse(addService));
		System.out.println("Service1: " + getOutputAsXML(addService));

	}

	private static String getResponse(WebResource service) {
		return service.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
	}

	private static String getOutputAsXML(WebResource service) {
		return service.accept(MediaType.TEXT_XML).get(String.class);
	}
}
