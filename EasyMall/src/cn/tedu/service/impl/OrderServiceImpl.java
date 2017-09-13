package cn.tedu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderInfo;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.Product;
import cn.tedu.bean.SaleInfo;
import cn.tedu.dao.OrderDao;
import cn.tedu.dao.ProdDao;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class OrderServiceImpl implements OrderService{
	private OrderDao orderDao = BasicFactory.getFactory().getInstance(OrderDao.class);
	private ProdDao prodDao = BasicFactory.getFactory().getInstance(ProdDao.class);

	public void addOrder(Order order, List<OrderItem> oiList)
			throws MsgException {
		//1.��order�����һ����¼
		orderDao.addOrder(order);
		//2.����oiList
		for(OrderItem oi : oiList){
			//3.������Ʒ��id��ѯ��Ʒ����Ϣ
			Product prod = prodDao.findProdById(oi.getProduct_id());
			//4.�жϿ���Ƿ����
			if(prod.getPnum()<oi.getBuynum()){//��治��
				throw new MsgException("��Ʒ��治��: "+prod.getId()+","+prod.getName()+
						", ʣ����Ϊ: "+prod.getPnum());
			}
			//5.���������,���޸���Ʒ�Ŀ��
			prodDao.changePnum(prod.getId(), prod.getPnum()-oi.getBuynum());
			//6.��orderitem�������һ����¼
			orderDao.addOrderItem(oi);
		}
	}
	public List<OrderInfo> findOrdersByUid(int uid) {
		//1.����һ�����϶���,���ڱ������еĶ�����Ϣ
		List<OrderInfo> result = new ArrayList<OrderInfo>();
		//3.��ѯ��ǰ�û��µ����ж�����Ϣ(orders��)
		List<Order> orderList = orderDao.findOrdersByUid(uid);
		//4.��ѯ��ǰ�û������ж��������Ķ�����Ŀ(orderitem)
		List<OrderItem> itemList = orderDao.findOrderItemsByUid(uid);
		//5.��ѯ��ǰ�û�������Ŀ�µ����в�Ʒ��Ϣ(products)
		List<Product> prodList = prodDao.findProdsByUid(uid);
		//6.����orderList
		for(Order order:orderList){
			//Order��Ӧ��OrderInfo(1V1)
			OrderInfo oinfo = new OrderInfo();
			oinfo.setOrder(order);
			//��ǰ�����µ����ж�����Ŀ
			Map<Product,Integer> map = new HashMap<Product,Integer>();
			for(OrderItem item:itemList){
				//�жϵ�ǰ������Ŀ�Ķ���id�Ƿ��order.getId()��ͬ
				if(item.getOrder_id().equals(order.getId())){
					//����prodList
					for(Product prod:prodList){
						//11.�ж�prod��item��Ӧ����Ʒid��ͬ
						if(prod.getId().equals(item.getProduct_id())){
							map.put(prod, item.getBuynum());
						}
					}
				}
			}
			oinfo.setMap(map);
			//��order��ӵ�resultList��
			result.add(oinfo);
		}
		//2.���ؽ��
		return result;
	}
	public void deleteOrderById(String oid) throws MsgException {
		//1.���ݶ���id��ѯ������Ϣ
		Order order = orderDao.findOrderById(oid);
		//2.����֧��״̬,�������δ֧���Ķ���,�׳��쳣
		if(order.getPaystate()!=0){
			throw new MsgException("ֻ��δ֧���Ķ�������ɾ��");
		}
		//3.���ݶ���id��ѯ��ǰ�����µ����ж�����Ŀ
		List<OrderItem> itemList = orderDao.findOrderItemsByOrderId(oid);
		//4.����itemList,��һ��ԭ��Ʒ�Ŀ��
		for(OrderItem item:itemList){
			prodDao.updatePnum(item.getProduct_id(),item.getBuynum());
		}
		//5.���ݶ���idɾ�����������еĶ�����Ŀ
		orderDao.deleteOrderItemsByOrderId(oid);
		//6.���ݶ���idɾ����Ӧ����
		orderDao.deleteOrderById(oid);
	}
	public List<SaleInfo> findSaleList() {
		return orderDao.findSaleList();
	}
	public Order findOrderById(String oid) {
		return orderDao.findOrderById(oid);
	}
	public void changeOrder(String oid) {
		Order order = orderDao.findOrderById(oid);
		if(order.getPaystate()==0){
			orderDao.changeOrderPaystate(oid,1);
		}
	}
}
