/**
 * 
 */
package oracle.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constant {

	
	public static final DateFormat DEFAULT_DATE_FORMATE = new SimpleDateFormat("yyyy/MM/dd");
	public static final DateFormat DEFAULT_TIME_FORMATE_SHORT = new SimpleDateFormat("HH:mm");
	public static final DateFormat DEFAULT_DATETIME_FORMATE_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat DEFAULT_DATETIME_FORMATE_SHORT = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	public static final DateFormat DEFAULT_DATETIME_FORMATE_SIMPLE = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final String PASSWORDSALT = "AllsRightWithTheWorld";
	
}
