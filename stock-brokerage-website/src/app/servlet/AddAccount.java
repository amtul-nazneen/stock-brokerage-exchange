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

public class AddAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddAccount() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--AddAccount--> Servlet");
		String accountNo = request.getParameter("account");
		String routingNo = request.getParameter("routing");
		Logger.info("Provided Account Number:" + accountNo);
		Logger.info("Provided Routing Number:" + routingNo);

		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		Logger.info("Passing these both to Stock-Brokerage-Webapp for User:" + user.getUsername());
		@SuppressWarnings("rawtypes")
		MultivaluedMap formData = new MultivaluedMapImpl();
		ServiceUtil.addAuthToken(formData);
		formData.add("username", user.getUsername());
		formData.add("account", accountNo);
		formData.add("routing", routingNo);
		Logger.info("Calling the Brokerage-Service/" + Constants.ADD_USER_ACCOUNT);
		ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.ADD_USER_ACCOUNT, formData);
		if (restResponse.getStatus() != 200) {
			Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
			Logger.info("Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			String result = restResponse.getEntity(String.class);
			String status = ServletUtil.getStatusAfterAddingAccount(result);
			Logger.info("Account Add Status:" + status);
			if (Constants.TRUE.equalsIgnoreCase(status)) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("AccountConfirm.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			} else {
				Logger.info("There's some issue in adding the Account");
				RequestDispatcher dispatcher = request.getRequestDispatcher("GenericError.jsp");
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}
		}
		Logger.info("End of <--AddAccount--> Servlet");
	}

}
