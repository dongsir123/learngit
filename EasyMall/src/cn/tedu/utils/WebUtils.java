package cn.tedu.utils;

public class WebUtils {
	/**
	 * ��鴫����ַ����Ƿ�ΪNull����Ϊ���ַ���
	 * @param str
	 * @return true ��ʾΪnull����ַ���  false ��ʾ��ΪnullҲ��Ϊ���ַ���
	 */
	public static boolean isNull(String str){
		return (str == null || "".equals(str.trim()));
	}
}
