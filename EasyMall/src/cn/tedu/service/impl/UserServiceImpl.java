package cn.tedu.service.impl;

import cn.tedu.bean.User;
import cn.tedu.dao.UserDao;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
public class UserServiceImpl implements UserService {
	private UserDao dao = (UserDao) BasicFactory.getFactory().getInstance(UserDao.class);
	
	public void registUser(User user) throws MsgException {
		//1.����û����Ƿ����
		//>>����dao��ķ�������û����Ƿ����
		boolean result = dao.findUserByUsername(user.getUsername());
		if(result){//����
			throw new MsgException("�û����Ѵ���!");
		}
		
		//2.���������, �����ݴ������ݿ���
		dao.addUser(user);
	}
	public User loginUser(String username,String password) {
		return dao.findUserByUsernameAndPassword(username, password);
	}
	public boolean hasUser(String username) {
		return dao.findUserByUsername(username);
	}
	
}