package com.qaapi.action;

import org.apache.struts2.convention.annotation.ParentPackage;

@ParentPackage(value = "struts-default")
public class TokenvalidAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public String valid() {
		System.out.println("into validate");
		return NONE;
	}

}
