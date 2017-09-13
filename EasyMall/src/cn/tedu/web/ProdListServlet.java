package cn.tedu.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class ProdListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收参数
		//String 变量名=request.getParameter("name 属性值");
		String nameStr = request.getParameter("name");
		String cateStr = request.getParameter("category");
		String minpriceStr = request.getParameter("minprice");
		String maxpriceStr = request.getParameter("maxprice");
		
		//2.为null处理
		String name = "";
		String cate = "";
		if(nameStr!=null && !"".equals(nameStr.trim())){
			name = nameStr.trim();
		}
		if(cateStr!=null && !"".equals(cateStr.trim())){
			cate = cateStr.trim();
		}
		Double min = null;
		Double max = null;
		if(minpriceStr!=null && !"".equals(minpriceStr.trim())){
			min = Double.parseDouble(minpriceStr);
		}
		if(maxpriceStr!=null && !"".equals(maxpriceStr.trim())){
			max = Double.parseDouble(maxpriceStr);
		}
		//3.创建业务层对象
		ProdService prodService = BasicFactory.getFactory().getInstance(ProdService.class);
		//4.调用查询的方法
		List<Product> list = prodService.findAllByKey(name,cate,min,max);
		//5.将查询关键字和list保存到request作用域中
		request.setAttribute("list", list);
		request.setAttribute("name", name);
		request.setAttribute("cate", cate);
		request.setAttribute("min", min);
		request.setAttribute("max", max);
		//6.转发到prod_list.jsp
		request.getRequestDispatcher("/prod_list.jsp").
		forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
