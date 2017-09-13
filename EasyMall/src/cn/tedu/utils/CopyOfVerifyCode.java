package cn.tedu.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class CopyOfVerifyCode {
	private int base = 30;
	private int width = 4*base;
	private int height = base;
	private int len = 4;
	private String codes = "23456789ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
	private String[] fontNames = {"΢���ź�", "����", "����", "����", "�п�", "����"};
	public void drawImage(OutputStream output){
		Graphics2D g2 = null;
		try {
			//1.����һ��ͼƬ����������
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
			//2.�õ����ƻ���(�õ�����)
			g2 = (Graphics2D) bi.getGraphics();
			
			//3.��ʼ��ͼ
			//>>���ñ�����ɫ
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, width, height);
			
			//>>���û�����ɫ
			g2.setColor(Color.GREEN);
			//g2.setColor(new Color(0, 0, 255));
			
			//xxxxx
			/*
			//>>��ֱ��
			g2.drawLine(0, 0, width, height);
			
			//>>����Բ(Բ)
			g2.drawOval(0, 0, width, height);
			g2.drawOval(0, 0, 30, 30);
			
			//>>������
			g2.setColor(Color.RED);
			g2.drawRect(0, 0, width-1, height-1);
			*/
			
			//>>��������
			g2.setFont(new Font("΢���ź�", Font.BOLD, 22));
			
			//>>���ַ�(��)
//			g2.drawString("��", 30*0+5, height-6);
//			g2.drawString("��", 30*1+5, height-6);
//			g2.drawString("��", 30*2+5, height-6);
//			g2.drawString("��", 30*3+5, height-6);
			for (int i = 0; i < len; i++) {
				//�����������
				g2.setFont(new Font(fontNames[this.getRandom(0, fontNames.length)], Font.BOLD, 22));
				//���������ɫ
				g2.setColor(new Color(this.getRandom(0, 150), this.getRandom(0, 150), this.getRandom(0, 150)));
				
				double theta = this.getRandom(-45, 45) * Math.PI/180;//��ת�Ļ���
				g2.rotate(theta, 30*i+7, height-6);
				
				String code = codes.charAt(this.getRandom(0, codes.length()))+"";
				g2.drawString(code, 30*i+7, height-6);
				
				g2.rotate(-theta, 30*i+7, height-6);
			}
			
			//>>��������
			for (int i = 0; i < 5; i++) {
				//���������ɫ
				g2.setColor(new Color(this.getRandom(0, 150), this.getRandom(0, 150), this.getRandom(0, 150)));
				g2.drawLine(this.getRandom(0, width), this.getRandom(0, height), this.getRandom(0, width), this.getRandom(0, height));
				g2.drawOval(this.getRandom(0, width), this.getRandom(0, height), this.getRandom(0, 7), this.getRandom(0, 7));
			}
			
			//>>��ӱ߿�
			g2.setColor(Color.GRAY);
			g2.drawRect(0, 0, width-1, height-1);
			
			//4.����ͼƬ��ָ����λ��
			ImageIO.write(bi, "JPEG", output);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			//5.�ͷ���Դ
			g2.dispose();
		}
	}
	
	/**
	 * ����ָ����Χ�ڵ������
	 * @param start
	 * @param end
	 * @return 
	 */
	public int getRandom(int start, int end){
		Random random = new Random();
		return random.nextInt(end-start)+start;
	}
	public static void main(String[] args) throws Exception {
		CopyOfVerifyCode vc = new CopyOfVerifyCode();
		vc.drawImage(new FileOutputStream(new File("d:/vc.jpg")));
	}
}
