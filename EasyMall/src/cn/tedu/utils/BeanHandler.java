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
				//创建对象User,Product,Order
				T t = clz.newInstance();
				//从rs取值,并为t的属性赋值
				BeanInfo bi = Introspector.getBeanInfo(clz);
				PropertyDescriptor[] pds = bi.getPropertyDescriptors();
				//遍历赋值
				for(int i=0; i<pds.length; i++){
					//获取当前对象里面封装的属性名称rs.getObject(name);
					//属性的名称必须和对应表的字段的名称一样
					String name = pds[i].getName();
					//user.setUsername(rs.getString(name));
					//getWriteMethod()属性对应的setXxx(..)
					Method method = pds[i].getWriteMethod();
					//执行setXxx()方法t对应的属性赋值
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
