package cn.tedu.bean;
//一张orders表记录对应一个OrderInfo
import java.util.Map;

public class OrderInfo {
	
	private Order order;
	private Map<Product,Integer> map;
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Map<Product, Integer> getMap() {
		return map;
	}
	public void setMap(Map<Product, Integer> map) {
		this.map = map;
	}
}
