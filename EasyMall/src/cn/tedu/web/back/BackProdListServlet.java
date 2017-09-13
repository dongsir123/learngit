package cn.tedu.web.back;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class BackProdListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.创建业务层对象
		ProdService prodService = BasicFactory.getFactory().getInstance(ProdService.class);
		
		//2.调用查询全部商品的方法
		List<Product> list = prodService.findAll();
		//3.将查询的结果保存到request作用域中
		request.setAttribute("list", list);
		//4.转发到prod_list.jsp
		request.getRequestDispatcher("/back/prod_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
