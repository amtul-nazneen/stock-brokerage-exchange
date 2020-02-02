package app.utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import app.config.Config;
import app.config.Constants;

public class ServiceUtil {

	public static WebResource getBrokerageServiceHandleOld()
	{
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(Config.BROKERAGE_WEBAPP);
		return service;
	}
	
	public static String getOutputAsXML(WebResource service) {
		return service.accept(MediaType.TEXT_XML).get(String.class);
	}
	
	@SuppressWarnings("rawtypes")
	public static ClientResponse getBrokerageServiceHandle(String endpoint, MultivaluedMap formData)
	{

		Client client = Client.create();
		WebResource webResource = client.resource(Config.BROKERAGE_WEBAPP + endpoint);
		ClientResponse restResponse = webResource
		    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		    .post(ClientResponse.class, formData);
		return restResponse;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addAuthToken(MultivaluedMap formData)
	{
		formData.add(Constants.STOCK_APP_TOKEN, Constants.STOCK_APP_KEY);
	}
}
