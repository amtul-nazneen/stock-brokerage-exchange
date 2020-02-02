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

import app.config.Constants;
import app.dao.User;
import app.dao.UserSchedule;
import app.utils.Logger;
import app.utils.ServiceUtil;
import app.utils.ServletUtil;

public class MySchedules extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MySchedules() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		Logger.info("Begin of <--MySchedules--> Servlet");
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		Logger.info("Passing to Stock-Brokerage-Webapp for User:" + user.getUsername());
		@SuppressWarnings("rawtypes")
		MultivaluedMap formData = new MultivaluedMapImpl();
		ServiceUtil.addAuthToken(formData);
		formData.add("username", user.getUsername());
		Logger.info("Calling the Brokerage-Service/" + Constants.USER_SCHEDULE);
		ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.USER_SCHEDULE, formData);
		if (restResponse.getStatus() != 200) {
			Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
			Logger.info("Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			String result = restResponse.getEntity(String.class);
			List<UserSchedule> schedules = ServletUtil.getUserSchedules(result);
			Logger.info("Received schedules:" + schedules);
			session.setAttribute("schedules", schedules);
			Logger.info("Redirecting to MySchedules Page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("MySchedules.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
			Logger.info("End of <--MySchedules--> Servlet");
		}
	}

}
