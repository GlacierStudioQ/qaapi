package com.qaapi.action;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.qaapi.bean.CutPage;
import com.qaapi.bean.Feedback;
import com.qaapi.service.FeedbackService;


@ParentPackage(value = "struts-default")
public class FeedbackAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	FeedbackService feedbackService;
	
	Feedback feedback;
	String schemaName;// 此处变量名必须和QaapiStatic里面的静态量值一致

	CutPage cutPage;
	
	public String save() throws Exception {

		System.out.println("into save");
		
		JSONObject returnJson = feedbackService.save(feedback);
		getResponseWriter().append(returnJson.toString()).flush();
		
		return NONE;
	}
	
	public String findByPage() throws Exception {
		System.out.println("into find by page");

		JSONObject returnJson = feedbackService.findByPage(cutPage);
		getResponseWriter().append(returnJson.toString()).flush();
		
		return NONE;
	}
	// ============setter&getter============

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
	public CutPage getCutPage() {
		return cutPage;
	}
	
	public void setCutPage(CutPage cutPage) {
		this.cutPage = cutPage;
	}
}
