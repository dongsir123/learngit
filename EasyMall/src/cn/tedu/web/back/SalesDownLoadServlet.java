package cn.tedu.web.back;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.SaleInfo;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class SalesDownLoadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");
		//1.�����ļ�����
		String fname = UUID.randomUUID().toString()+".csv";
		//2.��֪������Ը������صķ������д���
		response.setHeader("Content-Disposition", "attachment;filename="+fname);
		//3.����ҵ������
		OrderService os = BasicFactory.getFactory().getInstance(OrderService.class);
		//4.��ѯ���۰��б�
		List<SaleInfo> list = os.findSaleList();
		//5.ƴ���ַ���
		StringBuffer sbuf = new StringBuffer("��ƷID,��Ʒ����,��������\n");
		for(SaleInfo info : list){
			sbuf.append(info.getProd_id()+","+info.getProd_name()+","+info.getSale_num()+"\n");
		}
		//6.������ʹ����Ӧ����������
		response.getWriter().write(sbuf.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
