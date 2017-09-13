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
		//1.生成文件名称
		String fname = UUID.randomUUID().toString()+".csv";
		//2.告知浏览器以附件下载的方法进行处理
		response.setHeader("Content-Disposition", "attachment;filename="+fname);
		//3.创建业务层对象
		OrderService os = BasicFactory.getFactory().getInstance(OrderService.class);
		//4.查询销售榜单列表
		List<SaleInfo> list = os.findSaleList();
		//5.拼接字符串
		StringBuffer sbuf = new StringBuffer("商品ID,商品名称,销售数量\n");
		for(SaleInfo info : list){
			sbuf.append(info.getProd_id()+","+info.getProd_name()+","+info.getSale_num()+"\n");
		}
		//6.将内容使用响应的输出流输出
		response.getWriter().write(sbuf.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
