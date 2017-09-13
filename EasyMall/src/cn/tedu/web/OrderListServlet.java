package cn.tedu.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderInfo;
import cn.tedu.bean.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class OrderListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.�ж��û��Ƿ��¼
		Object userObj = request.getSession().getAttribute("user");
		if(userObj==null){//��ʾ�û�δ��¼
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		//2.��ȡ�û�id
		User user = (User)userObj;
		int uid = user.getId();
		//3.��ѯ��ǰ�û����еĶ�����Ϣ(����ҵ��㷽��)
		//3.1�ȴ���ҵ������
		OrderService orderService = BasicFactory.getFactory().getInstance(OrderService.class);
		//3.2����ҵ��㷽��
		List<OrderInfo> list = orderService.findOrdersByUid(uid);
		//4.��list���浽request��������
		request.setAttribute("list", list);
		//5.ת����order_list.jsp
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
