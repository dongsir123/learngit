package cn.tedu.service.impl;

import java.util.List;

import cn.tedu.bean.Product;
import cn.tedu.dao.ProdDao;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class ProdServiceImpl implements ProdService {
	private ProdDao prodDao = BasicFactory.getFactory().getInstance(ProdDao.class);
	public List<Product> findAll() {
		return prodDao.findAll();
	}
	public boolean changePnum(String pid, int pnum) {
		int row = prodDao.changePnum(pid,pnum);
		return row>0;
	}
	public Boolean deleteById(String id) {
		int row = prodDao.deleteById(id);
		return row>0;
	}
	public List<Product> findAllByKey(String name, String cate, Double min,
			Double max) {
		return prodDao.findAllByKey(name,cate,min,max);
	}
	public Product findProdById(String id) {
		return prodDao.findProdById(id);
	}
}
