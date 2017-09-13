package cn.tedu.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class BackAjaxChangePnumServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收参数
		String pid = request.getParameter("pid");
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		//2.创建业务层对象
		ProdService ps = BasicFactory.getFactory().getInstance(ProdService.class);
		//3.调用修改数量的方法
		boolean flag = ps.changePnum(pid,pnum);
		//4.输出结果
		response.getWriter().write(""+flag);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
