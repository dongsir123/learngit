package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class ProdInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收商品id
		String id = request.getParameter("id");
		//2.创建业务层对象
		ProdService  prodService = BasicFactory.getFactory().getInstance(ProdService.class);
		//3.调用根据id查询商品详细的方法
		Product prod = prodService.findProdById(id);
		//4.将查询结果保存到request作用域中
		request.setAttribute("prod", prod);
		//5.转发到prod_info.jsp
		request.getRequestDispatcher("/prod_info.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
