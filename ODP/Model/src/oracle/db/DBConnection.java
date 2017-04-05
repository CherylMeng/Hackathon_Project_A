package oracle.db; 

import java.sql.Connection;



import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
 
import com.mysql.jdbc.Driver;          

public class DBConnection {

	public static void main(String[] args) {
		System.out.println(getConnection());
	}


	/**
	 * 
	 * @return
	 */
	public static Connection getConnection() {  
				String forName ="com.mysql.jdbc.Driver";
				String dbUrl = "jdbc:mysql://localhost:3306/fate";
				String dbUser = "xiadai";
				String dbPwd = "welcome1";
				Connection conn = null; 
		try {
			Class.forName(forName);
			java.sql.DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
