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
		//1.向order表添加一条记录
		orderDao.addOrder(order);
		//2.遍历oiList
		for(OrderItem oi : oiList){
			//3.根据商品的id查询商品的信息
			Product prod = prodDao.findProdById(oi.getProduct_id());
			//4.判断库存是否充足
			if(prod.getPnum()<oi.getBuynum()){//库存不足
				throw new MsgException("商品库存不足: "+prod.getId()+","+prod.getName()+
						", 剩余库存为: "+prod.getPnum());
			}
			//5.如果库存充足,则修改商品的库存
			prodDao.changePnum(prod.getId(), prod.getPnum()-oi.getBuynum());
			//6.向orderitem表中添加一条记录
			orderDao.addOrderItem(oi);
		}
	}
	public List<OrderInfo> findOrdersByUid(int uid) {
		//1.定义一个集合对象,用于保存所有的订单信息
		List<OrderInfo> result = new ArrayList<OrderInfo>();
		//3.查询当前用户下的所有订单信息(orders表)
		List<Order> orderList = orderDao.findOrdersByUid(uid);
		//4.查询当前用户下所有订单包含的订单条目(orderitem)
		List<OrderItem> itemList = orderDao.findOrderItemsByUid(uid);
		//5.查询当前用户订单条目下的所有产品信息(products)
		List<Product> prodList = prodDao.findProdsByUid(uid);
		//6.遍历orderList
		for(Order order:orderList){
			//Order对应的OrderInfo(1V1)
			OrderInfo oinfo = new OrderInfo();
			oinfo.setOrder(order);
			//当前订单下的所有订单条目
			Map<Product,Integer> map = new HashMap<Product,Integer>();
			for(OrderItem item:itemList){
				//判断当前订单条目的订单id是否和order.getId()相同
				if(item.getOrder_id().equals(order.getId())){
					//遍历prodList
					for(Product prod:prodList){
						//11.判断prod和item对应的商品id相同
						if(prod.getId().equals(item.getProduct_id())){
							map.put(prod, item.getBuynum());
						}
					}
				}
			}
			oinfo.setMap(map);
			//将order添加到resultList中
			result.add(oinfo);
		}
		//2.返回结果
		return result;
	}
	public void deleteOrderById(String oid) throws MsgException {
		//1.根据订单id查询订单信息
		Order order = orderDao.findOrderById(oid);
		//2.根据支付状态,如果不是未支付的订单,抛出异常
		if(order.getPaystate()!=0){
			throw new MsgException("只有未支付的订单才能删除");
		}
		//3.根据订单id查询当前订单下的所有订单条目
		List<OrderItem> itemList = orderDao.findOrderItemsByOrderId(oid);
		//4.遍历itemList,逐一还原商品的库存
		for(OrderItem item:itemList){
			prodDao.updatePnum(item.getProduct_id(),item.getBuynum());
		}
		//5.根据订单id删除订单下所有的订单条目
		orderDao.deleteOrderItemsByOrderId(oid);
		//6.根据订单id删除对应订单
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
