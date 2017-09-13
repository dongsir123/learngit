package cn.tedu.dao;

import java.util.List;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;

public interface OrderDao extends Dao{
	
	/**
	 * 向orders表中添加一条记录
	 * @param order:　封装了订单相关的信息对象
	 */
	public void addOrder(Order order);
	
	/**
	 * 向orderitem表中添加一条记录
	 * @param oi: 封装了订单添加信息的对象
	 */
	public void addOrderItem(OrderItem oi);
	
	/**根据用户id查询该用户所有的订单(orders)信息
	 * @param uid: 用户id
	 * @return 该用户所有的订单信息
	 */
	public List<Order> findOrdersByUid(int uid);
	
	/**根据用户id查询该用户下所有的订单条目(orderitem)
	 * @param uid: 用户id
	 * @return 该用户所有的订单条目信息
	 */
	public List<OrderItem> findOrderItemsByUid(int uid);
	
	/**根据订单id查询订单信息(根据orders表)
	 * @param oid: 订单id
	 * @return oid对应订单的信息
	 */
	public Order findOrderById(String oid);
	
	/**通过订单id查询当前订单下的所有订单条目
	 * @param oid: 订单id
	 * @return oid对应订单下所有的订单条目信息
	 */
	public List<OrderItem> findOrderItemsByOrderId(String oid);

	/**根据订单id删除该订单下所有的订单条目
	 * @param oid: 订单id
	 */
	public void deleteOrderItemsByOrderId(String oid);
	
	/**根据订单id删除对应订单的订单信息
	 * @param oid: 订单id
	 */
	public void deleteOrderById(String oid);
	/**查询销售榜单信息
	 * @return 销售榜单信息列表
	 */
	public List<SaleInfo> findSaleList();
	
	/**修改订单的支付状态
	 * @param oid: 订单id
	 * @param i: 支付状态  0未支付   1已支付
	 */
	public void changeOrderPaystate(String oid, int paystate);
	
}
