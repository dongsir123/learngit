package cn.tedu.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class BackProdDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收商品id
		String id = request.getParameter("id");
		//2.创建业务层对象
		ProdService prodService = BasicFactory.getFactory().getInstance(ProdService.class);
		//3.调用根据id删除商品的方法
		Boolean result = prodService.deleteById(id);
		//4.判断删除成功还是失败,给予对应的提示
		if(result){
			response.getWriter().write("删除成功,2秒后自动跳转,如果没有跳转,"+
		"请<a href='"+request.getContextPath()+"/BackProdListServlet'>点击此处</a>");
		}else{
			response.getWriter().write("删除失败,两秒后自动跳转,如果没有跳转,"+
		"请<a href='"+request.getContextPath()+"/BackProdListServlet'>点击此处</a>");
		}
		//5.设置定时刷新->BackProdListServlet
		response.setHeader("Refresh", "2;url="+request.getContextPath()+"/BackProdListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
