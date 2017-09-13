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
		//1.接收商品id
		String id = request.getParameter("id");
		//2.根据id查询商品信息
		ProdService prodService = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod = prodService.findProdById(id);
		//3.从session中获取cart
		Object cartObj = request.getSession().getAttribute("cart");
		//4.声明Map
		Map<Product,Integer> cart = null;
		//5.判断cartObj是否为null
		if(cartObj!=null){
			cart = (Map<Product,Integer>)cartObj;
		}else{
			cart = new HashMap<Product,Integer>();
			request.getSession().setAttribute("cart", cart);
		}
		//6.判断prod在cart是否存在
		if(cart.containsKey(prod)){
			//说明之前买过
			cart.put(prod, cart.get(prod)+1);
		}else{//说明第一次购买
			cart.put(prod, 1);
		}
		//7.跳转到cart.jsp
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
