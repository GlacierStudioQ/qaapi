package com.qaapi.action;

import java.io.IOException;
import java.io.PrintWriter;


import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.qaapi.util.GlobeStatus;
import com.qaapi.util.ReturnJsonKey;

/**
 * 本类继承ActionSupport
 * 之后的所有Action都要继承此类，其中规定了所有action都可能用到的通用方法
 * @author IceAsh
 *
 */
public class BaseAction extends ActionSupport implements ReturnJsonKey{
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 写入返回前端数据的方法
	 * @return PrintWriter
	 * @throws IOException
	 */
	protected PrintWriter getResponseWriter() throws IOException{
		return ServletActionContext.getResponse().getWriter();
	}
	/**
	 * 判断一个返回给前台的json是成功还是失败
	 * @param json
	 * @return
	 */
	protected boolean isReturnSuccess(JSONObject json){
		if(json.getInt(STATUS) == GlobeStatus.SUCCESS_200){
			return true;
		}else{
			return false;
		}
	}
}
