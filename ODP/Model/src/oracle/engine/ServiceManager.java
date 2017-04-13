package oracle.engine;

import java.sql.Connection;



import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import org.apache.log4j.Logger;
import oracle.db.DBConnection;

public class ServiceManager {
	private static Logger logger = Logger.getLogger(ServiceManager.class);
	private static HashMap<String, IService> serviceManager = null;


	public static void main(String[] args){
		Connection conn = DBConnection.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select \"service_name\", \"class_name\" from \"t_service\"");
			while (rs.next()) {
				try {

					System.out.print(rs.getString(1));
					serviceManager.put(rs.getString(1), (IService) Class
							.forName(rs.getString(2)).newInstance());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			DBConnection.closeConnection(conn);
		}
	}

	public static void init(Context context) {
		serviceManager = new HashMap<String, IService>();
		Connection conn = DBConnection.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select service_name, class_name from t_service");
			while (rs.next()) {
				try {
					serviceManager.put(rs.getString(1), (IService) Class
							.forName(rs.getString(2)).newInstance());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			DBConnection.closeConnection(conn);
		}
	}

	public static IService getService(Context context) {
		IService service = null;

		if (null == serviceManager || serviceManager.size()<=0) {
			init(context);
		}
		service = serviceManager.get(context.getServiceName());
		return service;
	}
}
