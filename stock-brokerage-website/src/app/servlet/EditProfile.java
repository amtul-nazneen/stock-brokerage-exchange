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

public class EditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditProfile() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--EditProfile--> Servlet");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		Logger.info("Provided Address:" + address);
		Logger.info("Provided Email:" + email);

		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		Logger.info("Passing these both to Stock-Brokerage-Webapp for User:" + user.getUsername());
		@SuppressWarnings("rawtypes")
		MultivaluedMap formData = new MultivaluedMapImpl();
		ServiceUtil.addAuthToken(formData);
		formData.add("address", address);
		formData.add("email", email);
		formData.add("username", user.getUsername());
		Logger.info("Calling the Brokerage-Service/" + Constants.EDIT_PROFILE);
		ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.EDIT_PROFILE, formData);
		if (restResponse.getStatus() != 200) {
			Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
			Logger.info("Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			String result = restResponse.getEntity(String.class);
			User profile = ServletUtil.getUserFromXML(result);
			session.setAttribute("user", profile);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Profile.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}

		}
		Logger.info("End of <--EditProfile--> Servlet");
	}

}
