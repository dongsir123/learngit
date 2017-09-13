package cn.tedu.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodeFilter implements Filter {
	private String encode;
	public void init(FilterConfig config) throws ServletException {
		encode = config.getInitParameter("encode");
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//设置响应的类型
		response.setContentType("text/html;charset=utf-8");
		/*Wrapper: 包装器*/
		chain.doFilter(new MyHSR((HttpServletRequest) request), response);
		
	}	
		class MyHSR extends HttpServletRequestWrapper {
			private HttpServletRequest request;
			Map<String,String[]> map ;
			public MyHSR(HttpServletRequest request) {
				super(request);
				this.request=request;
			}
			public Map<String,String[]> getParameterMap(){
				if(map == null){
					try{
						//判断提交的方式是GET还是POST
						if("POST".equals(request.getMethod())){
							request.setCharacterEncoding(encode);
							map = request.getParameterMap();
							return map;
						}else if("GET".equals(request.getMethod())){
							map = request.getParameterMap();
							Set<Entry<String, String[]>> entrySet = map.entrySet();
							for(Entry<String,String[]> entry: entrySet){
								String[] values = entry.getValue();
								for (int i = 0; i < values.length; i++) {
									values[i] = new String(values[i].getBytes("ISO8859-1"),encode);
								}
							}
							return map;
						}else{
							request.getParameterMap();
						}
					}catch(UnsupportedEncodingException e){
						e.printStackTrace();
					}
					return super.getParameterMap();
				}else{
					return map;
				}
			}	
			public String[] getParameterValues(String name){
				return this.getParameterMap().get(name);
			}
			public String getParameter(String name){
				return this.getParameterValues(name)==null?null:this.getParameterValues(name)[0];
			}
		}
	public void destroy() {
	
	}

}
