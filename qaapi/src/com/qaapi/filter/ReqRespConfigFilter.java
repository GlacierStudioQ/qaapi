package com.qaapi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ReqRespConfigFilter implements Filter{
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	/**
	 * 设置请求和返回的一些头信息
	 * 比如UTF-8编码
	 * 比如允许跨域
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		resp.setContentType("text/html;charset=UTF-8");
		
//		((HttpServletResponse)resp).addHeader("Access-Control-Allow-Origin", "http://localhost:9000");
//		((HttpServletResponse)resp).addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
//		((HttpServletResponse)resp).addHeader("Access-Control-Allow-Credentials", "true");
//		((HttpServletResponse)resp).addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//		((HttpServletResponse)resp).addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
		
		chain.doFilter(req, resp);
		
	}
	
	@Override
	public void destroy() {}
}
