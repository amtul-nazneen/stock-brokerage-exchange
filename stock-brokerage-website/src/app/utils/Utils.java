package app.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Utils {
	public static String getStockPriceOptionVal(String option)
	{
		HashMap<String,String> options=new HashMap<String,String>();
		options.put("CurrentDay","Current Day");
		options.put("CurrentWeek", "Current Week");
		options.put("PastWeek","Past Week");
		options.put("MonthToDate","Month to Date");
		options.put("YearToDate","Year to Date");
		options.put("5Years", "5 Year");
		return options.get(option);
		
		
	}
	
	private static boolean validCurrentDay() {
		LocalDate today = LocalDate.now();
		DayOfWeek day=today.getDayOfWeek();
		if(day!=DayOfWeek.SATURDAY && day!=DayOfWeek.SUNDAY)
			return true;
		else
			return false;
	}
	
	private static boolean validCurrentTime()
	{
		LocalTime now = LocalTime.now();
		LocalTime start=LocalTime.of(8, 0, 0);
		LocalTime end=LocalTime.of(17, 0, 0);
		if(now.isAfter(start) &&now.isBefore(end))
			return true;
		else
			return false;
	}
	
	public static boolean isValidTimeAndDay()
	{
		Logger.info("Checking if Day and Time is Valid for transaction");
		boolean result=validCurrentDay()&&validCurrentTime();
		Logger.info("Is Day and Time is Valid for transaction:"+result);
		result=true;
		return result;
		
	}
}
