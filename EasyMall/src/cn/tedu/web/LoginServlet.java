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
		//1.处理请求参数乱码(过滤器已设置)
		//2.获取用户登陆信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remname  = request.getParameter("remname");
		
		//3.去登陆(调用service层的方法进行登陆)
		password = MD5Utils.md5(password);
		User user = service.loginUser(username,password);
		
		if(user == null){//用户名密码不正确
			request.setAttribute("msg", "用户名或密码不正确");
			request.getRequestDispatcher("/login.jsp").forward(request,response);
			return;
		}else{//用户名密码正确
			//>>记住用户名
			if("true".equals(remname)){
				Cookie cookie = new Cookie("remname",URLEncoder.encode(username, "utf-8"));
				cookie.setMaxAge(3600*24*30);//设置存活时间为30天
				cookie.setPath(request.getContextPath()+"/");
				response.addCookie(cookie);
			}else {//取消记住用户名
				Cookie cookie = new Cookie("remname","");
				cookie.setMaxAge(0);//0会立即删除数据
				cookie.setPath(request.getContextPath()+"/");
				response.addCookie(cookie);
			}
			//>>将User对象保存在session中作为登陆的标识
			request.getSession().setAttribute("user", user);
			//记录30天自动登陆的cookie处理
			if("true".equals(request.getParameter("autologin"))){
				Cookie alck = new Cookie("autologin",URLEncoder.encode(username+":"+password, "utf-8"));
				alck.setPath("/");
				alck.setMaxAge(30*24*3600);
				response.addCookie(alck);
			}
			//>>登陆成功跳转回首页
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
			if(rs.next()){//用户名密码正确
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setNickname(rs.getString("nickname"));
				user.setEmail(rs.getString("email"));
				
				request.getSession().setAttribute("username", username);
//				request.getSession().setAttribute("password", password);
				
				//4.实现记住用户名
				if("true".equals(remname)){
					Cookie cookie = new Cookie("remname",URLEncoder.encode(username, "utf-8"));
					cookie.setMaxAge(3600*24*30);//设置存活时间为30天
					cookie.setPath(request.getContextPath()+"/");
					response.addCookie(cookie);
				}else{//取消记住用户名,需要删除之前的Cookie
					Cookie cookie = new Cookie("remname","");
					cookie.setMaxAge(0);//0会立即删除数据
					cookie.setPath(request.getContextPath()+"/");
					response.addCookie(cookie);
				}
				
				//5.登陆成功跳转回首页(重定向)
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				
			}else{//用户名密码不正确
				request.setAttribute("msg", "用户名或密码不正确");
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
