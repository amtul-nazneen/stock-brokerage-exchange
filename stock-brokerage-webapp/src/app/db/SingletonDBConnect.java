package app.db;

import java.sql.*;

import app.config.Config;

public final class SingletonDBConnect {
	public Connection conn;
	private Statement statement;
	public static SingletonDBConnect db;

	private SingletonDBConnect() {

		try {
			Class.forName(Config.DRIVER).newInstance();
			this.conn = (Connection) DriverManager.getConnection(Config.URL, Config.USER_NAME, Config.PASSWORD);
		} catch (Exception sqle) {
			sqle.printStackTrace();
		}
	}

	public static synchronized SingletonDBConnect getDbCon() {
		if (db == null) {
			db = new SingletonDBConnect();
		}
		return db;
	}

	public ResultSet query(String query) throws SQLException {
		statement = db.conn.createStatement();
		ResultSet res = statement.executeQuery(query);
		return res;
	}

	public int insert(String insertQuery) throws SQLException {
		statement = db.conn.createStatement();
		int result = statement.executeUpdate(insertQuery);
		return result;
	}

	public int update(String updateQuery) throws SQLException {
		statement = db.conn.createStatement();
		int result = statement.executeUpdate(updateQuery);
		return result;
	}
}
