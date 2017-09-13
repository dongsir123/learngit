package cn.tedu.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.Product;
import cn.tedu.bean.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class OrderAddServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.从session中获取购物车信息
		Object cartObj = request.getSession().getAttribute("cart");
		//2.cartObj==null,-->index.jsp//接个电话31分钟,session没有商品了
		if(cartObj==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		//3.判断是否登录
		Object userObj = request.getSession().getAttribute("user");
		if(userObj==null){//表示未登录
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		//4.已选中商品,并已登录,先强制类型转换
		Map<Product,Integer> cart = (Map<Product,Integer>)cartObj;
		//5.创建Order对象,并为该对象赋值
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setOrdertime(new Date());
		order.setPaystate(0);//0表示未支付
		order.setReceiverinfo(request.getParameter("receiverinfo"));
		order.setUser_id(((User)userObj).getId());
		double money = 0;
		//6.创建集合对象,保存当前订单下所有的订单条目
		List<OrderItem> oiList = new ArrayList<OrderItem>();
		//7.变量cart
		for(Map.Entry<Product, Integer> entry:cart.entrySet()){
			OrderItem oi = new OrderItem();
			oi.setOrder_id(order.getId());
			oi.setProduct_id(entry.getKey().getId());
			oi.setBuynum(entry.getValue());
			oiList.add(oi);
			//8.计算money
			money += entry.getKey().getPrice()*entry.getValue();
		}
		order.setMoney(money);
		//9.调用业务层方法添加订单
		OrderService orderService = BasicFactory.getFactory().getInstance(OrderService.class);
		try {
			orderService.addOrder(order,oiList);
			//清空购物车
			request.getSession().removeAttribute("cart");
			//10.添加成功
			request.getRequestDispatcher("/OrderListServlet").forward(request, response);
			
		} catch (MsgException e) {
			//10.添加失败
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
