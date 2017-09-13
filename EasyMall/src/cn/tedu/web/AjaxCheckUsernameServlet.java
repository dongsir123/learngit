package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;

public class AjaxCheckUsernameServlet extends HttpServlet {
	private UserService service = BasicFactory.getFactory().getInstance(UserService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.��������
		//response.setContentType("text/html;charset=utf-8");
		//request.setCharacterEncoding("utf-8");
		
		String username = request.getParameter("username");
		
		// >>����û����Ƿ����(����service��÷������)
		boolean result = service.hasUser(username);
		if(result){//�û�������
			response.getWriter().write("�û����Ѵ���");
		}else{//�û���������
			response.getWriter().write("��ϲ,�û�������ʹ��");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
