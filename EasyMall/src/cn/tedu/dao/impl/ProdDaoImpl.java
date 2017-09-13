package cn.tedu.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.tedu.bean.Product;
import cn.tedu.dao.Dao;
import cn.tedu.dao.ProdDao;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.BeanListHandler;
import cn.tedu.utils.DaoUtils;

public class ProdDaoImpl implements ProdDao {

	public List<Product> findAll() {
		//1.编写sql语句
		String sql = "select * from products";
		//2.调用方法
		try {
			return DaoUtils.query(sql, new BeanListHandler<Product>(Product.class));
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Product>();
		}
	}

	public int changePnum(String pid, int pnum) {
		//1.编写sql
		String sql = "update products set pnum=? where id=?";
		return DaoUtils.update(sql, pnum, pid);
	}

	public int deleteById(String id) {
		//编写sql语句
		String sql = "delete from products where id=?";
		return DaoUtils.update(sql, id);
	}
	//修改ProdDaoImpl,实现对应的方法(重点和难点)
	public List<Product> findAllByKey(String name, String cate, Double min,
			Double max) {
		String sql = "select * from products "+
			"where name like ? and category like ?";
		BeanListHandler<Product> bhl = new BeanListHandler<Product>(Product.class);
		//min max 
		try {
			if(min==null && max==null){
				return DaoUtils.query(sql, bhl,"%"+name+"%","%"+cate+"%");
			}else if(min!=null && max==null){
				sql = sql+" and price>=?";
				return DaoUtils.query(sql, bhl, "%"+name+"%","%"+cate+"%",min);
			}else if(min==null && max!=null){
				sql = sql+" and price<=?";
				return DaoUtils.query(sql, bhl, "%"+name+"%","%"+cate+"%",max);
			}else{
				sql = sql+" and price>=? and price<=?";
				return DaoUtils.query(sql, bhl, "%"+name+"%","%"+cate+"%",min,max);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Product>();
		}
	}

	public Product findProdById(String id) {
		String sql = "select * from products where id=?";
		try {
			return DaoUtils.query(sql, new BeanHandler<Product>(Product.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Product> findProdsByUid(int uid) {
		String sql = "SELECT prod.* FROM "+
				" orders od,orderitem oi,products prod"+
				" WHERE od.id=oi.order_id"+
				" AND oi.product_id=prod.id"+
				" AND od.user_id=?";
		try {
			return DaoUtils.query(sql, new BeanListHandler<Product>(Product.class), uid);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Product>();
		} 
	}

	public void updatePnum(String id, int pnum) {
		String sql = "update products set pnum=pnum+? where id=?";
		DaoUtils.update(sql, pnum, id);
	}
}
