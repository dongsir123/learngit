package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class OrderDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.���ն���id
		String oid = request.getParameter("id");
		//2.����ҵ�������ɾ������
		//2.1.����ҵ������
		OrderService orderService = BasicFactory.getFactory().getInstance(OrderService.class);
		try {
			//2.2.����ɾ�������ķ���
			orderService.deleteOrderById(oid);
			//3.ɾ���ɹ�,������ʾ
			response.getWriter().write("<h1 style='text-align:center;color:red'>ɾ���ɹ�</h1>");
		} catch (MsgException e) {
			//3.ɾ��ʧ��,������ʾ
			response.getWriter().write(e.getMessage());
		}
		//4.���ö�ʱ��ת->OrderListServlet
		response.setHeader("refresh","2;url="+request.getContextPath()+"/OrderListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
