package oracle.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

public class StringUtil {

	public static void main(String[] args) {
		// char[] charArray = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
		// 'h',
		// 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
		// 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
		// '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
		// 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
		// 'U', 'V', 'W', 'X', 'Y', 'Z' };
		// System.out.println(new StringUtil().createAppCode());

		// Timestamp ts = new Timestamp(System.currentTimeMillis());
		// String tsStr = "";
		// DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// try {
		// // 方法一
		// tsStr = sdf.format(ts);
		// System.out.println(tsStr);
		// // 方法二
		// tsStr = ts.toString();
		// System.out.println(tsStr);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// StringUtil su = new StringUtil();
		// System.out.println(su.timestampToStringYMD(su.stringToTimestamp("2012-09-19 12:00:00")));
	}

	public static String createAppCode() {
		char[] charArray = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
				'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
				'U', 'V', 'W', 'X', 'Y', 'Z' };
		Random random = new Random();
		HashSet<Integer> set = new HashSet<Integer>();
		while (true) {
			int a = random.nextInt(charArray.length);
			set.add(a);
			if (set.size() == 7)
				break;
		}

		int i = 0;
		char[] newChar = new char[7];
		for (Integer integer : set) {
			newChar[i] = charArray[integer];
			i++;
		}
		String code = "";
		int num = 0;
		for (char c : newChar) {
			code += c;
			num += c;
		}
		code += charArray[num % 62];
		return code;
	}

	public static boolean verifyAppCode(String appCode) {
		char[] charArray = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
				'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
				'U', 'V', 'W', 'X', 'Y', 'Z' };
		if (null == appCode || appCode.length() != 8)
			return false;
		int num = 0;
		for (int i = 0; i < 7; i++) {
			num += appCode.charAt(i);
		}
		return appCode.charAt(7) == charArray[num % 62];

	}

	public Timestamp stringToTimestamp(String str) {
		Timestamp result = null;
		try {
			Date t1 = Constant.DEFAULT_DATETIME_FORMATE_LONG.parse(str);
			result = new Timestamp(t1.getTime());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

	public String timestampToStringYMD(Timestamp ts){
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			tsStr = sdf.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}
	
	
	public String timestampToStringYMDHMS(Timestamp ts) {
		// Timestamp ts = new Timestamp(System.currentTimeMillis());
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			tsStr = sdf.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}

	public String timestampToStringOther(Timestamp ts) {
		// Timestamp ts = new Timestamp(System.currentTimeMillis());
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			tsStr = sdf.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}

}
