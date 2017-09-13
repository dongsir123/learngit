package cn.tedu.dao;

import cn.tedu.bean.User;
public interface UserDao extends Dao {
	
	/**
	 * 根据用户名查询用户是否存在
	 * @param username 用户名
	 * @return boolean true表示已存在
	 */
	public boolean findUserByUsername(String username);
	/**
	 * 将注册数据存入数据库中
	 * @param user 用户的注册信息
	 */
	public void addUser(User user) ;
	/**
	 * 根据用户名和密码查询用户信息
	 * @param username 用户名
	 * @param password 密码
	 * @return user对象
	 */
	public User findUserByUsernameAndPassword(String username, String password);
}
