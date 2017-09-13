package cn.tedu.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TranManager {
	private static ThreadLocal<Connection> tl = 
			new ThreadLocal<Connection>(){
		protected Connection initialValue(){
			try {
				return DaoUtils.getConn();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	};
	public static Connection getConn(){
		return tl.get();
	}
	public static void startTran(){
		try {
			tl.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void commitTran(){
		try {
			tl.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollbackTran(){
		try {
			tl.get().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void releaseTran(){
		try {
			tl.get().close();
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
