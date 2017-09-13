package cn.tedu.dao;

import cn.tedu.bean.User;
public interface UserDao extends Dao {
	
	/**
	 * �����û�����ѯ�û��Ƿ����
	 * @param username �û���
	 * @return boolean true��ʾ�Ѵ���
	 */
	public boolean findUserByUsername(String username);
	/**
	 * ��ע�����ݴ������ݿ���
	 * @param user �û���ע����Ϣ
	 */
	public void addUser(User user) ;
	/**
	 * �����û����������ѯ�û���Ϣ
	 * @param username �û���
	 * @param password ����
	 * @return user����
	 */
	public User findUserByUsernameAndPassword(String username, String password);
}
