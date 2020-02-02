package app.utils;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static List<String> getCurrentWeek() {
		List<String> currentWeek=new ArrayList<String>();
		LocalDate today = LocalDate.now();
		LocalDate monday = today;
		while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
			monday = monday.minusDays(1);
		}
		LocalDate day=monday;
		while(day.getDayOfWeek()!=DayOfWeek.SATURDAY)
		{
			currentWeek.add(day.toString());
			day=day.plusDays(1);
		}
		return currentWeek;
	}
	
	public static List<String> getPastWeek() {
		List<String> pastWeek=new ArrayList<String>();
		LocalDate today = LocalDate.now();
		LocalDate sunday = today;
		while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
			sunday = sunday.minusDays(1);
		}
		LocalDate lastMonday=sunday.minusDays(6);
		LocalDate day=lastMonday;
		while(day.getDayOfWeek()!=DayOfWeek.SATURDAY)
		{
			pastWeek.add(day.toString());
			day=day.plusDays(1);
		}
		return pastWeek;
	}
	
	public static List<String> getMonthToDateDays() {
		List<String> monthToDateDays=new ArrayList<String>();
		LocalDate today = LocalDate.now();
		LocalDate monthStartDay=today.withDayOfMonth(1);
		LocalDate day=monthStartDay;
		while (!day.equals(today.plusDays(1))) {
			if(day.getDayOfWeek()!=DayOfWeek.SATURDAY && day.getDayOfWeek()!=DayOfWeek.SUNDAY)
				monthToDateDays.add(day.toString());
			day = day.plusDays(1);
		}
		return monthToDateDays;
	}
	
	public static List<String> getYearToDateDays() {
		List<String> yearToDateDays=new ArrayList<String>();
		LocalDate today = LocalDate.now();
		LocalDate yearStartDay=today.withDayOfYear(1);
		LocalDate day=yearStartDay;
		while (!day.equals(today.plusDays(1))) {
			if(day.getDayOfWeek()!=DayOfWeek.SATURDAY && day.getDayOfWeek()!=DayOfWeek.SUNDAY)
				yearToDateDays.add(day.toString());
			day = day.plusDays(1);
		}
		return yearToDateDays;
	}
	public static List<String> get5YearsDays() {
		List<String> _5yearToDateDays=new ArrayList<String>();
		LocalDate today = LocalDate.now();
		LocalDate _5yearsdate=today.minusYears(5);
		LocalDate _5yearsStartDay=_5yearsdate.withDayOfYear(1);
		LocalDate day=_5yearsStartDay;
		while (!day.equals(today.plusDays(1))) {
			if(day.getDayOfWeek()!=DayOfWeek.SATURDAY && day.getDayOfWeek()!=DayOfWeek.SUNDAY)
				_5yearToDateDays.add(day.toString());
			day = day.plusDays(1);
		}
		return _5yearToDateDays;
	}

	public static String generateRandomPrice() {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		double randomValue = 10 + Math.random() * 70;
		double tempRes = randomValue * 65;
		String price = formatter.format(tempRes);
		return price;
	}

	public static String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String currdate = dtf.format(now);
		return currdate;
	}

}
