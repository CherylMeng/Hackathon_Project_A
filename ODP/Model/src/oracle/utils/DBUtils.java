package oracle.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
    public static boolean checkRecordExists(ResultSet rs, String idColumn) {
        long id = -1;
        if (rs != null) {
            try{
                while (rs.next()) {
                    id = rs.getLong(idColumn);
                }
            } catch (SQLException e){
                e.printStackTrace();
            }    
        }
        return id != -1;
    }
    
    
}
