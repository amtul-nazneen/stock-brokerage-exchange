package app.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.utils.Logger;

public class SearchStocks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchStocks() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--SearchStocks--> Servlet");
		String company = request.getParameter("company");
		Logger.info("Selected Company:" + company);
		HttpSession session = request.getSession(true);
		session.setAttribute("company", company);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BuyOptions.jsp");
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}
		Logger.info("End of <--SearchStocks--> Servlet");
	}

}
