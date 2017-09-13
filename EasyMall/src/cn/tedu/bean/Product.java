package cn.tedu.bean;

public class Product {

	private String id;//商品id
	private String name;//商品名
	private double price;//价格
	private String category;//分类
	private int pnum;//库存
	private String imgurl;//图片的地址
	private String description;//商品描述
	//Alt+Shift+S->R->Tab->Enter->点击OK
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int hashCode(){
		return id==null?0:id.hashCode();
	}
	public boolean equals(Object obj){
		//this不为null
		if(obj==null){
			return false;
		}
		//this和obj在堆中的地址相同
		if(obj==this){
			return true;
		}
		//判断obj是否为Product类的对象
		if(obj instanceof Product){
			Product prod = (Product)obj;
			return id!=null && this.id.equals(prod.id);
		}
		return false;
	}
}
