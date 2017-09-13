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
		//1.��ȡid��pnum(����)
		String id = request.getParameter("id");
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		//2.��session�л�ȡ���ﳵ��Ϣ
		Object cartObj = request.getSession().getAttribute("cart");
		//3.Ϊnull,��-->index.jsp
		if(cartObj==null){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}
		//4.��Ϊnull,ǿ������ת��
		Map<Product,Integer> cart = (Map<Product,Integer>)cartObj;
		//5.������Ʒ����,��Ϊid���Ը�ֵ
		Product prod = new Product();
		prod.setId(id);
		//6.ִ���޸Ĳ���
		cart.put(prod, pnum);
		//7.��ʾ����ת
		response.getWriter().write("�޸ĳɹ�,2����Զ���ת");
		response.setHeader("Refresh","2;url="+request.getContextPath()+"/cart.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
