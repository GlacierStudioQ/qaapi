package com.qaapi.action;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.qaapi.util.ReturnJson;

import static com.qaapi.util.QaapiStatic.*;
import static com.qaapi.util.GlobeStatus.*;
import static com.qaapi.memorydb.DataHolder.*;

@ParentPackage(value = "struts-default")
public class AdminConfigAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	String domainName;
	
	public String adminQuestion() throws IOException {
		
		// 若输入为空
		if(StringUtils.isEmpty(domainName)){
			JSONObject returnJson = ReturnJson.notok("", "域名不能为空", REQ_ERROR_400);
			getResponseWriter().append(returnJson.toString()).flush();
			return NONE;
		}
		// 若输入的domain不存在
		if(!DOMAINS_NAME.contains(domainName)){
			JSONObject returnJson = ReturnJson.notok("", "域名不存在", REQ_ERROR_400);
			getResponseWriter().append(returnJson.toString()).flush();
			return NONE;
		}
		
		ServletActionContext.getRequest().getSession().setAttribute(PARAM_DOMAIN, domainName);
		ServletActionContext.getRequest().getSession().setAttribute(STATU_IS_ADMIN, true);
		
		JSONObject returnJson = ReturnJson.ok("", "验证成功");
		getResponseWriter().append(returnJson.toString()).flush();
		return NONE;
	}

	//============setter&getter============
	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
}
