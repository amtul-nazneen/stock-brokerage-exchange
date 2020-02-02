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

public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Registration() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--Registration--> Servlet");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		Logger.info("Input Username: " + username);
		Logger.info("Input Password: " + password);
		Logger.info("Input Email: " + email);
		Logger.info("Input Address: " + address);

		@SuppressWarnings("rawtypes")
		MultivaluedMap formData = new MultivaluedMapImpl();
		ServiceUtil.addAuthToken(formData);
		formData.add("username", username);
		formData.add("password", password);
		formData.add("email", email);
		formData.add("address", address);
		Logger.info("Calling the Brokerage-Service/" + Constants.SIGN_UP);

		ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.SIGN_UP, formData);
		if (restResponse.getStatus() != 200) {
			Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
			Logger.info("Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}

		String result = restResponse.getEntity(String.class);
		User user = ServletUtil.getRegisteredUserFromXML(result);
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
		Logger.info("End of <--Registration--> Servlet");
	}

}
