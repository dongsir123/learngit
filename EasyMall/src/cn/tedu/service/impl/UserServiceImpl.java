package cn.tedu.service.impl;

import cn.tedu.bean.User;
import cn.tedu.dao.UserDao;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
public class UserServiceImpl implements UserService {
	private UserDao dao = (UserDao) BasicFactory.getFactory().getInstance(UserDao.class);
	
	public void registUser(User user) throws MsgException {
		//1.检查用户名是否存在
		//>>调用dao层的方法检查用户名是否存在
		boolean result = dao.findUserByUsername(user.getUsername());
		if(result){//存在
			throw new MsgException("用户名已存在!");
		}
		
		//2.如果不存在, 将数据存入数据库中
		dao.addUser(user);
	}
	public User loginUser(String username,String password) {
		return dao.findUserByUsernameAndPassword(username, password);
	}
	public boolean hasUser(String username) {
		return dao.findUserByUsername(username);
	}
	
}