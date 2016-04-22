package com.qaapi.action;

import org.apache.struts2.convention.annotation.ParentPackage;

@ParentPackage(value = "struts-default")
public class QuestionConfigAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	String username;
	
	public String cfg() {
		System.out.println("into qsc cfg");
		System.out.println("username = " + username);
		return NONE;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
