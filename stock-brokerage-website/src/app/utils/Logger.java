package app.utils;

import java.sql.Timestamp;
import java.util.Date;

public class Logger {
	private static String getTimestampForLog() {
		return "[" + (new Timestamp(new Date().getTime())) + "]";
	}

	public static void info(String message) {
		String time = getTimestampForLog();
		if (time.length() == 25)
			System.out.println(time + " " + message);
		else if (time.length() == 24)
			System.out.println(time + "  " + message);
		else
			System.out.println(time + "   " + message);
	}
	
	public static void error(Exception e)
	{
		info("ERROR"+e.getMessage());
		e.printStackTrace();
	}
}
