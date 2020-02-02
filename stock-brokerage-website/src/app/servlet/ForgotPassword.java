package app.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import app.config.Constants;
import app.utils.Logger;
import app.utils.ServiceUtil;
import app.utils.ServletUtil;

public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ForgotPassword() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--ForgotPassword--> Servlet");
		String email = request.getParameter("email");
		Logger.info("Input E-Mail: " + email);

		Logger.info("Calling the Brokerage-Service/" + Constants.FORGOT_PASSWORD);
		@SuppressWarnings("rawtypes")
		MultivaluedMap formData = new MultivaluedMapImpl();
		ServiceUtil.addAuthToken(formData);
		formData.add("email", email);

		ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.FORGOT_PASSWORD, formData);
		if (restResponse.getStatus() != 200) {
			Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
			Logger.info("Redirecting to Error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}

		String result = restResponse.getEntity(String.class);
		String status = ServletUtil.getStatusAfterPwdChange(result);
		if (status.equalsIgnoreCase(Constants.TRUE)) {
			Logger.info("Temporary OTP Generated from Brokerage-Service");
			Logger.info("Redirecting to the Recover Account page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("RecoverAccount.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			Logger.info("Error in generating Temporary OTP from Brokerage-Service");
			Logger.info("Redirecting to Error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}
		Logger.info("End of <--ForgotPassword--> Servlet");
	}

}
