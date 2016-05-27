package com.qaapi.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.qaapi.bean.Schema;
import com.qaapi.util.ReturnJson;

import static com.qaapi.util.QaapiStatic.*;
import static com.qaapi.util.GlobeStatus.*;
import static com.qaapi.memorydb.DataHolder.*;

@ParentPackage(value = "struts-default")
public class UnlimitedAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 获得当前domain可用的Schema的List
	 * @return
	 * @throws IOException 
	 */
	public String avaliableSchemas() throws IOException{
		String domainName = (String) ServletActionContext.getRequest().getSession().getAttribute(PARAM_DOMAIN);
		if(StringUtils.isEmpty(domainName)){
			JSONObject returnJson = ReturnJson.notok("", "域名不能为空", REQ_ERROR_400);
			getResponseWriter().append(returnJson.toString()).flush();
			return NONE;
		}
		
		List<String> avaliableSchemaNames = AUTHORITIES.get(domainName);
		if(avaliableSchemaNames == null){
			JSONObject returnJson = ReturnJson.notok("", "不存在此域名", REQ_ERROR_400);
			getResponseWriter().append(returnJson.toString()).flush();
			return NONE;
		}
		
		List<Schema> avaliableSchemas = new ArrayList<Schema>();
		for (String schemaName : avaliableSchemaNames) {
			avaliableSchemas.add(SCHEMAS_NAME_INDEX.get(schemaName));
		}
		
		JSONObject returnJson = ReturnJson.ok(JSONArray.fromObject(avaliableSchemas), "成功返回数据");
		getResponseWriter().append(returnJson.toString()).flush();
		
		return NONE;
	}
	
}
