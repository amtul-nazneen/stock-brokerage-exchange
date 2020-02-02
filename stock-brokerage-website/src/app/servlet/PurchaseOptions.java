package app.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import app.config.Constants;
import app.dao.User;
import app.utils.Logger;
import app.utils.ServiceUtil;
import app.utils.ServletUtil;
import app.utils.Utils;

public class PurchaseOptions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PurchaseOptions() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--PurchaseOptions--> Servlet");
		HttpSession session = request.getSession(true);
		doGet(request, response);
		String purchaseopt = request.getParameter("purchaseopt");
		Logger.info("Selected Purchase Option:" + purchaseopt);
		Logger.info("Selected Company:" + session.getAttribute("company"));
		String[] selectedStocks = (String[]) session.getAttribute("selectedStocks");
		User user = (User) session.getAttribute("user");
		JsonObject total = new JsonObject();
		total.addProperty("username", user.getUsername());
		total.addProperty("company", session.getAttribute("company").toString());
		total.addProperty("code", purchaseopt);
		JsonArray arr = new JsonArray();
		for (String obj : selectedStocks) {
			arr.add(obj.trim());
		}
		total.add("stocks", arr);
		Logger.info("Input sent to Stock-Brokerage-Server:" + total.toString());
		if (!Utils.isValidTimeAndDay()) {
			Logger.info("Not a valid time/day to make a transaction. Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("InvalidPurchase.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			Logger.info("Valid time/day to make a transaction. Proceeding with the purchase");
			// if (Constants.BUY_NOW.equalsIgnoreCase(purchaseopt))
			// {
			Logger.info("Selected Purchase is:");
			@SuppressWarnings("rawtypes")
			MultivaluedMap formData = new MultivaluedMapImpl();
			ServiceUtil.addAuthToken(formData);
			formData.add("purchase", total.toString());
			Logger.info("Calling the Brokerage-Service/" + Constants.BUY_STOCKS);
			ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.BUY_STOCKS, formData);

			if (restResponse.getStatus() != 200) {
				Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
				Logger.info("Redirecting to error page");
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}
			String result = restResponse.getEntity(String.class);
			String status = ServletUtil.getStatusAfterPurchase(result);
			if (Constants.TRUE.equalsIgnoreCase(status)) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("BuySuccess.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}
			// }
		}
		Logger.info("End of <--PurchaseOptions--> Servlet");
	}

}
