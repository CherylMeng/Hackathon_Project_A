package oracle.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.db.DBConnection;

public class UserBean { 
	public static int addUser(String user_name, int user_id){
		int result = 1; 
		String sql = "insert into users (name,user_id) values(?,?)";
		PreparedStatement pstmt = null;
                
		Connection conn = DBConnection.getConnection();
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_name);
		        pstmt.setInt(2, user_id);
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
	String sql = "select * from users";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = DBConnection.getConnection(); 
        pstmt = conn.prepareStatement(sql); 
        rs = pstmt.executeQuery();
        while(rs.next()){
            User user = new User();
            user.setUserName(rs.getString("name"));
            user.setUserId(rs.getInt("user_id"));
            list.add(user);
		} 
	return list;
    }
    public static User getUserById(int user_id) throws SQLException { 
        User user = new User();
        String sql = "select * from users where user_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
            Connection conn = DBConnection.getConnection(); 
            pstmt = conn.prepareStatement(sql); 
            pstmt.setInt(1, user_id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                user.setUserName(rs.getString("name"));
                user.setUserId(rs.getInt("user_id"));
            } 
        DBConnection.closeConnection(conn);
        return user;    
    }
    public static int deleteUser(int user_id){
            int result = 1; 
            String sql = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement pstmt = null;
            Connection conn = DBConnection.getConnection();
            try{
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, user_id);
                    result = pstmt.executeUpdate();
            }catch(Exception e){
                    e.printStackTrace();
            }finally{
                    DBConnection.closeConnection(conn);
            }
            return result;
    }
    public static int updateUser(User user){
            int result = 1; 
            String sql = "update users set name = ? where user_id = ?";
            PreparedStatement pstmt = null;
            
            Connection conn = DBConnection.getConnection();
            try{
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(2, user.getUserId());
                    pstmt.setString(1, user.getUserName());
                    //pstmt.setInt(3, user.getUserId());
                    result = pstmt.executeUpdate();
            }catch(Exception e){
                    e.printStackTrace();
            }finally{
                    DBConnection.closeConnection(conn);
            }
            return result;
    }
}
