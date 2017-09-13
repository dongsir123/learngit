package cn.tedu.dao.impl;

import java.sql.SQLException;

import cn.tedu.bean.User;
import cn.tedu.dao.UserDao;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.DaoUtils;

public class UserDaoImpl implements UserDao {

	/**
	 * �����û�����ѯ�û��Ƿ����
	 * 
	 * @param username
	 *            �û���
	 * @return boolean true��ʾ�Ѵ���
	 */
	public boolean findUserByUsername(String username) {
		try {
			User user = DaoUtils.query("select * from user where username=?",
					new BeanHandler<User>(User.class), username);
			return (user != null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		/*
		 * Connection conn = null; PreparedStatement ps = null; ResultSet rs =
		 * null; try { conn = JDBCUtils.getConn(); String sql =
		 * "select * from user where username=?"; ps =
		 * conn.prepareStatement(sql); ps.setString(1, username); rs =
		 * ps.executeQuery(); return rs.next(); } catch (Exception e) {
		 * e.printStackTrace(); throw new RuntimeException(e); } finally {
		 * JDBCUtils.close(conn, ps, rs); }
		 */
	}

	/**
	 * ��ע�����ݴ������ݿ���
	 * 
	 * @param user
	 *            �û���ע����Ϣ
	 */
	public void addUser(User user) {

		// QueryRunner qr = new QueryRunner(DaoUtils.getPool());
		String sql = "insert into user(username,password,nickname,email) " +
				" values(?,?,?,?)";
		DaoUtils.update(
				sql,
				user.getUsername(), 
				user.getPassword(), 
				user.getNickname(),
				user.getEmail());

		/*
		 * Connection conn = null; PreparedStatement ps = null; ResultSet rs =
		 * null; try { conn = JDBCUtils.getConn(); String sql =
		 * "insert into user values(null,?,?,?,?)"; ps =
		 * conn.prepareStatement(sql); ps.setString(1, user.getUsername());
		 * ps.setString(2, user.getPassword()); ps.setString(3,
		 * user.getNickname()); ps.setString(4, user.getEmail());
		 * 
		 * ps.executeUpdate(); } catch (Exception e) { e.printStackTrace();
		 * throw new RuntimeException(e); } finally { JDBCUtils.close(conn, ps,
		 * rs); }
		 */
	}

	/**
	 * �����û����������ѯ�û���Ϣ
	 * 
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return user����
	 */
	public User findUserByUsernameAndPassword(String username, String password) {
		try {
			User user = DaoUtils.query("select * from user where username=? and password=?", 
					new BeanHandler<User>(User.class),username,password);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		/*
		 * Connection conn = null; PreparedStatement ps = null; ResultSet rs =
		 * null; try { conn = JDBCUtils.getConn(); String sql =
		 * "select * from user where username=? and password=?"; ps =
		 * conn.prepareStatement(sql); ps.setString(1, username);
		 * ps.setString(2,password); rs = ps.executeQuery();
		 * if(rs.next()){//�û���������ȷ //��������еĵ�һ�м�¼����װ��һ��JavaBean���󲢷��� User user =
		 * new User(); user.setId(rs.getInt("id"));
		 * user.setUsername(rs.getString("username"));
		 * user.setPassword(rs.getString("password"));
		 * user.setNickname(rs.getString("nickname"));
		 * user.setEmail(rs.getString("email")); return user; }else{//�û������벻��ȷ
		 * return null; } } catch (Exception e) { e.printStackTrace(); throw new
		 * RuntimeException(e); } finally { JDBCUtils.close(conn, ps, rs); }
		 */

	}
}
