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
		//1.接收订单id
		String oid = request.getParameter("id");
		//2.调用业务层对象的删除方法
		//2.1.创建业务层对象
		OrderService orderService = BasicFactory.getFactory().getInstance(OrderService.class);
		try {
			//2.2.调用删除订单的方法
			orderService.deleteOrderById(oid);
			//3.删除成功,给予提示
			response.getWriter().write("<h1 style='text-align:center;color:red'>删除成功</h1>");
		} catch (MsgException e) {
			//3.删除失败,给予提示
			response.getWriter().write(e.getMessage());
		}
		//4.设置定时跳转->OrderListServlet
		response.setHeader("refresh","2;url="+request.getContextPath()+"/OrderListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
