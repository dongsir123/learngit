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
		//1.���ղ���
		//String ������=request.getParameter("name ����ֵ");
		String nameStr = request.getParameter("name");
		String cateStr = request.getParameter("category");
		String minpriceStr = request.getParameter("minprice");
		String maxpriceStr = request.getParameter("maxprice");
		
		//2.Ϊnull����
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
		//3.����ҵ������
		ProdService prodService = BasicFactory.getFactory().getInstance(ProdService.class);
		//4.���ò�ѯ�ķ���
		List<Product> list = prodService.findAllByKey(name,cate,min,max);
		//5.����ѯ�ؼ��ֺ�list���浽request��������
		request.setAttribute("list", list);
		request.setAttribute("name", name);
		request.setAttribute("cate", cate);
		request.setAttribute("min", min);
		request.setAttribute("max", max);
		//6.ת����prod_list.jsp
		request.getRequestDispatcher("/prod_list.jsp").
		forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
