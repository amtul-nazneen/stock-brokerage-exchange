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
import app.dao.Company;
import app.utils.Logger;
import app.utils.ServiceUtil;
import app.utils.ServletUtil;

public class PopulateCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PopulateCompany() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--PopulateCompany--> Servlet");
		String result = "";

		SingletonMemCache cache = SingletonMemCache.getMemCacheCon();
		if (cache.getFromCache(Constants.CACHE_COMPANY) != null) {
			result = (String) cache.getFromCache(Constants.CACHE_COMPANY);
			Logger.info("Result From Cache:" + result);
		} else {
			Logger.info("Not present in Cache. Fetching from WebService");
			@SuppressWarnings("rawtypes")
			MultivaluedMap formData = new MultivaluedMapImpl();
			ServiceUtil.addAuthToken(formData);
			Logger.info("Calling the Brokerage-Service/" + Constants.ALL_COMPANIES);
			ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.ALL_COMPANIES, formData);
			if (restResponse.getStatus() != 200) {
				Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
				Logger.info("Redirecting to error page");
				RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}
			result = restResponse.getEntity(String.class);
			Logger.info("Result From WebService:" + result);
			Logger.info("Caching the result");
			cache.addToCache(Constants.CACHE_COMPANY, result);
		}
		Logger.info("Beginning to parse the Company Result");
		List<Company> companies = ServletUtil.getCompaniesFromXML(result);
		HttpSession session = request.getSession(true);
		session.setAttribute("companylist", companies);
		Logger.info("Redirecting to Search Stocks Page");
		RequestDispatcher dispatcher = request.getRequestDispatcher("SearchStocks.jsp");
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}
		Logger.info("End of <--PopulateCompany--> Servlet");
	}
}
