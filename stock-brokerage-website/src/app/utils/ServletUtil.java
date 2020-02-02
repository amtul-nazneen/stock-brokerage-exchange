package app.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import app.dao.Company;
import app.dao.Stock;
import app.dao.User;
import app.dao.UserAccount;
import app.dao.UserSchedule;
import app.dao.UserStocks;

public class ServletUtil {
	public static List<UserStocks> getUserStocks(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getUserStocks()");
		List<UserStocks> userstocks = new ArrayList<UserStocks>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("STOCK");

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList pricenode = element.getElementsByTagName("PRICE");
				Element line = (Element) pricenode.item(0);
				String price = getCharacterDataFromElement(line);
				
				NodeList dopnode = element.getElementsByTagName("DOP");
				 line = (Element) dopnode.item(0);
				String dop = getCharacterDataFromElement(line);
				
				NodeList qnode = element.getElementsByTagName("QUANTITY");
				 line = (Element) qnode.item(0);
				String quantity = getCharacterDataFromElement(line);
				
				NodeList cnode = element.getElementsByTagName("COMPANY");
				 line = (Element) cnode.item(0);
				String company = getCharacterDataFromElement(line);
				
				NodeList stockidnode = element.getElementsByTagName("STOCKID");
				 line = (Element) stockidnode.item(0);
				String stockid = getCharacterDataFromElement(line);
				
				UserStocks stock=new UserStocks(stockid,company,quantity,price,dop);
				userstocks.add(stock);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userstocks;
	}
	
	public static List<UserAccount> getUserAccounts(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getUserAccounts()");
		List<UserAccount> accounts = new ArrayList<UserAccount>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("ACCOUNT");

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList accnode = element.getElementsByTagName("ACCOUNT_NO");
				Element line = (Element) accnode.item(0);
				String acc = getCharacterDataFromElement(line);
				
				NodeList routingnode = element.getElementsByTagName("ROUTING_INFO");
				 line = (Element) routingnode.item(0);
				String routing = getCharacterDataFromElement(line);

				NodeList balancenode = element.getElementsByTagName("BALANCE");
				 line = (Element) balancenode.item(0);
				String balance = getCharacterDataFromElement(line);
				
				UserAccount account=new UserAccount(acc,routing,balance);
				accounts.add(account);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accounts;
	}
	
