package com.qaapi.interceptor;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import static com.qaapi.util.GlobeStatus.*;

import com.qaapi.util.ReturnJson;

import static com.qaapi.util.QaapiStatic.*;

/**
 * 
 * 用于验证是用问题配置的用户是不是授权用户
 * 
 * @author IceAsh
 *
 */
public class QuestionConfigInterceptor implements Interceptor {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		Boolean isAdmin = (Boolean) ServletActionContext.getRequest().getSession().getAttribute(STATU_IS_ADMIN);
		
		if(isAdmin != null && isAdmin){
			invocation.invoke();
		}else{
			
			JSONObject returnJson = ReturnJson.notok("", "不是管理员", ACCESS_DECLINE_403);
			ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
			ServletActionContext.getResponse().getWriter().append(returnJson.toString()).flush();
		}
		return Action.NONE;
		
	}

	@Override
	public void destroy() {
	}
}
