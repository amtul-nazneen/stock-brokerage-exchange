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

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--Login--> Servlet");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Logger.info("Input Username: " + username);
		Logger.info("Input Password: " + password);

		@SuppressWarnings("rawtypes")
		MultivaluedMap formData = new MultivaluedMapImpl();
		ServiceUtil.addAuthToken(formData);
		formData.add("username", username);
		formData.add("password", password);
		Logger.info("Calling the Brokerage-Service/" + Constants.VERIFY_USER);
		ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.VERIFY_USER, formData);

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
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			Logger.info("XML from Brokerage-Service:" + Constants.VERIFY_USER + " " + result);
			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);
			session.setAttribute("logged", "true");
			Logger.info("Set the HTTPSession Attribute to user object" + result);
			Logger.info("Redirecting to the Profile.jsp page");
			/*
			 * try { Logger.info("QUEUE: QueueConsumer Thread beginning to run");
			 * QueueConsumer consumer = new QueueConsumer(Config.RABBITMQ_QUEUE); Thread
			 * consumerThread = new Thread(consumer); consumerThread.start(); } catch
			 * (Exception e) { e.printStackTrace(); }
			 */

			RequestDispatcher dispatcher = request.getRequestDispatcher("Profile.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}
		Logger.info("End of <--Login--> Servlet");
	}

}
