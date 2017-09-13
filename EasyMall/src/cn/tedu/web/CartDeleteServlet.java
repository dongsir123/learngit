package cn.tedu.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;

public class CartDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收商品id
		String id = request.getParameter("id");
		//2.从session中获取cart
		Object cartObj = request.getSession().getAttribute("cart");
		//3.判断是否为null,如果null-->index.jsp
		if(cartObj == null){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}
		//4.不为null
		Map<Product,Integer> cart = (Map<Product,Integer>)cartObj;
		//5.创建Product对象prod,并为id属性赋值
		Product prod = new Product();
		prod.setId(id);
		//6.执行删除操作
		cart.remove(prod);
		//7.提示和跳转(定时刷新)
		response.getWriter().write("删除成功!");
		response.setHeader("Refresh","2;url="+request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
