package com.template.admin.base;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域请求解决方案
 * 需在WEB.XML中加入拦截器
 *   <filter>
 *     <filter-name>cors</filter-name>
 *     <filter-class>com.template.admin.base.CORSFilter</filter-class>
 *   </filter>
 *   <filter-mapping>
 *     <filter-name>cors</filter-name>
 *     <url-pattern>/*</url-pattern>
 *   </filter-mapping>
 */
public class CORSFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String origin = (String) servletRequest.getRemoteHost() + ":" + servletRequest.getRemotePort();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		
	}

}