	public static User getUserFromXML(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getUserFromXML()");
		User user = new User();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("USER");

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList emailnode = element.getElementsByTagName("EMAIL");
				Element line = (Element) emailnode.item(0);
				String email = getCharacterDataFromElement(line);
				user.setEmail(email);

				NodeList usernamenode = element.getElementsByTagName("USERNAME");
				line = (Element) usernamenode.item(0);
				String username = getCharacterDataFromElement(line);
				user.setUsername(username);

				NodeList balancenode = element.getElementsByTagName("BALANCE");
				line = (Element) balancenode.item(0);
				String balance = getCharacterDataFromElement(line);
				user.setBalance(Double.parseDouble(balance));

				NodeList address = element.getElementsByTagName("ADDRESS");
				line = (Element) address.item(0);
				String userAddress = getCharacterDataFromElement(line);
				user.setAddress(userAddress);

				/*NodeList contactNo = element.getElementsByTagName("CONTACT_NO");
				line = (Element) contactNo.item(0);
				String userContact = getCharacterDataFromElement(line);
				user.setContactNo(userContact);*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public static User getRegisteredUserFromXML(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getRegisteredUserFromXML()");
		User user = new User();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("USER");

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList emailnode = element.getElementsByTagName("EMAIL");
				Element line = (Element) emailnode.item(0);
				String email = getCharacterDataFromElement(line);
				user.setEmail(email);

				NodeList usernamenode = element.getElementsByTagName("USERNAME");
				line = (Element) usernamenode.item(0);
				String username = getCharacterDataFromElement(line);
				user.setUsername(username);

				NodeList balancenode = element.getElementsByTagName("BALANCE");
				line = (Element) balancenode.item(0);
				String balance = getCharacterDataFromElement(line);
				user.setBalance(Double.parseDouble(balance));

				NodeList address = element.getElementsByTagName("ADDRESS");
				line = (Element) address.item(0);
				String userAddress = getCharacterDataFromElement(line);
				user.setAddress(userAddress);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	private static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}

	public static List<Stock> getStocksFromXML(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getStocksFromXML()");
		List<Stock> stocks = new ArrayList<Stock>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("STOCK");
			Logger.info("Length:"+nodes.getLength());
			for (int i = 0; i < nodes.getLength(); i++) {

				Element element = (Element) nodes.item(i);
				String price = element.getAttribute("price");
				String date = element.getAttribute("date");
				Stock stock = new Stock(date, price);
				stocks.add(stock);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return stocks;
	}
	
	public static List<Company> getCompaniesFromXML(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getCompaniesFromXML()");
		List<Company> companies=new ArrayList<Company>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("COMPANY");
			Logger.info("Length:"+nodes.getLength());
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				String name = element.getAttribute("name");
				
				Company company=new Company(name);
				companies.add(company);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companies;
	}
	
	
	public static String getStatusAfterPurchase(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getStatusAfterPurchase()");
		String status="";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("PURCHASE");

			for (int i = 0; i < nodes.getLength(); i++) 
			{
				Element element = (Element) nodes.item(i);
				NodeList emailnode = element.getElementsByTagName("STATUS");
				Element line = (Element) emailnode.item(0);
				 status = getCharacterDataFromElement(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static String getStatusAfterAddingAccount(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getStatusAfterAddingAccount()");
		String status="";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("ACCOUNT");

			for (int i = 0; i < nodes.getLength(); i++) 
			{
				Element element = (Element) nodes.item(i);
				NodeList emailnode = element.getElementsByTagName("STATUS");
				Element line = (Element) emailnode.item(0);
				 status = getCharacterDataFromElement(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	public static String getStatusAfterSellStock(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getStatusAfterSellStock()");
		String status="";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("SELL");

			for (int i = 0; i < nodes.getLength(); i++) 
			{
				Element element = (Element) nodes.item(i);
				NodeList emailnode = element.getElementsByTagName("STATUS");
				Element line = (Element) emailnode.item(0);
				 status = getCharacterDataFromElement(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	public static String getStatusAfterDeleteSchedule(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getStatusAfterDeleteSchedule()");
		String status="";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("DELETE_SCHEDULE");

			for (int i = 0; i < nodes.getLength(); i++) 
			{
				Element element = (Element) nodes.item(i);
				NodeList emailnode = element.getElementsByTagName("STATUS");
				Element line = (Element) emailnode.item(0);
				 status = getCharacterDataFromElement(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	public static String getStatusAfterTransferAmount(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getStatusAfterTransferAmount()");
		String status="";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("TRANSFER");

			for (int i = 0; i < nodes.getLength(); i++) 
			{
				Element element = (Element) nodes.item(i);
				NodeList emailnode = element.getElementsByTagName("STATUS");
				Element line = (Element) emailnode.item(0);
				 status = getCharacterDataFromElement(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	public static List<UserSchedule> getUserSchedules(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getUserSchedules()");
		List<UserSchedule> schedules = new ArrayList<UserSchedule>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("SCHEDULE");

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList stockidnode = element.getElementsByTagName("STOCK_ID");
				Element line = (Element) stockidnode.item(0);
				String stockid = getCharacterDataFromElement(line);
				

				NodeList companynode = element.getElementsByTagName("COMPANY_NAME");
				line = (Element) companynode.item(0);
				String company = getCharacterDataFromElement(line);
				

				NodeList quantitynode = element.getElementsByTagName("QUANTITY");
				line = (Element) quantitynode.item(0);
				String quantity = getCharacterDataFromElement(line);
				

				NodeList schedulednode = element.getElementsByTagName("SCHEDULED_DATE");
				line = (Element) schedulednode.item(0);
				String scheduled = getCharacterDataFromElement(line);
				
				NodeList pricenode = element.getElementsByTagName("PRICE");
				line = (Element) pricenode.item(0);
				String price = getCharacterDataFromElement(line);
				
				NodeList plannode = element.getElementsByTagName("RECURSION_PLAN");
				line = (Element) plannode.item(0);
				String plan = getCharacterDataFromElement(line);
				
				NodeList statusnode = element.getElementsByTagName("PURCHASE_STATUS");
				line = (Element) statusnode.item(0);
				String status = getCharacterDataFromElement(line);
				
				UserSchedule schedule=new UserSchedule();
				schedule.setStockId(stockid);
				schedule.setCompany(company);
				schedule.setScheduledDate(scheduled);
				schedule.setQuantity(quantity);
				schedule.setPrice(price);
				schedule.setRecursionPlan(plan.toUpperCase());
				schedule.setPurchaseStatus(status.toUpperCase());
				schedules.add(schedule);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedules;
	}
	
	public static String getStatusAfterPwdChange(String xml) {
		Logger.info("Parsing XML_Response from Stock-Brokerage-Website for getStatusAfterDeleteSchedule()");
		String status="";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("RESET");

			for (int i = 0; i < nodes.getLength(); i++) 
			{
				Element element = (Element) nodes.item(i);
				NodeList emailnode = element.getElementsByTagName("STATUS");
				Element line = (Element) emailnode.item(0);
				 status = getCharacterDataFromElement(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
