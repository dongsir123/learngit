package cn.tedu.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;

public class CartEditServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取id和pnum(参数)
		String id = request.getParameter("id");
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		//2.从session中获取购物车信息
		Object cartObj = request.getSession().getAttribute("cart");
		//3.为null,则-->index.jsp
		if(cartObj==null){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}
		//4.不为null,强制类型转换
		Map<Product,Integer> cart = (Map<Product,Integer>)cartObj;
		//5.创建商品对象,并为id属性赋值
		Product prod = new Product();
		prod.setId(id);
		//6.执行修改操作
		cart.put(prod, pnum);
		//7.提示和跳转
		response.getWriter().write("修改成功,2秒后自动跳转");
		response.setHeader("Refresh","2;url="+request.getContextPath()+"/cart.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
