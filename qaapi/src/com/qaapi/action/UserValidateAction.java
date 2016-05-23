package com.qaapi.action;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import static com.qaapi.util.QaapiStatic.*;

@ParentPackage(value = "struts-default")

@Results({
		@Result(name = RETURN_UNIVERSAL, location = "/index.jsp")
})
public class UserValidateAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	public String universal() throws Exception {
		ServletActionContext.getRequest().getSession().setAttribute(PARAM_DOMAIN, DOMAIN_UNIVERSAL);
		return RETURN_UNIVERSAL;
	}
	
	//============setter&getter============
}
