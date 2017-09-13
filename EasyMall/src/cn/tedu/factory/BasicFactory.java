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
	 * ��������ʵ��
	 */
	@SuppressWarnings("unchecked")
	public <T>T getInstance(Class<T> clazz){//UserDao userService
		if(Service.class.isAssignableFrom(clazz)){
			//ҵ���
			try {
				String className = prop.getProperty(clazz.getSimpleName());
				Class<T> clz = (Class<T>) Class.forName(className);
				 final T t= (T)clz.newInstance();
				//�����������
				T proxy = (T)Proxy.newProxyInstance(
						clz.getClassLoader(),
						clz.getInterfaces(),
						new InvocationHandler() {
							public Object invoke(Object proxy, Method method, Object[] args)
									throws Throwable {
								//�ж�methdod�������Ƿ�ʹ�������ע��
								if(method.isAnnotationPresent(Tran.class)){
									//˵��method��Ӧ�ķ�����ʹ���������ע��
									Object result = null;
									try{
										//��������
										TranManager.startTran();
										result = method.invoke(t, args);
										//�ύ����
										TranManager.commitTran();
									}catch(InvocationTargetException e){
										//����ع�
										TranManager.rollbackTran();
										throw e.getCause();
									}finally{
										//��ʹ��ʹ������,��ִ����ҵ�񷽷�֮��,ҲҪ�ر����ݿ�����
										TranManager.releaseTran();
									}
									return result;
								}else{
									//˵��method��Ӧ�ķ�����û��ʹ���������ע��
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
			//Dao��
			String className = prop.getProperty(clazz.getSimpleName());
			try {
				Class<T> clz = (Class<T>)Class.forName(className);
				return (T)clz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else{//������
			System.out.println("����,�������Ĵ���ȥ...");
			return null;
		}
	}
}
