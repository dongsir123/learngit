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
		//1.判断用户是否登录
		Object userObj = request.getSession().getAttribute("user");
		if(userObj==null){//表示用户未登录
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		//2.获取用户id
		User user = (User)userObj;
		int uid = user.getId();
		//3.查询当前用户所有的订单信息(调用业务层方法)
		//3.1先创建业务层对象
		OrderService orderService = BasicFactory.getFactory().getInstance(OrderService.class);
		//3.2调用业务层方法
		List<OrderInfo> list = orderService.findOrdersByUid(uid);
		//4.将list保存到request作用域中
		request.setAttribute("list", list);
		//5.转发到order_list.jsp
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
