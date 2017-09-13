package cn.tedu.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class CartAddServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.������Ʒid
		String id = request.getParameter("id");
		//2.����id��ѯ��Ʒ��Ϣ
		ProdService prodService = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod = prodService.findProdById(id);
		//3.��session�л�ȡcart
		Object cartObj = request.getSession().getAttribute("cart");
		//4.����Map
		Map<Product,Integer> cart = null;
		//5.�ж�cartObj�Ƿ�Ϊnull
		if(cartObj!=null){
			cart = (Map<Product,Integer>)cartObj;
		}else{
			cart = new HashMap<Product,Integer>();
			request.getSession().setAttribute("cart", cart);
		}
		//6.�ж�prod��cart�Ƿ����
		if(cart.containsKey(prod)){
			//˵��֮ǰ���
			cart.put(prod, cart.get(prod)+1);
		}else{//˵����һ�ι���
			cart.put(prod, 1);
		}
		//7.��ת��cart.jsp
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
