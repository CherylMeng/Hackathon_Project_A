package oracle.utils;

import java.security.MessageDigest;


public class Password {
	public static void main(String[] args) {
		System.out.println(Password.Md5For32("123456"+Constant.PASSWORDSALT));
	}
	

	public static String Md5For32(String plainText) {
		String password = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			password = buf.toString();// 32位的加密

		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}
}
