package app.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import app.config.Config;
import app.config.Constants;
import app.dao.Stocks;
import app.db.QueryHandler;
import app.queue.Producer;
import app.queue.AsynchBuyConsumer;
import app.utils.Logger;
import app.utils.ServiceUtil;

@Path("BrokerageService")
public class BrokerageService {

	@Path("/verifyLoginUser")
	@POST
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String verifyLoginUser(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--verifyLoginUser--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String password = formParam.getFirst("password");
			String username = formParam.getFirst("username");
			boolean result = QueryHandler.verifyLoginUser(username, password);
			String output = "";
			if (result)
				output = QueryHandler.getUserProfile(username);
			else
				output = "<USER/>";
			Logger.info("Sending the <--verifyLoginUser-->XML_RESPONSE to Stock-Brokerage-Website" + output);
			return output;
		}
	}

	@POST
	@Path("/allCompanies")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String getAllCompanies(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--allCompanies--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String result = QueryHandler.getAllCompanies();
			Logger.info("Sending the <--allCompanies-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}

	@POST
	@Path("/signUpUser")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String signUpUser(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--signUpUser--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String password = formParam.getFirst("password");
			String username = formParam.getFirst("username");
			String email = formParam.getFirst("email");
			String userAddress = formParam.getFirst("address");
			boolean result = QueryHandler.signUpUser(username, password, email, userAddress);
			String output = null;
			if (result)
				output = QueryHandler.getUserProfile(username);
			Logger.info("Sending the <--signUpUser-->XML_RESPONSE to Stock-Brokerage-Website" + output);
			return output;
		}
	}

	@POST
	@Path("/forgotPwd")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String forgotPwd(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--forgotPwd--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String email = formParam.getFirst("email");
			boolean result = QueryHandler.forgotPwd(email);
			Logger.info("Sending the <--forgotPwd-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return "<RESET><STATUS>"+String.valueOf(result)+"</STATUS></RESET>";
		}
	}

	@POST
	@Path("/resetUserPwd")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String resetUserPwd(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--resetUserPwd--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String password = formParam.getFirst("password");
			String username = formParam.getFirst("username");
			boolean result = QueryHandler.resetUserPwd(username, password);
			Logger.info("Sending the <--resetUserPwd-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return "<RESET><STATUS>"+String.valueOf(result)+"</STATUS></RESET>";
		}
	}

	@POST
	@Path("/recoverAccount")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String recoverAccount(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--recoverAccount--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String code = formParam.getFirst("code");
			String username = formParam.getFirst("username");
			boolean result = QueryHandler.recoverAccount(username, code);
			String output = null;
			if (result)
				output = QueryHandler.getUserProfile(username);
			Logger.info("Sending the <--recoverAccount-->XML_RESPONSE to Stock-Brokerage-Website" + output);
			return output;
		}
	}

	@POST
	@Path("/userSchedule")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String getUserSchedule(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--userSchedule--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String username = formParam.getFirst("username");
			String result = QueryHandler.getUserSchedule(username);
			Logger.info("Sending the <--userSchedule-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}

	@POST
	@Path("/buyStocksold")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String buyStocksold(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--buyStocks--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String purchase = formParam.getFirst("purchase");
			System.out.println("GOT:" + purchase);
			Gson gson = new Gson();
			Object object = gson.fromJson(purchase, Stocks.class);
			Stocks stks = (Stocks) object;
			String code = stks.getCode();
			String company = stks.getCompany();
			String username = stks.getUsername();
			String recurringplan = "";
			StringBuilder result = new StringBuilder();
			result.append("<PURCHASE><STATUS>");
			String status = "";
			if (code.equals("buynow")) {
				recurringplan = "N/A";
			}
			if (code.equals("weekly") || code.equals("monthly")) {
				recurringplan = code;
			}
			for (int counter = 0; counter < stks.getStocks().size(); counter++) {
				String stck_price = stks.getStocks().get(counter);
				String cost = stck_price.split("-")[0];
				String qty = stck_price.split("-")[1];
				boolean resultfromquery = QueryHandler.buyStocks(username, company, qty, recurringplan, cost);
				if (resultfromquery == false) {
					status = "false";
					result.append("false</STATUS></PURCHASE>");
					return result.toString();
				}
			}
			result.append("true</STATUS></PURCHASE>");
			String finalresult = result.toString();
			Logger.info("Sending the <--buyStocks-->XML_RESPONSE to Stock-Brokerage-Website" + finalresult);
			return finalresult;
		}
	}

	@POST
	@Path("/userStocks")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String userStocks(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--userStocks--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String username = formParam.getFirst("username");
			String result = "";
			result = QueryHandler.getAllUserStocks(username);
			Logger.info("Sending the <--userStocks-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}

	@POST
	@Path("/userAccounts")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String userAccounts(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--userAccounts--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String username = formParam.getFirst("username");
			String result = "";
			result = QueryHandler.getAllUserAccounts(username);
			Logger.info("Sending the <--userAccounts-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}

	@POST
	@Path("/addAccount")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String addAccount(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--addAccount--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String username = formParam.getFirst("username");
			String account = formParam.getFirst("account");
			String routing = formParam.getFirst("routing");
			String result = "";
			result = QueryHandler.addAccount(username, account, routing);
			Logger.info("Sending the <--addAccount-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}

	@POST
	@Path("/transferAmount")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String transferAmount(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--transferAmount--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String payeraccount = formParam.getFirst("payeraccount");
			String payeeaccount = formParam.getFirst("payeeaccount");
			String amount = formParam.getFirst("amount");
			String result = "";
			result = QueryHandler.transferAmount(payeraccount, payeeaccount, amount);
			Logger.info("Sending the <--transferAmount-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}


	@POST
	@Path("/deleteSchedule")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String deleteSchedule(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--deleteSchedule--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String stocks = formParam.getFirst("stock_ids");
			String result = "";
			result = QueryHandler.deleteSchedules(stocks);
			Logger.info("Sending the <--deleteSchedule-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}

	@POST
	@Path("/deleteSellSchedule")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String deleteSellSchedule(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--deleteSellSchedule--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String stocks = formParam.getFirst("stock_ids");
			String result = "";
			result = QueryHandler.deleteSellSchedules(stocks);
			Logger.info("Sending the <--deleteSellSchedule-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}
	
	@POST
	@Path("/editProfile")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String editProfile(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--editProfile--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String address = formParam.getFirst("address");
			String email = formParam.getFirst("email");
			String username = formParam.getFirst("username");
			String result = "";
			result = QueryHandler.editProfile(username, email, address);
			Logger.info("Sending the <--editProfile-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}

	@POST
	@Path("/companyPrice")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String currentPrice(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--companyPrice--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to Stock-Exchange-Webapp");
			Client client = Client.create();
			WebResource webResource = client.resource(Config.EXCHANGE_WEBSERVICE_URI + Config.COMPANY_PRICE);
			ClientResponse restResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					.post(ClientResponse.class, formParam);
			String result = restResponse.getEntity(String.class);
			Logger.info("Received the XML_RESPONSE from Stock-Exchange-Webapp");
			Logger.info("Sending the <--companyPrice-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}
	
	@POST
	@Path("/buyStocks")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String buyStocks(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--buyStocks--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String purchase = formParam.getFirst("purchase");
			Logger.info("Received Input:" + purchase);
			Gson gson = new Gson();
			Object object = gson.fromJson(purchase, Stocks.class);
			Stocks stks = (Stocks) object;
			String code = stks.getCode();
			String company = stks.getCompany();
			String username = stks.getUsername();
			String recurringplan = "";
			StringBuilder result = new StringBuilder();
			result.append("<PURCHASE><STATUS>");
			String status = "";
			if (Constants.BUY_NOW.equalsIgnoreCase(code)) {
				recurringplan = "N/A";
				for (int counter = 0; counter < stks.getStocks().size(); counter++) {
					String stck_price = stks.getStocks().get(counter);
					String cost = stck_price.split("-")[0];
					String qty = stck_price.split("-")[1];
					boolean resultfromquery = QueryHandler.buyStocks(username, company, qty, recurringplan, cost);
					if (resultfromquery == false) {
						status = "false";
						result.append("false</STATUS></PURCHASE>");
						return result.toString();
					}
				}
				result.append("true</STATUS></PURCHASE>");
				String finalresult = result.toString();
				Logger.info("Sending the <--buyStocks-->Synchronous XML_RESPONSE to Stock-Brokerage-Website" + finalresult);
				return finalresult;
			}
			else if (Constants.BUY_MONTHLY.equalsIgnoreCase(code) || Constants.BUY_WEEKLY.equalsIgnoreCase(code)) {
				recurringplan = code;
				Logger.info("Purchase Method is Scheduled, making the Asynchronous Call");
				ServiceUtil.asynchBuyProducer(purchase);
				ServiceUtil.asynchBuyConsumer();
				Logger.info("Completed the asynchronous call");
				String asynchResponse="<PURCHASE><STATUS>true</STATUS></PURCHASE>";
				Logger.info("Sending the <--buyStocks-->Synchronous XML_RESPONSE to Stock-Brokerage-Website" +asynchResponse );
				return asynchResponse;
			}
			return "";
		}
	}
	@POST
	@Path("/sellStocks")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String sellStocks(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--sellStocks--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String sell = formParam.getFirst("sell");
			Logger.info("Received Input:" + sell);
			String code=sell.split(":")[0];
			String stockIds=sell.split(":")[1];
			if (Constants.SELL_NOW.equalsIgnoreCase(code)) {
				String result=QueryHandler.sellStocksOneTime(stockIds);
				Logger.info("Sending the <--buyStocks-->Synchronous XML_RESPONSE to Stock-Brokerage-Website" + result);
				return result;
			}
			else if (Constants.SELL_MONTHLY.equalsIgnoreCase(code) || Constants.SELL_WEEKLY.equalsIgnoreCase(code)) {
				Logger.info("Sell Method is Scheduled, making the Asynchronous Call");
				ServiceUtil.asynchSellProducer(sell);
				ServiceUtil.asynchSellConsumer();
				Logger.info("Completed the asynchronous call");
				String asynchResponse="<SELL><STATUS>true</STATUS></SELL>";
				Logger.info("Sending the <--sellStocks-->Synchronous XML_RESPONSE to Stock-Brokerage-Website" +asynchResponse );
				return asynchResponse;
			}
			return "";
		}
	}

	@POST
	@Path("/userSellSchedule")
	@Produces(MediaType.TEXT_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String getUserSellSchedule(MultivaluedMap<String, String> formParam) {
		Logger.info("REQUEST for <--userSellSchedule--> from Stock-Brokerage-Website");
		Logger.info("Parsing the parameters:" + formParam.toString());
		if (formParam.getFirst(Constants.STOCK_APP_TOKEN)==null || !formParam.getFirst(Constants.STOCK_APP_TOKEN).equals(Constants.STOCK_APP_KEY)) {
			Logger.info("Unauthenticated Request,returning empty string");
			return Constants.UNAUTHORIZED;
		} else {
			Logger.info("Request Authenticated,Proceeding further");
			Logger.info("Sending Requests to QueryHandler");
			String username = formParam.getFirst("username");
			String result = QueryHandler.getUserSellSchedule(username);
			Logger.info("Sending the <--userSellSchedule-->XML_RESPONSE to Stock-Brokerage-Website" + result);
			return result;
		}
	}
	
}
