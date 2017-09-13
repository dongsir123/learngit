package cn.tedu.service;

import java.util.List;

import cn.tedu.bean.Product;

public interface ProdService extends Service {
	/**��ѯȫ������Ʒ�б�
	 * @return ȫ����Ʒ�ļ��϶���
	 */
	public List<Product> findAll();
	
	/**�޸���Ʒ�Ŀ��
	 * @param pid: ��Ʒid
	 * @param pnum: �޸ĺ����Ʒ����
	 * @return true: �޸ĳɹ� ,false: ��ʾ�޸�ʧ�� 
	 */
	public boolean changePnum(String pid, int pnum);
	/**������Ʒidɾ����Ӧ����Ʒ
	 * @param id: ��Ʒid
	 * @return true:��ʾɾ���ɹ�, false��ʾɾ��ʧ��
	 */
	public Boolean deleteById(String id);
	
	/**���ݹؼ��ʲ�ѯ����������ȫ����Ʒ
	 * @param name:��Ʒ���ƹؼ���
	 * @param cate:��Ʒ����ؼ���
	 * @param min:�۸��������Сֵ
	 * @param max:�۸���������ֵ
	 * @return ���ϲ�ѯ��������Ʒ����
	 */
	public List<Product> findAllByKey(String name, String cate, Double min,
			Double max);
	
	/**������Ʒ��id��ѯ��Ʒ��ϸ��Ϣ
	 * @param id: ��Ʒid
	 * @return ��id��Ӧ����Ʒ��ϸ��Ϣ
	 */
	public Product findProdById(String id);

}
