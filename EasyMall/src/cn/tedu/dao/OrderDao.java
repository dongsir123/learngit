package cn.tedu.dao;

import java.util.List;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;

public interface OrderDao extends Dao{
	
	/**
	 * ��orders�������һ����¼
	 * @param order:����װ�˶�����ص���Ϣ����
	 */
	public void addOrder(Order order);
	
	/**
	 * ��orderitem�������һ����¼
	 * @param oi: ��װ�˶��������Ϣ�Ķ���
	 */
	public void addOrderItem(OrderItem oi);
	
	/**�����û�id��ѯ���û����еĶ���(orders)��Ϣ
	 * @param uid: �û�id
	 * @return ���û����еĶ�����Ϣ
	 */
	public List<Order> findOrdersByUid(int uid);
	
	/**�����û�id��ѯ���û������еĶ�����Ŀ(orderitem)
	 * @param uid: �û�id
	 * @return ���û����еĶ�����Ŀ��Ϣ
	 */
	public List<OrderItem> findOrderItemsByUid(int uid);
	
	/**���ݶ���id��ѯ������Ϣ(����orders��)
	 * @param oid: ����id
	 * @return oid��Ӧ��������Ϣ
	 */
	public Order findOrderById(String oid);
	
	/**ͨ������id��ѯ��ǰ�����µ����ж�����Ŀ
	 * @param oid: ����id
	 * @return oid��Ӧ���������еĶ�����Ŀ��Ϣ
	 */
	public List<OrderItem> findOrderItemsByOrderId(String oid);

	/**���ݶ���idɾ���ö��������еĶ�����Ŀ
	 * @param oid: ����id
	 */
	public void deleteOrderItemsByOrderId(String oid);
	
	/**���ݶ���idɾ����Ӧ�����Ķ�����Ϣ
	 * @param oid: ����id
	 */
	public void deleteOrderById(String oid);
	/**��ѯ���۰���Ϣ
	 * @return ���۰���Ϣ�б�
	 */
	public List<SaleInfo> findSaleList();
	
	/**�޸Ķ�����֧��״̬
	 * @param oid: ����id
	 * @param i: ֧��״̬  0δ֧��   1��֧��
	 */
	public void changeOrderPaystate(String oid, int paystate);
	
}
