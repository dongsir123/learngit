package cn.tedu.dao.impl;

import java.io.FileOutputStream;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.tedu.bean.User;
import cn.tedu.dao.UserDao;

public class XmlUserDaoImpl implements UserDao{
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return 
	 */
	public boolean findUserByUsername(String username) {
		try {
			String xmlpath = XmlUserDaoImpl.class.getClassLoader().getResource("/user.xml").getPath();
			SAXReader reader = new SAXReader();
			Document dom = reader.read(xmlpath);
			Element root = dom.getRootElement();
			Element userEle = (Element) root.selectSingleNode("//user[@username='"+ username +"']");
			User user = null;
			if(userEle != null){
				user = new User();
				user.setUsername(userEle.attributeValue("username"));
				user.setPassword(userEle.attributeValue("password"));
				user.setNickname(userEle.attributeValue("nickname"));
				user.setEmail(userEle.attributeValue("email"));
			}
			return user !=null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 保存用户信息到数据库
	 * @param user
	 */
	public void addUser(User user) {
		try {
			String xmlpath = XmlUserDaoImpl.class.getClassLoader().getResource("/user.xml").getPath();
			SAXReader reader = new SAXReader();
			Document dom = reader.read(xmlpath);
			Element root = dom.getRootElement();
			Element userEle = DocumentHelper.createElement("user");
			userEle.addAttribute("username", user.getUsername());
			userEle.addAttribute("password", user.getPassword());
			userEle.addAttribute("nickname", user.getNickname());
			userEle.addAttribute("email", user.getEmail());
			root.add(userEle);
			XMLWriter writer = new XMLWriter(new FileOutputStream(xmlpath), OutputFormat.createPrettyPrint());
			writer.write(dom);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据用户名和密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	public User findUserByUsernameAndPassword(String username, String password) {
		try {
			String xmlpath = XmlUserDaoImpl.class.getClassLoader().getResource("/user.xml").getPath();
			SAXReader reader = new SAXReader();
			Document dom = reader.read(xmlpath);
			Element root = dom.getRootElement();
			Element userEle = (Element) root.selectSingleNode("//user[@username='"+ username +"' and @password='"+ password +"']");
			User user = null;
			if(userEle != null){
				user = new User();
				user.setUsername(userEle.attributeValue("username"));
				user.setPassword(userEle.attributeValue("password"));
				user.setNickname(userEle.attributeValue("nickname"));
				user.setEmail(userEle.attributeValue("email"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
