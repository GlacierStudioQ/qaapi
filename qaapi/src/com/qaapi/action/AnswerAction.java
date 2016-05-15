package com.qaapi.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.qaapi.bean.FaqEntry;
import com.qaapi.kei.QueryMatchKeiService;
import com.qaapi.lcs.QueryMatchLcsService;
import com.qaapi.lucenequery.QueryMatchService;
import com.qaapi.util.ReturnJson;

import static com.qaapi.util.GlobeStatus.*;

@ParentPackage(value = "struts-default")
public class AnswerAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	String question;
	String schemaName;// 此处变量名必须和QaapiStatic里面的静态量值一致
	
	
	public String answer() throws Exception {
		System.out.println("sys => into answer action");
		
		List<FaqEntry> answers1 = QueryMatchService.queryMatch(schemaName, question);
		List<FaqEntry> answers2 = QueryMatchLcsService.queryMatch(schemaName, question);
		List<FaqEntry> answers3 = QueryMatchKeiService.queryMatch(schemaName, question);
		
		if(answers1.size() == 0 && answers2.size() == 0 && answers3.size() == 0){
			JSONObject returnJson = ReturnJson.notok("", "未查询到结果", SERVICE_ERROR_500);
			getResponseWriter().append(returnJson.toString()).flush();
		}else{
			JSONArray answers1Json = JSONArray.fromObject(answers1);
			JSONArray answers2Json = JSONArray.fromObject(answers2);
			JSONArray answers3Json = JSONArray.fromObject(answers3);
			JSONObject answersAll = new JSONObject();
			answersAll.put("from lucene", answers1Json);
			answersAll.put("from lcs", answers2Json);
			answersAll.put("from kei", answers3Json);
			JSONObject returnJson = ReturnJson.ok(answersAll, "查询成功");
			getResponseWriter().append(returnJson.toString()).flush();
		}
		
		
		
		return NONE;
	}

	//============setter&getter============
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
}
