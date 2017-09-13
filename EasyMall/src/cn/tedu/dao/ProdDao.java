package cn.tedu.dao;

import java.util.List;

import cn.tedu.bean.Product;

public interface ProdDao extends Dao{
	/**��ѯȫ������Ʒ�б���Ϣ
	 * @return ȫ����Ʒ�б�ļ��϶���
	 */
	public List<Product> findAll();
	
	/**�޸���Ʒ�Ŀ������,����Ӱ�������
	 * @param pid: ��Ʒid
	 * @param pnum: �޸ĺ�Ŀ������
	 * @return Ӱ�������
	 */
	public int changePnum(String pid, int pnum);
	/**������Ʒidɾ��ָ������Ʒ,����Ӱ�������
	 * @param id: ��Ʒid
	 * @return Ӱ�������
	 */
	public int deleteById(String id);
	/**���ݹؼ��ֲ�ѯ����������ȫ����Ʒ
	 * @param name: ��Ʒ���ƹؼ���
	 * @param cate: ��Ʒ����ؼ���
	 * @param min: �۸��������Сֵ
	 * @param max: �۸���������ֵ
	 * @return ���ϲ�ѯ��������Ʒ����
	 */
	public List<Product> findAllByKey(String name, String cate, Double min,
			Double max);
	/**����id��ѯ��Ӧ��Ʒ����ϸ��Ϣ
	 * @param id: ��Ʒid
	 * @return ��id��Ӧ����Ʒ��ϸ��Ϣ
	 */
	public Product findProdById(String id);
	/**�����û�id��ѯ��ǰ�û������ж�����������Ʒ
	 * @param uid: �û�id
	 * @return uid��Ӧ�û����ж�����������Ʒ
	 */
	public List<Product> findProdsByUid(int uid);
	/**�޸���Ʒ�Ŀ��
	 * @param product_id: ��Ʒid
	 * @param buynum: pid��Ӧ��Ʒ�Ŀ������num
	 */
	public void updatePnum(String product_id, int buynum);
	
	
}
