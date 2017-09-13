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
		//1.未登陆
		if(req.getSession().getAttribute("user")==null){
			//2.获取所有的cookie
			Cookie[] cks = req.getCookies();
			Cookie autoLoginCk = null;
			if(cks!=null){
				//遍历找到自动登陆的cookie
				for (int i = 0; i < cks.length; i++) {
					if("autologin".equals(cks[i].getName())){
						autoLoginCk = cks[i];
						break;
					}
				}
			}
			//3.如果找到了(之前设置过该网站的30天自动登陆)
			if(autoLoginCk!=null){
				//获取cookie中的用户名和密码
				String value = URLDecoder.decode(autoLoginCk.getValue(), "utf-8");
				String username = value.split(":")[0];
				String password = value.split(":")[1];
				//5.调用业务层的方法,实现登陆判断
				//5.1.声明业务层对象,并使用工厂类创建实现类的对象
				UserService us = BasicFactory.getFactory().getInstance(UserService.class);
				//5.2调用登陆的方法
				User user = us.loginUser(username, password);
				//5.3.判断
				if(user!=null){//用户名密码正确,实现登陆
					req.getSession().setAttribute("user", user);
					
				}
			}
		}
		
		//0.无论是否实现自动登陆,最终都需要放行
		chain.doFilter(request, response);
		
		
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
