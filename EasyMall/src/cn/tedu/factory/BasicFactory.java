package cn.tedu.factory;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

import cn.tedu.dao.Dao;
import cn.tedu.service.Service;
import cn.tedu.tran.Tran;
import cn.tedu.utils.TranManager;
public class BasicFactory {
	private static BasicFactory factory= new BasicFactory();
	private static Properties prop = new Properties();
	private BasicFactory(){
		
	}
	public static BasicFactory getFactory(){
		return factory;
	}
	static{
		try {
			String path = BasicFactory.class.getClassLoader().getResource("config.properties").getPath();
			prop.load(new FileInputStream(new File(path)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 创建各种实例
	 */
	@SuppressWarnings("unchecked")
	public <T>T getInstance(Class<T> clazz){//UserDao userService
		if(Service.class.isAssignableFrom(clazz)){
			//业务层
			try {
				String className = prop.getProperty(clazz.getSimpleName());
				Class<T> clz = (Class<T>) Class.forName(className);
				 final T t= (T)clz.newInstance();
				//创建代理对象
				T proxy = (T)Proxy.newProxyInstance(
						clz.getClassLoader(),
						clz.getInterfaces(),
						new InvocationHandler() {
							public Object invoke(Object proxy, Method method, Object[] args)
									throws Throwable {
								//判断methdod对象上是否使用事务的注解
								if(method.isAnnotationPresent(Tran.class)){
									//说明method对应的方法上使用了事务的注解
									Object result = null;
									try{
										//开启事务
										TranManager.startTran();
										result = method.invoke(t, args);
										//提交事务
										TranManager.commitTran();
									}catch(InvocationTargetException e){
										//事务回滚
										TranManager.rollbackTran();
										throw e.getCause();
									}finally{
										//即使不使用事务,在执行完业务方法之后,也要关闭数据库连接
										TranManager.releaseTran();
									}
									return result;
								}else{
									//说明method对应的方法上没有使用了事务的注解
									Object result = null;
									try{
										result = method.invoke(t, args);
									}catch(InvocationTargetException e){
										throw e.getCause();
									}finally {
										TranManager.releaseTran();
									}
									return result;
								}
							}
						});
				return proxy;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else if(Dao.class.isAssignableFrom(clazz)){
			//Dao层
			String className = prop.getProperty(clazz.getSimpleName());
			try {
				Class<T> clz = (Class<T>)Class.forName(className);
				return (T)clz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else{//其它层
			System.out.println("别捣乱,哪凉快哪呆着去...");
			return null;
		}
	}
}
