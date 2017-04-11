package oracle.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;

import oracle.model.ActionType;
import oracle.model.Catalog;
import oracle.model.Notification;
import oracle.model.OfficeDepot;
import oracle.model.Order;
import oracle.model.OrderItem;
import oracle.model.Price;
import oracle.model.SKU;
import oracle.model.ToDoListItem;
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
        String forName = "oracle.jdbc.OracleDriver";
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
        String sql =
            "SELECT USER_ID FROM USERS WHERE NAME = ? AND STATUS NOT IN (SELECT STATUS_ID FROM USER_STATUS WHERE STATUS_NAME = 'DELETED' OR STATUS = 'REFUSED')";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return DBUtils.checkRecordExists(rs, "USER_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
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
        String sql =
            "SELECT USER_ID FROM USERS WHERE NAME = ? AND PASSWORD = ? AND STATUS NOT IN (SELECT STATUS_ID FROM USER_STATUS WHERE STATUS_NAME = 'DELETED' OR STATUS = 'REFUSED')";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return DBUtils.checkRecordExists(rs, "USER_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static HashMap<Long, Double> getCurrencyExchangeMap(){
        HashMap<Long, Double> map = new HashMap<Long, Double>();
        Connection conn = getConnection();
        String sql = "SELECT CURRENCY_ID, CURRENCY_EXCHANGE FROM CURRENCY_TYPE";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    Long currencyID = rs.getLong("CURRENCY_ID");
                    Double currencyExchange = rs.getDouble("CURRENCY_EXCHANGE");
                    map.put(currencyID, currencyExchange);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    public static HashMap<String, Long> getRoleMap(boolean forRegister) {
        HashMap<String, Long> map = new HashMap<String, Long>();
        Connection conn = getConnection();
        String sql = "SELECT ROLE_ID, ROLE_NAME FROM USER_ROLE";
        String registerFilter = " WHERE ROLE_ID = 2 OR ROLE_ID = 4";
        if (forRegister) {
            sql = sql + registerFilter;
        }
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    String roleName = rs.getString("ROLE_NAME");
                    long roleID = rs.getLong("ROLE_ID");
                    map.put(roleName, roleID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
    
    public static HashMap<String, Long> getManagerMap() {
        HashMap<String, Long> map = new HashMap<String, Long>();
        Connection conn = getConnection();
        String getManagerSql = "SELECT ''||MANAGER_FIRST_NAMES.USER_INFO_VALUE||' '||MANAGER_LAST_NAMES.USER_INFO_VALUE||'' MANAGER_NAME, MANAGER_FIRST_NAMES.USER_ID MANAGER_ID FROM (SELECT USER_INFO_VALUE, USER_ID FROM USER_INFO WHERE USER_INFO_TYPE = 8) MANAGER_FIRST_NAMES INNER JOIN (SELECT USER_ID, USER_INFO_VALUE FROM USER_INFO WHERE USER_INFO_TYPE = 9) MANAGER_LAST_NAMES ON MANAGER_FIRST_NAMES.USER_ID = MANAGER_LAST_NAMES.USER_ID";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getManagerSql);
            if (rs != null) {
                while (rs.next()) {
                    String roleName = rs.getString("MANAGER_NAME");
                    long roleID = rs.getLong("MANAGER_ID");
                    map.put(roleName, roleID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
    
    public static HashMap<String, Long> getSiteMap() {
        HashMap<String, Long> map = new HashMap<String, Long>();
        Connection conn = getConnection();
        String getManagerSql = "SELECT * FROM SITE";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getManagerSql);
            if (rs != null) {
                while (rs.next()) {
                    String siteName = rs.getString("SITE_NAME");
                    long siteID = rs.getLong("SITE_ID");
                    map.put(siteName, siteID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
    
    public static HashMap<String, Long> getOrderTypeMap() {
        HashMap<String, Long> map = new HashMap<String, Long>();
        Connection conn = getConnection();
        String getOrderTypeSql = "SELECT * FROM ORDER_TYPE";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getOrderTypeSql);
            if (rs != null) {
                while (rs.next()) {
                    String typeName = rs.getString("TYPE_NAME");
                    long typeID = rs.getLong("TYPE_ID");
                    map.put(typeName, typeID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
    
    public static HashMap<String, Long> getCurrencyMap() {
        HashMap<String, Long> map = new HashMap<String, Long>();
        Connection conn = getConnection();
        String getCurrencySql = "SELECT CURRENCY_ID, CURRENCY_NAME FROM CURRENCY_TYPE";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getCurrencySql);
            if (rs != null) {
                while (rs.next()) {
                    String currencyName = rs.getString("CURRENCY_NAME");
                    long currencyID = rs.getLong("CURRENCY_ID");
                    map.put(currencyName, currencyID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
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
        String sql =
            "UPDATE USERS SET STATUS = (SELECT STATUS_ID FROM USER_STATUS WHERE STATUS_NAME = ?), _TIME_COLUMN_ = systimestamp WHERE USER_ID = ?";
        PreparedStatement pstmt = null;
        try {
            sql = sql.replaceAll("_TIME_COLUMN_", status + DBConstant.UPDATE_TIME_SUFFIX);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setLong(2, userID);
            int linesUpdated = pstmt.executeUpdate();
            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static long createUser(long roleID) {
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
                while (rs.next()) {
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
            if (pstmt != null) {
                try {
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
        String getUserSql =
            "SELECT USER_ID, ROLE_NAME, NAME, MANAGER_ID, STATUS_NAME, USER_ROLE.ROLE_ID ROLE_ID FROM (SELECT USER_ID, ROLE_ID,NAME, MANAGER_ID, STATUS FROM USERS WHERE USER_ID = ?) FILTERED_USERS LEFT JOIN USER_ROLE ON FILTERED_USERS.ROLE_ID = USER_ROLE.ROLE_ID LEFT JOIN USER_STATUS ON FILTERED_USERS.STATUS = USER_STATUS.STATUS_ID";
        String getUserInfoSql =
            "SELECT TYPE_ID, TYPE_NAME, DISPLAY_NAME, USER_INFO_VALUE, IS_TEXT, MAP_NAME FROM (SELECT TYPE_ID, TYPE_NAME, DISPLAY_NAME, DISPLAY_ORDER, IS_TEXT, MAP_NAME FROM USER_INFO_TYPE WHERE ROLE_ID = ? _MANDATORY_) FILTERED_IINFO_TYPE LEFT JOIN USER_INFO ON USER_INFO.USER_INFO_TYPE = FILTERED_IINFO_TYPE.TYPE_ID _WHERE_ ORDER BY DISPLAY_ORDER";

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
            pstmt = conn.prepareStatement(getUserSql);
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
                } else if (!isNew) {
                    pstmt.setLong(2, userID);
                }
            }
            rs = pstmt.executeQuery();
            if (rs != null) {
                UserInfoItem item = null;
                while (rs.next()) {
                    item = new UserInfoItem();
                    item.setType(rs.getLong("TYPE_ID"));
                    item.setTypeName(rs.getString("TYPE_NAME"));
                    item.setDisplayName(rs.getString("DISPLAY_NAME"));
                    item.setValue(rs.getString("USER_INFO_VALUE"));
                    item.setIsText(rs.getInt("IS_TEXT") == 1);
                    item.setMap(rs.getString("MAP_NAME"));
                    user.addUserInfoItem(item);
                }
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public static ArrayList<User> getUserByManager(long managerID){
        ArrayList<User> users = new ArrayList<User>();
        String getUserByManagerSql = "SELECT * FROM USERS WHERE MANAGER_ID = ?";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(getUserByManagerSql);
            pstmt.setLong(1, managerID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    long userID = rs.getLong("USER_ID");
                    User user = getUser(userID, true, false);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }
    
    public static ArrayList<User> getUserByRole(String role){
        ArrayList<User> users = new ArrayList<User>();
        String getUserByManagerSql = "SELECT * FROM USERS WHERE ROLE_ID = (SELECT ROLE_ID FROM USER_ROLE WHERE ROLE_NAME = ?)";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(getUserByManagerSql);
            pstmt.setString(1, role);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    long userID = rs.getLong("USER_ID");
                    User user = getUser(userID, true, false);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    public static long saveUserInfoItem(UserInfoItem item, long userID) {
        Connection conn = getConnection();
        String getUserInfoIdSql = "SELECT SEQ_PROCUREMENT_USER_INFO_ID.NEXTVAL USER_INFO_ID FROM DUAL";
        String addUserInfoSql =
            "INSERT INTO USER_INFO (USER_INFO_ID, USER_ID, USER_INFO_TYPE, USER_INFO_VALUE) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long userInfoID = -1;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getUserInfoIdSql);
            if (rs != null) {
                while (rs.next()) {
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
        } finally {
            if (pstmt != null) {
                try {
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
        String updateUserSql = "UPDATE USER SET NAME = ?, PASSWORD = ? _MANAGER_ WHERE USER_ID = ?";
        PreparedStatement pstmt = null;
        
        if (DBConstant.USER_ROLE_REQUESTOR.equals(user.getRole()) || DBConstant.USER_ROLE_MANAGER.equals(user.getRole())){
            updateUserSql = updateUserSql.replaceAll("_MANAGER_", ", MANAGER_ID = ?");
        } else {
            updateUserSql = updateUserSql.replaceAll("_MANAGER_", "");
        }
        
        try {
            pstmt = conn.prepareStatement(updateUserSql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            if(DBConstant.USER_ROLE_REQUESTOR.equals(user.getRole()) || DBConstant.USER_ROLE_MANAGER.equals(user.getRole())){
                pstmt.setLong(3, user.getManagerID());
                pstmt.setLong(4, user.getUserID());
            }else{
                pstmt.setLong(3, user.getUserID());
            }
            
            int linesUpdated = pstmt.executeUpdate();

            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static Catalog getCataLog(long catalogID) {
        Catalog catalog = null;
        Connection conn = getConnection();
        String sql =
            "SELECT CATALOG_ID, PARENT_CATALOG, CATALOG_DEPTH, CATALOG_NAME FROM CATALOG WHERE CATALOG_ID = ? AND IS_DELETED = 0";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, catalogID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                catalog = new Catalog();
                while (rs.next()) {
                    catalog.setCatalogID(rs.getLong("CATALOG_ID"));
                    catalog.setParentCatalog(rs.getLong("PARENT_CATALOG"));
                    catalog.setCatalogDepth(rs.getInt("CATALOG_DEPTH"));
                    catalog.setCatalogName(rs.getString("CATALOG_NAME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalog;
    }

    public static ArrayList<Catalog> getCataLogByParent(long parent) {
        ArrayList<Catalog> catalogs = new ArrayList<Catalog>();
        Connection conn = getConnection();
        String sql =
            "SELECT CATALOG_ID, PARENT_CATALOG, CATALOG_DEPTH, CATALOG_NAME FROM CATALOG WHERE PARENT_CATALOG = ? AND IS_DELETED = 0";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, parent);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Catalog catalog = new Catalog();
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

    public static long addCataLog(Catalog newCatalog) {
        Connection conn = getConnection();
        String getCatalogIdSql = "SELECT SEQ_PROCUREMENT_CATALOG_ID.NEXTVAL CATALOG_ID FROM DUAL";
        String addCatalogSql =
            "INSERT INTO CATALOG(CATALOG_ID, PARENT_CATALOG, CATALOG_DEPTH, CATALOG_NAME, IS_DELETED) VALUES (?, ?, ?, ?, 0)";
        Statement stmt = null;
        PreparedStatement pstmt = null;
        long catalogID = -1;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getCatalogIdSql);
            if (rs != null) {
                while (rs.next()) {
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
        } finally {
            if (pstmt != null) {
                try {
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

    public static boolean updateCatalogName(long catalogID, String catalogName) {
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
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean deleteCatalog(long catalogID) {
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
            if (totalCount == 0) {
                pstmt = conn.prepareStatement(deleteCatalogSql);
                pstmt.setLong(1, catalogID);
                int linesUpdated = pstmt.executeUpdate();

                return linesUpdated > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static Price getPrice(long priceSkuID) {
        Connection conn = getConnection();
        String getPriceIDSql = "SELECT SKU_VALUE FROM SKU WHERE SKU_ID = ?";
        PreparedStatement pstmt = null;
        long priceID = -1;
        Price price = null;
        try {
            pstmt = conn.prepareStatement(getPriceIDSql);
            pstmt.setLong(1, priceSkuID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    priceID = Long.parseLong(rs.getString("SKU_VALUE"));
                }
            }
            pstmt.close();
            rs.close();
            if (priceID > 0) {
                price = getPriceByPriceID(priceID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return price;
    }
    
    public static Price getPriceByPriceID(long priceID) {
        Connection conn = getConnection();
        String getPriceSql = "SELECT PRICE_VALUE, FILTERED_PRICE.CURRENCY_ID CURRENCY_ID, CURRENCY_NAME, CURRENCY_SYMBOL FROM (SELECT * FROM PRICE WHERE PRICE_ID = ?) FILTERED_PRICE LEFT JOIN CURRENCY_TYPE ON FILTERED_PRICE.CURRENCY_ID = CURRENCY_TYPE.CURRENCY_ID";
        PreparedStatement pstmt = null;
        Price price = null;
        try {
            pstmt = conn.prepareStatement(getPriceSql);
            pstmt.setLong(1, priceID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                price = new Price();
                while (rs.next()) {
                    price.setPriceID(priceID);
                    price.setPriceValue(rs.getDouble("PRICE_VALUE"));
                    price.setCurrencyID(rs.getLong("CURRENCY_ID"));
                    price.setCurrencyName(rs.getString("CURRENCY_NAME"));
                    price.setCurrencySymbol(rs.getString("CURRENCY_SYMBOL"));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return price;
    }

    public static long addPrice(Price price) {
        Connection conn = getConnection();
        String getPriceID = "SELECT SEQ_PROCUREMENT_PRICE_ID.NEXTVAL PRICE_ID FROM DUAL";
        String addPriceSQL = "INSERT INTO PRICE(PRICE_ID, PRICE_VALUE, CURRENCY_ID) VALUES (?, ?, ?)";
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long priceID = -1;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getPriceID);
            if (rs != null) {
                while (rs.next()) {
                    priceID = rs.getLong("PRICE_ID");
                }
            }
            if (priceID > 0) {
                pstmt = conn.prepareStatement(addPriceSQL);
                pstmt.setLong(1, priceID);
                pstmt.setDouble(2, price.getPriceValue());
                pstmt.setLong(3, price.getCurrencyID());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
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
        return priceID;
    }

    public static boolean updatePrice(Price price) {
        Connection conn = getConnection();
        String updatePriceSql = "UPDATE PRICE SET PRICE_VALUE = ?, CURRENCY_ID = ? WHERE PRICE_ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(updatePriceSql);
            pstmt.setDouble(1, price.getPriceValue());
            pstmt.setLong(2, price.getCurrencyID());
            pstmt.setLong(3, price.getPriceID());
            int linesUpdated = pstmt.executeUpdate();

            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static ArrayList<SKU> getSKUType() {
        ArrayList<SKU> skuList = new ArrayList<SKU>();
        Connection conn = getConnection();
        String getOrderTypeSql = "SELECT * FROM SKU_TYPE";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getOrderTypeSql);
            if (rs != null) {
                while (rs.next()) {
                    SKU sku = new SKU();
                    sku.setSkuType(rs.getLong("TYPE_ID"));
                    sku.setSkuName(rs.getString("SKU_NAME"));
                    sku.setIsMandatory(rs.getInt("IS_MANDATORY") == 1);
                    skuList.add(sku);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return skuList;
    }

    public static ArrayList<SKU> getSkuByOfficeDepot(long officeDepotID) {
        Connection conn = getConnection();
        ArrayList<SKU> skuList = new ArrayList<SKU>();
        String selectSkuSql =
            "SELECT FILTERED_SKU.SKU_ID SKU_ID, OFFICE_DEPOT_ID, FILTERED_SKU.SKU_TYPE_ID SKU_TYPE_ID, SKU_NAME, SKU_VALUE FROM (SELECT * FROM SKU WHERE SKU.IS_DELETED = 0 AND SKU.OFFICE_DEPOT_ID = ?) FILTERED_SKU LEFT JOIN SKU_TYPE ON FILTERED_SKU.SKU_TYPE_ID = SKU_TYPE.TYPE_ID";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(selectSkuSql);
            pstmt.setLong(1, officeDepotID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                SKU sku = new SKU();
                while (rs.next()) {
                    sku.setSkuID(rs.getLong("SKU_ID"));
                    sku.setOfficeDepotID(rs.getLong("OFFICE_DEPOT_ID"));
                    sku.setSkuType(rs.getLong("SKU_TYPE_ID"));
                    sku.setSkuName(rs.getString("SKU_NAME"));
                    sku.setSkuValue(rs.getString("SKU_VALUE"));
                    skuList.add(sku);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return skuList;
    }

    public static long addSku(SKU sku) {
        Connection conn = getConnection();
        String getSkuIdSql = "SELECT SEQ_PROCUREMENT_SKU_ID.NEXTVAL SKU_ID FROM DUAL";
        String addSkuSql = "INSERT INTO SKU(SKU_ID, SKU_TYPE_ID, SKU_VALUE, IS_DELETED) VALUES (?, ?, ?, 0)";
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long skuID = -1;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getSkuIdSql);
            if (rs != null) {
                while (rs.next()) {
                    skuID = rs.getLong("SKU_ID");
                }
            }
            if (skuID > 0) {
                pstmt = conn.prepareStatement(addSkuSql);
                pstmt.setLong(1, skuID);
                pstmt.setLong(2, sku.getSkuType());
                pstmt.setString(3, sku.getSkuValue());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
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
        return skuID;
    }

    public static boolean updateSku(SKU sku) {
        Connection conn = getConnection();
        String updateSkuSql = "UPDATE SKU SET SKU_VALUE = ? WHERE SKU_ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(updateSkuSql);
            pstmt.setString(1, sku.getSkuValue());
            pstmt.setLong(2, sku.getSkuID());
            int linesUpdated = pstmt.executeUpdate();

            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean canDeleteSku(long skuID) {
        Connection conn = getConnection();
        String checkSkuSql =
            "SELECT IS_MANDATORY FROM SKU_TYPE WHERE TYPE_ID IN (SELECT SKU_TYPE_ID FROM SKU WHERE SKU_ID = ?)";
        PreparedStatement pstmt = null;
        int isMandatory = -1;
        try {
            pstmt = conn.prepareStatement(checkSkuSql);
            pstmt.setLong(1, skuID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    isMandatory = rs.getInt("IS_MANDATORY");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isMandatory != 1;
    }

    public static boolean deleteSku(SKU sku) {
        Connection conn = getConnection();
        String updateSkuSql = "UPDATE SKU SET IS_DELETED = 1 WHERE SKU_ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(updateSkuSql);
            pstmt.setLong(1, sku.getSkuID());
            int linesUpdated = pstmt.executeUpdate();

            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static OfficeDepot getOfficeDepot(long officeDeportID) {
        ArrayList<SKU> skuList = getSkuByOfficeDepot(officeDeportID);

        Price price = null;
        SKU priceSku = null;
        //NEED TEST HERE, NOT SURE
        for (SKU sku : skuList) {
            if (sku.getSkuType() == 3) {
                price = getPrice(sku.getSkuID());
                priceSku = sku;
            }
        }
        skuList.remove(priceSku);

        Connection conn = getConnection();
        String selectOfficeDepotSql = "SELECT * FROM OFFICE_DEPOT WHERE ITEM_ID = ?";
        PreparedStatement pstmt = null;
        OfficeDepot officeDepot = null;
        try {
            pstmt = conn.prepareStatement(selectOfficeDepotSql);
            pstmt.setLong(1, officeDeportID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                officeDepot = new OfficeDepot();
                while (rs.next()) {
                    officeDepot.setCatalogID(rs.getLong("CATALOG_ID"));
                    officeDepot.setSupplierID(rs.getLong("SUPPLIER_ID"));
                }
                officeDepot.setOfficeDepotID(officeDeportID);
                officeDepot.setPrice(price);
                officeDepot.setSkuList(skuList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return officeDepot;
    }

    public static ArrayList<OfficeDepot> getOfficeDepotByCatalog(long catalogID) {
        Connection conn = getConnection();
        String selectOfficeDepotSql = "SELECT ITEM_ID FROM OFFICE_DEPOT WHERE CATALOG_ID = ? AND IS_DELETED = 0";
        PreparedStatement pstmt = null;
        ArrayList<Long> officeDepotIdList = new ArrayList<Long>();
        ArrayList<OfficeDepot> officeDepotList = new ArrayList<OfficeDepot>();
        try {
            pstmt = conn.prepareStatement(selectOfficeDepotSql);
            pstmt.setLong(1, catalogID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    officeDepotIdList.add(rs.getLong("ITEM_ID"));
                }
            }
            for (Long id : officeDepotIdList) {
                officeDepotList.add(getOfficeDepot(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return officeDepotList;
    }

    public static ArrayList<OfficeDepot> getOfficeDepotBySupplier(long supplierID) {
        Connection conn = getConnection();
        String selectOfficeDepotSql = "SELECT ITEM_ID FROM OFFICE_DEPOT WHERE SUPPLIER_ID = ? AND IS_DELETED = 0";
        PreparedStatement pstmt = null;
        ArrayList<Long> officeDepotIdList = new ArrayList<Long>();
        ArrayList<OfficeDepot> officeDepotList = new ArrayList<OfficeDepot>();
        try {
            pstmt = conn.prepareStatement(selectOfficeDepotSql);
            pstmt.setLong(1, supplierID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    officeDepotIdList.add(rs.getLong("ITEM_ID"));
                }
            }
            for (Long id : officeDepotIdList) {
                officeDepotList.add(getOfficeDepot(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return officeDepotList;
    }

    public static long addOfficeDepot(OfficeDepot officeDepot) {
        Connection conn = getConnection();
        String getOfficeDepotIDSql = "SELECT SEQ_PROCUREMENT_OFFICE_DEPOT_ID.NEXTVAL OFFICE_DEPORT_ID FROM DUAL";
        String addOfficeDepotSql =
            "INSERT INTO OFFICE_DEPOT(ITEM_ID, CATALOG_ID, SUPPLIER_ID, IS_DELETED) VALUES (?, ?, ?, 0)";
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long officeDepotID = -1;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getOfficeDepotIDSql);
            if (rs != null) {
                while (rs.next()) {
                    officeDepotID = rs.getLong("OFFICE_DEPORT_ID");
                }
            }
            if (officeDepotID > 0) {
                pstmt = conn.prepareStatement(addOfficeDepotSql);
                pstmt.setLong(1, officeDepotID);
                pstmt.setLong(2, officeDepot.getCatalogID());
                pstmt.setLong(3, officeDepot.getSupplierID());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
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

        for (SKU skuItem : officeDepot.getSkuList()) {
            skuItem.setOfficeDepotID(officeDepotID);
            addSku(skuItem);
        }

        long priceID = addPrice(officeDepot.getPrice());
        SKU priceSku = new SKU();
        priceSku.setOfficeDepotID(officeDepotID);
        priceSku.setSkuName("Price");
        priceSku.setSkuValue(String.valueOf(priceID));
        priceSku.setSkuType(3);
        addSku(priceSku);

        return officeDepotID;
    }

    public static boolean updateOfficeDepot(OfficeDepot officeDepot) {
        boolean result = true;
        for (SKU sku : officeDepot.getSkuList()) {
            result &= updateSku(sku);
        }
        result &= updatePrice(officeDepot.getPrice());
        return result;
    }

    public static boolean deleteOfficeDepot(long officeDepotID) {
        Connection conn = getConnection();
        String updateOfficeDepotSql = "UPDATE OFFICE_DEPOT SET IS_DELETED = 1 WHERE ITEM_ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(updateOfficeDepotSql);
            pstmt.setLong(1, officeDepotID);
            int linesUpdated = pstmt.executeUpdate();

            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static ArrayList<OrderItem> getOrderItemByOrder(long orderID) {
        Connection conn = getConnection();
        String getOrderItemSql = "SELECT * FROM ORDER_ITEM WHERE ORDER_ID = ?";
        PreparedStatement pstmt = null;
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
        try {
            pstmt = conn.prepareStatement(getOrderItemSql);
            pstmt.setLong(1, orderID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setOrderID(orderID);
                    item.setOfficeDepotID(rs.getLong("OFFICE_DEPOT_ID"));
                    item.setDiscount(rs.getDouble("DISCOUNT"));
                    item.setAmount(rs.getInt("AMOUNT"));

                    ArrayList<SKU> skuList = getSkuByOfficeDepot(item.getOfficeDepotID());
                    Price price = null;
                    SKU priceSku = null;

                    for (SKU sku : skuList) {
                        if (sku.getSkuType() == 3) {
                            price = getPrice(sku.getSkuID());
                            priceSku = sku;
                        }
                    }
                    skuList.remove(priceSku);
                    item.setSkuList(skuList);
                    item.setPrice(price);

                    orderItems.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return orderItems;
    }

    public static long addOrderItem(OrderItem orderItem) {
        Connection conn = getConnection();
        String getOrderItemIdSql = "SELECT SEQ_PROCUREMENT_ORDER_ITEM_ID.NEXTVAL ORDER_ITEM_ID FROM DUAL";
        String addOrderItemSql = "INSERT INTO ORDER_ITEM (ORDER_ID, OFFICE_DEPOT_ID, AMOUNT, DISCOUNT) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long orderItemID = -1;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getOrderItemIdSql);
            if (rs != null) {
                while (rs.next()) {
                    orderItemID = rs.getLong("ORDER_ITEM_ID");
                }
            }
            if (orderItemID > 0) {
                pstmt = conn.prepareStatement(addOrderItemSql);
                pstmt.setLong(1, orderItem.getOrderID());
                pstmt.setLong(2, orderItem.getOfficeDepotID());
                pstmt.setInt(3, orderItem.getAmount());
                pstmt.setDouble(4, orderItem.getDiscount());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
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
        return orderItemID;
    }
    
    public static Order getOrderById(long orderID) {
        Connection conn = getConnection();
        String getOrderSql = "SELECT FILTERED_ORDER.ORDER_TYPE ORDER_TYPE, ORDER_TYPE.TYPE_NAME TYPE_NAME, FILTERED_ORDER.ORDER_STATUS ORDER_STATUS, ORDER_STATUS.STATUS_NAME STATUS_NAME, FILTERED_ORDER.REQUESTOR REQUESTOR, USER1.NAME REQUESTOR_NAME, " +
            "FILTERED_ORDER.RECEIVER RECEIVER, USER2.NAME RECEIVER_NAME, FILTERED_ORDER.JUSTIFICATION JUSTIFICATION, FILTERED_ORDER.CREATED_TIME CREATED_TIME, FILTERED_ORDER.SUBMITTED_TIME SUBMITTED_TIME, FILTERED_ORDER.APPROVED_TIME APPROVED_TIME, " +
            "FILTERED_ORDER.PROCESSING_TIME PROCESSING_TIME, FILTERED_ORDER.COMPLETED_TIME COMPLETED_TIME, FILTERED_ORDER.REFUSED_TIME REFUSED_TIME, FILTERED_ORDER.DELETED_TIME DELETED_TIME, CANCELED_TIME, PRICE_ID FROM (SELECT * FROM ORDERS WHERE ORDER_ID = ?) " +
            "FILTERED_ORDER LEFT JOIN ORDER_TYPE ON FILTERED_ORDER.ORDER_TYPE = ORDER_TYPE.TYPE_ID LEFT JOIN ORDER_STATUS ON FILTERED_ORDER.ORDER_STATUS = ORDER_STATUS.STATUS_ID LEFT JOIN USERS USER1 ON FILTERED_ORDER.REQUESTOR = USER1.USER_ID LEFT JOIN USERS USER2 " +
            "ON FILTERED_ORDER.RECEIVER = USER2.USER_ID";
        PreparedStatement pstmt = null;
        Order order = null;
        try {
            pstmt = conn.prepareStatement(getOrderSql);
            pstmt.setLong(1, orderID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    order = new Order();
                    order.setOrderID(orderID);
                    order.setOrderType(rs.getLong("ORDER_TYPE"));
                    order.setOrderTypeName(rs.getString("TYPE_NAME"));
                    order.setOrderStatus(rs.getLong("ORDER_STATUS"));
                    order.setOrderStatusName(rs.getString("STATUS_NAME"));
                    order.setRequestor(rs.getLong("REQUESTOR"));
                    order.setRequestorName(rs.getString("REQUESTOR_NAME"));
                    order.setReceiver(rs.getLong("RECEIVER"));
                    order.setReceiverName(rs.getString("RECEIVER_NAME"));
                    order.setJustification(rs.getString("JUSTIFICATION"));
                    order.setCreatedTime(rs.getString("CREATED_TIME"));
                    order.setSubmittedTime(rs.getString("SUBMITTED_TIME"));
                    order.setApprovedTime(rs.getString("APPROVED_TIME"));
                    order.setProcessingTime(rs.getString("PROCESSING_TIME"));
                    order.setCompletedTime(rs.getString("COMPLETED_TIME"));
                    order.setRefusedTime(rs.getString("REFUSED_TIME"));
                    order.setDeletedTime(rs.getString("DELETED_TIME"));
                    order.setCanceledTime(rs.getString("CANCELED_TIME"));
                    
                    Price price = getPriceByPriceID(rs.getLong("PRICE_ID"));
                    
                    order.setTotal(price);
                }
            }
            ArrayList<OrderItem> orderItems = getOrderItemByOrder(orderID);
            order.setOrderItems(orderItems);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return order;
    }
    
    public static ArrayList<Order> getOrders(long userID, String role){
        Connection conn = getConnection();
        String getOrderSql = "SELECT ORDER_ID FROM ORDERS WHERE _ROLE_ = ? AND ORDER_STATUS NOT IN (SELECT STATUS_ID FROM ORDER_STATUS WHERE STATUS_NAME = 'DELETED')";
        PreparedStatement pstmt = null;
        ArrayList<Order> orderList = new ArrayList<Order>();
        getOrderSql = getOrderSql.replaceAll("_ROLE_", role);
        try {
            pstmt = conn.prepareStatement(getOrderSql);
            pstmt.setLong(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    long orderID = rs.getLong("ORDER_ID");
                    Order order = getOrderById(orderID);
                    orderList.add(order);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return orderList;
    }
    
    public static long addOrder(Order order){
        HashMap<Long, Double> currecyMap = getCurrencyExchangeMap();
        for (OrderItem item : order.getOrderItems()){
            OfficeDepot officeDepot = getOfficeDepot(item.getOfficeDepotID());
            item.setPrice(officeDepot.getPrice());
            item.setOrderID(order.getOrderID());
        }
        order.calculateTotalPrice(currecyMap);
        long priceID = addPrice(order.getTotal());
        
        Connection conn = getConnection();
        String getOrderItemIdSql = "SELECT SEQ_PROCUREMENT_ORDER_ID.NEXTVAL ORDER_ID FROM DUAL";
        String addOrderItemSql = "INSERT INTO ORDER (ORDER_ID, ORDER_TYPE, ORDER_STATUS, REQUESTOR, RECEIVER, JUSTIFICATION, PRICE_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long orderID = -1;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getOrderItemIdSql);
            if (rs != null) {
                while (rs.next()) {
                    orderID = rs.getLong("ORDER_ID");
                }
            }
            if (orderID > 0) {
                pstmt = conn.prepareStatement(addOrderItemSql);
                pstmt.setLong(1, orderID);
                pstmt.setLong(2, order.getOrderType());
                pstmt.setLong(3, order.getOrderStatus());
                pstmt.setLong(4, order.getRequestor());
                pstmt.setLong(5, order.getReceiver());
                pstmt.setString(6, order.getJustification());
                pstmt.setLong(7, priceID);
                pstmt.executeUpdate();
            }
            for (OrderItem item : order.getOrderItems()) {
                item.setOrderID(orderID);
                addOrderItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
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
        return orderID;
    }
    
    public static boolean updateOrderStatus(long orderID, String status){
        Connection conn = getConnection();
        String sql =
            "UPDATE ORDERS SET STATUS = (SELECT STATUS_ID FROM ORDER_STATUS WHERE STATUS_NAME = ?), _TIME_COLUMN_ = systimestamp WHERE ORDER_ID = ?";
        PreparedStatement pstmt = null;
        try {
            sql = sql.replaceAll("_TIME_COLUMN_", status + DBConstant.UPDATE_TIME_SUFFIX);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setLong(2, orderID);
            int linesUpdated = pstmt.executeUpdate();
            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static long createToDoListForOrder(Order order){
        User requestor = getUser(order.getRequestor(), true, false);
        String selectToDoListTypeIdSql = "SELECT TYPE_ID FROM TO_DO_LIST_TYPE WHERE ROLE_ID = (SELECT ROLE_ID FROM USER_ROLE WHERE ROLE_NAME = ?) AND OWNER_ROLE_ID = (SELECT ROLE_ID FROM USER_ROLE WHERE ROLE_NAME = ?) AND MANAGEMENT_ID = (SELECT TYPE_ID FROM MANAGEMENT_TYPE WHERE TYPE_NAME = ?)";
        String getToDoListIdSql = "SELECT SEQ_PROCUREMENT_TO_DO_LIST_ITEM_ID.NEXTVAL TO_DO_LIST_ITEM_ID FROM DUAL";
        String addToDoListSql = "INSERT INTO TO_DO_LIST_ITEM(ITEM_ID, TYPE_ID, OWNER_ID, ASSIGNEE_ID, ORDER_ID, IS_FINISHED) VALUES (?, ?, ?, ?, ?, 0)";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long toDoListID = -1;
        long toDoListTypeID = -1;
        try {
            pstmt = conn.prepareStatement(selectToDoListTypeIdSql);
            if (DBConstant.ORDER_STATUS_SUBMITTED.equals(order.getOrderStatusName())){
                pstmt.setString(1, DBConstant.USER_ROLE_MANAGER);
            } else if (DBConstant.ORDER_STATUS_APPROVED.equals(order.getOrderStatusName())) {
                pstmt.setString(1, DBConstant.USER_ROLE_SUPPLIER);
            }
            pstmt.setString(2, DBConstant.USER_ROLE_REQUESTOR);
            pstmt.setString(3, DBConstant.MANAGEMENT_TYPE_ORDER);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    toDoListTypeID = rs.getLong("TYPE_ID");
                }
            }
            rs.close();
            pstmt.close();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getToDoListIdSql);
            if (rs != null) {
                while (rs.next()) {
                    toDoListID = rs.getLong("TO_DO_LIST_ITEM_ID");
                }
            }
            rs.close();
            if (toDoListID > 0 && toDoListTypeID > 0) {
                pstmt = conn.prepareStatement(addToDoListSql);
                pstmt.setLong(1, toDoListID);
                pstmt.setLong(2, toDoListTypeID);
                pstmt.setLong(3, requestor.getUserID());
                pstmt.setLong(4, requestor.getManagerID());
                pstmt.setLong(5, order.getOrderID());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                if (pstmt != null) {
                    try {
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
        return toDoListID;
    }
    
    public static long getToDoListType(String assigneeRole, String ownerRole, String management){
        String selectToDoListTypeIdSql = "SELECT TYPE_ID FROM TO_DO_LIST_TYPE WHERE ROLE_ID = (SELECT ROLE_ID FROM USER_ROLE WHERE ROLE_NAME = ?) AND OWNER_ROLE_ID = (SELECT ROLE_ID FROM USER_ROLE WHERE ROLE_NAME = ?) AND MANAGEMENT_ID = (SELECT TYPE_ID FROM MANAGEMENT_TYPE WHERE TYPE_NAME = ?)";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        long toDoListTypeID = -1;
        try {
            pstmt = conn.prepareStatement(selectToDoListTypeIdSql);
            pstmt.setString(1, assigneeRole);
            pstmt.setString(2, ownerRole);
            pstmt.setString(3, management);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    toDoListTypeID = rs.getLong("TYPE_ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return toDoListTypeID;
    }
    
    public static long createToDoListForAccount(User user){
        String selectToDoListTypeIdSql = "SELECT TYPE_ID FROM TO_DO_LIST_TYPE WHERE ROLE_ID = (SELECT ROLE_ID FROM USER_ROLE WHERE ROLE_NAME = ?) AND OWNER_ROLE_ID = (SELECT ROLE_ID FROM USER_ROLE WHERE ROLE_NAME = ?) AND MANAGEMENT_ID = (SELECT TYPE_ID FROM MANAGEMENT_TYPE WHERE TYPE_NAME = ?)";
        String getToDoListIdSql = "SELECT SEQ_PROCUREMENT_TO_DO_LIST_ITEM_ID.NEXTVAL TO_DO_LIST_ITEM_ID FROM DUAL";
        String addToDoListSql = "INSERT INTO TO_DO_LIST_ITEM(ITEM_ID, TYPE_ID, OWNER_ID, ASSIGNEE_ID, IS_FINISHED) VALUES (?, ?, ?, ?, 0)";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long toDoListID = -1;
        long toDoListTypeID = -1;
        try {
            pstmt = conn.prepareStatement(selectToDoListTypeIdSql);
            if (DBConstant.USER_ROLE_REQUESTOR.equals(user.getRole()) && DBConstant.USER_STATUS_CREATED.equals(user.getStatus())){
                pstmt.setString(1, DBConstant.USER_ROLE_MANAGER);
            } else if (DBConstant.USER_ROLE_SUPPLIER.equals(user.getRole()) && DBConstant.USER_STATUS_CREATED.equals(user.getStatus())) {
                pstmt.setString(1, DBConstant.USER_ROLE_ADMIN);
            }
            pstmt.setString(2, user.getRole());
            pstmt.setString(3, DBConstant.MANAGEMENT_TYPE_ACCOUNT);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    toDoListTypeID = rs.getLong("TYPE_ID");
                }
            }
            rs.close();
            pstmt.close();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getToDoListIdSql);
            if (rs != null) {
                while (rs.next()) {
                    toDoListID = rs.getLong("TO_DO_LIST_ITEM_ID");
                }
            }
            rs.close();
            if (toDoListID > 0 && toDoListTypeID > 0) {
                pstmt = conn.prepareStatement(addToDoListSql);
                pstmt.setLong(1, toDoListID);
                pstmt.setLong(2, toDoListTypeID);
                pstmt.setLong(3, user.getUserID());
                pstmt.setLong(4, DBConstant.USER_ROLE_REQUESTOR.equals(user.getRole()) ? user.getManagerID() : 1);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                if (pstmt != null) {
                    try {
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
        return toDoListID;
    }
    
    public static ArrayList<ToDoListItem> getToDoListByAssignee(long assigneeID, String managementType){
        String getToDoListSql = "SELECT * FROM (SELECT * FROM TO_DO_LIST_ITEM WHERE ASSIGNEE_ID = ? AND IS_FINISHED = 0 _MANAGEMENT_ ) FILTERED_TO_DO_LIST_ITEM LEFT JOIN USERS ON FILTERED_TO_DO_LIST_ITEM.OWNER_ID = USERS.USER_ID";
        if (DBConstant.MANAGEMENT_TYPE_ACCOUNT.equals(managementType)) {
            getToDoListSql = getToDoListSql.replaceAll("_MANAGEMENT_", "AND (TYPE_ID = 1 OR TYPE_ID = 2)");
        } else if (DBConstant.MANAGEMENT_TYPE_ORDER.equals(managementType)) {
            getToDoListSql = getToDoListSql.replaceAll("_MANAGEMENT_", "AND (TYPE_ID = 3 OR TYPE_ID = 4)");
        } else {
            getToDoListSql = getToDoListSql.replaceAll("_MANAGEMENT_", "");
        }
        ArrayList<ToDoListItem> todoList = new ArrayList<ToDoListItem>();
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(getToDoListSql);
            pstmt.setLong(1, assigneeID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    ToDoListItem item = new ToDoListItem();
                    item.setAssigneeID(assigneeID);
                    item.setOwnerID(rs.getLong("OWNER_ID"));
                    item.setOwnerName(rs.getString("NAME"));
                    item.setTypeID(rs.getLong("TYPE_ID"));
                    item.setToDoListItemID(rs.getLong("ITEM_ID"));
                    item.setOrderID(rs.getLong("ORDER_ID"));
                    
                    todoList.add(item);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return todoList;
    }
    
    public static boolean closeToDoList(long toDoListID) {
        String updateToDoListSql = "UPDATE TO_DO_LIST_ITEM SET IS_FINISHED = 1 WHERE ITEM_ID = ?";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(updateToDoListSql);
            pstmt.setLong(1, toDoListID);
            int linesUpdated = pstmt.executeUpdate();
            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static int getToDoListNum(long assigneeID) {
        String getToDoListSql = "SELECT COUNT(*) NUM FROM TO_DO_LIST_ITEM WHERE ASSIGNEE_ID = ? AND IS_FINISHED = 0";
        int toDoListNum = -1;
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(getToDoListSql);
            pstmt.setLong(1, assigneeID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    toDoListNum = rs.getInt("NUM");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return toDoListNum;
    }
    
    public static int getNotificationNum(long receiverID) {
        String getToDoListSql = "SELECT COUNT(*) NUM FROM NOTIFICATION WHERE RECEIVER_ID = ? AND IS_READ = 0";
        int notificationtNum = -1;
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(getToDoListSql);
            pstmt.setLong(1, receiverID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    notificationtNum = rs.getInt("NUM");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return notificationtNum;
    }
    
    public static ArrayList<Notification> getNotification(long receiver, boolean checkNew){
        String getNotificationSql = "SELECT * FROM NOTIFICATION WHERE RECEIVER_ID = ? AND IS_READ = ?";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ArrayList<Notification> notificationList = new ArrayList<Notification>();
        try {
            pstmt = conn.prepareStatement(getNotificationSql);
            pstmt.setLong(1, receiver);
            if (checkNew) {
                pstmt.setInt(2, 0);
            } else {
                pstmt.setInt(2, 1);
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Notification item = new Notification();
                    item.setNotificationID(rs.getLong("NOTIFICATION_ID"));
                    item.setActionMappingID(rs.getLong("ACTION_MAPPING_ID"));
                    item.setReceiverID(rs.getLong("RECEIVER_ID"));
                    item.setMessage(rs.getString("MESSAGE"));
                    item.setCreatedTime(rs.getString("CREATED_TIME"));
                    notificationList.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return notificationList;
    }
    
    public static long createNotification(Notification notification) {
        String getNotificationMessageSql = "SELECT NOTIFICATION_MASSAGE_PATERN FROM ACTION_MAPPING WHERE MAPPING_ID = ?";
        String getNotificationIdSql = "SELECT SEQ_PROCUREMENT_NOTIFICATIONM_ID.NEXTVAL NOTIFICATION_ID FROM DUAL";
        String addNotificationSql = "INSERT INTO NOTIFICATION(NOTIFICATION_ID, ACTION_MAPPING_ID, RECEIVER_ID, MESSAGE, IS_READ) VALUES (?, ?, ?, ?, 0)";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        Statement stmt = null;
        long notificationID = -1;
        String message = null;
        try {
            pstmt = conn.prepareStatement(getNotificationMessageSql);
            pstmt.setLong(1, notification.getActionMappingID());
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    message = rs.getString("NOTIFICATION_MASSAGE_PATERN");
                }
            }
            rs.close();
            pstmt.close();
            message = message.replaceAll("<ORDER>", Long.toString(notification.getOrderID()));
            notification.setMessage(message);
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(getNotificationIdSql);
            if (rs != null) {
                while (rs.next()) {
                    notificationID = rs.getLong("NOTIFICATION_ID");
                }
            }
            rs.close();
            if (notificationID > 0) {
                pstmt = conn.prepareStatement(addNotificationSql);
                pstmt.setLong(1, notificationID);
                pstmt.setLong(2, notification.getActionMappingID());
                pstmt.setLong(3, notification.getReceiverID());
                pstmt.setString(4, notification.getMessage());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                if (pstmt != null) {
                    try {
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
        return notificationID;
    }
    
    public static boolean closeNotification(long notificationID) {
        String updateNotificationSql = "UPDATE NOTIFICATION SET IS_READ = 1 WHERE NOTIFICATION_ID = ?";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(updateNotificationSql);
            pstmt.setLong(1, notificationID);
            int linesUpdated = pstmt.executeUpdate();
            return linesUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static HashMap<Long, String> getActionTypeMap(){
        HashMap<Long, String> map = new HashMap<Long, String>();
        Connection conn = getConnection();
        String getOrderTypeSql = "SELECT * FROM ACTION_TYPE";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getOrderTypeSql);
            if (rs != null) {
                while (rs.next()) {
                    long typeID = rs.getLong("ACTION_ID");
                    String typeName = rs.getString("ACTION_NAME");
                    map.put(typeID, typeName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
    
    public static ArrayList<ActionType> getActions(long toDoListTypeID, long initialStateID){
        String getActionsSql = "SELECT FILTERED_ACTION.ACTION_ID ACTION_ID, FILTERED_ACTION.NOTIFICATION_MASSAGE_PATERN NOTIFICATION_MASSAGE_PATERN, FILTERED_ACTION.REQUEST_URL REQUEST_URL, ACTION_TYPE.ACTION_NAME ACTION_NAME FROM (SELECT * FROM ACTION_MAPPING WHERE TO_DO_LIST_TYPE_ID = ? AND INITIAL_STATE_ID = ?) FILTERED_ACTION LEFT JOIN ACTION_TYPE ON FILTERED_ACTION.ACTION_ID = ACTION_TYPE.ACTION_ID";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ArrayList<ActionType> actionList = new ArrayList<ActionType>();
        try {
            pstmt = conn.prepareStatement(getActionsSql);
            pstmt.setLong(1, toDoListTypeID);
            pstmt.setLong(2, initialStateID);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    ActionType item = new ActionType();
                    item.setActionID(rs.getLong("ACTION_ID"));
                    item.setMessage(rs.getString("NOTIFICATION_MASSAGE_PATERN"));
                    item.setRequestUrl(rs.getString("REQUEST_URL"));
                    item.setActionName(rs.getString("ACTION_NAME"));
                    
                    actionList.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return actionList;
    }
    
    public static HashMap<String, Long> getInitialStatusMap(){
        HashMap<String, Long> map = new HashMap<String, Long>();
        Connection conn = getConnection();
        String getInitialStateSql = "SELECT * FROM INITIAL_STATE_TYPE";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getInitialStateSql);
            if (rs != null) {
                while (rs.next()) {
                    long stateID = rs.getLong("TYPE_ID");
                    String stateName = rs.getString("TYPE_NAME");
                    map.put(stateName, stateID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
    
    public static HashMap<String, String> getEndingStatusMap(){
        HashMap<String, String> map = new HashMap<String, String>();
        Connection conn = getConnection();
        String getInitialStateSql = "SELECT ACTION_NAME, END_STATUS FROM ACTION_TYPE";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getInitialStateSql);
            if (rs != null) {
                while (rs.next()) {
                    String actionName = rs.getString("ACTION_NAME");
                    String endState = rs.getString("END_STATUS");
                    map.put(actionName, endState);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
        //System.out.println(getConnection());
        //getUserRoles();
        //System.out.println(isUserExist("Test"));
        //System.out.println(userAuth("Test","passwd"));
        //System.out.println(getRegisterUserRoleMap());
        //System.out.println(updateUserStatus(1, DBConstant.USER_STATUS_ENABLED));
        //System.out.println(getNewUserID(1));
        //System.out.println(getUser(4, true, true).getUserInfo());
        //System.out.println(deleteCatalogName(46));
        //System.out.println(getCataLogByParent(0));
        //updateCatalogName(46, "Bookbinding Supplie");
        //System.out.println(getSkuByOfficeDepot(1));
        //getOrders(1, DBConstant.ORDER_ROLE_REQUESTOR);
        //System.out.println(getManagerMap());
        //System.out.println(getSiteMap());
        //System.out.println(getCurrencyMap());
        //System.out.println(getOrderTypeMap());
        //System.out.println(getNotificationNum(1));
        //System.out.println(getToDoListNum(1));
        //System.out.println(getUserByRole(DBConstant.USER_ROLE_SUPPLIER));
    }
}
