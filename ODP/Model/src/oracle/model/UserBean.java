package oracle.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.db.DBConnection;

public class UserBean { 
	public static int addUser(String user_name){
		int result = 1; 
		String sql = "insert into user (user_name)values(?)";
		PreparedStatement pstmt = null;
		Connection conn = DBConnection.getConnection();
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_name);
			result = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		return result;
	}
    
    public static ArrayList<User> getAllUser() throws SQLException { 
	ArrayList<User> list = new ArrayList<User>();
	String sql = "select * from user";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = DBConnection.getConnection(); 
        pstmt = conn.prepareStatement(sql); 
        rs = pstmt.executeQuery();
        while(rs.next()){
            User user = new User();
            //user.setUserName(rs.getString("user_name"));
            //.setUserId(rs.getInt("user_id"));
            list.add(user);
		} 
	return list;
    }
}
