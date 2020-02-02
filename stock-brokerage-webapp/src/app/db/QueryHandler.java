package app.db;

import java.io.StringWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXB;

import com.sun.xml.internal.ws.util.xml.XmlUtil;

import app.dao.RecurringBuy;
import app.dao.User;
import app.dao.UserStocks;
import app.utils.EmailWorker;
import app.utils.Logger;

public class QueryHandler {

	public static String getAllUserStocks(String username) {
		List<UserStocks> userstocks = new ArrayList<UserStocks>();
		String query = "select stocks.stock_id as stockid, stocks.date_of_purchase as dop,stocks.quantity as quantity,stocks.price as price,company.company_name as company\n"
				+ "from wpl_user user1,wpl_stocks_purchased stocks,wpl_company company\n"
				+ "where stocks.user_id=user1.user_id and company.company_id=stocks.company_id "
				+ "and stocks.purchase_status='PURCHASED' and user1.username='" + username + "'";
		Logger.info(query);
		try {
			SingletonDBConnect con = SingletonDBConnect.getDbCon();
			ResultSet rs = con.query(query);
			while (rs.next()) {

				String company = rs.getString("company");
				String price = rs.getString("price");
				String quantity = rs.getString("quantity");
				String dop = rs.getString("dop");
				String stockid = rs.getString("stockid");
				UserStocks stock = new UserStocks(stockid, company, quantity, price, dop);
				userstocks.add(stock);
			}

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
		}
		String xmlString = QueryUtility.convertStocksToXML(userstocks);
		return xmlString;
	}

	public static String getAllCompanies() {
		List<String> companies = new ArrayList<String>();
		try {
			SingletonDBConnect con = SingletonDBConnect.getDbCon();
			ResultSet rs = con.query("SELECT company_name as companyName from wpl_company");
			while (rs.next()) {
				String companyName = rs.getString("companyName");
				companies.add(companyName);
			}

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
		}
		String result = "<COMPANIES>";
		for (String company : companies)
			result = result + "<COMPANY name=" + "\"" + company + "\"" + "/>";
		result += "</COMPANIES>";
		return result;
	}

	public static boolean verifyLoginUser(String inputUser, String inputPwd) {
		boolean verified = false;
		try {
			SingletonDBConnect con = SingletonDBConnect.getDbCon();
			Logger.info("got db connection");
			ResultSet rs = con.query("SELECT username,pwd FROM wpl_user where username=" + "'" + inputUser + "'");
			while (rs.next()) {
				String pwd = rs.getString("pwd");
				Logger.info("password is:" + pwd);
				if (pwd.equalsIgnoreCase(inputPwd)) {
					verified = true;
					break;
				}
			}

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
		}
		return verified;
	}

	public static boolean recoverAccount(String inputUser, String inputPwd) {
		boolean verified = false;
		try {
			SingletonDBConnect con = SingletonDBConnect.getDbCon();
			int success = con
					.update("UPDATE WPL_USER SET PWD='" + inputPwd + "' WHERE USERNAME=" + "'" + inputUser + "'");

			if (success > 0) {
				verified = true;

			}

			return verified;
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
		}
		return verified;
	}

	public static boolean signUpUser(String userName, String userPwd, String userEmail, String userAddress) {
		boolean verified = false;
		try {
			SingletonDBConnect con = SingletonDBConnect.getDbCon();

			String insertStmt = "INSERT INTO WPL_USER  (USERNAME, PWD, EMAIL, ADDRESS) " + "VALUES(" + "'" + userName
					+ "'," + "'" + userPwd + "'," + "'" + userEmail + "'," + "'" + userAddress + "')";

			System.err.println(insertStmt);
			int success = con.insert("INSERT INTO WPL_USER  (USERNAME, PWD, EMAIL, ADDRESS) " + "VALUES(" + "'"
					+ userName + "'," + "'" + userPwd + "'," + "'" + userEmail + "'," + "'" + userAddress + "')");

			if (success == 1)
				verified = true;
			else
				return false;

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
		}
		return verified;
	}

