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
import app.dao.User;
import app.utils.Logger;
import app.utils.ServiceUtil;
import app.utils.ServletUtil;

public class DeleteSellSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteSellSchedule() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--DeleteSellSchedule--> Servlet");
		String[] delete = request.getParameterValues("deletesellschedules");
		Logger.info("Total No. of Scheduled Stocks to Delete:" + delete.length);
		String stocks = "";
		for (String obj : delete) {
			stocks = stocks + obj.trim() + ",";
		}
		stocks = stocks.substring(0, stocks.length() - 1);
		Logger.info("IDs of Scheduled Stocks to Delete:" + stocks);
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		Logger.info("Passing these Stock-Brokerage-Webapp for User:" + user.getUsername());
		@SuppressWarnings("rawtypes")
		MultivaluedMap formData = new MultivaluedMapImpl();
		ServiceUtil.addAuthToken(formData);
		formData.add("stock_ids", stocks);

		Logger.info("Calling the Brokerage-Service/" + Constants.DELETE_SELL_SCHEDULE);
		ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.DELETE_SELL_SCHEDULE, formData);
		if (restResponse.getStatus() != 200) {
			Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
			Logger.info("Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			String result = restResponse.getEntity(String.class);
			String status = ServletUtil.getStatusAfterDeleteSchedule(result);
			Logger.info("Scheduled Delete Stock Status:" + status);
			if (Constants.TRUE.equalsIgnoreCase(status)) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("DeleteSchedConfirm.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			} else {
				Logger.info("There's some issue in deleting the Scheduled stocks");
				RequestDispatcher dispatcher = request.getRequestDispatcher("GenericError.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}
		}
		Logger.info("End of <--DeleteSellSchedule--> Servlet");
	}
}
