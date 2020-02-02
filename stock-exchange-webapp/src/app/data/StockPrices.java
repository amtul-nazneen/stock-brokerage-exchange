package app.data;

import app.config.Constants;
import app.utils.Logger;

public class StockPrices {

	public static String getStockPrices(String duration) {
		String result = "";
		Logger.info("Requested Duration is:"+ duration.toUpperCase());
		Logger.info("Fetching Stock Prices for " + duration.toUpperCase());
		try {
			if (duration != null) {
				if (duration.equalsIgnoreCase(Constants.CURRENT_DAY)) {
					result = StockPriceUtil.getPriceCurrentDay();
				} else if (duration.equalsIgnoreCase(Constants.CURRENT_WEEK)) {
					result = StockPriceUtil.getPriceCurrentWeek();
				} else if (duration.equalsIgnoreCase(Constants.PAST_WEEK)) {
					result = StockPriceUtil.getPricePastWeek();
				} else if (duration.equalsIgnoreCase(Constants.MONTH_TO_DATE)) {
					result = StockPriceUtil.getPriceMonthToDate();
				} else if (duration.equalsIgnoreCase(Constants.YEAR_TO_DATE)) {
					result = StockPriceUtil.getPriceYearToDate();
				} else if (duration.equalsIgnoreCase(Constants.FIVE_YEARS)) {
					result = StockPriceUtil.getPrice5Years();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
