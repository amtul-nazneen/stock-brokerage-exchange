package app.db;

import java.util.List;

import app.config.Constants;
import app.dao.UserStocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryUtility {

	public static String convertStocksToXML(List<UserStocks> stocks) {
		String finalxml = "";
		String result = "";
		for (UserStocks stock : stocks) {
			String dop = stock.getDateOfPurchase();
			String price = stock.getPrice();
			String quantity = stock.getQuantity();
			String company = stock.getCompanyName();
			String stockid = stock.getStockid();
			String content = "";
			dop = getCurrentDate(dop);
			content += "<PRICE>" + price + "</PRICE>";
			content += "<DOP>" + dop + "</DOP>";
			content += "<QUANTITY>" + company + "</QUANTITY>";
			content += "<COMPANY>" + quantity + "</COMPANY>";
			content += "<STOCKID>" + stockid + "</STOCKID>";
			result += "<STOCK>" + content + "</STOCK>";
		}
		finalxml = "<STOCKS>" + result + "</STOCKS>";
		return finalxml;
	}

	private static String getCurrentDate(String datein) {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		String dateInString = datein;
		String result = "";

		try {

			Date date = formatter.parse(dateInString);
			result = formatter.format(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

}
