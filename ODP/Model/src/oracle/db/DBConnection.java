package oracle.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	public static void main(String[] args) {
		System.out.println(getConnection());
	    getUserRoles();
	}


	/**
	 * 
	 * @return
	 */
	public static Connection getConnection() {  
				String forName ="oracle.jdbc.OracleDriver";
				String dbUrl = "jdbc:oracle:thin:@slc09xzf.us.oracle.com:1521:epps";
				String dbUser = "c##procurement";
				String dbPwd = "group3";
				Connection conn = null; 
		try {
			Class.forName(forName);
			//java.sql.DriverManager.registerDriver(new com.oracle.jdbc.OracleDriver());
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
        
        public static void getUserRoles(){
            Connection conn = getConnection();
            String sql = "SELECT * FROM USER_ROLE";
            try{
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs != null) {
                    try {
                        while (rs.next()) {
                            //Retrieve by column name
                            int id = rs.getInt("ROLE_ID");
                            String name = rs.getString("ROLE_NAME");
                            System.out.println(id + " " + name);
                            }
                    } catch (SQLException se) {
                        se.printStackTrace();
                        }
                    }
            }catch(SQLException e){
                e.printStackTrace();
            }
            
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
