package cn.tedu.dao;

import java.util.List;

import cn.tedu.bean.Product;

public interface ProdDao extends Dao{
	/**查询全部的商品列表信息
	 * @return 全部商品列表的集合对象
	 */
	public List<Product> findAll();
	
	/**修改商品的库存数量,返回影响的行数
	 * @param pid: 商品id
	 * @param pnum: 修改后的库存数量
	 * @return 影响的行数
	 */
	public int changePnum(String pid, int pnum);
	/**根据商品id删除指定的商品,返回影响的行数
	 * @param id: 商品id
	 * @return 影响的行数
	 */
	public int deleteById(String id);
	/**根据关键字查询符合条件的全部商品
	 * @param name: 商品名称关键字
	 * @param cate: 商品分类关键字
	 * @param min: 价格区间的最小值
	 * @param max: 价格区间的最大值
	 * @return 符合查询条件的商品集合
	 */
	public List<Product> findAllByKey(String name, String cate, Double min,
			Double max);
	/**根据id查询对应商品的详细信息
	 * @param id: 商品id
	 * @return 该id对应的商品详细信息
	 */
	public Product findProdById(String id);
	/**根据用户id查询当前用户的所有订单下所有商品
	 * @param uid: 用户id
	 * @return uid对应用户所有订单下所有商品
	 */
	public List<Product> findProdsByUid(int uid);
	/**修改商品的库存
	 * @param product_id: 商品id
	 * @param buynum: pid对应商品的库存增加num
	 */
	public void updatePnum(String product_id, int buynum);
	
	
}
