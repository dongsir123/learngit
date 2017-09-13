package cn.tedu.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T> {
	/**
	 * 从ResultSet集中获取数据,并对数据进行封装T t,并返回
	 * @param rs :结果集
	 * @return T t(单个实体类对象或者List<实体类> list)
	 * @throws SQLException
	 */
	public T handle(ResultSet rs) throws SQLException;
}
