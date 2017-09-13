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
			//1.��������
			//>>�����������
			//request.setCharacterEncoding("utf-8");
			
			//>>��Ӧ��������
			//response.setContentType("text/html;charset=utf-8");
			
			//2.��ȡ�û�ע����Ϣ,�����ݷ�װ��JavaBean
			
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());

			//����̨���Ƶ���֤���ı������User��
			user.setValistr2((String)request.getSession().getAttribute("code"));
			//System.out.println(user);
			
			//3.��������(����javaBean�еķ���У������)
			user.checkData();
			//4.ʵ��ע���û�(����service��÷�������ע��)
			user.setPassword(MD5Utils.md5(user.getPassword()));
			service.registUser(user);
			//5.��ʾ�û�ע��ɹ�,3�����ת��ҳ
			response.getWriter().write("<h1 style='color:red;text-align:center'>��ϲ��ע��ɹ�,3�����ת����ҳ</h1>");
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
		// ��������
		// >>������Ӧ��������
		response.setContentType("text/html;charset=utf-8");
		// >>���������������
		request.setCharacterEncoding("utf-8");
		// 1.��ȡ�û���ע����Ϣ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String valistr = request.getParameter("valistr");
		// TODO
		// 2.У��ע����Ϣ
		// >>�ǿ�У��
		if (WebUtils.isNull(username)) {
			request.setAttribute("msg", "�û�������Ϊ��!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		if (WebUtils.isNull(password)) {
			request.setAttribute("msg", "���벻��Ϊ��!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		if (WebUtils.isNull(nickname)) {
			request.setAttribute("msg", "�ǳƲ���Ϊ��!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		if (WebUtils.isNull(email)) {
			request.setAttribute("msg", "���䲻��Ϊ��!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		if (WebUtils.isNull(valistr)) {
			request.setAttribute("msg", "��֤�벻��Ϊ��!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		// >>���������Ƿ�һ��У��
		if (!password.equals(password2)) {
			request.setAttribute("msg", "�������벻һ��!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		// >>�����ʽ�Ƿ���ȷ
		String reg = "^\\w+@\\w+(\\.\\w+)+$";
		if (!email.matches(reg)) {
			request.setAttribute("msg", "�����ʽ����ȷ!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		// >>��֤���Ƿ���ȷ(��ʱ������!)
		 

		// >>�û����Ƿ����
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConn();
			String sql = "select * from user where username=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) { // �û�������
				request.setAttribute("msg", "�û����Ѵ���!");
				request.getRequestDispatcher("/regist.jsp").forward(request,
						response);
				return;
			} else {// �û���������
					// 3.ע���û�(��ע�����ݱ�������ݿ�)
					// TODO (ʹ��JDBC�����ݱ�����mysql������)
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
		// ��ʾ�û�ע��ɹ�3��֮�󽫻���ת��ҳ
		response.getWriter()
				.write("<h1 style='text-align:center;color:red'>��ϲ��ע��ɹ�, 3��֮����ת����ҳ......</h1>");
		response.setHeader("refresh", "3;url=" + request.getContextPath()
				+ "/index.jsp");

		response.getWriter().write("ע��ɹ�!");
		* 
		*/
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
