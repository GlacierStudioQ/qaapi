package com.qaapi.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.qaapi.bean.FaqEntry;
import com.qaapi.kei.QueryMatchKeiService;
import com.qaapi.lcs.QueryMatchLcsService;
import com.qaapi.lucenequery.QueryMatchService;
import com.qaapi.util.FilterAnswersUtil;
import com.qaapi.util.ReturnJson;
import com.qaapi.util.WechatUtil;

import static com.qaapi.util.WechatUtil.*;
import static com.qaapi.util.GlobeStatus.*;


@ParentPackage(value = "struts-default")
public class WechatAnswerAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	public String answer() throws Exception {
		System.out.println("sys => into wechat answer action");
		
		HttpServletRequest req = ServletActionContext.getRequest();
		Map<String, String> xmlMap = WechatUtil.parseReqXml(req);
		
		String question = xmlMap.get(WX_Content);
		
		List<List<FaqEntry>> answersByAllMethod = new ArrayList<List<FaqEntry>>();
		
//		answersByAllMethod.add(QueryMatchService.queryMatch(schemaName, question));
//		answersByAllMethod.add(QueryMatchLcsService.queryMatch(schemaName, question));
//		answersByAllMethod.add(QueryMatchKeiService.queryMatch(schemaName, question));
		
		
		// 计算获得的匹配结果总数，并找出最匹配的结果
		//TODO:  最匹配的结果先定义为字数最相近的结果
		int totalCount = 0;
		
		FaqEntry fitAnswer = FilterAnswersUtil.LengthMaxMatch(answersByAllMethod);
		
		if(totalCount == 0){
			String respXml = WechatUtil.makeRespXml(xmlMap, "未查询到结果");
			getResponseWriter().append(respXml).flush();
		}else{
			
//			String respXml = WechatUtil.makeRespXml(xmlMap, content);
//			getResponseWriter().append(respXml).flush();
		}
		return NONE;
	}
	
	
	//============setter&getter============
}
