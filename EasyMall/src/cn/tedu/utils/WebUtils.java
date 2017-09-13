package cn.tedu.utils;

public class WebUtils {
	/**
	 * 检查传入的字符串是否为Null或者为空字符串
	 * @param str
	 * @return true 表示为null或空字符串  false 表示不为null也不为空字符串
	 */
	public static boolean isNull(String str){
		return (str == null || "".equals(str.trim()));
	}
}
