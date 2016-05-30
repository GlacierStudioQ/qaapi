package com.qaapi.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.qaapi.bean.FaqEntry;
import com.qaapi.kei.QueryMatchKeiService;
import com.qaapi.lcs.QueryMatchLcsService;
import com.qaapi.lucenequery.QueryMatchService;
import com.qaapi.util.FilterAnswersUtil;
import com.qaapi.util.ReturnJson;

import static com.qaapi.util.GlobeStatus.*;

@ParentPackage(value = "struts-default")
public class AnswerAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	String question;
	String schemaName;// 此处变量名必须和QaapiStatic里面的静态量值一致

	public String answer() throws Exception {
		System.out.println("sys => into answer action");
		
		if(StringUtils.isEmpty(question)){
			getResponseWriter().append(ReturnJson.notok("", "问题不能为空", REQ_ERROR_400).toString()).flush();
			return NONE;
		}
		
		List<List<FaqEntry>> answersByAllMethod = new ArrayList<List<FaqEntry>>();
		
		answersByAllMethod.add(QueryMatchService.queryMatch(schemaName, question));
		answersByAllMethod.add(QueryMatchLcsService.queryMatch(schemaName, question));
		answersByAllMethod.add(QueryMatchKeiService.queryMatch(schemaName, question));

		FaqEntry fitAnswer = FilterAnswersUtil.LengthMaxMatch(answersByAllMethod);
		
		JSONObject returnJson = ReturnJson.ok(fitAnswer, "查询成功", question);
		getResponseWriter().append(returnJson.toString()).flush();

		return NONE;
	}

	// ============setter&getter============
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
