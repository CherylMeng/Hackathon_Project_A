package oracle.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;

import oracle.model.Catalog;
import oracle.model.User;
import oracle.model.UserInfoItem;

import oracle.utils.DBConstant;
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
    
    public static boolean isUserExist(String username) {
        Connection conn = getConnection();
        String sql = "SELECT USER_ID FROM USERS WHERE NAME = ? AND STATUS NOT IN (SELECT STATUS_ID FROM USER_STATUS WHERE STATUS_NAME = 'DELETED' OR STATUS = 'REFUSED')";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return DBUtils.checkRecordExists(rs, "USER_ID");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (pstmt != null){
                try{
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static boolean userAuth(String username, String password) {
        Connection conn = getConnection();
        String sql = "SELECT USER_ID FROM USERS WHERE NAME = ? AND PASSWORD = ? AND STATUS NOT IN (SELECT STATUS_ID FROM USER_STATUS WHERE STATUS_NAME = 'DELETED' OR STATUS = 'REFUSED')";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return DBUtils.checkRecordExists(rs, "USER_ID");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (pstmt != null){
                try{
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static HashMap getRegisterUserRoles(){
        HashMap<String, Long> map = new HashMap<String, Long>();
        Connection conn = getConnection();
        String sql = "SELECT ROLE_ID, ROLE_NAME FROM USER_ROLE WHERE ROLE_ID = 2 OR ROLE_ID = 4";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
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
        } finally {
            if (stmt != null){
                try{
                    stmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
    
    public static boolean updateUserStatus(long userID, String status) {
        Connection conn = getConnection();
        String sql = "UPDATE USERS SET STATUS = (SELECT STATUS_ID FROM USER_STATUS WHERE STATUS_NAME = ?), _TIME_COLUMN_ = systimestamp WHERE USER_ID = ?";
        PreparedStatement pstmt = null;
        try{
            sql = sql.replaceAll("_TIME_COLUMN_", status + DBConstant.USER_STATUS_UPDATE_TIME_SUFFIX);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setLong(2, userID);
            int linesUpdated = pstmt.executeUpdate();
            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null){
                try{
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static long createUser(long roleID){
        Connection conn = getConnection();
        String getUserIdSql = "SELECT SEQ_PROCUREMENT_USER_ID.NEXTVAL USER_ID FROM DUAL";
        String addUserSql = "INSERT INTO USERS (USER_ID, ROLE_ID, STATUS) VALUES (?, ?, 1)";
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long userID = -1;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getUserIdSql);
            if (rs != null) {
                while (rs.next()){
                    userID = rs.getLong("USER_ID");
                }
            }
            if (userID > 0) {
                pstmt = conn.prepareStatement(addUserSql);
                pstmt.setLong(1, userID);
                pstmt.setLong(2, roleID);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null){
                try{
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(conn);
        }
        return userID;
    }
    
    public static User getUser(long userID, boolean isMandatory, boolean isNew) {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        User user = new User();
        long roleID = -1;
        String getUsedSql = "SELECT USER_ID, ROLE_NAME, NAME, MANAGER_ID, STATUS_NAME, USER_ROLE.ROLE_ID ROLE_ID FROM (SELECT USER_ID, ROLE_ID,NAME, MANAGER_ID, STATUS FROM USERS WHERE USER_ID = ?) FILTERED_USERS LEFT JOIN USER_ROLE ON FILTERED_USERS.ROLE_ID = USER_ROLE.ROLE_ID LEFT JOIN USER_STATUS ON FILTERED_USERS.STATUS = USER_STATUS.STATUS_ID";
        String getUserInfoSql = "SELECT TYPE_ID, TYPE_NAME, DISPLAY_NAME, USER_INFO_VALUE FROM (SELECT TYPE_ID, TYPE_NAME, DISPLAY_NAME, DISPLAY_ORDER FROM USER_INFO_TYPE WHERE ROLE_ID = ? _MANDATORY_) FILTERED_IINFO_TYPE LEFT JOIN USER_INFO ON USER_INFO.USER_INFO_TYPE = FILTERED_IINFO_TYPE.TYPE_ID _WHERE_ ORDER BY DISPLAY_ORDER";
        
        if (isMandatory) {
            getUserInfoSql = getUserInfoSql.replaceAll("_MANDATORY_", "AND IS_MANDATORY = ?");
        } else {
            getUserInfoSql = getUserInfoSql.replaceAll("_MANDATORY_", "");
        }
        
        if (isNew) {
            getUserInfoSql = getUserInfoSql.replaceAll("_WHERE_", "");
        } else {
            getUserInfoSql = getUserInfoSql.replaceAll("_WHERE_", "WHERE USER_ID = ?");
        }
        try {
            pstmt = conn.prepareStatement(getUsedSql);
            pstmt.setLong(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    user.setUserID(rs.getLong("USER_ID"));
                    user.setRole(rs.getString("ROLE_NAME"));
                    user.setUsername(rs.getString("NAME"));
                    user.setManagerID(rs.getLong("MANAGER_ID"));
                    user.setStatus(rs.getString("STATUS_NAME"));
                    roleID = rs.getLong("ROLE_ID");
                }
            }
            rs.close();
            pstmt.close();
            pstmt = conn.prepareStatement(getUserInfoSql);
            pstmt.setLong(1, roleID);
            if (isMandatory && (!isNew)) {
                pstmt.setInt(2, 1);
                pstmt.setLong(3, userID);
            } else {
                if (isMandatory) {
                    pstmt.setInt(2, 1);
                } else if (!isNew){
                    pstmt.setLong(2, userID);
                }
            }
            rs = pstmt.executeQuery();
            if(rs != null){
                UserInfoItem item = null;
                while (rs.next()) {
                    item = new UserInfoItem();
                    item.setType(rs.getLong("TYPE_ID"));
                    item.setTypeName(rs.getString("TYPE_NAME"));
                    item.setDisplayName(rs.getString("DISPLAY_NAME"));
                    item.setValue(rs.getString("USER_INFO_VALUE"));
                    user.addUserInfoItem(item);
                }
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null){
                try{
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public static long saveUserInfoItem(UserInfoItem item, long userID) {
        Connection conn = getConnection();
        String getUserInfoIdSql = "SELECT SEQ_PROCUREMENT_USER_INFO_ID.NEXTVAL USER_INFO_ID FROM DUAL";
        String addUserInfoSql = "INSERT INTO USER_INFO (USER_INFO_ID, USER_ID, USER_INFO_TYPE, USER_INFO_VALUE) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long userInfoID = -1;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getUserInfoIdSql);
            if (rs != null) {
                while (rs.next()){
                    userInfoID = rs.getLong("USER_INFO_ID");
                }
            }
            if (userInfoID > 0) {
                pstmt = conn.prepareStatement(addUserInfoSql);
                pstmt.setLong(1, userInfoID);
                pstmt.setLong(2, userID);
                pstmt.setLong(3, item.getType());
                pstmt.setString(4, item.getValue());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            if (pstmt != null){
                try{
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(conn);
        }
        return userInfoID;
    }
    
    public static boolean saveUser(User user) {
        for (UserInfoItem item : user.getUserInfo()) {
            saveUserInfoItem(item, user.getUserID());
        }
        Connection conn = getConnection();
        String updateUserSql = "UPDATE USER SET NAME = ?, PASSWORD = ?, MANAGER_ID = ? WHERE USER_ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(updateUserSql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setLong(3, user.getManagerID());
            pstmt.setLong(4, user.getUserID());
            int linesUpdated = pstmt.executeUpdate();
            
            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null){
                try{
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static ArrayList<Catalog> getCataLogByParent(long parent){
        ArrayList<Catalog> catalogs = new ArrayList<Catalog>();
        Connection conn = getConnection();
        String sql = "SELECT CATALOG_ID, PARENT_CATALOG, CATALOG_DEPTH, CATALOG_NAME FROM CATALOG WHERE PARENT_CATALOG = ? AND IS_DELETED = 0";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, parent);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                Catalog catalog = new Catalog();
                while (rs.next()) {
                    catalog.setCatalogID(rs.getLong("CATALOG_ID"));
                    catalog.setParentCatalog(rs.getLong("PARENT_CATALOG"));
                    catalog.setCatalogDepth(rs.getInt("CATALOG_DEPTH"));
                    catalog.setCatalogName(rs.getString("CATALOG_NAME"));
                    catalogs.add(catalog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogs;
    }
    
    public static long addCataLog(Catalog newCatalog){
        Connection conn = getConnection();
        String getCatalogIdSql = "SELECT SEQ_PROCUREMENT_CATALOG_ID.NEXTVAL CATALOG_ID FROM DUAL";
        String addCatalogSql = "INSERT INTO CATALOG(CATALOG_ID, PARENT_CATALOG, CATALOG_DEPTH, CATALOG_NAME, IS_DELETED) VALUES (?, ?, ?, ?, 0)";
        Statement stmt = null;
        PreparedStatement pstmt = null;
        long catalogID = -1;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getCatalogIdSql);
            if (rs != null) {
                while (rs.next()){
                    catalogID = rs.getLong("CATALOG_ID");
                }
            }
            if (catalogID > 0) {
                pstmt = conn.prepareStatement(addCatalogSql);
                pstmt.setLong(1, catalogID);
                pstmt.setLong(2, newCatalog.getParentCatalog());
                pstmt.setLong(3, newCatalog.getCatalogDepth());
                pstmt.setString(4, newCatalog.getCatalogName());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            if (pstmt != null){
                try{
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(conn);
        }
        return catalogID;
    }
    
    public static boolean updateCatalogName(long catalogID, String catalogName){
        Connection conn = getConnection();
        String updateCatalogSql = "UPDATE CATALOG SET CATALOG_NAME = ? WHERE CATALOG_ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(updateCatalogSql);
            pstmt.setString(1, catalogName);
            pstmt.setLong(2, catalogID);
            int linesUpdated = pstmt.executeUpdate();
            
            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null){
                try{
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static boolean deleteCatalogName(long catalogID){
        Connection conn = getConnection();
        String checkCatalogSql = "SELECT COUNT(*) TOTAL_COUNT FROM OFFICE_DEPOT WHERE CATALOG_ID = ?";
        String deleteCatalogSql = "UPDATE CATALOG SET IS_DELETED = 1 WHERE CATALOG_ID = ?";
        PreparedStatement pstmt = null;
        int totalCount = -1;
        try {
            pstmt = conn.prepareStatement(checkCatalogSql);
            pstmt.setLong(1, catalogID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    totalCount = rs.getInt("TOTAL_COUNT");
                }
            }
            pstmt.close();
            if (totalCount == 0){
                pstmt = conn.prepareStatement(deleteCatalogSql);
                pstmt.setLong(1, catalogID);
                int linesUpdated = pstmt.executeUpdate();
                
                return linesUpdated > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null){
                try{
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
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
        //System.out.println(getConnection());
        //getUserRoles();
        //System.out.println(isUserExist("Test"));
        //System.out.println(userAuth("Test","passwd"));
        //System.out.println(getRegisterUserRoleMap());
        //System.out.println(updateUserStatus(1, DBConstant.USER_STATUS_ENABLED));
        //System.out.println(getNewUserID(1));
        //System.out.println(getUser(1, true, true));
        //System.out.println(deleteCatalogName(46));
        //System.out.println(getCataLogByParent(0));
        //updateCatalogName(46, "Bookbinding Supplie");
    }
}
