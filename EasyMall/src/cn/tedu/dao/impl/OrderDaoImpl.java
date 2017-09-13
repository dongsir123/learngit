package cn.tedu.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;
import cn.tedu.dao.OrderDao;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.BeanListHandler;
import cn.tedu.utils.DaoUtils;

public class OrderDaoImpl implements OrderDao {

	public void addOrder(Order order) {
		String sql = "insert into orders(id,money,receiverinfo,"+
			"paystate,ordertime,user_id) values(?,?,?,?,?,?)";
		DaoUtils.update(sql, order.getId(),order.getMoney(),order.getReceiverinfo(),
				order.getPaystate(),order.getOrdertime(),order.getUser_id());
		
	}

	public void addOrderItem(OrderItem oi) {
		String sql = "insert into orderitem(order_id,product_id,buynum) values(?,?,?)";
		DaoUtils.update(sql, oi.getOrder_id(),oi.getProduct_id(),oi.getBuynum());
	}

	public List<Order> findOrdersByUid(int uid) {
		String sql = "select * from orders where user_id=?";
		try {
			return DaoUtils.query(sql, new BeanListHandler<Order>(Order.class), uid);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Order>();
		}
	}

	public List<OrderItem> findOrderItemsByUid(int uid) {
		String sql = "select oi.* from orders od,orderitem oi " +
				"where od.id=oi.order_id and od.user_id=?";
		try {
			return DaoUtils.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), uid);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<OrderItem>();
		}
	}

	public Order findOrderById(String oid) {
		String sql = "select * from orders where id=?";
		try {
			return DaoUtils.query(sql, new BeanHandler<Order>(Order.class), oid);
		} catch (SQLException e) {
			e.printStackTrace();
			return new Order();
		}
	}

	public List<OrderItem> findOrderItemsByOrderId(String oid) {
		String sql = "select * from orderitem where order_id=?";
		try {
			return DaoUtils.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), oid);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<OrderItem>();
		}
	}

	public void deleteOrderItemsByOrderId(String oid) {
		String sql = "delete from orderitem where order_id=?";
		DaoUtils.update(sql, oid);
	}

	public void deleteOrderById(String oid) {
		String sql = "delete from orders where id=?";
		DaoUtils.update(sql, oid);
	}

	public List<SaleInfo> findSaleList() {
		String sql = "select pd.id prod_id,pd.name prod_name,"+
						" sum(oi.buynum) sale_num"+
						" from  products pd,orderitem oi,orders od"+
						" where pd.id=oi.product_id"+
						" and od.id=oi.order_id"+
						" and od.paystate=1"+
						" group by pd.id"+
						" order by sale_num desc"+
						" limit 0,1000";
		try {
			return DaoUtils.query(sql, new BeanListHandler<SaleInfo>(SaleInfo.class));
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<SaleInfo>();
		}
	}

	public void changeOrderPaystate(String oid, int paystate) {
		String sql = "update orders set paystate=? where id=?";
		DaoUtils.update(sql, paystate,oid);
	}
}
