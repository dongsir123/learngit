package cn.tedu.bean;

import cn.tedu.exception.MsgException;
import cn.tedu.utils.WebUtils;

public class User {
	private int id;
	private String username;
	private String password;
	private String password2;
	private String nickname;
	private String email;
	private String valistr;
	private String valistr2;
	
	public String getValistr2() {
		return valistr2;
	}

	public void setValistr2(String valistr2) {
		this.valistr2 = valistr2;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getValistr() {
		return valistr;
	}

	public void setValistr(String valistr) {
		this.valistr = valistr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", password2=" + password2 + ", nickname="
				+ nickname + ", email=" + email + ", valistr=" + valistr + "]";
	}

	/*
	 * У���û�ע����Ϣ
	 */
	public void checkData() throws MsgException{
		// >>�ǿ�У��
		if (WebUtils.isNull(username)) {
			throw new MsgException("�û�������Ϊ��");
		}
		if (WebUtils.isNull(password)) {
			throw new MsgException("���벻��Ϊ��");
		}
		if (WebUtils.isNull(password2)) {
			throw new MsgException("ȷ�����벻��Ϊ��");
		}
		// >>���������Ƿ�һ��У��
		if (!password.equals(password2)) {
			throw new MsgException("�������벻һ��");
		}
		if (WebUtils.isNull(nickname)) {
			throw new MsgException("�ǳƲ���Ϊ��");
		}
		if (WebUtils.isNull(email)) {
			throw new MsgException("���䲻��Ϊ��");
		}
		// >>�����ʽ�Ƿ���ȷ
		String reg = "^\\w+@\\w+(\\.\\w+)+$";
		if (!email.matches(reg)) {
			throw new MsgException("�����ʽ����ȷ");
		}
		if (WebUtils.isNull(valistr)) {
			throw new MsgException("��֤�벻��Ϊ��");
		}
		//>>��֤���Ƿ���ȷ
		
		if (!valistr.equalsIgnoreCase(valistr2)){
			throw new MsgException("��֤�벻��ȷ");
		}
	}
}
