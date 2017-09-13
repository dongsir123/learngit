package cn.tedu.web;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.tarena.utils.PaymentUtil;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;
import cn.tedu.utils.PropUtils;

public class CallBackServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.���ղ���  ,��ûص���������
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		//1��ʾ�ض���,2��ʾ��Ե�ͨѶ
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// ���У�� --- �ж��ǲ���֧����˾֪ͨ��
		String hmac = request.getParameter("hmac");
		// 2.У�������Ƿ��޸�,true:��ʾδ���޸�   false:���޸�
		//�Լ����������ݽ��м��� --- �Ƚ�֧����˾������hamc
		boolean isNoUpdate = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, PropUtils.getProperty("keyValue"));
		//3.δ�޸ĵĻ�
		if (isNoUpdate) {//δ���޸�
			//�ж��ض������,���ǵ�Ե�ͨѶ������
			// ��Ӧ������Ч
			if (r9_BType.equals("1")) {//�ض���
				// ����ʱʹ��...
				// �޸Ķ�����֧��״̬
				OrderService orderService = BasicFactory.getFactory().getInstance(OrderService.class);
				//��ѯ��ǰ������Ϣ  
				orderService.changeOrder(r6_Order);
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().println("<h1>����ɹ����ȴ��̳ǽ�һ���������ȴ��ջ�...</h1>");
			} else if (r9_BType.equals("2")&& "1".equals(r1_Code)) {
				// ��������Ե� --- ֧����˾֪ͨ��
				System.out.println("����ɹ���");
				// �޸Ķ���״̬ Ϊ�Ѹ���
				OrderService orderService = BasicFactory.getFactory().getInstance(OrderService.class);
				//��ѯ��ǰ�Ķ�����Ϣ
				orderService.changeOrder(r6_Order);
				// �ظ�֧����˾
				response.getWriter().print("success");
			}
		} else {//4.��������
			// ������Ч
			System.out.println("���ݱ��۸ģ�");
			//��������ת�������ݱ����ĵ�ҳ��
		}
	}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
