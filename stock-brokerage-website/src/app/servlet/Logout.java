package app.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.utils.Logger;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--Logout--> Servlet");
		HttpSession session = request.getSession(false);
		session.removeAttribute("user");
		session.invalidate();
		Logger.info("Session was successfully destroyed");
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}
		Logger.info("Begin of <--Logout--> Servlet");
	}

}
