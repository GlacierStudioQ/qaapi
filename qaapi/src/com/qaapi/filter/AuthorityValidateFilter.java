package com.qaapi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import static com.qaapi.util.GlobeStatus.*;

import com.qaapi.util.ReturnJson;

import net.sf.json.JSONObject;
import static com.qaapi.util.QaapiStatic.*;
import static com.qaapi.memorydb.DataHolder.AUTHORITIES;
import static com.qaapi.memorydb.DataHolder.SCHEMAS_NAME;

public class AuthorityValidateFilter implements Filter{
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	/**
	 * 
	 * 验证请求是否是特定的用户发送的
	 * 以及验证他们有没有访问该domain的权限
	 * 同时验证有没有这个domain
	 * 
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		//进行用户验证的action不过滤
		String uri= ((HttpServletRequest)req).getRequestURI();
		if(uri.contains("user-validate") ||
				uri.contains("admin-config")){
			chain.doFilter(req, resp);
			return;
		}

		String schemaName = req.getParameter(PARAM_SCHEMA);
		
		// 先从session中获取domainName，若获取不到则看请求中有没有
		HttpSession session = ((HttpServletRequest)req).getSession();
		String domainName = (String) session.getAttribute(PARAM_DOMAIN);
		if(StringUtils.isEmpty(domainName)){
			domainName = req.getParameter(PARAM_DOMAIN);
		}
		
		if(!AUTHORITIES.containsKey(domainName)){
			JSONObject returnJson = ReturnJson.notok("", "非法用户", ACCESS_DECLINE_403);
			resp.getWriter().append(returnJson.toString()).flush();
			return;
		}
		
		if(!AUTHORITIES.get(domainName).contains(schemaName)){
			JSONObject returnJson = ReturnJson.notok("", "没有访问此schema的权限", REQ_ERROR_400);
			resp.getWriter().append(returnJson.toString()).flush();
			return;
		}
		
		if(!SCHEMAS_NAME.contains(schemaName)){
			JSONObject returnJson = ReturnJson.notok("", "schema不存在", REQ_ERROR_400);
			resp.getWriter().append(returnJson.toString()).flush();
			return;
		}
		
		chain.doFilter(req, resp);
		
	}
	
	@Override
	public void destroy() {}
}
