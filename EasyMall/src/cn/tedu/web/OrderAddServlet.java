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
		//1.��session�л�ȡ���ﳵ��Ϣ
		Object cartObj = request.getSession().getAttribute("cart");
		//2.cartObj==null,-->index.jsp//�Ӹ��绰31����,sessionû����Ʒ��
		if(cartObj==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		//3.�ж��Ƿ��¼
		Object userObj = request.getSession().getAttribute("user");
		if(userObj==null){//��ʾδ��¼
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		//4.��ѡ����Ʒ,���ѵ�¼,��ǿ������ת��
		Map<Product,Integer> cart = (Map<Product,Integer>)cartObj;
		//5.����Order����,��Ϊ�ö���ֵ
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setOrdertime(new Date());
		order.setPaystate(0);//0��ʾδ֧��
		order.setReceiverinfo(request.getParameter("receiverinfo"));
		order.setUser_id(((User)userObj).getId());
		double money = 0;
		//6.�������϶���,���浱ǰ���������еĶ�����Ŀ
		List<OrderItem> oiList = new ArrayList<OrderItem>();
		//7.����cart
		for(Map.Entry<Product, Integer> entry:cart.entrySet()){
			OrderItem oi = new OrderItem();
			oi.setOrder_id(order.getId());
			oi.setProduct_id(entry.getKey().getId());
			oi.setBuynum(entry.getValue());
			oiList.add(oi);
			//8.����money
			money += entry.getKey().getPrice()*entry.getValue();
		}
		order.setMoney(money);
		//9.����ҵ��㷽����Ӷ���
		OrderService orderService = BasicFactory.getFactory().getInstance(OrderService.class);
		try {
			orderService.addOrder(order,oiList);
			//��չ��ﳵ
			request.getSession().removeAttribute("cart");
			//10.��ӳɹ�
			request.getRequestDispatcher("/OrderListServlet").forward(request, response);
			
		} catch (MsgException e) {
			//10.���ʧ��
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
