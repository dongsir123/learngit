package cn.tedu.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
import cn.tedu.utils.MD5Utils;

public class LoginServlet extends HttpServlet {
	private UserService service = BasicFactory.getFactory().getInstance(UserService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.���������������(������������)
		//2.��ȡ�û���½��Ϣ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remname  = request.getParameter("remname");
		
		//3.ȥ��½(����service��ķ������е�½)
		password = MD5Utils.md5(password);
		User user = service.loginUser(username,password);
		
		if(user == null){//�û������벻��ȷ
			request.setAttribute("msg", "�û��������벻��ȷ");
			request.getRequestDispatcher("/login.jsp").forward(request,response);
			return;
		}else{//�û���������ȷ
			//>>��ס�û���
			if("true".equals(remname)){
				Cookie cookie = new Cookie("remname",URLEncoder.encode(username, "utf-8"));
				cookie.setMaxAge(3600*24*30);//���ô��ʱ��Ϊ30��
				cookie.setPath(request.getContextPath()+"/");
				response.addCookie(cookie);
			}else {//ȡ����ס�û���
				Cookie cookie = new Cookie("remname","");
				cookie.setMaxAge(0);//0������ɾ������
				cookie.setPath(request.getContextPath()+"/");
				response.addCookie(cookie);
			}
			//>>��User���󱣴���session����Ϊ��½�ı�ʶ
			request.getSession().setAttribute("user", user);
			//��¼30���Զ���½��cookie����
			if("true".equals(request.getParameter("autologin"))){
				Cookie alck = new Cookie("autologin",URLEncoder.encode(username+":"+password, "utf-8"));
				alck.setPath("/");
				alck.setMaxAge(30*24*3600);
				response.addCookie(alck);
			}
			//>>��½�ɹ���ת����ҳ
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		/*
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConn();
			String sql = "select * from user where username = ? and password = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, username);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			if(rs.next()){//�û���������ȷ
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setNickname(rs.getString("nickname"));
				user.setEmail(rs.getString("email"));
				
				request.getSession().setAttribute("username", username);
//				request.getSession().setAttribute("password", password);
				
				//4.ʵ�ּ�ס�û���
				if("true".equals(remname)){
					Cookie cookie = new Cookie("remname",URLEncoder.encode(username, "utf-8"));
					cookie.setMaxAge(3600*24*30);//���ô��ʱ��Ϊ30��
					cookie.setPath(request.getContextPath()+"/");
					response.addCookie(cookie);
				}else{//ȡ����ס�û���,��Ҫɾ��֮ǰ��Cookie
					Cookie cookie = new Cookie("remname","");
					cookie.setMaxAge(0);//0������ɾ������
					cookie.setPath(request.getContextPath()+"/");
					response.addCookie(cookie);
				}
				
				//5.��½�ɹ���ת����ҳ(�ض���)
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				
			}else{//�û������벻��ȷ
				request.setAttribute("msg", "�û��������벻��ȷ");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally{
			JDBCUtils.close(conn, ps, rs);
		}*/
		
	}	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
