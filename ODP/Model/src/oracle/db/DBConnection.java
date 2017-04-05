package oracle.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;

import oracle.utils.DBUtils;

public class DBConnection {
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
                         int id = rs.getInt("ROLE_ID");
                         String name = rs.getString("ROLE_NAME");
                         System.out.println(id + " " + name);
                    }
                } catch (SQLException e) {
                     e.printStackTrace();
                }
            }
        }catch(SQLException e){
             e.printStackTrace();
        }
    }
    public static boolean isUserExist(String username) {
        Connection conn = getConnection();
        String sql = "SELECT USER_ID FROM USERS WHERE NAME = ? AND STATUS NOT IN (SELECT STATUS_ID FROM USER_STATUS WHERE STATUS_NAME = 'DELETED' OR STATUS = 'REFUSED')";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return DBUtils.checkRecordExists(rs, "USER_ID");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean userAuth(String username, String password) {
        Connection conn = getConnection();
        String sql = "SELECT USER_ID FROM USERS WHERE NAME = ? AND STATUS NOT IN (SELECT STATUS_ID FROM USER_STATUS WHERE STATUS_NAME = 'DELETED' OR STATUS = 'REFUSED')";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return DBUtils.checkRecordExists(rs, "USER_ID");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static HashMap getRegisterUserRoleMap(){
        HashMap<String, Long> map = new HashMap<String, Long>();
        Connection conn = getConnection();
        String sql = "SELECT ROLE_ID, ROLE_NAME FROM USER_ROLE WHERE ROLE_ID = 2 OR ROLE_ID = 4";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs != null) {
                while (rs.next()){
                    String roleName = rs.getString("ROLE_NAME");
                    long roleID = rs.getLong("ROLE_ID");
                    map.put(roleName, roleID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
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
        
    public static void main(String[] args) {
        System.out.println(getConnection());
        //getUserRoles();
        //System.out.println(isUserExist("Test"));
        //System.out.println(userAuth("Test","passwd"));
        System.out.println(getRegisterUserRoleMap());
    }
}
