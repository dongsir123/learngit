package cn.tedu.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.tedu.bean.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;

public class AutoLoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		//1.δ��½
		if(req.getSession().getAttribute("user")==null){
			//2.��ȡ���е�cookie
			Cookie[] cks = req.getCookies();
			Cookie autoLoginCk = null;
			if(cks!=null){
				//�����ҵ��Զ���½��cookie
				for (int i = 0; i < cks.length; i++) {
					if("autologin".equals(cks[i].getName())){
						autoLoginCk = cks[i];
						break;
					}
				}
			}
			//3.����ҵ���(֮ǰ���ù�����վ��30���Զ���½)
			if(autoLoginCk!=null){
				//��ȡcookie�е��û���������
				String value = URLDecoder.decode(autoLoginCk.getValue(), "utf-8");
				String username = value.split(":")[0];
				String password = value.split(":")[1];
				//5.����ҵ���ķ���,ʵ�ֵ�½�ж�
				//5.1.����ҵ������,��ʹ�ù����ഴ��ʵ����Ķ���
				UserService us = BasicFactory.getFactory().getInstance(UserService.class);
				//5.2���õ�½�ķ���
				User user = us.loginUser(username, password);
				//5.3.�ж�
				if(user!=null){//�û���������ȷ,ʵ�ֵ�½
					req.getSession().setAttribute("user", user);
					
				}
			}
		}
		
		//0.�����Ƿ�ʵ���Զ���½,���ն���Ҫ����
		chain.doFilter(request, response);
		
		
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
