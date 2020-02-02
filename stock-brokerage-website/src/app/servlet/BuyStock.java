package app.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.utils.Logger;

public class BuyStock extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BuyStock() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--BuyStock--> Servlet");

		String[] selectedStocks = request.getParameterValues("selectedstocks");
		HttpSession session = request.getSession(true);
		session.setAttribute("selectedStocks", selectedStocks);
		Logger.info("Total No. of Stocks to Buy:" + selectedStocks.length);
		Logger.info("Details of Individual Stocks to Buy");
		for (String obj : selectedStocks) {
			String tokens[] = obj.split("-");
			String price = tokens[0];
			String quantity = tokens[1];
			Logger.info("Stock Price:" + price);
			Logger.info("Stock Quantity:" + quantity);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("Buy.jsp");
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}
		Logger.info("End of <--BuyStock--> Servlet");
	}

}
