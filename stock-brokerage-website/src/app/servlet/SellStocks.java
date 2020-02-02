package app.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.utils.Logger;
import app.utils.Utils;

public class SellStocks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SellStocks() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--SellStocks--> Servlet");
		String[] sellstocks = request.getParameterValues("sellstocks");
		Logger.info("Total No. of Stocks to Sell:" + sellstocks.length);
		String stocks = "";
		for (String obj : sellstocks) {
			stocks = stocks + obj.trim() + ",";
		}
		stocks = stocks.substring(0, stocks.length() - 1);
		Logger.info("List of StockIDs to Sell:" + stocks);
		HttpSession session = request.getSession(true);
		session.setAttribute("sellstocks", stocks);
		if (!Utils.isValidTimeAndDay()) {
			Logger.info("Not a valid time/day to make a transaction. Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("InvalidPurchase.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			Logger.info("Redirecting to Sell-Stock options");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Sell.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}

		/*
		 * User user = (User) session.getAttribute("user");
		 * Logger.info("Passing these Stock-Brokerage-Webapp for User:" +
		 * user.getUsername());
		 * 
		 * @SuppressWarnings("rawtypes") MultivaluedMap formData = new
		 * MultivaluedMapImpl(); ServiceUtil.addAuthToken(formData);
		 * formData.add("stock_ids", stocks); if (!Utils.isValidTimeAndDay()) { Logger.
		 * info("Not a valid time/day to make a transaction. Redirecting to error page"
		 * ); RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("InvalidPurchase.jsp"); if (dispatcher != null)
		 * { dispatcher.forward(request, response); } } else { Logger.
		 * info("Valid time/day to make a transaction. Proceeding with the purchase");
		 * Logger.info("Calling the Brokerage-Service/" + Constants.SELL_STOCKS);
		 * ClientResponse restResponse =
		 * ServiceUtil.getBrokerageServiceHandle(Constants.SELL_STOCKS, formData); if
		 * (restResponse.getStatus() != 200) { Logger.error(new
		 * RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
		 * Logger.info("Redirecting to error page"); RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("Error.jsp"); if (dispatcher != null) {
		 * dispatcher.forward(request, response); } } else { String result =
		 * restResponse.getEntity(String.class); String status =
		 * ServletUtil.getStatusAfterSellStock(result); Logger.info("Sell Stock Status:"
		 * + status); if (Constants.TRUE.equalsIgnoreCase(status)) { RequestDispatcher
		 * dispatcher = request.getRequestDispatcher("SellConfirm.jsp"); if (dispatcher
		 * != null) { dispatcher.forward(request, response); } } else {
		 * Logger.info("There's some issue in selling the stocks"); RequestDispatcher
		 * dispatcher = request.getRequestDispatcher("GenericError.jsp"); if (dispatcher
		 * != null) { dispatcher.forward(request, response); } } } }
		 */
		Logger.info("End of <--SellStocks--> Servlet");
	}

}
