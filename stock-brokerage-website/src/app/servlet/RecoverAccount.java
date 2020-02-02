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

public class RecoverAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RecoverAccount() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--RecoverAccount--> Servlet");
		String username = request.getParameter("username");
		String code = request.getParameter("code");
		Logger.info("Input Username: " + username);
		Logger.info("Input Code: " + code);

		@SuppressWarnings("rawtypes")
		MultivaluedMap formData = new MultivaluedMapImpl();
		ServiceUtil.addAuthToken(formData);
		formData.add("username", username);
		formData.add("code", code);
		Logger.info("Calling the Brokerage-Service/" + Constants.RECOVER_ACCOUNT);
		ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.RECOVER_ACCOUNT, formData);

		if (restResponse.getStatus() != 200) {
			Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
			Logger.info("Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}

		String result = restResponse.getEntity(String.class);
		User user = ServletUtil.getUserFromXML(result);
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			Logger.info("Username:" + username + " doesn't exist in the database");
			Logger.info("Redirecting to the error page");
		} else {
			Logger.info("XML from Brokerage-Service:" + Constants.VERIFY_USER + " " + result);
			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);
			Logger.info("Set the HTTPSession Attribute to user object" + result);
			Logger.info("Redirecting to the Profile.jsp page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Profile.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}
		Logger.info("End of <--RecoverAccount--> Servlet");
	}

}
