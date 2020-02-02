package app.test;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import app.db.SingletonDBConnect;

public class OracleAPI {
	public static boolean forgotPwd(String email) {
		// TODO- fetch userid from email
		boolean verified = false;
		try {
			SingletonDBConnect con = SingletonDBConnect.getDbCon();
			Random rand = new Random();

			int tempCode = rand.nextInt(1000000);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String localDate = dtf.format(LocalDate.now());
			System.out.println(dtf.format(LocalDate.now()));
			String userID = "";
			// SELECT USER_ID FROM WPL_USER WHERE EMAIL='hemanjenikundem@gmail.com';
			ResultSet rs = con.query("SELECT USER_ID FROM WPL_USER WHERE EMAIL=" + "'" + email + "'");
			while (rs.next()) {
				userID = rs.getString("USER_ID");
			}

			// TO_DATE('1989-12-09','YYYY-MM-DD')

			// INSERT INTO WPL_TEMP_OTP(TEMP_CODE,DATE_CREATED,USED_FLAG)
			// VALUES('ABCD',TO_DATE('2003/07/09', 'yyyy/mm/dd'),'F');
			String insertStmt = "INSERT INTO WPL_TEMP_OTP(USER_ID,TEMP_CODE,DATE_CREATED,USED_FLAG) " + "VALUES("
					+ Integer.parseInt(userID) + "," + "'" + tempCode + "'," + "TO_DATE('" + localDate
					+ "','YYYY-MM-DD')" + "," + "'F')";

			System.err.println(insertStmt);
			int success1 = con.insert("INSERT INTO WPL_TEMP_OTP(USER_ID,TEMP_CODE,DATE_CREATED,USED_FLAG) " + "VALUES("
					+ Integer.parseInt(userID) + "," + "'" + tempCode + "'," + "TO_DATE('" + localDate
					+ "','YYYY-MM-DD')" + "," + "'F')");

			if (success1 == 1)
				verified = true;
			else
				return false;

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
		}
		return verified;
	}
	
	public static Boolean buyStocks(String inputUserID, String companyName, String stockscount, String recurringplan,
			String amtPaid) {
		// INSERT INTO WPL_RECURRING_BUY
		// VALUES(1,1,1,TO_DATE('2019-01-01','YYYY-MM-DD'),TO_DATE('2019-02-01','YYYY-MM-DD'),'MONTHLY','T');
		try {
			SingletonDBConnect con5 = SingletonDBConnect.getDbCon();
			// Integer userid = Integer.parseInt(inputUser);
			// select USER_ID,USERNAME,EMAIL,CONTACT_NO,ADDRESS,BALANCE from WPL_USER where
			// USER_ID=1;
			ResultSet rs5 = con5.query("SELECT USER_ID from WPL_USER where USERNAME=" + "'" + inputUserID + "'");
			rs5.next();
			Integer userid = rs5.getInt("USER_ID");
			SingletonDBConnect con6 = SingletonDBConnect.getDbCon();
			// Integer userid = Integer.parseInt(inputUser);
			// select USER_ID,USERNAME,EMAIL,CONTACT_NO,ADDRESS,BALANCE from WPL_USER where
			// USER_ID=1;
			ResultSet rs6 = con6
					.query("SELECT COMPANY_ID from WPL_COMPANY where COMPANY_NAME=" + "'" + companyName + "'");
			rs6.next();
			Integer companyId = rs6.getInt("COMPANY_ID");
			SingletonDBConnect con = SingletonDBConnect.getDbCon();
			Integer stocksCount = Integer.parseInt(stockscount);
			Double amtpaid = Double.parseDouble(amtPaid);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String localDate = dtf.format(LocalDate.now());
			LocalDate returnvalue = null;
			String planflag = "";
			if (recurringplan.equals("weekly")) {
				LocalDate date = LocalDate.parse(localDate);
				returnvalue = date.plusWeeks(1);
				System.err.println(returnvalue.toString());
				planflag = "T";
			}
			if (recurringplan.equals("monthly")) {
				LocalDate date = LocalDate.parse(localDate);
				returnvalue = date.plusMonths(1);
				System.err.println(returnvalue.toString());
				planflag = "T";
			} else if (recurringplan.equals("N/A")) {
				LocalDate date = LocalDate.parse(localDate);
				returnvalue = date.plusYears(1);
				System.err.println(returnvalue.toString());
				planflag = "F";
			}
			if (!recurringplan.equals("N/A")) {
				int rs = con.insert("INSERT INTO WPL_RECURRING_BUY VALUES(" + userid + "," + companyId + ","
						+ stocksCount + "," + "TO_DATE('" + localDate + "','YYYY-MM-DD'),TO_DATE('"
						+ returnvalue.toString() + "','YYYY-MM-DD')," + "'" + recurringplan + "','" + planflag + "')");
				if (rs == 0) {
					return false;
				}
			}
			// update balance of user
///UPDATE WPL_USER set BALANCE = (BALANCE-1) WHERE USER_ID='1';
			SingletonDBConnect con2 = SingletonDBConnect.getDbCon();
			String rsstmt2 = "UPDATE WPL_USER set BALANCE = (BALANCE-" + amtpaid + ") WHERE USER_ID=" + userid;
			int rs2 = con2.update("UPDATE WPL_USER set BALANCE = (BALANCE-" + amtpaid + ") WHERE USER_ID=" + userid);
			if (rs2 == 0) {
				return false;
			}
			// update of WPL_STOCKS_PURCHASED TABLE
			// INSERT INTO WPL_STOCKS_PURCHASED
			// VALUES(1,TO_DATE('2019-11-13','YYYY-MM-DD'),1,1,50);
			SingletonDBConnect con3 = SingletonDBConnect.getDbCon();
			String rsstmt3 = "INSERT INTO WPL_STOCKS_PURCHASED VALUES(" + userid + ",TO_DATE('" + localDate
					+ "','YYYY-MM-DD')," + companyId + "," + stockscount + "," + amtpaid;
			int rs3 = con3.insert("INSERT INTO WPL_STOCKS_PURCHASED VALUES(" + userid + ",TO_DATE('" + localDate
					+ "','YYYY-MM-DD')," + companyId + "," + stockscount + "," + amtpaid + ")");
			if (rs3 == 0) {
				return false;
			}
			// update WPL_TRANSACTION table
			// INSERT INTO WPL_TRANSACTION_TABLE VALUES(1,'BUY',1,1,'OK');
			SingletonDBConnect con4 = SingletonDBConnect.getDbCon();
			String rsstmt4 = "INSERT INTO WPL_TRANSACTION_TABLE VALUES(" + userid + ",'BUY'," + companyId + ","
					+ stocksCount + "," + "'OK')";
			int rs4 = con4.insert("INSERT INTO WPL_TRANSACTION_TABLE VALUES(" + userid + ",'BUY'," + companyId + ","
					+ stocksCount + "," + "'OK')");
			if (rs4 == 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
		}
		return true;
	}
}
