package cn.tedu.bean;

public class Product {

	private String id;//��Ʒid
	private String name;//��Ʒ��
	private double price;//�۸�
	private String category;//����
	private int pnum;//���
	private String imgurl;//ͼƬ�ĵ�ַ
	private String description;//��Ʒ����
	//Alt+Shift+S->R->Tab->Enter->���OK
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
		//this��Ϊnull
		if(obj==null){
			return false;
		}
		//this��obj�ڶ��еĵ�ַ��ͬ
		if(obj==this){
			return true;
		}
		//�ж�obj�Ƿ�ΪProduct��Ķ���
		if(obj instanceof Product){
			Product prod = (Product)obj;
			return id!=null && this.id.equals(prod.id);
		}
		return false;
	}
}
