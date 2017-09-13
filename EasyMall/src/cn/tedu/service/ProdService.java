package cn.tedu.service;

import java.util.List;

import cn.tedu.bean.Product;

public interface ProdService extends Service {
	/**查询全部的商品列表
	 * @return 全部商品的集合对象
	 */
	public List<Product> findAll();
	
	/**修改商品的库存
	 * @param pid: 商品id
	 * @param pnum: 修改后的商品数量
	 * @return true: 修改成功 ,false: 表示修改失败 
	 */
	public boolean changePnum(String pid, int pnum);
	/**根据商品id删除对应的商品
	 * @param id: 商品id
	 * @return true:表示删除成功, false表示删除失败
	 */
	public Boolean deleteById(String id);
	
	/**根据关键词查询符合条件的全部商品
	 * @param name:商品名称关键字
	 * @param cate:商品分类关键字
	 * @param min:价格区间的最小值
	 * @param max:价格区间的最大值
	 * @return 符合查询条件的商品集合
	 */
	public List<Product> findAllByKey(String name, String cate, Double min,
			Double max);
	
	/**根据商品的id查询商品详细信息
	 * @param id: 商品id
	 * @return 该id对应的商品详细信息
	 */
	public Product findProdById(String id);

}
