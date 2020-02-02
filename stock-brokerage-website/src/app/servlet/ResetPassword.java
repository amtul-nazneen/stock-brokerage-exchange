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

public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ResetPassword() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--ResetPassword--> Servlet");
		String password = request.getParameter("password1");
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		String username = user.getUsername();
		Logger.info("Input Password: " + password);
		Logger.info("Session Username: " + username);

		@SuppressWarnings("rawtypes")
		MultivaluedMap formData = new MultivaluedMapImpl();
		ServiceUtil.addAuthToken(formData);
		formData.add("username", username);
		formData.add("password", password);
		Logger.info("Calling the Brokerage-Service/" + Constants.RESET_PASSWORD);
		ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.RESET_PASSWORD, formData);

		if (restResponse.getStatus() != 200) {
			Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
			Logger.info("Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}

		String result = restResponse.getEntity(String.class);
		String status = ServletUtil.getStatusAfterPwdChange(result);
		if (status.equalsIgnoreCase(Constants.TRUE)) {
			Logger.info("Password Successfully Updated");
			Logger.info("Redirecting to the Profile page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Profile.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			Logger.info("Error in Reset Password");
			Logger.info("Redirecting to the Error.jsp page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}
		Logger.info("End of <--ResetPassword--> Servlet");
	}

}
