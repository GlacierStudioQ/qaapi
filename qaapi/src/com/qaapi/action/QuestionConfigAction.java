package com.qaapi.action;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.qaapi.bean.CutPage;
import com.qaapi.bean.FaqEntry;
import com.qaapi.service.FaqEntryService;

@ParentPackage(value = "admin")
@InterceptorRef(value = "questionConfigInterceptorStack")
public class QuestionConfigAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	FaqEntryService faqEntryService;
	
	String question;
	String schemaName;// 此处变量名必须和QaapiStatic里面的静态量值一致
	
	FaqEntry entry;
	CutPage cutPage;

	public String save() throws Exception {
		System.out.println("into save");
		
		JSONObject returnJson = faqEntryService.save(entry);
		getResponseWriter().append(returnJson.toString()).flush();
		
		return NONE;
	}
	
	public String delete() throws Exception {
		System.out.println("into delete");

		JSONObject returnJson = faqEntryService.delete(entry);
		getResponseWriter().append(returnJson.toString()).flush();
		
		return NONE;
	}
	
	public String update() throws Exception {
		System.out.println("into update");

		JSONObject returnJson = faqEntryService.update(entry);
		getResponseWriter().append(returnJson.toString()).flush();
		
		return NONE;
	}
	
	public String findByPage() throws Exception {
		System.out.println("into find by page");

		JSONObject returnJson = faqEntryService.findByPage(cutPage);
		getResponseWriter().append(returnJson.toString()).flush();
		
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
	public FaqEntry getEntry() {
		return entry;
	}
	public void setEntry(FaqEntry entry) {
		this.entry = entry;
	}
	public FaqEntryService getFaqEntryService() {
		return faqEntryService;
	}
	public void setFaqEntryService(FaqEntryService faqEntryService) {
		this.faqEntryService = faqEntryService;
	}
	public CutPage getCutPage() {
		return cutPage;
	}
	public void setCutPage(CutPage cutPage) {
		this.cutPage = cutPage;
	}
	
}