package com.example.sdldemo.example.widget.media;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;

public class StringHelper {

	public static String stringToHexString(String str) {
		String ret = "";
		byte[] bytes = str.getBytes();
		for (byte b : bytes) {
			ret = ret + Integer.toHexString((int) b & 0xFF);
		}
		return ret;
	}

	public static String hexStringToString(String hex) {
		String ret = "";
		int l = hex.length();
		byte[] bytes = new byte[l / 2];
		for (int i = 0; i < l; i += 2) {
			bytes[i / 2] = (byte) (Integer.parseInt(hex.substring(i, i + 2), 16) & 0xFF);
		}
		try {
			ret = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}


	/**
	 * desc:将16进制的数据转为数组
	 * <p>
	 * 创建人：聂旭阳 , 2014-5-25 上午11:08:33
	 * </p>
	 * 
	 * @param data
	 * @return modified:
	 */
	public static byte[] stringToBytes(String data) {
		String hexString = data.toUpperCase().trim();
		if (hexString.length() % 2 != 0) {
			return null;
		}
		byte[] retData = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i++) {
			int int_ch; // 两位16进制数转化后的10进制数
			char hex_char1 = hexString.charAt(i); // //两位16进制数中的第一位(高位*16)
			int int_ch1;
			if (hex_char1 >= '0' && hex_char1 <= '9')
				int_ch1 = (hex_char1 - 48) * 16; // // 0 的Ascll - 48
			else if (hex_char1 >= 'A' && hex_char1 <= 'F')
				int_ch1 = (hex_char1 - 55) * 16; // // A 的Ascll - 65
			else
				return null;
			i++;
			char hex_char2 = hexString.charAt(i); // /两位16进制数中的第二位(低位)
			int int_ch2;
			if (hex_char2 >= '0' && hex_char2 <= '9')
				int_ch2 = (hex_char2 - 48); // // 0 的Ascll - 48
			else if (hex_char2 >= 'A' && hex_char2 <= 'F')
				int_ch2 = hex_char2 - 55; // // A 的Ascll - 65
			else
				return null;
			int_ch = int_ch1 + int_ch2;
			retData[i / 2] = (byte) int_ch;// 将转化后的数放入Byte里
		}
		return retData;
	}

	public static Object getObj(String string) {
		try {
			byte[] stringToBytes = StringHelper.stringToBytes(string);
			ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
			ObjectInputStream is = new ObjectInputStream(bis);
			// 返回反序列化得到的对象
			Object readObject = is.readObject();
			return readObject;
		} catch (Exception e) {
		}
		return null;
	}

}
