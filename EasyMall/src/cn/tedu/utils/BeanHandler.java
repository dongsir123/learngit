package cn.tedu.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanHandler<T> implements ResultSetHandler<T> {
	private Class<T> clz;
	public BeanHandler(Class<T> clz) {
		this.clz = clz;
	}
	public T handle(ResultSet rs) throws SQLException{
		try {
			if(rs != null && rs.next()){
				//��������User,Product,Order
				T t = clz.newInstance();
				//��rsȡֵ,��Ϊt�����Ը�ֵ
				BeanInfo bi = Introspector.getBeanInfo(clz);
				PropertyDescriptor[] pds = bi.getPropertyDescriptors();
				//������ֵ
				for(int i=0; i<pds.length; i++){
					//��ȡ��ǰ���������װ����������rs.getObject(name);
					//���Ե����Ʊ���Ͷ�Ӧ����ֶε�����һ��
					String name = pds[i].getName();
					//user.setUsername(rs.getString(name));
					//getWriteMethod()���Զ�Ӧ��setXxx(..)
					Method method = pds[i].getWriteMethod();
					//ִ��setXxx()����t��Ӧ�����Ը�ֵ
					try{
						method.invoke(t, rs.getObject(name));
					}catch(Exception e){
						continue;
					}
					
				}
				
				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
