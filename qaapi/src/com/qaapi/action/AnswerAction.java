package com.qaapi.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.qaapi.bean.FaqEntry;
import com.qaapi.lucenequery.QueryMatchService;
import com.qaapi.util.ReturnJson;

import static com.qaapi.memorydb.DataHolder.FAQ_ENTRIES;
import static com.qaapi.util.GlobeStatus.*;

@ParentPackage(value = "struts-default")
public class AnswerAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	String question;
	String schemaName;// 此处变量名必须和QaapiStatic里面的静态量值一致
	
	
	public String answer() throws Exception {
		System.out.println("sys => into answer action");
		
		List<Long> answerIds = QueryMatchService.queryMatch(schemaName, question);
		
		List<FaqEntry> answers = new ArrayList<FaqEntry>();
		Map<Long, FaqEntry> faqEntriesInSchema = FAQ_ENTRIES.get(schemaName);
		
		for (Long id : answerIds) {
			answers.add(faqEntriesInSchema.get(id));
		}
		
		if(answers.size() == 0){
			JSONObject returnJson = ReturnJson.notok("", "未查询到结果", SERVICE_ERROR_500);
			getResponseWriter().append(returnJson.toString()).flush();
		}else{
			JSONArray answersJson = JSONArray.fromObject(answers);
			JSONObject returnJson = ReturnJson.ok(answersJson, "查询成功");
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
