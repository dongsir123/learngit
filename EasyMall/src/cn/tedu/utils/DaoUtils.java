package cn.tedu.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DaoUtils {
	private static Connection conn = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static ComboPooledDataSource pool = new ComboPooledDataSource();
	private DaoUtils(){}
	public static ComboPooledDataSource getPool(){
		return pool;
	}
	/**
	 * ��ѯ����
	 * @param sql:sql���
	 * @param rs:
	 * @return:��ѯ�Ľ��������
	 * @throws SQLException
	 */
	public static <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException{
		try {
			//��ȡ���ݿ�����
			//conn = getConn();
			conn = TranManager.getConn();
			//Ԥ����sql���,������ps����
				ps = conn.prepareStatement(sql);
			//Ϊռλ����ֵ
			for(int i=0;i<params.length;i++){
				ps.setObject(i+1, params[i]);
			}
			//ִ�в�ѯ,�����ؽ����
			rs = ps.executeQuery();
			//���ط�װ��Ķ���
			return rsh.handle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(null,ps,rs);
		}
	}
	/**
	 * ִ��CUD����
	 * @param sql: sql���
	 * @param params:�����ݹ�������Ϊռλ����ֵ������
	 * @return Ӱ�������
	 */
	public static int update(String sql,Object... params) {
		try {
			// ��ȡ���ݿ�����
//			conn = getConn();
			conn = TranManager.getConn();
			// Ԥ����sql���,������pstat����
			ps = conn.prepareStatement(sql);
			// Ϊռλ����ֵ �� ���û�д��ݲ���paramsΪnull��������Ϊ0
			for (int i = 0; i < params.length; i++) {
				// ���ݲ�����˳��Ҫ��sql�����ռλ����˳���Ӧ
				ps.setObject(i + 1, params[i]);
			}
			// ִ��sql���,������Ӱ�������
			return ps.executeUpdate();
		} catch (SQLException e) {
			return 0;
		} finally {
			//�ر����ݿ����Ӷ���
			close(null, ps, null);
		}
	}
	/**
	 * ��ȡ����
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConn() throws SQLException{
		return pool.getConnection();
	}
	
	/*���߷���:�ر���Դ*/
	 
	public static void close(Connection conn,Statement stat,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
		if(stat!=null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				stat = null;
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}
}
