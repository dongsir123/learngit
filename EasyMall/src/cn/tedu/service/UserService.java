package cn.tedu.service;

import cn.tedu.bean.User;
import cn.tedu.dao.UserDao;
import cn.tedu.exception.MsgException;

public interface UserService extends Service {
	/**实现注册功能
	 * throw MsgException
	 */
	public void registUser(User user) throws MsgException;
	/**登陆用户
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	
	public User loginUser(String username, String password) ;
	/**根据用户名查询用户是否存在
	 * @param username
	 * @return
	 */
	public boolean hasUser(String username) ;
}
