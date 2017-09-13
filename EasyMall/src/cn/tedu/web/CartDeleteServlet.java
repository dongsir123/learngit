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
		//1.������Ʒid
		String id = request.getParameter("id");
		//2.��session�л�ȡcart
		Object cartObj = request.getSession().getAttribute("cart");
		//3.�ж��Ƿ�Ϊnull,���null-->index.jsp
		if(cartObj == null){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}
		//4.��Ϊnull
		Map<Product,Integer> cart = (Map<Product,Integer>)cartObj;
		//5.����Product����prod,��Ϊid���Ը�ֵ
		Product prod = new Product();
		prod.setId(id);
		//6.ִ��ɾ������
		cart.remove(prod);
		//7.��ʾ����ת(��ʱˢ��)
		response.getWriter().write("ɾ���ɹ�!");
		response.setHeader("Refresh","2;url="+request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
