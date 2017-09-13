package cn.tedu.service;

import java.util.List;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderInfo;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;
import cn.tedu.exception.MsgException;
import cn.tedu.tran.Tran;

public interface OrderService extends Service {
	
	/**添加订单
	 * @param order：封装了订单相关信息对象（向orders表添加的一条信息）
	 * @param oiList：封装了当前订单下所有的订单条目对象（向orderitem表中添加的N条信息）
	 * @throws MsgException购买的商品中存在库存不足时
	 */
	@Tran
	void addOrder(Order order, List<OrderItem> oiList) throws MsgException;
	/**根据用户id查询他所有的订单信息
	 * @param uid用户id
	 * @return 该uid对应用户的所有订单信息
	 */
	List<OrderInfo> findOrdersByUid(int uid);
	/**根据订单id删除订单相关信息，并还原库存
	 * @param oid：订单id
	 * @throws MsgException
	 */
	@Tran
	void deleteOrderById(String oid) throws MsgException;
	
	/**修改订单的支付状态
	 * @param oid：订单id
	 * @param paystate：修改后的支付状态：0:表示未支付 1表示已支付
	 */
	public List<SaleInfo> findSaleList();
	/**修改订单的支付状态
	 * @param oid: 订单id
	 */
	void changeOrder(String oid);
	
}
