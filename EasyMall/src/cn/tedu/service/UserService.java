package cn.tedu.service;

import cn.tedu.bean.User;
import cn.tedu.dao.UserDao;
import cn.tedu.exception.MsgException;

public interface UserService extends Service {
	/**ʵ��ע�Ṧ��
	 * throw MsgException
	 */
	public void registUser(User user) throws MsgException;
	/**��½�û�
	 * @param username �û���
	 * @param password ����
	 * @return
	 */
	
	public User loginUser(String username, String password) ;
	/**�����û�����ѯ�û��Ƿ����
	 * @param username
	 * @return
	 */
	public boolean hasUser(String username) ;
}
