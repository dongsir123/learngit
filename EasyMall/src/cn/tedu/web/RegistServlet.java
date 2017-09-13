package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.tedu.bean.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
import cn.tedu.utils.MD5Utils;

public class RegistServlet extends HttpServlet {
	private UserService service = BasicFactory.getFactory().getInstance(UserService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			//1.处理乱码
			//>>请求参数乱码
			//request.setCharacterEncoding("utf-8");
			
			//>>响应正文乱码
			//response.setContentType("text/html;charset=utf-8");
			
			//2.获取用户注册信息,将数据封装到JavaBean
			
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());

			//将后台绘制的验证码文本保存进User中
			user.setValistr2((String)request.getSession().getAttribute("code"));
			//System.out.println(user);
			
			//3.检验数据(调用javaBean中的方法校验数据)
			user.checkData();
			//4.实现注册用户(调用service层得方法进行注册)
			user.setPassword(MD5Utils.md5(user.getPassword()));
			service.registUser(user);
			//5.提示用户注册成功,3秒后跳转首页
			response.getWriter().write("<h1 style='color:red;text-align:center'>恭喜您注册成功,3秒后跳转回首页</h1>");
			response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");
			
		} catch(MsgException e){
			e.getMessage();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		/*
		// 处理乱码
		// >>处理响应正文乱码
		response.setContentType("text/html;charset=utf-8");
		// >>处理请求参数乱码
		request.setCharacterEncoding("utf-8");
		// 1.获取用户的注册信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String valistr = request.getParameter("valistr");
		// TODO
		// 2.校验注册信息
		// >>非空校验
		if (WebUtils.isNull(username)) {
			request.setAttribute("msg", "用户名不能为空!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		if (WebUtils.isNull(password)) {
			request.setAttribute("msg", "密码不能为空!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		if (WebUtils.isNull(nickname)) {
			request.setAttribute("msg", "昵称不能为空!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		if (WebUtils.isNull(email)) {
			request.setAttribute("msg", "邮箱不能为空!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		if (WebUtils.isNull(valistr)) {
			request.setAttribute("msg", "验证码不能为空!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		// >>两次密码是否一致校验
		if (!password.equals(password2)) {
			request.setAttribute("msg", "两次密码不一致!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		// >>邮箱格式是否正确
		String reg = "^\\w+@\\w+(\\.\\w+)+$";
		if (!email.matches(reg)) {
			request.setAttribute("msg", "邮箱格式不正确!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		// >>验证码是否正确(暂时不用做!)
		 

		// >>用户名是否存在
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConn();
			String sql = "select * from user where username=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) { // 用户名存在
				request.setAttribute("msg", "用户名已存在!");
				request.getRequestDispatcher("/regist.jsp").forward(request,
						response);
				return;
			} else {// 用户名不存在
					// 3.注册用户(将注册数据保存进数据库)
					// TODO (使用JDBC将数据保存在mysql数据中)
				sql = "insert into user values(null,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setString(3, nickname);
				ps.setString(4, email);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, ps, rs);
		}
		// 提示用户注册成功3秒之后将会跳转主页
		response.getWriter()
				.write("<h1 style='text-align:center;color:red'>恭喜您注册成功, 3秒之后跳转回首页......</h1>");
		response.setHeader("refresh", "3;url=" + request.getContextPath()
				+ "/index.jsp");

		response.getWriter().write("注册成功!");
		* 
		*/
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
