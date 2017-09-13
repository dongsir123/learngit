package cn.tedu.utils;
/**
 * 查询结果为多个对象结果集
 */
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.tedu.bean.User;

public class BeanListHandler<T> implements ResultSetHandler<List<T>> {
	private Class<T> clz;
	public BeanListHandler(Class<T> clz){
		this.clz=clz;
	}
	public List<T> handle(ResultSet rs) throws SQLException{
		List<T> list = new ArrayList<T>();
		try {
			if(rs != null){
				while(rs.next()){
					T t = clz.newInstance();
					BeanInfo bi = Introspector.getBeanInfo(clz);
					PropertyDescriptor[] pds = bi.getPropertyDescriptors();
					for (int i = 0; i < pds.length; i++) {
						//获取属性名称--表中字段名称
						String name = pds[i].getName();
						//获取setXxx(...)
						Method method = pds[i].getWriteMethod();
						try{
							Object obj = null;
							//判断当前的属性类型是否为int
							//pds[i].getPropertyType()==Integer.TYPE
							if(pds[i].getPropertyType()==int.class){
								obj = rs.getInt(name);
							}else{
								obj = rs.getObject(name);
							}
							method.invoke(t, obj);
						}catch(Exception e){
							continue;
						}
					}
					list.add(t);
				}
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
