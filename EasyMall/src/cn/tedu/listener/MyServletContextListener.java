package cn.tedu.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().removeAttribute("app");
	}

	public void contextInitialized(ServletContextEvent sce) {
		//getServletContext():��ȡServletContext����
		sce.getServletContext().setAttribute("app", sce.getServletContext().getContextPath());
		
		
	}

}
