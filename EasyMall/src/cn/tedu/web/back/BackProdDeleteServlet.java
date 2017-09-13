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
		//1.������Ʒid
		String id = request.getParameter("id");
		//2.����ҵ������
		ProdService prodService = BasicFactory.getFactory().getInstance(ProdService.class);
		//3.���ø���idɾ����Ʒ�ķ���
		Boolean result = prodService.deleteById(id);
		//4.�ж�ɾ���ɹ�����ʧ��,�����Ӧ����ʾ
		if(result){
			response.getWriter().write("ɾ���ɹ�,2����Զ���ת,���û����ת,"+
		"��<a href='"+request.getContextPath()+"/BackProdListServlet'>����˴�</a>");
		}else{
			response.getWriter().write("ɾ��ʧ��,������Զ���ת,���û����ת,"+
		"��<a href='"+request.getContextPath()+"/BackProdListServlet'>����˴�</a>");
		}
		//5.���ö�ʱˢ��->BackProdListServlet
		response.setHeader("Refresh", "2;url="+request.getContextPath()+"/BackProdListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