	public static boolean forgotPwd(String email) {
		boolean verified = false;
		try {
			SingletonDBConnect con = SingletonDBConnect.getDbCon();
			Random rand = new Random();

			int tempCode = rand.nextInt(1000000);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String localDate = dtf.format(LocalDate.now());
			Logger.info(dtf.format(LocalDate.now()));
			String userID = "";
			ResultSet rs = con.query("SELECT USER_ID FROM WPL_USER WHERE EMAIL=" + "'" + email + "'");
			while (rs.next()) {
				userID = rs.getString("USER_ID");
			}

			String insertStmt = "INSERT INTO WPL_TEMP_OTP(USER_ID,TEMP_CODE,DATE_CREATED,USED_FLAG) " + "VALUES("
					+ Integer.parseInt(userID) + "," + "'" + tempCode + "'," + "STR_TO_DATE('" + localDate
					+ "','%Y-%m-%d')" + "," + "'F')";

			System.err.println(insertStmt);
			int success1 = con.insert("INSERT INTO WPL_TEMP_OTP(USER_ID,TEMP_CODE,DATE_CREATED,USED_FLAG) " + "VALUES("
					+ Integer.parseInt(userID) + "," + "'" + tempCode + "'," + "STR_TO_DATE('" + localDate
					+ "','%Y-%m-%d')" + "," + "'F')");
			EmailWorker.sendEmail(tempCode);
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

	public static String getUserProfile(String inputUser) {
		try {
			SingletonDBConnect con = SingletonDBConnect.getDbCon();
			ResultSet rs = con
					.query("SELECT USER_ID,USERNAME,EMAIL,CONTACT_NO,ADDRESS,BALANCE from WPL_USER where USERNAME="
							+ "'" + inputUser + "'");
			while (rs.next()) {
				Integer USER_ID;
				String USERNAME;
				String EMAIL;
				String CONTACT_NO;
				String ADDRESS;
				Double BALANCE;
				USER_ID = rs.getInt("USER_ID");
				USERNAME = rs.getString("USERNAME");
				EMAIL = rs.getString("EMAIL");
				CONTACT_NO = rs.getString("CONTACT_NO");
				ADDRESS = rs.getString("ADDRESS");
				BALANCE = rs.getDouble("BALANCE");
				User user = new User();
				user.setUSER_ID(USER_ID);
				user.setUSERNAME(USERNAME);
				user.setEMAIL(EMAIL);
				user.setBALANCE(BALANCE);
				user.setCONTACT_NO(CONTACT_NO);
				user.setADDRESS(ADDRESS);
				XmlUtil xmlUtil = new XmlUtil();
				StringWriter sw = new StringWriter();
				JAXB.marshal(user, sw);
				String xmlString = sw.toString();
				String finalstring = xmlString.replace("<user>", "<USER>");
				String finalstring2 = finalstring.replace("</user>", "</USER>");
				System.err.println(finalstring2);

				return finalstring2;

			}

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
		}
		return null;

	}

	public static Boolean resetUserPwd(String inputUsername, String newPass) {// TODO
		try {
			SingletonDBConnect con = SingletonDBConnect.getDbCon();
			int rs = con
					.update("UPDATE  WPL_USER SET PWD='" + newPass + "' WHERE USERNAME=" + "'" + inputUsername + "'");
			if (rs != 0) {
				return true;

			}

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
		}
		return false;

	}

	public static Boolean buyStocks(String inputUserID, String companyName, String stockscount, String recurringplan,
			String amtPaid) {
		try {
			SingletonDBConnect con5 = SingletonDBConnect.getDbCon();
			ResultSet rs5 = con5.query("SELECT USER_ID from WPL_USER where USERNAME=" + "'" + inputUserID + "'");
			rs5.next();
			Integer userid = rs5.getInt("USER_ID");
			SingletonDBConnect con6 = SingletonDBConnect.getDbCon();
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
			} else if (recurringplan.equals("monthly")) {
				LocalDate date = LocalDate.parse(localDate);
				returnvalue = date.plusMonths(1);
				System.err.println(returnvalue.toString());
				planflag = "T";
			} else {
				LocalDate date = LocalDate.parse(localDate);
				returnvalue = date.plusYears(1);
				System.err.println(returnvalue.toString());
				planflag = "F";
			}
			if (!recurringplan.equals("N/A")) {
				String insertstr = "INSERT INTO WPL_RECURRING_BUY (USER_ID,COMPANY_ID,QUANTITY,PRICE,SCHEDULED_DATE,RECURSION_PLAN,PURCHASE_STATUS) VALUES(";
				insertstr = insertstr + userid + "," + companyId + "," + stocksCount + "," + amtpaid + ","
						+ "STR_TO_DATE('" + localDate + "','%Y-%m-%d')" + "," + "'" + recurringplan.toUpperCase() + "',"
						+ "'PURCHASED'" + ")";
				Logger.info(insertstr);
				int rs = con.insert(insertstr);
				if (rs == 0) {
					return false;
				}
				String insert2str = "INSERT INTO WPL_RECURRING_BUY (USER_ID,COMPANY_ID,QUANTITY,PRICE,SCHEDULED_DATE,RECURSION_PLAN,PURCHASE_STATUS) VALUES(";

				insert2str = insert2str + userid + "," + companyId + "," + stocksCount + "," + amtpaid + ","
						+ "STR_TO_DATE('" + returnvalue + "','%Y-%m-%d')" + "," + "'" + recurringplan.toUpperCase()
						+ "'," + "'SCHEDULED'" + ")";
				rs = con.insert(insert2str);

			}
			SingletonDBConnect con2 = SingletonDBConnect.getDbCon();
			String rsstmt2 = "UPDATE WPL_USER set BALANCE = (BALANCE-" + amtpaid + ") WHERE USER_ID=" + userid;
			int rs2 = con2.update("UPDATE WPL_USER set BALANCE = (BALANCE-" + amtpaid + ") WHERE USER_ID=" + userid);
			if (rs2 == 0) {
				return false;
			}
			if (recurringplan.equals("N/A")) {
				SingletonDBConnect con3 = SingletonDBConnect.getDbCon();
				String rsstmt3 = "INSERT INTO WPL_STOCKS_PURCHASED (USER_ID,DATE_OF_PURCHASE,COMPANY_ID,QUANTITY,PRICE) VALUES("
						+ userid + ",STR_TO_DATE('" + localDate + "','%Y-%m-%d')," + companyId + "," + stockscount + ","
						+ amtpaid;
				int rs3 = con3.insert(
						"INSERT INTO WPL_STOCKS_PURCHASED (USER_ID,DATE_OF_PURCHASE,COMPANY_ID,QUANTITY,PRICE) VALUES("
								+ userid + ",STR_TO_DATE('" + localDate + "','%Y-%m-%d')," + companyId + ","
								+ stockscount + "," + amtpaid + ")");
				if (rs3 == 0) {
					return false;
				}
			}
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

	public static String getUserScheduleOld(String inputUserID) {// TODO Change this

		try {
			SingletonDBConnect con = SingletonDBConnect.getDbCon();
			Integer userid = Integer.parseInt(inputUserID);
			// SELECT * FROM WPL_RECURRING_BUY WHERE USER_ID=1;
			ResultSet rs = con.query("SELECT * FROM WPL_RECURRING_BUY WHERE USER_ID=" + userid);
			XmlUtil xmlUtil = new XmlUtil();
			RecurringBuy rb = new RecurringBuy();
			StringWriter sw = new StringWriter();
			String xmlString = "";
			while (rs.next()) {
				// USER_ID
				// COMPANY_ID
				// QUANTITY
				// SCHEDULED_DATE
				// NEXT_DATE
				// RECURSION_PLAN
				// ACTIVE_FLAG

				Integer USER_ID;
				Integer Company_ID;
				String EMAIL;
				String Recusion_Plan;
				String Active_Flag;
				Integer Quantity;
				Date scheduled_date;
				Date next_date;
				USER_ID = rs.getInt("USER_ID");
				Company_ID = rs.getInt("COMPANY_ID");
				Quantity = rs.getInt("QUANTITY");
				scheduled_date = rs.getDate("SCHEDULED_DATE");
				next_date = rs.getDate("NEXT_DATE");
				Recusion_Plan = rs.getString("RECURSION_PLAN");
				Active_Flag = rs.getString("ACTIVE_FLAG");

				rb.setUSER_ID(USER_ID);
				rb.setACTIVE_FLAG(Active_Flag);
				rb.setCOMPANY_ID(Company_ID);
				rb.setNEXT_DATE(next_date);
				rb.setSCHEDULED_DATE(scheduled_date);
				rb.setRECURSION_PLAN(Recusion_Plan);
				rb.setQUANTITY(Quantity);

				JAXB.marshal(rb, sw);
				String xml = sw.toString();
				System.err.println(xml);
				xmlString = new String(xml);

			}
			String replace = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
			String replaceString = xmlString.replace(replace, "");
			String finalResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + "<UserSchedule>"
					+ replaceString + "</UserSchedule>";
			return finalResult;

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
		}

		return "N/A";
	}

	public static String getAllUserAccounts(String username) {
		String result = "";
		// String
		// result="<BANK_ACCOUNTS><ACCOUNT><ACCOUNT_NO>012098323</ACCOUNT_NO><ROUTING_NO>11241434</ROUTING_NO><BALANCE>100.90</BALANCE></ACCOUNT><ACCOUNT><ACCOUNT_NO>2543645664</ACCOUNT_NO><ROUTING_NO>14342323</ROUTING_NO><BALANCE>4059.90</BALANCE></ACCOUNT></BANK_ACCOUNTS>";
		// ============================
		SingletonDBConnect con = SingletonDBConnect.getDbCon();
		// select * from wpl_bank_account where user_id=1;
		// select user_id from wpl_user where username='hema';
		StringBuilder res = new StringBuilder();

		int userID = 0;

		try {
			ResultSet rsuser = con.query("SELECT USER_ID FROM WPL_USER WHERE username=" + "'" + username + "'");
			while (rsuser.next()) {
				userID = rsuser.getInt("USER_ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int userid = userID;
		String querystmt = "select * from wpl_bank_account where user_id=" + userid;

		System.err.println(querystmt);
		ResultSet rs;
		try {
			rs = con.query("select * from wpl_bank_account where user_id=" + userid);
			res.append("<BANK_ACCOUNTS>");
			while (rs.next()) {
				res.append("<ACCOUNT>");
				String acc_no = rs.getString("BANK_ACC_NO");
				res.append("<ACCOUNT_NO>" + acc_no + "</ACCOUNT_NO>");
				String routing = rs.getString("ROUTING_INFO");
				res.append("<ROUTING_INFO>" + routing + "</ROUTING_INFO>");
				int balance = rs.getInt("CURR_BALANCE");
				res.append("<BALANCE>" + Integer.toString(balance) + "</BALANCE>");
				res.append("</ACCOUNT>");
			}
			res.append("</BANK_ACCOUNTS>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = res.toString();

		return result;// TODO
	}

	public static String addAccount(String username, String account, String routing) {

		// String result = "<ACCOUNT><STATUS>TRUE</STATUS></ACCOUNT>";
		String result = "";
		// ===============
		SingletonDBConnect con = SingletonDBConnect.getDbCon();
		// select * from wpl_bank_account where user_id=1;
		// select user_id from wpl_user where username='hema';
		StringBuilder res = new StringBuilder();
		res.append("<ACCOUNT><STATUS>");
		int userID = 0;

		try {
			ResultSet rsuser = con.query("SELECT USER_ID FROM WPL_USER WHERE username=" + "'" + username + "'");
			while (rsuser.next()) {
				userID = rsuser.getInt("USER_ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int userid = userID;
		int startingbalance = 900000;

		// insert into wpl_bank_account values(1,'4567','4567',878687);
		String insertstmt = "insert into wpl_bank_account values(" + userid + ",'" + account + "','" + routing + "',"
				+ startingbalance + ")";

		System.err.println(insertstmt);

		int rs;
		try {
			rs = con.insert("insert into wpl_bank_account values(" + userid + ",'" + account + "','" + routing + "',"
					+ startingbalance + ")");
			if (rs != 0) {
				res.append("TRUE</STATUS></ACCOUNT>");
			} else {
				res.append("FALSE</STATUS></ACCOUNT>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = res.toString();
		// ===========

		return result;// TODO
	}

	public static String transferAmount(String payeraccount, String payeeaccount, String amount) {

		// String result = "<TRANSFER><STATUS>TRUE</STATUS></TRANSFER>";
		String result = "";
		SingletonDBConnect con = SingletonDBConnect.getDbCon();
		StringBuilder res = new StringBuilder();
		res.append("<TRANSFER><STATUS>");

//update wpl_bank_account set curr_balance=curr_balance-10000 where user_id=1;
		// update wpl_bank_account set CURR_BALANCE=CURR_BALANCE-100 where
		// BANK_ACC_NO=1;
		int amt = Integer.parseInt(amount);

		int rs;
		int rs2;

		try {
			rs = con.update("update wpl_bank_account set CURR_BALANCE=CURR_BALANCE-" + amount + " where BANK_ACC_NO= "
					+ payeraccount);
			rs2 = con.update("update wpl_bank_account set CURR_BALANCE=CURR_BALANCE+" + amount + " where BANK_ACC_NO= "
					+ payeeaccount);
			if (rs != 0 && rs2 != 0) {
				res.append("TRUE</STATUS></TRANSFER>");
			} else {
				res.append("FALSE</STATUS></TRANSFER>");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = res.toString();

		return result;// TODO
	}

	public static String sellStocksOneTime(String stockIds) {
		String result = "";
		SingletonDBConnect con = SingletonDBConnect.getDbCon();
		StringBuilder res = new StringBuilder();
		res.append("<SELL><STATUS>");
		String stocks[] = stockIds.split(",");
		int stockslen = stocks.length;
		int flag = 0;
		for (int i = 0; i < stockslen; i++) {
			String stockiter = stocks[i];
			try {
				ResultSet rs = con
						.query("select * from wpl_stocks_purchased where STOCK_ID=" + Integer.parseInt(stockiter));
				rs.next();
				int stockid = rs.getInt("STOCK_ID");
				int userid = rs.getInt("USER_ID");
				Date dop = rs.getDate("DATE_OF_PURCHASE");
				int companyID = rs.getInt("COMPANY_ID");
				int qty = rs.getInt("QUANTITY");
				Double price = rs.getDouble("PRICE");
				String insertstatement = "insert into wpl_stocks_sold values(" + userid + "," + stockid + ","
						+ "STR_TO_DATE(" + "'" + dop + "'" + ",'%Y-%m-%d')" + "," + companyID + "," + qty + "," + price
						+ ")";
				Logger.info(insertstatement);
				int rs2 = con.insert(insertstatement);
				if (rs2 == 0) {
					flag = 1;
				}
				String updateStocksPurchased = "UPDATE WPL_STOCKS_PURCHASED SET PURCHASE_STATUS='" + "SOLD" + "'"
						+ " WHERE STOCK_ID=" + stockiter;
				Logger.info(updateStocksPurchased);
				con.update(updateStocksPurchased);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (flag == 1) {
			res.append("FALSE</STATUS></SELL>");
		}

		else {
			res.append("TRUE</STATUS></SELL>");
		}
		result = res.toString();
		return result;
	}

	public static void sellStocksRecurring(String stockIds, String recurringplan) {
		SingletonDBConnect con = SingletonDBConnect.getDbCon();
		String stocks[] = stockIds.split(",");
		int stockslen = stocks.length;
		for (int i = 0; i < stockslen; i++) {
			String stockiter = stocks[i];
			try {
				ResultSet rs = con
						.query("select * from wpl_stocks_purchased where STOCK_ID=" + Integer.parseInt(stockiter));
				rs.next();
				int userid = rs.getInt("USER_ID");
				int companyID = rs.getInt("COMPANY_ID");
				int qty = rs.getInt("QUANTITY");
				Double price = rs.getDouble("PRICE");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String localDate = dtf.format(LocalDate.now());

				LocalDate returnvalue = null;
				if (recurringplan.equals("weekly")) {
					LocalDate date = LocalDate.parse(localDate);
					returnvalue = date.plusWeeks(1);
					System.err.println(returnvalue.toString());
				} else if (recurringplan.equals("monthly")) {
					LocalDate date = LocalDate.parse(localDate);
					returnvalue = date.plusMonths(1);
					System.err.println(returnvalue.toString());
				} else {
					LocalDate date = LocalDate.parse(localDate);
					returnvalue = date.plusYears(1);
					System.err.println(returnvalue.toString());
				}
				String insertstr = "INSERT INTO WPL_RECURRING_SELL (USER_ID,COMPANY_ID,QUANTITY,PRICE,SCHEDULED_DATE,RECURSION_PLAN,SOLD_STATUS) VALUES(";
				insertstr = insertstr + userid + "," + companyID + "," + qty + "," + price + "," + "STR_TO_DATE('"
						+ localDate + "','%Y-%m-%d')" + "," + "'" + recurringplan.toUpperCase() + "'," + "'SOLD'" + ")";
				Logger.info(insertstr);
				int rs2 = con.insert(insertstr);

				String insertstr2 = "INSERT INTO WPL_RECURRING_SELL (USER_ID,COMPANY_ID,QUANTITY,PRICE,SCHEDULED_DATE,RECURSION_PLAN,SOLD_STATUS) VALUES(";
				insertstr2 = insertstr2 + userid + "," + companyID + "," + qty + "," + price + "," + "STR_TO_DATE('"
						+ returnvalue + "','%Y-%m-%d')" + "," + "'" + recurringplan.toUpperCase() + "'," + "'SCHEDULED'"
						+ ")";
				Logger.info(insertstr2);
				int rs22 = con.insert(insertstr2);

				String updateStocksPurchased = "UPDATE WPL_STOCKS_PURCHASED SET PURCHASE_STATUS='" + "SOLD" + "'"
						+ " WHERE STOCK_ID=" + stockiter;
				Logger.info(updateStocksPurchased);
				con.update(updateStocksPurchased);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static String deleteSchedules(String stockIds) {
		String result = "";
		SingletonDBConnect con = SingletonDBConnect.getDbCon();
		StringBuilder res = new StringBuilder();
		res.append("<DELETE_SCHEDULE><STATUS>");
		String stocks[] = stockIds.split(",");
		int stockslen = stocks.length;
		int flag = 0;
		for (int i = 0; i < stockslen; i++) {
			String stockstr = stocks[i];
			try {
				int rsuser = con.update("DELETE FROM WPL_RECURRING_BUY WHERE STOCK_ID=" + Integer.parseInt(stockstr));
				if (rsuser == 0) {
					flag = 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (flag == 1) {
			res.append("FALSE</STATUS></DELETE_SCHEDULE>");
		} else
			res.append("TRUE</STATUS></DELETE_SCHEDULE>");

		result = res.toString();
		return result;
	}
	public static String deleteSellSchedules(String stockIds) {
		String result = "";
		SingletonDBConnect con = SingletonDBConnect.getDbCon();
		StringBuilder res = new StringBuilder();
		res.append("<DELETE_SCHEDULE><STATUS>");
		String stocks[] = stockIds.split(",");
		int stockslen = stocks.length;
		int flag = 0;
		for (int i = 0; i < stockslen; i++) {
			String stockstr = stocks[i];
			try {
				int rsuser = con.update("DELETE FROM WPL_RECURRING_SELL WHERE STOCK_ID=" + Integer.parseInt(stockstr));
				if (rsuser == 0) {
					flag = 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (flag == 1) {
			res.append("FALSE</STATUS></DELETE_SCHEDULE>");
		} else
			res.append("TRUE</STATUS></DELETE_SCHEDULE>");

		result = res.toString();
		return result;
	}

	public static String getUserSchedule(String username) {
		String result = "";
		SingletonDBConnect con = SingletonDBConnect.getDbCon();
		StringBuilder res = new StringBuilder();
		res.append("<SCHEDULES>");
		int userId = 0;
		try {
			ResultSet rsuser = con.query("SELECT USER_ID FROM WPL_USER WHERE username=" + "'" + username + "'");
			while (rsuser.next()) {
				userId = rsuser.getInt("USER_ID");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ResultSet rs = con.query("SELECT STOCK_ID, \n" + "QUANTITY, PRICE, SCHEDULED_DATE, RECURSION_PLAN,\n"
					+ "PURCHASE_STATUS, COMPANY_NAME FROM WPL_RECURRING_BUY , wpl_company company where "
					+ "company.COMPANY_ID=WPL_RECURRING_BUY.company_id AND USER_ID=" + userId);
			while (rs.next()) {
				res.append("<SCHEDULE>");
				int stockid = rs.getInt("STOCK_ID");
				res.append("<STOCK_ID>" + stockid + "</STOCK_ID>");
				String company = rs.getString("COMPANY_NAME");
				res.append("<COMPANY_NAME>" + company + "</COMPANY_NAME>");
				int qty = rs.getInt("QUANTITY");
				res.append("<QUANTITY>" + qty + "</QUANTITY>");
				Double price = rs.getDouble("PRICE");
				res.append("<PRICE>" + price + "</PRICE>");

				Date schedule_date = rs.getDate("SCHEDULED_DATE");
				res.append("<SCHEDULED_DATE>" + schedule_date + "</SCHEDULED_DATE>");

				String plan = rs.getString("RECURSION_PLAN");
				res.append("<RECURSION_PLAN>" + plan + "</RECURSION_PLAN>");

				String status = rs.getString("PURCHASE_STATUS");
				res.append("<PURCHASE_STATUS>" + status + "</PURCHASE_STATUS>");

				res.append("</SCHEDULE>");
			}
			res.append("</SCHEDULES>");
		} catch (SQLException e) {
			e.printStackTrace();
			res.append("</SCHEDULES>");
		}
		return res.toString();
	}
	
	public static String getUserSellSchedule(String username) {
		SingletonDBConnect con = SingletonDBConnect.getDbCon();
		StringBuilder res = new StringBuilder();
		res.append("<SCHEDULES>");
		int userId = 0;
		try {
			ResultSet rsuser = con.query("SELECT USER_ID FROM WPL_USER WHERE username=" + "'" + username + "'");
			while (rsuser.next()) {
				userId = rsuser.getInt("USER_ID");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ResultSet rs = con.query("SELECT STOCK_ID, \n" + "QUANTITY, PRICE, SCHEDULED_DATE, RECURSION_PLAN,\n"
					+ "SOLD_STATUS, COMPANY_NAME FROM WPL_RECURRING_SELL , wpl_company company where "
					+ "company.COMPANY_ID=WPL_RECURRING_SELL.company_id AND USER_ID=" + userId);
			while (rs.next()) {
				res.append("<SCHEDULE>");
				int stockid = rs.getInt("STOCK_ID");
				res.append("<STOCK_ID>" + stockid + "</STOCK_ID>");
				String company = rs.getString("COMPANY_NAME");
				res.append("<COMPANY_NAME>" + company + "</COMPANY_NAME>");
				int qty = rs.getInt("QUANTITY");
				res.append("<QUANTITY>" + qty + "</QUANTITY>");
				Double price = rs.getDouble("PRICE");
				res.append("<PRICE>" + price + "</PRICE>");

				Date schedule_date = rs.getDate("SCHEDULED_DATE");
				res.append("<SCHEDULED_DATE>" + schedule_date + "</SCHEDULED_DATE>");

				String plan = rs.getString("RECURSION_PLAN");
				res.append("<RECURSION_PLAN>" + plan + "</RECURSION_PLAN>");

				String status = rs.getString("SOLD_STATUS");
				res.append("<PURCHASE_STATUS>" + status + "</PURCHASE_STATUS>");

				res.append("</SCHEDULE>");
			}
			res.append("</SCHEDULES>");
		} catch (SQLException e) {
			e.printStackTrace();
			res.append("</SCHEDULES>");
		}
		return res.toString();
	}

	public static String editProfile(String username, String email, String address) {
		String update = "UPDATE WPL_USER SET EMAIL='" + email + "'" + "," + " ADDRESS=" + "'" + address + "'"
				+ " WHERE USERNAME='" + username + "'";
		Logger.info("In editProfile() Query Handler");
		Logger.info("Update Query:" + update);
		SingletonDBConnect con = SingletonDBConnect.getDbCon();
		try {
			con.update(update);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Logger.info("Successfullly updated the profile, returning the User Profile");
		return getUserProfile(username);
	}
}
