package app.data;
import java.util.List;

import app.config.Constants;
import app.utils.Utils;

public class StockPriceUtil {

	public static String getPriceCurrentDay() {
		String result = Constants.ROOT_START;
		result += generateStockTag(Utils.getCurrentDate()) + Constants.ROOT_END;
		return result;
	}

	public static String getPriceCurrentWeek() {
		List<String> currentWeek = Utils.getCurrentWeek();
		String result = Constants.ROOT_START;
		for (String day : currentWeek)
			result += generateStockTag(day);
		result += Constants.ROOT_END;
		return result;
	}

	public static String getPricePastWeek() {
		List<String> pastWeek = Utils.getPastWeek();
		String result = Constants.ROOT_START;
		for (String day : pastWeek)
			result += generateStockTag(day);
		result += Constants.ROOT_END;
		return result;
	}

	public static String getPriceMonthToDate() {
		List<String> monthToDateDays = Utils.getMonthToDateDays();
		String result = Constants.ROOT_START;
		for (String day : monthToDateDays)
			result += generateStockTag(day);
		result += Constants.ROOT_END;
		return result;

	}

	public static String getPriceYearToDate() {
		List<String> yearToDateDays = Utils.getYearToDateDays();
		String result = Constants.ROOT_START;
		for (String day : yearToDateDays)
			result += generateStockTag(day);
		result += Constants.ROOT_END;
		return result;

	}

	public static String getPrice5Years() {
		List<String> _5yearToDateDays = Utils.get5YearsDays();
		String result = Constants.ROOT_START;
		for (String day : _5yearToDateDays)
			result += generateStockTag(day);
		result += Constants.ROOT_END;
		return result;
	}

	private static String generateStockTag(String date) {
		String result = "<" + Constants.STOCK_TAG + " ";
		result += Constants.DATE_ATTR + "=" + "\"" + date + "\"" + " ";
		result += Constants.PRICE_ATTR + "=" + "\"" + Utils.generateRandomPrice() + "\"";
		result += "/>";
		return result;
	}
	private static String generateStockTagOld(String date) {
		String stock="<STOCK>";
		String datetag="<DATE>"+date+"</DATE>";
		String price="<PRICE>"+Utils.generateRandomPrice()+"</PRICE>";
		return stock + datetag+price+"</STOCK>";
	}
	
	

}
