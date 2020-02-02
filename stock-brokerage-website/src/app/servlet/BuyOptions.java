package app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import app.cache.SingletonMemCache;
import app.config.Constants;
import app.dao.Stock;
import app.utils.Logger;
import app.utils.ServiceUtil;
import app.utils.ServletUtil;
import app.utils.Utils;

public class BuyOptions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BuyOptions() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--BuyOptions--> Servlet");
		String option = request.getParameter("options");
		String result = "";
		Logger.info("Selected Buying DurationOption:" + option);
		HttpSession session = request.getSession(true);
		session.setAttribute("option", option);
		session.setAttribute("optionval", Utils.getStockPriceOptionVal(option));
		Logger.info("Selected Company is:" + session.getAttribute("company"));
		SingletonMemCache cache = SingletonMemCache.getMemCacheCon();
		String key = session.getAttribute("company").toString().replace(" ", "_") + "_" + option;
		if (cache.getFromCache(key) != null) {
			result = (String) cache.getFromCache(key);
			Logger.info("Result From Cache:" + result);
		} else {
			Logger.info("Not found in cache, fetching from Webservice");
			@SuppressWarnings("rawtypes")
			MultivaluedMap formData = new MultivaluedMapImpl();
			ServiceUtil.addAuthToken(formData);
			formData.add("option", option);
			ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.COMPANY_PRICE, formData);

			if (restResponse.getStatus() != 200) {
				Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
				Logger.info("Redirecting to error page");
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}
			result = restResponse.getEntity(String.class);
			Logger.info("Result:" + result);
			cache.addToCache(key, result);
		}

		List<Stock> stocks = ServletUtil.getStocksFromXML(result);
		session.setAttribute("stockslist", stocks);

		if (option.equalsIgnoreCase(Constants.CURRENT_DAY) || option.equalsIgnoreCase(Constants.CURRENT_WEEK)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("PriceCurrent.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("PriceHistory.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}
		Logger.info("End of <--BuyOptions--> Servlet");
	}

}
