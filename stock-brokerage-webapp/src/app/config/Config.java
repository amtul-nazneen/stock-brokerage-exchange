package app.config;

public class Config {
	
	public static final String URL = "jdbc:mysql://localhost:3306/"+"stocks";
	public static final  String DRIVER = "com.mysql.jdbc.Driver";
	public static final  String USER_NAME = "root";
	public static final  String PASSWORD = "****";
	
	
	public static final String EXCHANGE_WEBSERVICE_URI = "https://localhost:7443/stock-exchange-webapp/rest/ExchangeService/";
	public static final String COMPANY_PRICE = "companyPrice/";
	
	public static final String RABBITMQ_HOST="localhost";
	public static final String RABBITMQ_QUEUE="queue";
}
