package app.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import app.config.Constants;
import app.utils.Logger;
import app.utils.ServiceUtil;
import app.utils.ServletUtil;
import app.utils.Utils;

public class SellOptions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SellOptions() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--SellOptions--> Servlet");
		HttpSession session = request.getSession(true);
		String sellopt = request.getParameter("sellopt");
		Logger.info("Selected Sell Option:" + sellopt);
		String selectedStockIds = (String) session.getAttribute("sellstocks");
		String inputToWS = sellopt + ":" + selectedStockIds;
		Logger.info("Input sent to Stock-Brokerage-Server:" + inputToWS);
		if (!Utils.isValidTimeAndDay()) {
			Logger.info("Not a valid time/day to make a transaction. Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("InvalidPurchase.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			Logger.info("Valid time/day to make a transaction. Proceeding with the purchase");
			@SuppressWarnings("rawtypes")
			MultivaluedMap formData = new MultivaluedMapImpl();
			ServiceUtil.addAuthToken(formData);
			formData.add("sell", inputToWS);
			Logger.info("Calling the Brokerage-Service/" + Constants.SELL_STOCKS);
			ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.SELL_STOCKS, formData);

			if (restResponse.getStatus() != 200) {
				Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
				Logger.info("Redirecting to error page");
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}
			String result = restResponse.getEntity(String.class);
			String status = ServletUtil.getStatusAfterSellStock(result);
			if (Constants.TRUE.equalsIgnoreCase(status)) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("SellConfirm.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}
		}
		Logger.info("End of <--SellOptions--> Servlet");
	}

}
