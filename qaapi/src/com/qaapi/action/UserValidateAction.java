package com.qaapi.action;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import static com.qaapi.util.QaapiStatic.*;

@ParentPackage(value = "struts-default")

@Results({
		@Result(name = RETURN_UNIVERSAL, location = "/universal_qa.jsp")
})
public class UserValidateAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	public String universal() throws Exception {
		ServletActionContext.getRequest().getSession().setAttribute(PARAM_DOMAIN, DOMAIN_UNIVERSAL);
		ServletActionContext.getRequest().getSession().setAttribute(PARAM_SCHEMA, "qaapi");
		return RETURN_UNIVERSAL;
	}
	
	public String njau() throws Exception {
		ServletActionContext.getRequest().getSession().setAttribute(PARAM_DOMAIN, DOMAIN_NJAU);
		ServletActionContext.getRequest().getSession().setAttribute(PARAM_SCHEMA, "qaapi_njau");
		return RETURN_UNIVERSAL;
	}
	
	public String tfwxs() throws Exception {
		ServletActionContext.getRequest().getSession().setAttribute(PARAM_DOMAIN, DOMAIN_TFWXS);
		ServletActionContext.getRequest().getSession().setAttribute(PARAM_SCHEMA, "qaapi_tfwxs");
		return RETURN_UNIVERSAL;
	}
	
	//============setter&getter============
}
