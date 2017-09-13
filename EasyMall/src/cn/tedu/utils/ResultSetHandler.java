package cn.tedu.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T> {
	/**
	 * ��ResultSet���л�ȡ����,�������ݽ��з�װT t,������
	 * @param rs :�����
	 * @return T t(����ʵ����������List<ʵ����> list)
	 * @throws SQLException
	 */
	public T handle(ResultSet rs) throws SQLException;
}
