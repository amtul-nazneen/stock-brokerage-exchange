package app.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import app.config.Constants;
import app.data.StockPrices;
import app.utils.Logger;

@Path("ExchangeService")
public class ExchangeService {

	@POST
	@Path("/companyPrice")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String currentPrice(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--companyPrice--> from Stock-Brokerage-Webapp");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			String option = formParam.getFirst("option");
			String result = StockPrices.getStockPrices(option);
			Logger.info("Sending RESPONSE to Stock-Brokerage-App");
			return result;
		}
	}
}
