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
	 * 查询操作
	 * @param sql:sql语句
	 * @param rs:
	 * @return:查询的结果集对象
	 * @throws SQLException
	 */
	public static <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException{
		try {
			//获取数据库连接
			//conn = getConn();
			conn = TranManager.getConn();
			//预编译sql语句,并返回ps对象
				ps = conn.prepareStatement(sql);
			//为占位符赋值
			for(int i=0;i<params.length;i++){
				ps.setObject(i+1, params[i]);
			}
			//执行查询,并返回结果集
			rs = ps.executeQuery();
			//返回封装后的对象
			return rsh.handle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(null,ps,rs);
		}
	}
	/**
	 * 执行CUD操作
	 * @param sql: sql语句
	 * @param params:将传递过来将作为占位符赋值的数组
	 * @return 影响的行数
	 */
	public static int update(String sql,Object... params) {
		try {
			// 获取数据库连接
//			conn = getConn();
			conn = TranManager.getConn();
			// 预编译sql语句,并返回pstat对象
			ps = conn.prepareStatement(sql);
			// 为占位符赋值 ？ 如果没有传递参数params为null还是数据为0
			for (int i = 0; i < params.length; i++) {
				// 传递参数的顺序要和sql语句中占位符的顺序对应
				ps.setObject(i + 1, params[i]);
			}
			// 执行sql语句,并返回影响的行数
			return ps.executeUpdate();
		} catch (SQLException e) {
			return 0;
		} finally {
			//关闭数据库连接对象
			close(null, ps, null);
		}
	}
	/**
	 * 获取连接
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConn() throws SQLException{
		return pool.getConnection();
	}
	
	/*工具方法:关闭资源*/
	 
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
